server:
  port: 8010
  undertow:
      io-threads: "50"
      worker-threads: "100"
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

mybatis-plus:
  mapper-locations: classpath*:/mapper/*Mapper.xml
  type-enums-package: com.r7.core.uim.constant

sms:
  config:
    access:
      key:
        id: LTAIDOb6J22j1LzH
        secret: 8y4GmAOfqGbOHTSsWQoONUPl76VsqF
  sign:
    name: 云觅