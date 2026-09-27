package com.lab.mapper;

import com.lab.entity.Emp;
import java.util.List;

public interface EmpMapper {
    List<Emp> selectAll();
    Emp selectById(Integer empId);
    int insert(Emp emp);
    int update(Emp emp);
    int deleteById(Integer empId);
}