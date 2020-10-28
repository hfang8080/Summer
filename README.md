# 手把手教你写Spring（Summer）
## 背景
大家在日常的工作当中都会或多或少的用到Spring，可见Spring在我们日常开发中的重要性。我知道如何去使用Spring开发我们的程序，但是有很大一部分同学并不知道Spring是怎样帮我们实现这一切的。因此这个项目是带领大家去手写一版简单版的Spring，带领大家一起学习Spring他到底是如何帮我们管理bean，以及如何帮我实现依赖注入的。
## 学习方式
本项目采用的分支逐步迭代的方式来实现一个个Spring的功能点，每个版本只包含一个或者两个功能点，方便大家学习和理解。大家可以采取先看后写的方式去学习手写一份属于自己的Spring框架，如果觉得看一遍直接写有难度，那么可以参看每个版本的diff去写。当然，如果可以通过看各个版本的diff之后，直接写出自己的实现方式，那说明你已经理解了Spring是怎么实现这套东西的核心了。
## 各版本迭代内容
[v1.0](https://github.com/hfang8080/Summer/pull/1/files) 创建一个基本的BeanFactory用来管理Bean，增加一个JsonApplicationContext来解析json数据 
[v2.0](https://github.com/hfang8080/Summer/pull/2/files) 增加ListableBeanFactory来实现同一个Class对应多个Bean
[v3.0](https://github.com/hfang8080/Summer/pull/3/files) 实现Bean的Lazy-init和scope特性
[v4.0](https://github.com/hfang8080/Summer/pull/4/files) 实现Bean的init-method和destory-method的配置支持，以及@PostConstruct和@Predestroy注解支持
[v5.0](https://github.com/hfang8080/Summer/pull/5/files) 优化部分代码
[v6.0](https://github.com/hfang8080/Summer/pull/6/files) 通过Bean的构造器创建Bean，并支持通过@BeanFactory创建Bean
[v7.0](https://github.com/hfang8080/Summer/pull/7/files) 通过配置注入属性，包含值和属性
[v8.0](https://github.com/hfang8080/Summer/pull/8/files) 增加各种Processor和Aware来实现用户的前置或后置的通知
[v9.0](https://github.com/hfang8080/Summer/pull/9/files) 增加Bean的继承关系的解析支持
[v10.0](https://github.com/hfang8080/Summer/pull/10/files) 增加Bean的循环依赖检测
[v11.0](https://github.com/hfang8080/Summer/pull/12/files) 增加@Configuration注解的支持，用来标识Java配置类
[v12.0](https://github.com/hfang8080/Summer/pull/13/files) 增加@Bean的支持，在Java配置类中实例化一个Bean
[v13.0](https://github.com/hfang8080/Summer/pull/14/files) 增加@Scope和@Lazy注解的支持，实现通过注解标识Bean的特性
[v14.0](https://github.com/hfang8080/Summer/pull/15/files) 增加@Import注解的支持，用来引入其他的Java配置类
[v15.0](https://github.com/hfang8080/Summer/pull/16/files) 增加@Bean中通过参数注入Bean的支持，用来更加便捷的实例化Bean
[v16.0](https://github.com/hfang8080/Summer/pull/18/files) 增加@Autowired注解的支持，用来向类的属性中注入Bean
[v17.0](https://github.com/hfang8080/Summer/pull/19/files) 增加@Primary注解的支持，用来标识Bean的是否为主Bean
[v18.0](https://github.com/hfang8080/Summer/pull/20/files) 增加@Conditional注解支持，用来标识Bean的实例化条件
[v19.0](https://github.com/hfang8080/Summer/pull/21/files) 增加@Profile注解的支持，用来标识Bean应该在什么环境下被实例化
[v20.0](https://github.com/hfang8080/Summer/pull/22/files) 增加Environment作为Bean可以被注入的能力，方便在使用的时候，拿到当前环境
[v21.0](https://github.com/hfang8080/Summer/pull/23/files) [补充](https://github.com/hfang8080/Summer/pull/24/files) 增加@Value注解的支持，用来向类的属性中注入配置文件中的值
[v22.0](https://github.com/hfang8080/Summer/pull/25) 增加对于@ComponentScan的支持，用来实现Bean的扫描及其加载

## 致谢
感谢大家能够看到这里，欢迎大家的star和fork!