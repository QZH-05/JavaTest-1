package com.lab.test;

import com.lab.entity.Emp;
import com.lab.mapper.EmpMapper;
import com.lab.util.MyBatisUtil;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import java.util.List;

public class MyBatisDemo {
    public static void main(String[] args) throws Exception {
        // 1. 读取配置文件，构建 SqlSessionFactory（全局只需构建一次）
        SqlSessionFactory factory = MyBatisUtil.getSqlSessionFactory();
        // 2. 开启 SqlSession（用完必须关闭）
        try (SqlSession session = MyBatisUtil.openSession()) {
            // 3. 获取 Mapper 动态代理对象
            EmpMapper mapper = session.getMapper(EmpMapper.class);
            List<Emp> list = mapper.selectAll();
            list.forEach(System.out::println);
        }
    }
}