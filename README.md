## 思路

本版本是希望增加新的对于Bean的初始化配置，包括lazy-init(懒加载)和scope(作用域，即是否单例)

## 实现

* 修改BeanDefinition，增加lazyInit和scope字段 
* 修改DefaultBeanFactory的registerBeanDefinition函数，增加对于lazy-init的判断，
增加新函数registerSingletonBean实现如果存在就返回bean，如果不存在就注册Bean，
修改增加getType（方法重载），直接用ClassLoader加载ClassName从而得到Class对象。