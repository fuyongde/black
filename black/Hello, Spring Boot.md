### Spring Boot 简介

　　通过使用Spring Boot，我们可以轻松的构建单独的、生产级的可以”即时运行“的Spring应用程序。使用Spring Boot可以从最小化的依赖开始构建我们的程序，大多数的Spring Boot应用程序只需要非常少的配置即可运行。

　　我们可以使用Spring Boot构建基于`java -jar`启动或传统的基于war部署启动的Java应用程序。Spring Boot也提供了一个可以运行“spring scripts”的命令行的工具。



### Spring Boot的优点

- 可以更快、更广泛的快速上手Spring开发。


- 快速开发，同时可以根据需求来进行不同的配置。


- 提供大多数项目所需的非功能特性，诸如：嵌入式服务器、安全、心跳检查、外部配置等。


- 不生成代码，同时也无需繁琐的XML配置。




### 系统要求

　　本文采用Spring Boot 1.4.1.RELEASE版本。

- Java Environment：


　　　　Java7及其以上版本，推荐使用Java8。可以通过使用额外的配置来在Java6版本上使用Spring Boot，详情见[这里](http://docs.spring.io/spring-boot/docs/1.4.1.RELEASE/reference/htmlsingle/#howto-use-java-6)。

- Build Tools：


　　　　Maven使用3.2以上的版本。

　　　　Gradle使用1.12或2.x的版本，***Gradle3不支持构建Spring Boot应用程序***。



### Hello，Spring Boot

　　在对Spring Boot有个简单的认识后，让我们开始构建基于Spring Boot的应用程序吧。

1、实用Maven构建一个工程，在工程的`pom.xml`中添加依赖：

```
<parent>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-parent</artifactId>
    <version>1.4.1.RELEASE</version>
</parent>
<dependencies>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>
</dependencies>
```

2、在`src\main\java\com\jason\web`目录下创建`IndexController.java`

```
package com.jason.black.web;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/index")
public class IndexController {
    @RequestMapping
    String index() {
        return "Hello, Spring Boot!";
    }
}
```

3、在`src\main\java\com\jason`目录下创建启动类`Application.java`

```
package com.jason;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@SpringBootApplication
public class Application {

    public static void main(String[] args) throws Exception {
        SpringApplication.run(Application.class, args);
    }
}
```
4、运行`Application.java`中的main方法，应用程序正常启动后，打开浏览器，在地址栏输入`http://localhost:8080/index`即可[访问](http://localhost:8080/index)。


本文示例程序请[点此](https://github.com/fuyongde/black/tree/black01)获取。
详细资料请参考[Spring Boot官网](http://projects.spring.io/spring-boot/)。