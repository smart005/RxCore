Android项目开发框架——RxCore核心包
-----
```text
android项目开发基础框架：
1.可自定义灵活配置的网络框架——OkRx;
  a.具体retrofit所有特性;
  b.支持网络数据缓存配置;
  c.支持同一Android项目工程调用不同API服务即Token名和值根据自己动态设置(可作用到类也可作用到某一接口)
  d.支持某一接口输出日志;(便于调试)
  e.具体网络请求基本验证(网络、token、是否必填参数),若不符合要求直接结束无需请求网络;
  f.根据业务需要可对请求的接口过滤(即不提交)值为空的参数;
  g.对多个接口返回数据结构相同且请求地址不同时可合并成一个接口请求;
2.本地缓存模块——RxCache;
3.DES、3DES、MD5加密功能;
4.日志输入组件(Logger.L)——errorinfowaringdebug等;
5.集成并封装glide+aliyun图片加载组件;
.....
```
框架完整集成文档 <a href="https://github.com/smart005/okandroid">![images](https://img.shields.io/badge/OkAndroid-V1.x-brightgreen.svg)</a>

[javadoc文档](http://htmlpreview.github.io/?https://github.com/smart005/RxCore/blob/master/javadoc/index.html)

### 2.功能使用文档
* [ToastUtils工具类](/docs/toast_doc.md)
  * [样式配置](/docs/toast_theme_config.md)
* [文件操作](/docs/file_operation.md)
* [框架中greendao数据库使用](/docs/db_use.md)
* [OkRx网络框架]
	* [验证工具类](/docs/network.md)
	* [初始化配置](/docs/okrx_init.md)
	* [使用方式](/docs/okrx_use.md)
* [应用程序MQ缓存](/docs/app_mq_cache.md)
* [文本首行缩进-TextIndentation](/docs/text_indentation.md)