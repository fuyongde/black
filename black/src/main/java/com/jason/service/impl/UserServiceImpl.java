package com.jason.service.impl;

import com.jason.entity.User;
import com.jason.entity.UsernamePasswordAuth;
import com.jason.exception.ServiceException;
import com.jason.repository.jpa.UserDao;
import com.jason.repository.jpa.UsernamePasswordAuthDao;
import com.jason.service.UserService;
import com.jason.utils.Digests;
import com.jason.utils.Encodes;
import com.jason.utils.Identities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;

/**
 * Created by fuyongde on 2016/11/12.
 */
@Service
public class UserServiceImpl implements UserService {

    public static final String HASH_ALGORITHM = "SHA-1";
    public static final int HASH_INTERATIONS = 1024;
    private static final int SALT_SIZE = 8;

    @Autowired
    private UserDao userDao;
    @Autowired
    private UsernamePasswordAuthDao usernamePasswordAuthDao;

    @Override
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

    /**
     * 设定安全的密码，生成随机的salt并经过1024次 sha-1 hash
     */
    private String entryptPassword(String password, byte[] salt) throws NoSuchAlgorithmException {
        Encodes.encodeHex(salt);
        byte[] hashPassword = Digests.sha1(password.getBytes(), salt, HASH_INTERATIONS);
        return Encodes.encodeHex(hashPassword);
    }
}
