## springboot-security-project
**基于`springboot2` + `springsecurity` + `mybatis` + `redis` + `swagger` + `oauth2` 的脚手架工程(前后端分离)**

1. 配置了mybatis的通用mapper，单表操作无需写SQL，实现了批量插入、批量删除等功能。
2. 内含freemarker的代码生成器，可以一键根据表名直接生成controller、service、dao、mapper等基础代码，
大大提高了开发效率，让你更专注于对业务的开发。
3. spring-security目前使用的是基于权限的动态校验，采用黑名单的方式对接口权限的判断。
4. 框架内已包含许多工具类，MD5加密、json、POI的excel文档操作，以及时间和日期等常见工具类。
5. 采用了@Log对项目日志的记录。
6. 加入对OAuth2的支持 （[博客地址](https://blog.csdn.net/qq_34997906/article/details/89600076)）

### 启动步骤
> 1. 创建数据库boot2-oauth, 执行web模块下resources/sql/init.sql文件
> 2. 使用postman测试登录接口  /login  POST 方式 ，本项目返回均是JSON字符串，重定向到登录页面，需要前端做处理，若想要后端直接重定向到登录页面的话，需要配置WebSecurityConfig中引入的那几个Handler，将里面返回的json值改为重定向到登录页面即可。 
