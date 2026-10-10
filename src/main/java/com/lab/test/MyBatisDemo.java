package com.lab.test;

import com.lab.entity.Emp;
import com.lab.mapper.EmpMapper;
import com.lab.util.MyBatisUtil;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import java.util.List;

/**
 * 任务 2.4：第一个 MyBatis 程序。
 * 演示 MyBatis 标准执行流程：
 * 读取配置 -> 构建 SqlSessionFactory -> 开启 SqlSession -> 获取 Mapper 代理
 * -> 执行 SQL -> 结果映射 -> 返回 Java 对象 -> 关闭 SqlSession。
 * <p>
 * SqlSessionFactory 的构建已封装在 {@link MyBatisUtil} 中（全局只构建一次）。
 */
public class MyBatisDemo {

    public static void main(String[] args) {
        // 1. 获取全局唯一的 SqlSessionFactory（内部读取 mybatis-config.xml 构建）
        SqlSessionFactory factory = MyBatisUtil.getSqlSessionFactory();

        // 2. 开启 SqlSession（非线程安全，用完必须关闭，try-with-resources 自动关闭）
        try (SqlSession session = factory.openSession()) {
            // 3. 获取 Mapper 动态代理对象
            EmpMapper mapper = session.getMapper(EmpMapper.class);

            // 4. 执行查询并打印结果
            List<Emp> list = mapper.selectAll();
            System.out.println("==== emp 表全部数据（共 " + list.size() + " 条）====");
            list.forEach(System.out::println);
        }
    }
}