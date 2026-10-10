package com.lab.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;

/**
 * MyBatis-Plus 分页插件（非 Spring 环境适配）。
 * <p>
 * MybatisPlusInterceptor 内部通过 {@code addInnerInterceptor(...)} 组织子拦截器。
 * 实验指导书给出的 XML 写法（&lt;property name="@...PaginationInnerInterceptor"/&gt;）
 * 在 MP 3.5.x 中无法为子拦截器实例化对象，因此这里继承 MybatisPlusInterceptor，
 * 在无参构造中追加 PaginationInnerInterceptor，
 * 这样就能在 mybatis-config.xml 中通过 &lt;plugin&gt; 正常注册（详见实验报告问题二）。
 */
public class MybatisPlusPageInterceptor extends MybatisPlusInterceptor {

    public MybatisPlusPageInterceptor() {
        this.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
    }
}