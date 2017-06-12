1、sql语句放到了src/main/sql/schema.sql文件中，在mysql数据库中执行此脚本，生成数据库相关表
2、修改src/main/resources/jdbc.properties中的数据库地址、用户名、密码
3、在IDE中导入项目，使用Maven构建项目，部署到tomcat中
4、通过http://本地地址:端口号/seckill/list访问秒杀项目的列表页面