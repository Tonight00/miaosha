# miaosha
这是一个基于SpringBoot的秒杀项目。
1. 使用springboot, 基于阿里云ECS搭建
2. 前后端分离、数据库与server分离、server水平扩展（两台）
3. 基于openrestry的nginx 单机反向代理、动静分离
4. redis单机 缓存商品信息、分布式token，jdk序列化存储
5. 令牌桶限流、阻塞队列削峰、验证码错峰、邮箱验证防黄牛
6. jmeter进行压力测试
