server:
  port: 8011
  servlet:
    context-path: /api

spring:
  datasource:
    druid:
      url: "jdbc:mysql://192.168.1.48:3306/core?seUnicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Shanghai"
      username: "root"
      password: "root.R7db@192.168.1.48"
      driver-class-name: "com.mysql.cj.jdbc.Driver"

  redis:
    host: 192.168.1.48
    port: 6379

feign:
  okhttp:
    enabled: true
ribbon:
  ReadTimeout: 60000
  ConnectTimeout: 60000

mybatis-plus:
  mapper-locations: classpath*:/mapper/*Mapper.xml
  type-enums-package: com.r7.core.proxy.constant