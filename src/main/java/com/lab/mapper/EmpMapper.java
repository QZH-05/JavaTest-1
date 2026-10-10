package com.lab.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lab.entity.Emp;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 员工 Mapper 接口。
 * <p>
 * 任务 4 起继承 {@link BaseMapper}，即可零 SQL 获得：
 * insert / deleteById / updateById / selectById / selectList / selectPage 等通用方法。
 * <p>
 * 命名约束（重要）：
 * MP 注入的语句 id 与自定义 XML 语句 id 都是 "namespace + 方法名"，二者不能重名，
 * 否则启动时报 "Mapped Statements collection already contains value for ..."。
 * 因此自定义方法一律使用不与 BaseMapper 冲突的名称
 * （selectAll、selectByCondition、updateDynamic、deleteBatch、insertBatch、selectByChoose），
 * 而 insert / selectById / updateById / deleteById 统一交给 BaseMapper 提供。
 */
public interface EmpMapper extends BaseMapper<Emp> {

    /** 查询全部员工（自定义 XML） */
    List<Emp> selectAll();

    /** 3.1 多条件模糊查询：if + where 标签 */
    List<Emp> selectByCondition(Emp emp);

    /** 3.1 多条件模糊查询：trim 标签等效实现 */
    List<Emp> selectByConditionTrim(Emp emp);

    /** 3.2 动态更新：set 标签 */
    int updateDynamic(Emp emp);

    /** 3.3 批量删除：foreach 标签 */
    int deleteBatch(@Param("ids") List<Integer> ids);

    /** 3.3 批量插入：foreach 标签 */
    int insertBatch(@Param("list") List<Emp> emps);

    /** 3.4 分支选择：choose / when / otherwise */
    List<Emp> selectByChoose(Emp emp);
}