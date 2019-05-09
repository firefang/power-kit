# power-starter-permission

> 通过AOP实现权限自动校验

## 用法

1. 添加依赖
```xml
<!-- Maven -->
<dependency>
    <groupId>io.github.firefang</groupId>
    <artifactId>power-starter-permission</artifactId>
    <version>latest version</version>
</dependency>
```
2. 根据业务实现 IPermissionChecker 接口，并注册为 Spring Bean
3. 在 Controller 上添加 PermissionGroup注解
4. 在需要进行权限校验的 方法上添加 Permission 注解，并根据情况设置相关属性

### 权限自动入库

starter 中提供了在启动时自动收集权限并写入数据库的功能，要使用该功能需满足如下条件：

1. power.permission.serialize = true
2. 存在实现了 IPermissionService 接口的 Bean

## 配置项

1. power.permission.enabled: 是否开启权限校验，默认为true
2. power.permission.serialize: 是否开启权限自动入库，默认为true