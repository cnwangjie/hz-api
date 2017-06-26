hz api
======

### Initialization

0. 复制一份配置文件并编辑好

    (./src/main/resources/application.yaml)

0. 运行mysql并创建一个编码为`utf8_general_ci`，名字为`hz`的数据库
0. 安装`maven`
0. 导入`pom.xml`中的依赖(运行`mvn install`)
0. 尝试编译运行`com.lf.hz.HzApplication`(运行`mvn spring-boot:run`)
0. 如果没有报错的话，尝试访问`127.0.0.1:2002/api/test`，如果成功返回数据则表示环境搭建完成
