package com.jason.black.utils;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.id.IdentifierGenerator;

import java.io.Serializable;

/**
 * Created by fuyongde on 2017/4/8.
 */
public class IdGenerator implements IdentifierGenerator {
    @Override
    public Serializable generate(SessionImplementor session, Object object) throws HibernateException {
        return Identities.genId();
    }
}
