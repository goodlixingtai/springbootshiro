###  spring boot +shiro +swagger

第一步 搭建spring boot 工程

-  配置pom.xml

  ```xml
  <parent>
      <groupId>org.springframework.boot</groupId>
      <artifactId>spring-boot-starter-parent</artifactId>
      <version>2.2.2.RELEASE</version>
      <relativePath/> <!-- lookup parent from repository -->
  </parent>
  
  
  <properties>
      <java.version>1.8</java.version>
  </properties>
  
  <dependencies>
      <dependency>
          <groupId>org.springframework.boot</groupId>
          <artifactId>spring-boot-starter-data-jpa</artifactId>
      </dependency>
      <dependency>
          <groupId>org.springframework.boot</groupId>
          <artifactId>spring-boot-starter-web</artifactId>
      </dependency>
  
      <dependency>
          <groupId>mysql</groupId>
          <artifactId>mysql-connector-java</artifactId>
          <scope>runtime</scope>
      </dependency>
      <dependency>
          <groupId>org.projectlombok</groupId>
          <artifactId>lombok</artifactId>
          <optional>true</optional>
      </dependency>
      
      <dependency>
          <groupId>org.springframework.boot</groupId>
          <artifactId>spring-boot-starter-test</artifactId>
          <!--  <scope>test</scope>-->
          <exclusions>
              <exclusion>
                  <groupId>org.junit.vintage</groupId>
                  <artifactId>junit-vintage-engine</artifactId>
              </exclusion>
          </exclusions>
      </dependency>
  </dependencies>
  
  <build>
      <plugins>
          <plugin>
              <groupId>org.springframework.boot</groupId>
              <artifactId>spring-boot-maven-plugin</artifactId>
          </plugin>
      </plugins>
  </build>
  ```

- 配置application.yml

```xml
server:
  port: 8081
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/springboot?useUnicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Shanghai
    username: root
    password: root
    driver-class-name: com.mysql.cj.jdbc.Driver
  jpa:
    show-sql: true
    properties:
      hibernate:
        format_sql: true #格式化代码
```

- 配置实体类user

```java
package com.yonyou.springbootshiro.entity;

import lombok.Data;

import javax.persistence.*;

/**
 * @author lxt
 * @version 1.0
 * @date 2020-03-05 21:42
 */
@Data
@Entity
@Table(name="sys_user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String password;
}

```



- 配置接口userrepository

```java
package com.yonyou.springbootshiro.repository;

import com.yonyou.springbootshiro.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author lxt
 * @version 1.0
 * @date 2020-03-05 21:49
 */
public interface UserRepository extends JpaRepository<User,Long> {
}
```

- 测试类

```java
package com.yonyou.springbootshiro.repository;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author lxt
 * @version 1.0
 * @date 2020-03-05 22:14
 */
@SpringBootTest
public class UserRepositoryTest {
    @Autowired
    UserRepository userRepository;
    @Test
    void  find(){
        userRepository.findAll();
    }
}
```

第二步配置Swagger

-   配置Swagger依赖

```xml
<!-- swagger -->
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

- Swagger配置类

```java
package com.yonyou.springbootshiro.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author lxt
 * @version 1.0
 * @date 2020-03-05 22:42
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {
 /*   @Configuration声明为配置类
    @EnableSwagger2则开启Swagger2文档支持，其余大致为固定写法，只是具体属性有所不同。
    basePackage指明文档包含的包范围（主要配置点）
   */

    @Bean
    public Docket createRestApi(){
        return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo())
                .select()
                /*.apis(RequestHandlerSelectors.basePackage("com.yonyou.springbootshir."))*/
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any()).build();
                //RequestHandlerSelectors.any()对所有路径
    }

    private ApiInfo apiInfo(){
    /* title和description即为文档标题和描述
    contact为创建人信息，
    version是版本信息。*/
        return new ApiInfoBuilder()
                .title("SpringBoot shiro Doc")
                .description("This is a restful api document of Spring Boot.")
                .version("1.0")
                .build();
    }

}
```

- 配置controller

```java
package com.yonyou.springbootshiro.controller;

import com.yonyou.springbootshiro.entity.User;
import com.yonyou.springbootshiro.repository.UserRepository;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author lxt
 * @version 1.0
 * @date 2020-03-05 22:58
 */
@RestController
@RequestMapping("user")
public class UserHandler {
    @Autowired
    UserRepository userRepository;

   /* 其中@ApiOperation是接口的描述信息
    @ApiImplicitParam针对具体的参数添加描述
    @ApiImplicitParams则是多个@ApiImplicitParam
    更多注解使用，可以参考官方文档。
    需要注意的是，
    使用@PathVariable需要设置paramType="path"，
    而使用@RequestParam时，
    需要设置paramType="query"，
    不然可能会导致好好的接口无法接受到请求参数。*/

    @ApiOperation(value = "用户" ,notes = "查询书籍信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id",paramType = "path",value = "用户id",required = true,dataType = "Long" )
    })
    @GetMapping("/findById/{id}")
    public User findById(@PathVariable(value = "id") Long id){
        User user =userRepository.findById(id).get();
       return   user;

    }
    

}
```

登陆Swagger控制台

控制台地址：http://localhost:8081/swagger-ui.html#/





shiro 搭建

需要了解shiro 的原理

- pom.xml shiro依赖

```xml
 <dependency>
    <groupId>org.apache.shiro</groupId>
    <artifactId>shiro-spring</artifactId>
    <version>1.4.0</version>
</dependency>
```

**Realms**

Realms在Shiro和你的程序安全数据之间，扮演着桥梁或者连接器的角色。当和安全相关数据有交互时，比如用户登录校验和鉴权，Shiro通过程序中配置的一个或者多个Realm来查询。

在此场景中，Realm是安全领域的DAO：它封装了数据源连接的细节，使得相关数据对于shiro可用。当你配置Shiro，你必须指定至少一个realm用来登录和授权。SecurityManager可能会配置多个Realm，但是至少有一个。

Shrio提供了开箱即用的Realm来链接各种安全数据源，例如LDAP，关系型数据库（JDBC），文本配置如INI或者properties文件，等等。如果默认的Realm不能满足你的需求，你可以插入你自己的Realm实现来表述客制化的数据源。

就像其他内部的组件，Shiro的SecurityManager管理如何使用Realm来获取Subject实例相关的安全和身份信息。



Realms配置

```java
package com.yonyou.springbootshiro.config;

import com.yonyou.springbootshiro.entity.Permission;
import com.yonyou.springbootshiro.entity.SysRole;
import com.yonyou.springbootshiro.entity.SysUser;
import com.yonyou.springbootshiro.service.LoginService;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author lxt
 * @version 1.0
 * @date 2020-03-06 19:32
 * 实现AuthorizingRealm接口用户用户认证
 */
public class MyShiroRealm extends AuthorizingRealm {

    @Autowired
    private LoginService loginService;


    //授权信息
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        // 获取登录用户名
        String name = (String) principalCollection.getPrimaryPrincipal();
        // 查询用户名称
        SysUser user = loginService.findByName(name);
        // 添加角色和权限
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        for (SysRole role : user.getSysRoles()) {
            // 添加角色
            simpleAuthorizationInfo.addRole(role.getRoleName());
            for (Permission permission : role.getPermissions()) {
                // 添加权限
                simpleAuthorizationInfo.addStringPermission(permission.getPermission());
            }
        }
        return simpleAuthorizationInfo;
    }

    //认证信息
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        String naem=authenticationToken.getPrincipal().toString();
        // 加这一步的目的是在Post请求的时候会先进认证，然后在到请求
        if (naem == null) {
            return null;
        }

        String name = authenticationToken.getPrincipal().toString();
        SysUser user = loginService.findByName(name);
        if (user == null) {
            // 这里返回后会报出对应异常
            return null;
        } else {
            // 这里验证authenticationToken和simpleAuthenticationInfo的信息
            SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(name,
                    user.getPassword().toString(), getName());
            return simpleAuthenticationInfo;
        }
    }
}
```

**SecurityManager**

SecurityManager是Shiro架构的核心，他扮演着伞对象的角色（持有各组件引用，协调调用），来协调它内部的安全组件，他们一块来形成对象视图。然而，一旦程序中的SecurityManager和他内部对象已经配置好，通常上就不用再管他，程序的开发人员几乎大部分时间都会花在Subject API上。

我们接下来会深入讨论SeccurityManager。当你和Subject交互时，所做的任何Subject的安全操作，实际上是SecurityManager在幕后辛苦劳作。你要意识到这一点很重要。这些都在上面的基础流程图中有所反映。

SecurityManager配置

```java
package com.yonyou.springbootshiro.config;

import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * @author lxt
 * @version 1.0
 * @date 2020-03-06 23:35
 */
@Configuration
public class ShiroConfig {
    // 将自己的验证方式加入容器
    @Bean(name="myShiroRealm")
    public MyShiroRealm myShiroRealm() {
        MyShiroRealm myShiroRealm = new MyShiroRealm();
        return myShiroRealm;
    }

    // 权限管理，配置主要是Realm的管理认证
   /* 配置SecurityManager (管理器，管理subject及其相关的登陆验证，授权等，需配置realm和缓存管理)*/
    @Bean(name="securityManager")
    public DefaultWebSecurityManager securityManager(@Qualifier("myShiroRealm") MyShiroRealm myShiroRealm){
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(myShiroRealm());
     /*   // 自定义session管理 使用redis
        securityManager.setSessionManager(sessionManager());
        // 自定义缓存实现 使用redis
        securityManager.setCacheManager(cacheManager());*/
        return securityManager;
    }

    // Filter工厂，设置对应的过滤条件和跳转条件 配置shiroFilter
    @Bean(name = "shiroFilter")
    public ShiroFilterFactoryBean shiroFilterFactoryBean(DefaultWebSecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        Map<String, String> filterMap = new HashMap<String, String>();
        // 登出
        filterMap.put("/logout", "logout");
        // swagger
        filterMap.put("/swagger**/**", "anon");
        filterMap.put("/webjars/**", "anon");
        filterMap.put("/v2/**", "anon");
        // 对所有用户认证
        filterMap.put("/**", "authc");
        // 登录
        shiroFilterFactoryBean.setLoginUrl("/login");
        // 首页
        shiroFilterFactoryBean.setSuccessUrl("/index");
        // 错误页面，认证不通过跳转
        shiroFilterFactoryBean.setUnauthorizedUrl("/error");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterMap);
        return shiroFilterFactoryBean;
    }

    // 加入注解的使用，不加入这个注解不生效
/*
    配置  AuthorizationAttributeSourceAdvisor（开启shiro spring aop 权限注解支持，即：@RequiresPermissions("权限code")
*/
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(DefaultWebSecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }
}
```

**Subject**

正如我们教程中提到的，Subject是程序安全视角的user。然而user通常指人类，但Subject可以是人，也可以指第三方的服务，守护进程账户，定时任务，或者其他类似的东西-基本上可以是和软件交互的任何事物。

Subject 的实例都绑定在SecurityManager上。当你和subject交互时，SecurityManager把交互转化为subject领域的交互。

```java
package com.yonyou.springbootshiro.controller;

import com.yonyou.springbootshiro.entity.SysUser;
import com.yonyou.springbootshiro.service.LoginService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author lxt
 * @version 1.0
 * @date 2020-03-06 19:07
 */
@RestController

public class login {
    @Autowired
    LoginService loginService;
    /**
     * POST登录
     * @param map
     * @return
     */
    @PostMapping(value = "/login")
    public String login(@RequestBody SysUser user) {
        // 添加用户认证信息
        UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(user.getName(), user.getPassword());
        // 进行验证，这里可以捕获异常，然后返回对应信息
        SecurityUtils.getSubject().login(usernamePasswordToken);
        return "login ok!";
    }
}
```

