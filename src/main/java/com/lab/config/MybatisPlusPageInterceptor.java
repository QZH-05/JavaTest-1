package com.lab.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;

import java.sql.Connection;

/**
 * MyBatis-Plus 分页插件（非 Spring 环境适配）。
 * <p>
 * MybatisPlusInterceptor 通过 {@code addInnerInterceptor(...)} 组织子拦截器，
 * 而 MyBatis 的 &lt;property&gt; 配置无法据字符串实例化子拦截器，
 * 因此这里继承 MybatisPlusInterceptor，在无参构造中追加 PaginationInnerInterceptor，
 * 再在 mybatis-config.xml 中用 &lt;plugin&gt; 注册即可。
 * <p>
 * 注意：MyBatis 的 {@code @Intercepts} 注解不会被继承，必须在本子类上重新声明，
 * 否则启动会话时报 "No @Intercepts annotation was found in interceptor"。
 */
@Intercepts({
        @Signature(type = StatementHandler.class, method = "prepare", args = {Connection.class, Integer.class}),
        @Signature(type = StatementHandler.class, method = "getBoundSql", args = {}),
        @Signature(type = Executor.class, method = "update", args = {MappedStatement.class, Object.class}),
        @Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class}),
        @Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class, CacheKey.class, BoundSql.class})
})
public class MybatisPlusPageInterceptor extends MybatisPlusInterceptor {

    public MybatisPlusPageInterceptor() {
        this.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
    }
}