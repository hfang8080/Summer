## 思路

本版本增加对于Bean实例化之后和Bean销毁之前一些功能。希望zhichi@PostConstruct和@PreDestroy注解的支持。

## 实现

* 修改`BeanDefinition`，增加`initMethod`和`destroyMethod`字段 
* 增加InitializingBean`接口，抽象出`afterPropertiesSet`函数
* `DefaultInitialingBean`实现`InitializingBean`接口，增加对于`@PostConstrct`、`initMethod`和实现`InitializingBean`接口的`Bean`的处理
* 增加`DisposableBean`接口，抽象出`destroy`函数
* `DefaultDisposableBean`实现`DisposableBean`接口，增加对于`@Predestroy`、`destroyMethod`和实现`DisposableBean`接口`Bean`的处理
* 在`createBean`的之后调用`InitializingBean.afterPropertiesSet`
* 在初始化`ApplicationContext`的时候，增加一个`ShutDown`的`hook`去调用`DefaultDisposableBean.destroy`