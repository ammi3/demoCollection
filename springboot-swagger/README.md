---
title: springboot整合swagger
date: 2020-04-18 09:54:54
tags:
  - Java框架
categories: Java
cover: https://ciwei3.cn-sh2.ufileos.com/1.jpg
---

### 1：Swagger

Swagger 是一套基于 OpenAPI 规范构建的开源工具，可以帮助我们设计、构建、记录以及使用 Rest API。Swagger 主要包含了以下三个部分：

- Swagger Editor：基于浏览器的编辑器，我们可以使用它编写我们 OpenAPI 规范。
- Swagger UI：它会将我们编写的 OpenAPI 规范呈现为交互式的 API 文档，后文我将使用浏览器来查看并且操作我们的 Rest API。
- Swagger Codegen：它可以通过为 OpenAPI（以前称为 Swagger）规范定义的任何 API 生成服务器存根和客户端 SDK 来简化构建过程。

<!--more-->

### 2：SpringBoot整合Swagger

#### 	2.1：Maven添加swagger的依赖

```xml
<dependency>
    <groupId>io.springfox</groupId>
    <artifactId>springfox-swagger2</artifactId>
    <version>2.9.2</version>
</dependency>
<dependency>
    <groupId>io.springfox</groupId>
    <artifactId>springfox-swagger-ui</artifactId>
    <version>2.9.2</version>
</dependency>
```

#### 	2.2：此时，我们先完善整个目录结构，首先来创建两个包`com.demo.swagger.controller`，`com.demo.swagger.model`

- `model的实体User代码`

```java
@ApiModel("用户实体")
public class User {

    /**
     * 用户Id
     */
    @ApiModelProperty("用户id")
    private int id;
    /**
     * 用户名
     */
    @ApiModelProperty("用户姓名")
    private String name;
    /**
     * 用户地址
     */
    @ApiModelProperty("用户地址")
    private String address;

    public int getId() {
        return id;
    }

    public User setId(int id) {
        this.id = id;
        return this;
    }
    public String getName() {
        return name;
    }
    public User setName(String name) {
        this.name = name;
        return this;
    }
    public String getAddress() {
        return address;
    }
    public User setAddress(String address) {
        this.address = address;
        return this;
    }
}
```

- controller层的UserController`代码

```java
@RestController
@RequestMapping("/user")
// @Api用在类上，说明该类的作用
@Api(tags = "用户相关接口")
public class UserController {

    // @ApiOperation注解给Api增加方法说明
    @ApiOperation("新增用户接口")
    @PostMapping("/add")
    public boolean addUser(@RequestBody User user) {
        return false;
    }

    @ApiOperation("通过id查找用户接口")
    @GetMapping("/find/{id}")
    public User findById(@PathVariable("id") int id) {
        return new User();
    }

    @ApiOperation("更新用户信息接口")
    @PutMapping("/update")
    public boolean update(@RequestBody User user) {
        return true;
    }

    @ApiOperation("删除用户接口")
    @DeleteMapping("/delete/{id}")
    public boolean delete(@PathVariable("id") int id) {
        return true;
    }

}
```

#### 2.3：Java配置

Springfox 提供了一个 Docket 对象，让我们可以灵活的配置 Swagger 的各项属性。下面我们新建一个 com.demo.swagger.conf.SwaggerConfig.java类，并增加如下内容:

```java
/**
  * apiInfo() 增加API相关信息
  *
  * @return
*/
@Bean
public Docket api() {
    return new Docket(DocumentationType.SWAGGER_2)
        .select()
        .apis(RequestHandlerSelectors.basePackage("com.demo.swagger.controller"))
        .paths(PathSelectors.any())
        .build()
        .apiInfo(apiInfo())
        .useDefaultResponseMessages(false)
        .globalResponseMessage(RequestMethod.GET, newArrayList(
            new ResponseMessageBuilder()
            .code(500)
            .message("服务器发生异常")
            .responseModel(new ModelRef("Error"))
            .build(),
            new ResponseMessageBuilder()
            .code(403)
            .message("资源不可用")
            .build()
        ));
}
```

#### 2.4：高级配置

文档信息配置，Swagger 还支持设置一些文档的版本号、联系人邮箱、网站、版权、开源协议等等信息，但与上面几条不同的是这些信息不是通过注解配置，而是通过创建一个 ApiInfo 对象，并且使用 `Docket.appInfo()` 方法来设置，我们在 SwaggerConfig.java 类中新增如下内容即可。

```java
private ApiInfo apiInfo() {
    return new ApiInfo(
        "Spring Boot项目集成Swagger实例文档",
        "我的博客网站：https://aimezhao.online，欢迎大家访问。",
        "API V1.0",
        "Terms of service",
        new Contact("aime", "https://aimezhao.online", "758217673@qq.com"),
        "Apache", "http://www.apache.org/", Collections.emptyList());
}
```

![](https://supers1.oss-cn-hangzhou.aliyuncs.com/20200418094845.png)

#### 2.5：接口过滤

有些时候我们并不是希望所有的 Rest API 都呈现在文档上，这种情况下 Swagger2 提供给我们了两种方式配置，一种是基于 `@ApiIgnore` 注解，另一种是在 Docket 上增加筛选。

- `@ApiIgnore`

```java
@ApiIgnore // 添加注解
public boolean delete(@PathVariable("id") int id)
```

- 在 Docket 上增加筛选。Docket 类提供了 `apis()` 和 `paths()`两 个方法来帮助我们在不同级别上过滤接口：
  - `apis()`：这种方式我们可以通过指定包名的方式，让 Swagger 只去某些包下面扫描。
  - `paths()`：这种方式可以通过筛选 API 的 url 来进行过滤。

```java
.apis(RequestHandlerSelectors.basePackage("cn.itweknow.sbswagger.controller"))
.paths(Predicates.or(PathSelectors.ant("/user/add"),
        PathSelectors.ant("/user/find/*")))
```

### 参考资料

- [本文源码地址](https://github.com/ammi3/demoCollection/tree/master/springboot-swagger)