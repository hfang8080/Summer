## 思路

本版本是希望增加新的对于`Bean`的初始化配置，包括`lazy-init`(懒加载)和`scope`(作用域，即是否单例)

## 实现

* 修改`BeanDefinition`，增加`lazyInit`和`scope`字段 
* 修改`DefaultBeanFactory`的`registerBeanDefinition`函数，增加对于`lazy-init`的判断，
增加新函数`registerSingletonBean`实现如果存在就返回`bean`，如果不存在就注册`Bean`，
修改增加`getType`（方法重载），直接用`ClassLoader`加载`ClassName`从而得到`Class`对象。