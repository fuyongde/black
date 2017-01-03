package com.jason.black.service.impl;

import com.jason.black.entity.User;
import com.jason.black.entity.UsernamePasswordAuth;
import com.jason.black.exception.ServiceException;
import com.jason.black.repository.jpa.UserDao;
import com.jason.black.repository.jpa.UsernamePasswordAuthDao;
import com.jason.black.service.UserService;
import com.jason.black.utils.Digests;
import com.jason.black.utils.Encodes;
import com.jason.black.utils.Identities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.NoSuchAlgorithmException;

/**
 * Created by fuyongde on 2016/11/12.
 */
@Service
public class UserServiceImpl implements UserService {

    public static final String HASH_ALGORITHM = "SHA-1";
    public static final int HASH_INTERATIONS = 1024;
    private static final int SALT_SIZE = 8;

    private static Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserDao userDao;
    @Autowired
    private UsernamePasswordAuthDao usernamePasswordAuthDao;

    @Override
    @Transactional
    public User register(String username, String password) {
        //先判断用户名是否存在
        UsernamePasswordAuth auth = usernamePasswordAuthDao.findByUsername(username);
        if (auth != null) {
            throw new ServiceException();
        }

        long now = System.currentTimeMillis();
        //保存用户信息
        User user = new User();
        user.setId(Identities.genId());
        user.setName(username);
        user.setCreated(now);
        user.setUpdated(now);
        userDao.save(user);

        byte[] salt = Digests.generateSalt(SALT_SIZE);

        UsernamePasswordAuth usernamePasswordAuth = new UsernamePasswordAuth();
        usernamePasswordAuth.setUserId(user.getId());
        usernamePasswordAuth.setUsername(username);
        try {
            usernamePasswordAuth.setPassword(entryptPassword(password, salt));
        } catch (NoSuchAlgorithmException e) {
            logger.error("Create user error, the Exception message is {}", e.getMessage());
            throw new ServiceException();
        }
        usernamePasswordAuth.setSalt(Encodes.encodeHex(salt));
        usernamePasswordAuth.setCreated(now);
        usernamePasswordAuth.setUpdated(now);
        usernamePasswordAuthDao.save(usernamePasswordAuth);
        return user;
    }

    @Override
    public User getById(String id) {
        return userDao.findOne(id);
    }

    @Override
    public void testServiceException() {
        throw new ServiceException(100000);
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
