package com.lab.util;

import com.baomidou.mybatisplus.core.MybatisSqlSessionFactoryBuilder;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import java.io.InputStream;

/**
 * MyBatis 工具类：全局唯一地构建 SqlSessionFactory，避免重复创建。
 * <p>
 * 任务 4 起项目使用 MyBatis-Plus，必须用 MP 提供的
 * {@link MybatisSqlSessionFactoryBuilder} 读取配置，
 * 否则 BaseMapper 的通用方法不会被注入，运行时报
 * "Invalid bound statement (not found)"。
 */
public class MyBatisUtil {

    private static final SqlSessionFactory FACTORY;

    static {
        try (InputStream in = Resources.getResourceAsStream("mybatis-config.xml")) {
            // MP 的 Builder 会使用 MybatisConfiguration / MybatisXMLConfigBuilder，
            // 从而在解析 mapper 时注入 BaseMapper 的通用 CRUD 语句。
            FACTORY = new MybatisSqlSessionFactoryBuilder().build(in);
        } catch (Exception e) {
            throw new ExceptionInInitializerError("初始化 SqlSessionFactory 失败: " + e.getMessage());
        }
    }

    private MyBatisUtil() {
    }

    public static SqlSessionFactory getSqlSessionFactory() {
        return FACTORY;
    }

    /** 打开一个 SqlSession（默认不自动提交事务） */
    public static SqlSession openSession() {
        return FACTORY.openSession();
    }

    /** 打开一个自动提交事务的 SqlSession */
    public static SqlSession openSession(boolean autoCommit) {
        return FACTORY.openSession(autoCommit);
    }
}