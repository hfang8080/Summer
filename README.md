## 思路

首先，创建一个可以管理`Bean`的工厂。用来创建和管理`Bean`。
其次，想办法解析用户的配置的，并保存在Bean工厂中。
拓展BeanFactory能力，使得能够保存相同类型的多个Bean实例。
## 实现

* 抽象出`BeanFactory`，并提供通过`beanName`获取对应`Bean`实例的方法。
* 抽象对于`Bean`的描述为`BeanDefinition`对象，包含属`name`, `className`。
* 实现`BeanFactory`为`DefaultBeanFactory`，使用Map结构保存`BeanName` -> `Bean`,`BeanName` -> `BeanDefination`和`type` -> `BeanNames`的映射，同时提供`Bean`的注册方式。其中实例化`Bean`的过程是通过反射的方式创建对象实例。
* 为了简单、直观的解析用户的配置数据，我们采用`json`格式来配置`Bean`。（`xml`也是同理的）
* 实现`JsonApplicationContext`主要用于对于`json`结构的配置的加载、解析和对解析后的`Bean`的注册，并使`JsonApplicationContext`的实现从继承`DefaultBeanFactory`变成继承`DefaultListableBeanFactory`。
* 抽象`ListableBeanFactory`，并提供通过类型获取对应所有`Bean`对象的方法。
* 实现`ListableBeanFactory`为`DefaultListableBeanFactory`。