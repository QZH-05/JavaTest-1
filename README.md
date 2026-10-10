# mybatis-lab —— 实验一：MyBatis 与 MyBatis-Plus 环境搭建及 CRUD 实操

《JAVA 框架技术（一）》实验一完整工程。以员工表 `emp` 为业务载体，覆盖
MyBatis 基础 CRUD、动态 SQL、批量操作、MyBatis-Plus 通用 CRUD / 条件构造器 / 分页，
以及 Log4j 日志与 JUnit 单元测试。

## 1. 环境要求

| 工具 | 版本 |
| --- | --- |
| JDK | 8（工程编译级别；JDK 21 亦可运行） |
| Maven | 3.8.x（配置阿里云镜像） |
| MySQL | 8.0 |
| MyBatis-Plus | 3.5.3.1 |
| JUnit | 4.13.2 |
| Log4j | 1.2.17 |

## 2. 数据库准备

执行 `sql/init.sql`，初始化数据库 `ssm_emp` 与表 `emp`。

修改 `src/main/resources/mybatis-config.xml` 中的数据库账号密码为你本地的配置。

## 3. 目录结构

```
src/main/java/com/lab
├── entity/Emp.java                      # 实体类（含 MyBatis-Plus 注解）
├── mapper/EmpMapper.java                # Mapper 接口（继承 BaseMapper + 自定义方法）
├── util/MyBatisUtil.java                # SqlSessionFactory 工具类
├── config/MybatisPlusPageInterceptor.java  # MP 分页插件（非 Spring 适配）
├── test/MyBatisDemo.java                # 任务 2.4 第一个 MyBatis 程序
└── testmp/MyBatisPlusDemo.java          # 任务 4 MyBatis-Plus 演示（main 方法）

src/main/resources
├── mybatis-config.xml                   # 全局配置
├── log4j.properties                     # 日志配置
└── mapper/EmpMapper.xml                 # SQL 映射（动态 SQL）

src/test/java/com/lab/test
└── EmpMapperTest.java                   # JUnit 单元测试

sql/init.sql                             # 建库建表脚本
实验报告.md                               # 实验报告
```

## 4. 运行方式

1. 用 IDEA 打开工程，刷新 Maven 依赖；
2. 运行 `MyBatisDemo#main`：观察任务 2 的查询与 SQL 日志；
3. 运行 `MyBatisPlusDemo#main`：观察任务 4 的通用 CRUD、条件构造器与分页；
4. 运行 `EmpMapperTest`：执行全部 JUnit 用例（全部为绿色即通过）。

## 5. 设计说明

- **XML 与 BaseMapper 共存**：`BaseMapper` 已提供 `insert/selectById/updateById/deleteById`，
  自定义 XML 方法使用不重名的方法名，避免语句 id 冲突。
- **分页插件**：MP 3.5.x 需以对象方式 `addInnerInterceptor(new PaginationInnerInterceptor(...))`，
  故自定义 `MybatisPlusPageInterceptor` 并通过 `<plugins>` 注册。
- **`status` 字段**：不使用 `@TableLogic`，作为“1在职/0离职”的业务状态字段处理。
