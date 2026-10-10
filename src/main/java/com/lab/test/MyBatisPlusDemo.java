package com.lab.test;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lab.entity.Emp;
import com.lab.mapper.EmpMapper;
import com.lab.util.MyBatisUtil;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class MyBatisPlusDemo {
    
    /**
     * MyBatis-Plus 方式的分页查询演示
     * 
     * 分页查询：第 1 页，每页 3 条
     */
    @Test
    public void testPage() {
        SqlSession session = MyBatisUtil.openSession();
        try {
            EmpMapper mapper = session.getMapper(EmpMapper.class);
            
            // MyBatis-Plus 分页查询
            Page<Emp> page = new Page<>(1, 3);
            Page<Emp> result = mapper.selectPage(page, null);
            
            System.out.println("总记录数 = " + result.getTotal());
            System.out.println("总页数 = " + result.getPages());
            System.out.println("当前页数据:");
            result.getRecords().forEach(System.out::println);
        } finally {
            session.close();
        }
    }
    
    /**
     * 条件构造器查询演示
     * 使用 Lambda 写法（推荐，防字段名写错）
     */
    @Test
    public void testQueryWrapper() {
        SqlSession session = MyBatisUtil.openSession();
        try {
            EmpMapper mapper = session.getMapper(EmpMapper.class);
            
            // 方法1：XML方式查询
            Emp condition = new Emp();
            condition.setDept("研发部");
            condition.setStatus(1);
            List<Emp> list = mapper.selectByCondition(condition);
            System.out.println("研发部在职员工:");
            list.forEach(System.out::println);
            
            // 方法2：如果使用 MP 的 QueryWrapper
            // QueryWrapper<Emp> wrapper = new QueryWrapper<>();
            // wrapper.eq("dept", "研发部").eq("status", 1);
            // List<Emp> list = mapper.selectList(wrapper);
        } finally {
            session.close();
        }
    }
    
    /**
     * MP 的基本 CRUD 操作演示
     */
    @Test
    public void testBaseCRUD() {
        SqlSession session = MyBatisUtil.openSession();
        try {
            EmpMapper mapper = session.getMapper(EmpMapper.class);
            
            // 插入（自动回填主键）
            Emp emp = new Emp();
            emp.setEmpName("MP测试用户");
            emp.setGender("男");
            emp.setDept("技术部");
            emp.setPost("高级工程师");
            emp.setSalary(new BigDecimal("15000"));
            emp.setHireDate(new Date());
            emp.setStatus(1);
            
            int rows = mapper.insert(emp);
            session.commit();
            System.out.println("插入行数: " + rows);
            System.out.println("回填主键 empId = " + emp.getEmpId());
            
            // 根据 ID 查询
            Emp found = mapper.selectById(emp.getEmpId());
            System.out.println("查询结果: " + found);
            
            // 更新
            emp.setSalary(new BigDecimal("16000"));
            rows = mapper.update(emp);
            session.commit();
            System.out.println("更新行数: " + rows);
            
            // 删除（软删/硬删取决于表结构）
            // 这里用硬删
            rows = mapper.deleteById(emp.getEmpId());
            session.commit();
            System.out.println("删除行数: " + rows);
        } finally {
            session.close();
        }
    }
}