package com.lab.util;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.InputStream;

public class MyBatisUtil {
    private static SqlSessionFactory factory;
    
    static {
        try {
            InputStream in = Resources.getResourceAsStream("mybatis-config.xml");
            factory = new SqlSessionFactoryBuilder().build(in);
        } catch (Exception e) {
            throw new RuntimeException("MyBatisUtil initialization failed", e);
        }
    }
    
    public static SqlSessionFactory getSqlSessionFactory() {
        return factory;
    }
    
    public static SqlSession openSession() {
        return factory.openSession();
    }
}