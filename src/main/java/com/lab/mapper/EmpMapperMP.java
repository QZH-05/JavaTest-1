package com.lab.test;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lab.entity.Emp;
import org.apache.ibatis.annotations.Mapper;

import java.math.BigDecimal;
import java.util.List;

/**
 * MyBatis-Plus Mapper 接口演示
 * 继承 BaseMapper 即获得 insert/deleteById/updateById/selectById/selectList 等方法
 * 
 * 实际项目中可以保留 XML，或用 MP 的 Mapper 接口替代
 */
public interface EmpMapper extends BaseMapper<Emp> {
    // 实际开发中可以使用 MP 自动生成的 CRUD 方法
    // 此处演示 MP 常用写法
    
    // 多条件查询演示
    default List<Emp> selectByConditionMP(Emp emp) {
        QueryWrapper<Emp> wrapper = new QueryWrapper<>();
        wrapper.like(emp.getEmpName() != null && !emp.getEmpName().isEmpty(), "emp_name", emp.getEmpName())
               .eq(emp.getGender() != null && !emp.getGender().isEmpty(), "gender", emp.getGender())
               .eq(emp.getDept() != null && !emp.getDept().isEmpty(), "dept", emp.getDept())
               .eq(emp.getStatus() != null, "status", emp.getStatus());
        return null; // 实际调用: this.selectList(wrapper)
    }
    
    // 分页查询演示
    default Page<Emp> selectPageMP(int pageNum, int pageSize, String name) {
        Page<Emp> page = new Page<>(pageNum, pageSize);
        QueryWrapper<Emp> wrapper = new QueryWrapper<>();
        wrapper.like(name != null && !name.isEmpty(), "emp_name", name)
               .eq("status", 1)
               .orderByDesc("salary");
        return null; // 实际调用: this.selectPage(page, wrapper)
    }
}