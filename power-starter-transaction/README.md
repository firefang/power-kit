# power-starter-transaction

> 自动事务管理

## 用法

1. 添加依赖
```xml
<!-- Maven -->
<dependency>
    <groupId>io.github.firefang</groupId>
    <artifactId>power-starter-transaction</artifactId>
    <version>latest version</version>
</dependency>
```
2. 根据命名会自动在Service的方法上添加事务

### 默认添加的命名

- 默认添加只读事务：select*、find*、count*、list*、query*、get*、check*、*
- 默认添加传播事务：add*、save*、insert*、create*、delete*、remove*、update*、edit*、batch*
- 默认拦截事务的Bean：*Service、*ServiceImpl

## 配置项

|配置项|作用|默认值|
|-|-|-|
|power.transaction.enabled|是否开启自动事务管理|true|
|power.transaction.readOnlyTransactionAttributes|自定义只读事务方法名，可以使用通配符`*`||
|power.transaction.requiredTransactionAttributes|自定义传播事务方法名，可以使用通配符`*`||
|power.transaction.txBeanNames|拦截事务Bean的名称||