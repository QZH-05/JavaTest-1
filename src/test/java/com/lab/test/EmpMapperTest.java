package com.lab.test;

import com.lab.entity.Emp;
import com.lab.mapper.EmpMapper;
import com.lab.util.MyBatisUtil;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;
import java.math.BigDecimal;
import java.util.Date;

public class EmpMapperTest {

    @Test
    public void testInsert() {
        try (SqlSession session = MyBatisUtil.openSession()) {
            EmpMapper mapper = session.getMapper(EmpMapper.class);
            Emp emp = new Emp();
            emp.setEmpName("陈晨"); emp.setGender("女");
            emp.setDept("研发部"); emp.setPost("测试工程师");
            emp.setSalary(new BigDecimal("9000"));
            emp.setHireDate(new Date()); emp.setStatus(1);
            int rows = mapper.insert(emp);
            session.commit();          // 增删改必须提交事务
            org.junit.Assert.assertEquals(1, rows);
            System.out.println("回填主键 empId = " + emp.getEmpId());
        }
    }

    @Test
    public void testSelectAll() {
        try (SqlSession session = MyBatisUtil.openSession()) {
            EmpMapper mapper = session.getMapper(EmpMapper.class);
            System.out.println("SelectAll:");
            mapper.selectAll().forEach(System.out::println);
        }
    }

    @Test
    public void testUpdate() {
        try (SqlSession session = MyBatisUtil.openSession()) {
            EmpMapper mapper = session.getMapper(EmpMapper.class);
            Emp emp = mapper.selectById(1);
            emp.setSalary(new BigDecimal("13000"));
            int rows = mapper.update(emp);
            session.commit();
            org.junit.Assert.assertEquals(1, rows);
            System.out.println("Update success, new salary: " + mapper.selectById(1).getSalary());
        }
    }

    @Test
    public void testDelete() {
        try (SqlSession session = MyBatisUtil.openSession()) {
            int rows = session.getMapper(EmpMapper.class).deleteById(4);
            session.commit();
            org.junit.Assert.assertEquals(1, rows);
            System.out.println("Delete success");
        }
    }
}