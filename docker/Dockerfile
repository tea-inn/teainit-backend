# teainn

# 设置jdk版本
FROM java:8

# 设置个人信息
MAINTAINER teainn <your_email@xxx.com>

# 设置容器内的时间以及 jvm 的时间(默认不是 GMT)
RUN /bin/cp /usr/share/zoneinfo/Asia/Shanghai /etc/localtime
RUN echo "Asia/Shanghai" > /etc/timezone

# teainit-backend-1.0.0.jar 是我们上传的 jar 包名称（target 目录下），后面的是新命名的 jar 包
ADD teainit-backend-1.0.0.jar springboot.jar

# Image 创建容器时的初始化内存，最大内存，及启动时使用的 profile. -c 为清除以前启动的数据
ENTRYPOINT ["java","-Xms256m","-Xmx256m","-jar","/springboot.jar","--spring.profiles.active=prod","-c"]

# 镜像启动成为容器后，对外暴露的端口，仅作为说明
EXPOSE 9015