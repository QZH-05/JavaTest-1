package com.lab.testmp;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lab.entity.Emp;
import com.lab.mapper.EmpMapper;
import com.lab.util.MyBatisUtil;
import org.apache.ibatis.session.SqlSession;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 任务 4：MyBatis-Plus 快速开发与分页演示（main 方法版，便于直接运行观察日志）。
 */
public class MyBatisPlusDemo {

    public static void main(String[] args) {
        MyBatisPlusDemo demo = new MyBatisPlusDemo();
        demo.step43BaseCRUD();
        demo.step44QueryWrapper();
        demo.step45Page();
    }

    /** 4.3 继承 BaseMapper 实现零 SQL 的通用 CRUD */
    public void step43BaseCRUD() {
        System.out.println("\n===== 4.3 通用 CRUD（BaseMapper）=====");
        try (SqlSession session = MyBatisUtil.openSession()) {
            EmpMapper mapper = session.getMapper(EmpMapper.class);

            Emp emp = new Emp();
            emp.setEmpName("MP演示用户");
            emp.setGender("男");
            emp.setDept("技术部");
            emp.setPost("高级工程师");
            emp.setSalary(new BigDecimal("15000"));
            emp.setHireDate(new Date());
            emp.setStatus(1);

            int rows = mapper.insert(emp);
            session.commit();
            System.out.println("insert 影响行数 = " + rows + "，回填主键 empId = " + emp.getEmpId());

            Emp found = mapper.selectById(emp.getEmpId());
            System.out.println("selectById => " + found);

            found.setSalary(new BigDecimal("16000"));
            rows = mapper.updateById(found);
            session.commit();
            System.out.println("updateById 影响行数 = " + rows);

            rows = mapper.deleteById(found.getEmpId());
            session.commit();
            System.out.println("deleteById 影响行数 = " + rows);
        }
    }

    /** 4.4 条件构造器实现多条件查询 */
    public void step44QueryWrapper() {
        System.out.println("\n===== 4.4 条件构造器 =====");
        try (SqlSession session = MyBatisUtil.openSession()) {
            EmpMapper mapper = session.getMapper(EmpMapper.class);

            String gender = "男";
            BigDecimal minSalary = new BigDecimal("8000");

            // 写法一：QueryWrapper（字符串列名）
            QueryWrapper<Emp> wrapper = new QueryWrapper<>();
            wrapper.eq(StringUtils.isNotBlank(gender), "gender", gender)
                   .ge(minSalary != null, "salary", minSalary)
                   .orderByDesc("salary");
            List<Emp> list = mapper.selectList(wrapper);
            System.out.println("QueryWrapper（男，薪资>=8000，按薪资降序）：");
            list.forEach(System.out::println);

            // 写法二：LambdaQueryWrapper（方法引用，防字段名写错，推荐）
            LambdaQueryWrapper<Emp> lw = new LambdaQueryWrapper<>();
            lw.eq(Emp::getStatus, 1)
              .orderByDesc(Emp::getSalary);
            List<Emp> list2 = mapper.selectList(lw);
            System.out.println("LambdaQueryWrapper（在职员工，按薪资降序）：");
            list2.forEach(System.out::println);
        }
    }

    /** 4.5 分页插件配置与分页查询 */
    public void step45Page() {
        System.out.println("\n===== 4.5 分页查询 =====");
        try (SqlSession session = MyBatisUtil.openSession()) {
            EmpMapper mapper = session.getMapper(EmpMapper.class);

            // 第 1 页，每页 3 条
            Page<Emp> page = new Page<>(1, 3);
            Page<Emp> result = mapper.selectPage(page, null);

            System.out.println("总记录数 = " + result.getTotal());
            System.out.println("总页数 = " + result.getPages());
            System.out.println("当前页数据（第 " + result.getCurrent() + " 页）：");
            result.getRecords().forEach(System.out::println);
        }
    }
}