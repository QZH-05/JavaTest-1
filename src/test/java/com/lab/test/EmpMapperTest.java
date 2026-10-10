package com.lab.test;

import com.lab.entity.Emp;
import com.lab.mapper.EmpMapper;
import com.lab.util.MyBatisUtil;
import org.apache.ibatis.session.SqlSession;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * 任务 2.6 / 任务 3：EmpMapper 单元测试。
 * 每个测试方法使用独立的 SqlSession，@After 中关闭。
 */
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

    private Emp newEmp(String name, String gender, String dept, String post, String salary) {
        Emp emp = new Emp();
        emp.setEmpName(name);
        emp.setGender(gender);
        emp.setDept(dept);
        emp.setPost(post);
        emp.setSalary(new BigDecimal(salary));
        emp.setHireDate(new Date());
        emp.setStatus(1);
        return emp;
    }

    // ==================== 任务 2：基础 CRUD ====================

    @Test
    public void testSelectAll() {
        List<Emp> list = mapper.selectAll();
        Assert.assertNotNull(list);
        Assert.assertFalse(list.isEmpty());
        System.out.println("selectAll 共 " + list.size() + " 条");
    }

    @Test
    public void testSelectById() {
        Emp emp = mapper.selectById(1);
        Assert.assertNotNull(emp);
        System.out.println("selectById(1) => " + emp);
    }

    @Test
    public void testInsert() {
        Emp emp = newEmp("陈晨", "女", "研发部", "测试工程师", "9000");
        int rows = mapper.insert(emp);
        session.commit();                     // 增删改必须提交事务
        Assert.assertEquals(1, rows);
        Assert.assertNotNull(emp.getEmpId());  // 自增主键回填
        System.out.println("插入成功，回填主键 empId = " + emp.getEmpId());
    }

    @Test
    public void testUpdate() {
        Emp emp = mapper.selectById(1);
        Assert.assertNotNull(emp);
        emp.setSalary(new BigDecimal("13000"));
        int rows = mapper.updateById(emp);    // BaseMapper 提供 updateById
        session.commit();
        Assert.assertEquals(1, rows);
        Assert.assertEquals(0,
                new BigDecimal("13000").compareTo(mapper.selectById(1).getSalary()));
    }

    @Test
    public void testDelete() {
        Emp emp = newEmp("待删除用户", "男", "测试部", "测试", "5000");
        mapper.insert(emp);
        session.commit();

        int rows = mapper.deleteById(emp.getEmpId());
        session.commit();
        Assert.assertEquals(1, rows);
        Assert.assertNull(mapper.selectById(emp.getEmpId()));
    }

    // ==================== 任务 3：动态 SQL ====================

    @Test
    public void testSelectByCondition() {
        Emp condition = new Emp();
        condition.setDept("研发部");
        condition.setStatus(1);
        List<Emp> list = mapper.selectByCondition(condition);
        Assert.assertNotNull(list);
        list.forEach(e -> {
            Assert.assertEquals("研发部", e.getDept());
            Assert.assertEquals(Integer.valueOf(1), e.getStatus());
        });
        System.out.println("按【部门=研发部, 状态=1】筛选出 " + list.size() + " 条");
    }

    @Test
    public void testSelectByConditionTrim() {
        Emp condition = new Emp();
        condition.setGender("男");
        List<Emp> list = mapper.selectByConditionTrim(condition);
        Assert.assertNotNull(list);
        list.forEach(e -> Assert.assertEquals("男", e.getGender()));
        System.out.println("trim 写法按【性别=男】筛选出 " + list.size() + " 条");
    }

    @Test
    public void testUpdateDynamic() {
        Emp emp = new Emp();
        emp.setEmpId(1);
        emp.setSalary(new BigDecimal("15000")); // 只更新薪资，其余字段为 null 不参与 SET
        int rows = mapper.updateDynamic(emp);
        session.commit();
        Assert.assertEquals(1, rows);
        Assert.assertEquals(0,
                new BigDecimal("15000").compareTo(mapper.selectById(1).getSalary()));
    }

    @Test
    public void testDeleteBatch() {
        Emp e1 = newEmp("批量删除1", "男", "测试部", "测试", "3000");
        Emp e2 = newEmp("批量删除2", "女", "测试部", "测试", "4000");
        mapper.insert(e1);
        mapper.insert(e2);
        session.commit();

        int rows = mapper.deleteBatch(Arrays.asList(e1.getEmpId(), e2.getEmpId()));
        session.commit();
        Assert.assertEquals(2, rows);
        System.out.println("批量删除成功，影响 " + rows + " 行");
    }

    @Test
    public void testInsertBatch() {
        Emp e1 = newEmp("批量插入1", "男", "批量部", "测试", "5000");
        Emp e2 = newEmp("批量插入2", "女", "批量部", "测试", "6000");
        int rows = mapper.insertBatch(Arrays.asList(e1, e2));
        session.commit();
        Assert.assertEquals(2, rows);
        System.out.println("批量插入成功，影响 " + rows + " 行");
    }

    @Test
    public void testSelectByChoose() {
        // 优先按姓名精确查询
        Emp byName = new Emp();
        byName.setEmpName("张伟");
        byName.setDept("市场部");           // 同时给了部门，choose 只取第一个满足的 when
        List<Emp> r1 = mapper.selectByChoose(byName);
        Assert.assertTrue(r1.stream().allMatch(e -> "张伟".equals(e.getEmpName())));
        System.out.println("choose 按姓名命中 " + r1.size() + " 条");

        // 姓名不传，回退到部门
        Emp byDept = new Emp();
        byDept.setDept("市场部");
        List<Emp> r2 = mapper.selectByChoose(byDept);
        Assert.assertTrue(r2.stream().allMatch(e -> "市场部".equals(e.getDept())));
        System.out.println("choose 按部门命中 " + r2.size() + " 条");

        // 都不传，走 otherwise（查询全部在职）
        List<Emp> r3 = mapper.selectByChoose(new Emp());
        Assert.assertTrue(r3.stream().allMatch(e -> Integer.valueOf(1).equals(e.getStatus())));
        System.out.println("choose otherwise 命中在职 " + r3.size() + " 条");
    }
}