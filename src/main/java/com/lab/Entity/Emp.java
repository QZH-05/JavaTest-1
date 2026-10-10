package com.lab.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 员工实体类，对应 emp 表。
 * <p>
 * 任务 2 阶段是纯 MyBatis 实体；任务 4 阶段补充 MyBatis-Plus 注解，
 * 使 BaseMapper 的通用 CRUD 可以零 SQL 运行。
 * <p>
 * 注意：实验指导书中 status 字段标注了 {@code @TableLogic}（逻辑删除，本实验可选）。
 * 但本项目 status 表示"1-在职 / 0-离职"的业务状态，若启用逻辑删除，
 * MP 的 deleteById 会退化为 UPDATE status=0，且所有查询会自动追加 status=1 条件，
 * 与"离职员工查询"等业务语义冲突。因此本项目不启用该注解（详见实验报告 AI 甄别记录）。
 */
@TableName("emp")
public class Emp {

    @TableId(value = "emp_id", type = IdType.AUTO)
    private Integer empId;

    @TableField("emp_name")
    private String empName;

    private String gender;
    private String dept;
    private String post;
    private BigDecimal salary;
    private Date hireDate;

    /** 状态：1在职 0离职 */
    private Integer status;

    public Integer getEmpId() {
        return empId;
    }

    public void setEmpId(Integer empId) {
        this.empId = empId;
    }

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getDept() {
        return dept;
    }

    public void setDept(String dept) {
        this.dept = dept;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }

    public BigDecimal getSalary() {
        return salary;
    }

    public void setSalary(BigDecimal salary) {
        this.salary = salary;
    }

    public Date getHireDate() {
        return hireDate;
    }

    public void setHireDate(Date hireDate) {
        this.hireDate = hireDate;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Emp{" +
                "empId=" + empId +
                ", empName='" + empName + '\'' +
                ", gender='" + gender + '\'' +
                ", dept='" + dept + '\'' +
                ", post='" + post + '\'' +
                ", salary=" + salary +
                ", hireDate=" + hireDate +
                ", status=" + status +
                '}';
    }
}