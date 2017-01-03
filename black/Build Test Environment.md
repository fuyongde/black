### Spring Boot Test 简介

　　Spring Boot提供了大量的实用的注解来帮助我们测试程序。针对测试支持由两个模块提供，`spring-boot-test`包含核心项目，而`spring-boot-test-autoconfigure`支持测试的自动配置。

　　大多数开发人员只使用`spring-boot-starter-test`即可，它会导入两个Spring Boot测试模块以及JUnit，AssertJ，Hamcrest和一些其他有用的库。



### 搭建测试环境

​	基于[上文](http://www.jianshu.com/p/8a0365eb0cd6)中的例子，我们来搭建测试环境。

1、在`pom.xml`文件中，添加`spring-boot-starter-test`的依赖，它包含了一系列的测试库（[JUnit](http://junit.org/) 、[Spring Test](http://docs.spring.io/spring/docs/4.3.3.RELEASE/spring-framework-reference/htmlsingle/#integration-testing.html) 、[AssertJ](http://joel-costigliola.github.io/assertj/) 、[Hamcrest](http://hamcrest.org/JavaHamcrest/)、[Mockito](http://mockito.org/) 、[JSONassert](https://github.com/skyscreamer/JSONassert) 、[JsonPath](https://github.com/jayway/JsonPath) ）。

```
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-test</artifactId>
    <scope>test</scope>
</dependency>
```



2、我们简单的先针对Controller层进行单元测试。测试Spring MVC只需在对应的测试类上添加`@WebMvcTest`注解即可。由于是基于Spring Test环境下的单元测试，请不要忘记添加`@RunWith(SpringRunner.class)`注解。

在`test\java\com\jason\web`目录下新建`IndexControllerTest.java`文件。

```
package com.jason.black.web;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(IndexController.class)
public class IndexControllerTest {

    @Autowired
    private MockMvc mvc;

    @Test
    public void testIndex() throws Exception {
        this.mvc.perform(get("/index").accept(MediaType.TEXT_PLAIN))
                .andExpect(status().isOk()).andExpect(content().string("Hello, Spring Boot!"));
    }

}
```

3、运行`IndexControllerTest.java`中的`testIndex()`方法，即可看到测试结果。



本文示例程序请[点此](https://github.com/fuyongde/black/tree/black01)获取。
详细资料请参考[Spring Boot官网](http://projects.spring.io/spring-boot/)。