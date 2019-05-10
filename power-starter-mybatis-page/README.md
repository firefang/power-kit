# power-starter-mybatis-page

> Mybatis 分页插件（物理分页）

## 用法

1. 添加依赖
```xml
<!-- Maven -->
<dependency>
    <groupId>io.github.firefang</groupId>
    <artifactId>power-starter-mybatis-page</artifactId>
    <version>latest version</version>
</dependency>
```
2. Mapper 继承 `IPageableMapper`，调用 `List<T> find(T condition, Pagination page)` 方法即可
3. 为简化代码，`IPageableService` 接口提供了对分页的默认实现