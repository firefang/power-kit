# power-starter-login

> 登录认证

## 用法

1. 添加依赖
```xml
<!-- Maven -->
<dependency>
    <groupId>io.github.firefang</groupId>
    <artifactId>power-starter-login</artifactId>
    <version>latest version</version>
</dependency>
```
2. 通过 `power.login.type` 设置认证类型
3. 根据认证类型实现 `ISessionAuthService` 或 `ITokenAuthService` 接口，并注册为 Spring Bean

### 自动添加Controller

当 `power.login.controller = true` 时，会根据认真类型自动注册一个用于登录的Controller

|功能|URL|方法|参数|
|-|-|-|-|
|登录|/auth/login|POST|username, password|
|注销|/auth/logout|POST||

### 白名单

若某个方法不想被拦截在方法上添加 `PublicEndPoint` 注解即可

## 配置项

|配置项|作用|默认值|
|-|-|-|
|power.login.enabled|是否开启登录认证|true|
|power.login.controller|是否自动添加登录Controller|true|
|power.login.type|认证类型(可选 SESSION, TOKEN)|SESSION|
|power.login.key|Session中存放用户信息或Header中存放Token的key|SESSION：userinfo, TOKEN：x-auth-token|