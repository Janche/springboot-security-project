## springboot-security-project
**基于`springboot2` + `springsecurity` + `mybatis` + `redis` + `swagger` + `oauth2` 的脚手架工程(前后端分离)**

1. 配置了mybatis的通用mapper，单表操作无需写SQL，实现了批量插入、批量删除等功能。
2. 内含freemarker的代码生成器，可以一键根据表名直接生成controller、service、dao、mapper等基础代码，
大大提高了开发效率，让你更专注于对业务的开发。
3. spring-security目前使用的是基于权限的动态校验，采用黑名单的方式对接口权限的判断。
4. 框架内已包含许多工具类，MD5加密、json、POI的excel文档操作，以及时间和日期等常见工具类。
5. 采用了@Log对项目日志的记录。
6. 接口文档地址：http://localhost:9999/swagger-ui.html
7. 加入对OAuth2的支持 （[博客地址](https://blog.csdn.net/qq_34997906/article/details/89600076)）

|依赖            |版本         |
|:------------- |:------------|
|[Spring Boot](http://mvnrepository.com/artifact/org.springframework.boot/spring-boot)    |2.1.4.RELEASE|
|[Spring Web MVC](http://mvnrepository.com/artifact/org.springframework/spring-webmvc)     |5.1.6.RELEASE|
|[Spring Security Web](http://mvnrepository.com/artifact/org.springframework.security/spring-security-web)|5.1.5.RELEASE|
|[MyBatis](http://mvnrepository.com/artifact/org.mybatis/mybatis)        |3.5.0      |
|[通用mapper](https://mvnrepository.com/artifact/tk.mybatis/mapper-spring-boot-starter)      |2.1.5|
|[Druid](http://mvnrepository.com/artifact/com.alibaba/druid-spring-boot-starter)          |1.1.10       |

### 启动步骤
> 1. 创建数据库boot2-oauth, 执行web模块下resources/sql/init.sql文件
> 2. 使用postman测试登录接口  /login  POST 方式 ，本项目返回均是JSON字符串，重定向到登录页面，需要前端做处理，若想要后端直接重定向到登录页面的话，需要配置WebSecurityConfig中引入的那几个Handler，将里面返回的json值改为重定向到登录页面即可。 

### 完成模块
1. 用户管理
    - [x] 用户的CRUD
    - [x] 在线用户数量统计
2. 角色管理
    - [x] 角色的CRUD
    - [x] 角色权限树
3. 权限管理
    - [x] 权限的CRUD
4. 日志管理
    - [x] 日志的CRUD

### OAuth2 授权码和password测试如下：
![OAuth2测试](https://img-blog.csdnimg.cn/20200320172751161.gif)
