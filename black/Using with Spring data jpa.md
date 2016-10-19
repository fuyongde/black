# 在Spring Boot中如何使用Spring Data Jpa？

Spring框架对SQL数据库提供了广泛的支持。本文介绍如何在Spring Boot中使用Spring Data Jpa来访问MySQL数据库。

### 准备工作

　　我们从国家统计局网站找来了[《最新县及县以上行政区划代码》（截止2015年9月30日）](http://www.stats.gov.cn/tjsj/tjbz/xzqhdm/201608/t20160809_1386477.html)，并将其录入数据库。SQL文件见本文[例子程序](https://github.com/fuyongde/black/tree/black04)中的region.sql文件。



### 开始编码

　　1、添加依赖

　　在[上文](http://www.jianshu.com/p/fac64c59b2f0)的例子的基础上，我们在工程的`pom.xml`文件中添加`spring-boot-starter-data-jpa`和`mysql-connector-java`的依赖。

```
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-data-jpa</artifactId>
    </dependency>
    <dependency>
        <groupId>mysql</groupId>
        <artifactId>mysql-connector-java</artifactId>
        <version>5.1.39</version>
    </dependency>
```

同时添加常用的第三方库`apache-commons-lang3`和`google-guava`

```
　　　　　<dependency>
            <groupId>org.apache.commons</groupId>
            <artifactId>commons-lang3</artifactId>
            <version>3.4</version>
        </dependency>
        <dependency>
            <groupId>com.google.guava</groupId>
            <artifactId>guava</artifactId>
            <version>19.0</version>
        </dependency>
```



　　2、在`src\main\java\com\jason\entity`目录下编写`Region.java`实体类，代码冗余，详见本文[例子程序](https://github.com/fuyongde/black/tree/black04)。

　　3、在`src\main\java\com\jason\repository`目录下编写Dao层的接口。由于Spring Data JPA实现了大量的方法，我们按如下方式写即可。

```
package com.jason.repository;

import com.jason.entity.Region;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface RegionDao extends PagingAndSortingRepository<Region, Integer>, JpaSpecificationExecutor<Region> {

}
```

　　4、在`src\main\java\com\jason\service`目录项编写Service层的接口及其实现类。此处只贴实现类相关的代码，详见本文[例子程序](https://github.com/fuyongde/black/tree/black04)。

```
package com.jason.service.impl;

import com.jason.entity.Region;
import com.jason.repository.RegionDao;
import com.jason.service.RegionService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class RegionServiceImpl implements RegionService {

    @Resource
    private RegionDao regionDao;

    @Override
    public Region getById(Integer id) {
        return regionDao.findOne(id);
    }
}
```

　　5、在`src\main\java\com\jason\rest`目录编写Controller层代码。此处我们遵循Restful风格的编码，Restful风格的编码详见[Oracle网站](http://www.oracle.com/technetwork/articles/javase/index-137171.html)以及[Rest API Tutorial](http://www.restapitutorial.com/index.html)。

```
package com.jason.rest;

import com.jason.entity.Region;
import com.jason.service.RegionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/regions")
public class RegionRestController {
    @Autowired
    private RegionService regionService;

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Region getRegionById(@PathVariable("id") Integer id) {
        Region result = regionService.getById(id);
        return result;
    }

}
```

　　6、运行该程序，访问`http://localhost:8080/black/api/regions/110000`，即可看到北京的地区信息。

```
{
  "id": 110000,
  "parentId": 0,
  "name": "北京市",
  "level": 1
}
```



### 编写单元测试

​	不同于[之前文章](http://www.jianshu.com/p/8364a18d6b1e)所写的测试方法，本文的单元测试需要配置`WebApplicationContext`，测试类如下。

```
package com.jason.rest;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RegionRestControllerTest {

    private MockMvc mvc;

    @Autowired
    private WebApplicationContext context;

    @Before
    public void before() {
        this.mvc = MockMvcBuilders.webAppContextSetup(this.context).build();
    }

    @Test
    public void testGetRegionById() throws Exception {
        this.mvc.perform(get("/api/regions/110000").accept(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.name").value("北京市"));
    }
} 
```






本文示例程序请[点此](https://github.com/fuyongde/black/tree/black04)获取。
详细资料请参考[Spring Boot官网](http://projects.spring.io/spring-boot/)。