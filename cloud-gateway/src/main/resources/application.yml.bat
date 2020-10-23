server:
  undertow:
    io-threads: "50"
    worker-threads: "100"
  port: 8080

spring:
  cloud:
    gateway:
      routes: #配置路由规则
        - id: cloud-uim-route
          uri: lb://cloud-uim
          predicates:
            - Path=/api/auth/**,/api/user/**,/api/oauth/**,/api/resource/**,/api/sign/**,/api/role/**,/api/organ/**,/api/sys/user/**
#          filters:
#            - StripPrefix=1
#      discovery:
#        locator:
#          enabled: true #开启从注册中心动态创建路由的功能
#          lower-case-service-id: true #使用小写服务名，默认是大写
  security:
    oauth2:
      resourceserver:
        jwt:
          jwk-set-uri: 'http://localhost:8010/api/rsa/public/key' #配置RSA的公钥访问地址
  redis:
    host: 192.168.1.48
    port: 6379

secure:
  ignore:
    urls: #配置白名单路径
      - "/api/sign/**"
      - "/api/oauth/**"

