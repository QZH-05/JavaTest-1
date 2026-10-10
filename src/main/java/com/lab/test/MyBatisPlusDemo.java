package com.lab.test;

import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.lab.entity.Emp;
import com.lab.mapper.EmpMapper;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Before;
import org.junit.Test;

import java.io.InputStream;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * MyBatis-Plus 测试演示
 */
public class MyBatisPlusDemo {
    private SqlSessionFactory sqlSessionFactory;

    @Before
    public void setUp() throws Exception {
        InputStream in = Resources.getResourceAsStream("mybatis-config.xml");
        sqlSessionFactory = new SqlSessionFactoryBuilder().build(in);
    }

    @Test
    public void testInsert() {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            EmpMapper mapper = session.getMapper(EmpMapper.class);
            Emp emp = new Emp();
            emp.setEmpName("AI测试员");
            emp.setGender("女");
            emp.setDept("AI部门");
            emp.setPost("算法工程师");
            emp.setSalary(new BigDecimal("15000"));
            emp.setHireDate(new Date());
            emp.setStatus(1);
            
            int rows = mapper.insert(emp);
            session.commit();
            System.out.println("插入行数: " + rows);
            System.out.println("回填主键: " + emp.getEmpId());
        }
    }

    @Test
    public void testSelectList() {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            EmpMapper mapper = session.getMapper(EmpMapper.class);
            List<Emp> list = mapper.selectList(null);
            System.out.println("查询结果:");
            list.forEach(System.out::println);
        }
    }

    @Test
    public void testUpdate() {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            EmpMapper mapper = session.getMapper(EmpMapper.class);
            Emp emp = new Emp();
            emp.setEmpId(1);
            emp.setSalary(new BigDecimal("15000"));
            int rows = mapper.updateById(emp);
            session.commit();
            System.out.println("更新行数: " + rows);
        }
    }
}