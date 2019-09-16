package com.github.xjjdog.seckill.demo.security.shiro.session.mgt.eis;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.SessionIdGenerator;

import java.io.Serializable;
import java.util.UUID;

public class JavaUuidSessionIdGenerator implements SessionIdGenerator {

    @Override
    public Serializable generateId(Session session) {
        return UUID.randomUUID().toString().replace("-", "").toUpperCase();
    }

}
