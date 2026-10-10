package com.lab.mapper;

import com.lab.entity.Emp;
import org.apache.ibatis.annotations.Param;
import java.util.List;

public interface EmpMapper {
    List<Emp> selectAll();
    Emp selectById(Integer empId);
    int insert(Emp emp);
    int update(Emp emp);
    int deleteById(Integer empId);
    
    // 任务3: 多条件模糊查询
    List<Emp> selectByCondition(Emp emp);
    
    // 动态更新
    int updateDynamic(Emp emp);
    
    // 批量删除
    int deleteBatch(@Param("ids") List<Integer> ids);
    
    // 批量插入
    int insertBatch(@Param("list") List<Emp> emps);
}