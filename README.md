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

<a href="https://github.com/smart005/RxCore/javadoc/index.html" target="_blank">[javadoc文档]</a>