package com.jason.black.service.impl;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.jason.black.domain.entity.PasswordAuth;
import com.jason.black.domain.entity.User;
import com.jason.black.exception.ServiceException;
import com.jason.black.manager.MailManager;
import com.jason.black.repository.jpa.PasswordAuthDAO;
import com.jason.black.repository.jpa.UserDAO;
import com.jason.black.service.UserService;
import com.jason.black.utils.Clock;
import com.jason.black.utils.Digests;
import com.jason.black.utils.Encodes;
import org.apache.commons.lang3.RandomUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.NoSuchAlgorithmException;
import java.util.Objects;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * Created by fuyongde on 2016/11/12.
 */
@Service
public class UserServiceImpl implements UserService {

    public static final String HASH_ALGORITHM = "SHA-1";
    public static final int HASH_INTERATIONS = 1024;
    private static final int SALT_SIZE = 8;

    private static Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
    private static Clock clock = Clock.DEFAULT;

    private static Cache<String, Integer> authCodeCache = CacheBuilder.newBuilder().expireAfterAccess(5, TimeUnit.MINUTES).build();

    @Autowired
    private UserDAO userDAO;
    @Autowired
    private PasswordAuthDAO passwordAuthDAO;
    @Autowired
    private MailManager mailManager;

    @Override
    @Transactional
    public User register(String username, String password) {
        //先判断用户名是否存在
        PasswordAuth auth = passwordAuthDAO.findByUsername(username);
        if (Objects.nonNull(auth)) {
            throw new ServiceException(200001);
        }

        long now = clock.getCurrentTimeInMillis();
        //保存用户信息
        User user = new User();
        //user.setId(Identities.genId());
        user.setName(username);
        user.setCreated(now);
        user.setUpdated(now);
        user.setStatus(User.UserStatus.normal.getStatus());
        userDAO.save(user);

        byte[] salt = Digests.generateSalt(SALT_SIZE);

        PasswordAuth passwordAuth = new PasswordAuth();
        passwordAuth.setUserId(user.getId());
        passwordAuth.setUsername(username);
        try {
            passwordAuth.setPassword(entryptPassword(password, salt));
        } catch (NoSuchAlgorithmException e) {
            logger.error("Create user error, the Exception message is {}", e.getMessage());
            throw new ServiceException();
        }
        passwordAuth.setSalt(Encodes.encodeHex(salt));
        passwordAuth.setCreated(now);
        passwordAuth.setUpdated(now);
        passwordAuthDAO.save(passwordAuth);
        return user;
    }

    @Override
    public User getById(String id) {
        return userDAO.findOne(id);
    }

    @Override
    @Transactional
    public void sendAuthMail(String userId, String email) {
        User user = getById(userId);
        if (Objects.isNull(user)) {
            //用户不存在
            throw new ServiceException(200000);
        }
        if (user.getStatus() != User.UserStatus.normal.getStatus()) {
            //用户已认证
            throw new ServiceException(200002);
        }
        int authCode = RandomUtils.nextInt(1000, 9999);
        authCodeCache.put(userId, authCode);
        user.setEmail(email);
        this.update(user);
        mailManager.sendActivationCode(email, authCode);
    }

    @Override
    @Transactional
    public void auth(String userId, Integer code) {
        User user = getById(userId);
        if (Objects.isNull(user)) {
            //用户不存在
            throw new ServiceException(200000);
        }
        if (user.getStatus() != User.UserStatus.normal.getStatus()) {
            //用户已认证
            throw new ServiceException(200002);
        }
        try {
            Integer authCode = authCodeCache.get(userId, () -> 0);
            if (Objects.isNull(authCode) || authCode.intValue() != code.intValue()) {
                throw new ServiceException(200003);
            }
            user.setStatus(User.UserStatus.authed.getStatus());
            this.update(user);
            authCodeCache.invalidate(userId);
        } catch (ExecutionException e) {
            throw new ServiceException(200003);
        }
    }

    private void update(User user) {
        long now = clock.getCurrentTimeInMillis();
        user.setUpdated(now);
        userDAO.save(user);
    }

    /**
     * 设定安全的密码，生成随机的salt并经过1024次 sha-1 hash
     */
    private String entryptPassword(String password, byte[] salt) throws NoSuchAlgorithmException {
        Encodes.encodeHex(salt);
        byte[] hashPassword = Digests.sha1(password.getBytes(), salt, HASH_INTERATIONS);
        return Encodes.encodeHex(hashPassword);
    }
}
