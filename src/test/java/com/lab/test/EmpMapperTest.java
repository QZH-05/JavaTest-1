package com.lab.test;

import com.lab.entity.Emp;
import com.lab.mapper.EmpMapper;
import com.lab.util.MyBatisUtil;
import org.apache.ibatis.session.SqlSession;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

public class EmpMapperTest {
    
    private SqlSession session;
    private EmpMapper mapper;
    
    @Before
    public void setUp() {
        session = MyBatisUtil.openSession();
        mapper = session.getMapper(EmpMapper.class);
    }
    
    @After
    public void tearDown() {
        session.close();
    }
    
    @Test
    public void testSelectAll() {
        List<Emp> list = mapper.selectAll();
        assertNotNull(list);
        assertFalse(list.isEmpty());
        System.out.println("所有员工:");
        list.forEach(System.out::println);
    }
    
    @Test
    public void testSelectById() {
        // 假设 emp_id = 1 的员工存在
        Emp emp = mapper.selectById(1);
        assertNotNull(emp);
        assertEquals("张伟", emp.getEmpName());
        System.out.println("查询到的员工: " + emp);
    }
    
    @Test
    public void testInsert() {
        Emp emp = new Emp();
        emp.setEmpName("陈晨");
        emp.setGender("女");
        emp.setDept("研发部");
        emp.setPost("测试工程师");
        emp.setSalary(new BigDecimal("9000"));
        emp.setHireDate(new Date());
        emp.setStatus(1);
        
        int rows = mapper.insert(emp);
        session.commit();
        
        assertEquals(1, rows);
        assertNotNull(emp.getEmpId()); // 回填主键
        System.out.println("插入后主键 empId = " + emp.getEmpId());
    }
    
    @Test
    public void testUpdate() {
        Emp emp = mapper.selectById(1);
        assertNotNull(emp);
        
        emp.setSalary(new BigDecimal("13000"));
        int rows = mapper.update(emp);
        session.commit();
        
        assertEquals(1, rows);
        
        // 验证更新
        Emp updated = mapper.selectById(1);
        assertEquals(new BigDecimal("13000"), updated.getSalary());
    }
    
    @Test
    public void testDelete() {
        // 先插入一条测试数据
        Emp emp = new Emp();
        emp.setEmpName("删除测试用户");
        emp.setGender("男");
        emp.setDept("测试部");
        emp.setPost("测试");
        emp.setSalary(new BigDecimal("5000"));
        emp.setHireDate(new Date());
        emp.setStatus(1);
        mapper.insert(emp);
        session.commit();
        
        // 测试删除
        int rows = mapper.deleteById(emp.getEmpId());
        session.commit();
        
        assertEquals(1, rows);
        
        // 验证已删除
        Emp deleted = mapper.selectById(emp.getEmpId());
        assertNull(deleted);
    }
    
    @Test
    public void testSelectByCondition() {
        Emp condition = new Emp();
        condition.setDept("研发部");
        condition.setStatus(1);
        
        List<Emp> list = mapper.selectByCondition(condition);
        assertNotNull(list);
        list.forEach(e -> {
            assertEquals("研发部", e.getDept());
            assertEquals(Integer.valueOf(1), e.getStatus());
        });
        System.out.println("按部门和状态筛选结果:");
        list.forEach(System.out::println);
    }
    
    @Test
    public void testUpdateDynamic() {
        // 测试动态更新
        Emp emp = new Emp();
        emp.setEmpId(1);
        emp.setSalary(new BigDecimal("15000")); // 只修改薪资
        
        int rows = mapper.updateDynamic(emp);
        session.commit();
        
        assertEquals(1, rows);
        Emp updated = mapper.selectById(1);
        assertEquals(new BigDecimal("15000"), updated.getSalary());
    }
    
    @Test
    public void testDeleteBatch() {
        // 先插入几条测试数据
        Emp emp1 = new Emp();
        emp1.setEmpName("批量删除1");
        emp1.setGender("男");
        emp1.setDept("测试部");
        emp1.setPost("测试");
        emp1.setSalary(new BigDecimal("3000"));
        emp1.setHireDate(new Date());
        emp1.setStatus(1);
        mapper.insert(emp1);
        
        Emp emp2 = new Emp();
        emp2.setEmpName("批量删除2");
        emp2.setGender("女");
        emp2.setDept("测试部");
        emp2.setPost("测试");
        emp2.setSalary(new BigDecimal("4000"));
        emp2.setHireDate(new Date());
        emp2.setStatus(1);
        mapper.insert(emp2);
        session.commit();
        
        // 批量删除
        List<Integer> ids = Arrays.asList(emp1.getEmpId(), emp2.getEmpId());
        int rows = mapper.deleteBatch(ids);
        session.commit();
        
        assertEquals(2, rows);
    }
    
    @Test
    public void testInsertBatch() {
        // 批量插入
        Emp e1 = new Emp();
        e1.setEmpName("批量插入1");
        e1.setGender("男");
        e1.setDept("批量部");
        e1.setPost("测试");
        e1.setSalary(new BigDecimal("5000"));
        e1.setHireDate(new Date());
        e1.setStatus(1);
        
        Emp e2 = new Emp();
        e2.setEmpName("批量插入2");
        e2.setGender("女");
        e2.setDept("批量部");
        e2.setPost("测试");
        e2.setSalary(new BigDecimal("6000"));
        e2.setHireDate(new Date());
        e2.setStatus(1);
        
        List<Emp> emps = Arrays.asList(e1, e2);
        int rows = mapper.insertBatch(emps);
        session.commit();
        
        assertTrue(rows > 0);
    }
}