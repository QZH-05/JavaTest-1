package com.lab.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lab.entity.Emp;

/**
 * MyBatis-Plus Mapper 接口
 * 继承 BaseMapper 即获得 insert(deleteById)updateById(selectById)selectList 等方法
 */
public interface EmpMapper extends BaseMapper<Emp> {
    // 继承后可直接使用通用CRUD方法
    // 如需自定义 SQL，可继续添加方法
}