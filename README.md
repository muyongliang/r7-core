# R7核心

## 股票模块
| 模块      | 描述     | 
| ---------- | :-----------:  |
| cloud-common     | 公共模块     |
| cloud-assets | 资产模块 包含钱包、资金、积分  |
| cloud-im| 即时通讯模块 |
|cloud-integral|文件资源模块|
|cloud-dividend| 红利模块 包含代理、分润|
|cloud-setting|公共配置模块|
|cloud-job|任务模块|
|cloud-uim|统一登录授权模块|
|cloud-trigger|定时任务模块|
|cloud-stand|支架模块 包含短信、电子签、视频录制|

## 开发要求

**分支**

以dev为主分支,基于dev来创新新分支

新分支规则: dev_xxx

bug修复分支: fix_xxx


## docker 镜像
 
>docker run -d \
 --name redis \
 --restart=always \
 -p 6379:6379 \
 -v /root/redis/redis.conf:/etc/redis/redis.conf \
 -v ~/redis/data:/data \
 -d redis redis-server --appendonly yes \
 --requirepass "905D39517af31c20119bf2556acf0fe6


>docker run -d \
--name nacos-server \
-e PREFER_HOST_MODE=hostname \
-e MODE=standalone \
-e SPRING_DATASOURCE_PLATFORM=mysql \
-e MYSQL_SERVICE_HOST=192.168.1.48 \
-e MYSQL_SERVICE_PORT=3306 \
-e MYSQL_SERVICE_USER=root \
-e MYSQL_SERVICE_PASSWORD=root.R7db@192.168.1.48 \
-e MYSQL_SERVICE_DB_NAME=nacos \
-p 8848:8848 \
nacos/nacos-server


>docker run -p 9000:9000 \
--name minio \
-d --restart=always \
-e "MINIO_ACCESS_KEY=admin" \
-e "MINIO_SECRET_KEY=admin123456" \
-v ~/minio/data:/data \
-v ~/minio/config:/root/.minio \
minio/minio server /data
>
>docker服务编排， Loki轻量级日志服务
>version: "3"
 networks:
   loki: 
 services:
   loki:
     image: grafana/loki:1.5.0
     ports:
       - "3100:3100"
     command: -config.file=/etc/loki/local-config.yaml
     networks:
       - loki
   promtail:
     image: grafana/promtail:1.5.0
     volumes:
       - /var/log:/var/log
     command: -config.file=/etc/promtail/docker-config.yaml
     networks:
       - loki
   grafana:
     image: grafana/grafana:latest
     ports:
       - "3000:3000"
     networks:
       - loki
