## 思路

拓展`BeanFactory`能力，使得能够保存相同类型的多个`Bean`名称。
## 实现

* `DefaultBeanFactory`中增加Map结构保存`type` -> `BeanNames`的映射。
* 抽象`ListableBeanFactory`，并提供通过类型获取对应所有`Bean`对象的方法。
* 实现`ListableBeanFactory`为`DefaultListableBeanFactory`。
* 修改`JsonApplicationContext`使他直接继承自`DefaultListableBeanFactory`。