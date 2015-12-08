#web相关、Java web相关


本页内容包括：
* [web服务器](#web服务器)
* [JSON](#JSON)
* [跨源资源共享](#跨源资源共享)
	* [JSONP](#JSONP)（JSON with Padding）
	* [CORS](#CORS)（Cross-Origin Resource Sharing）


##web服务器
Apache HTTP Server Version 2.2 
###基于域名的虚拟主机
***

**为了使用基于域名的虚拟主机，你必须指定服务器IP地址(和可能的端口)来使主机接受请求，这个可以用NameVirtualHost指令来进行配置。**
```
NameVirtualHost *
```
**反向代理**
```
<VirtualHost *>
    ServerName example.com
    Serveralias www.example.com    
    ProxyRequests Off
    ProxyPass / http://www.example_real.com/
    ProxyPassReverse / http://www.example_real.com/
</VirtualHost>
```
*注意:* ProxyPassReverse指令必须加上，以防后端服务器重定向，绕过反向代理器.

##JSON
<http://json.org/json-zh.html>
[Google JSON风格指南](https://github.com/darcyliu/google-styleguide/blob/master/JSONStyleGuide.md)

##跨源资源共享

###JSONP
[JSONP_wiki](https://zh.wikipedia.org/wiki/JSONP)

**原理：**

就是利用`<script>`标签没有跨域限制的“漏洞”（历史遗迹啊）来达到与第三方通讯的目的。当需要通讯时，本站脚本创建一个`<script>`元素，地址指向第三方的API网址，形如：
```javascript
<script src="http://www.example.net/api?param1=1&param2=2"></script>```
并提供一个回调函数来接收数据（函数名可约定，或通过地址参数传递）。第三方产生的响应为json数据的包装（故称之为jsonp，即json padding），形如：

```callback({"name":"hax","gender":"Male"})``` 

这样浏览器会调用callback函数，并传递解析后json对象作为参数。本站脚本可在callback函数里处理所传入的数据。 


###CORS
[官方规范Cross-Origin Resource Sharing](http://www.w3.org/TR/cors/)

该标准定义了在必须访问跨域资源时，浏览器与服务端应该如何沟通，它提供一种机制，允许客户端（如浏览器）对非源站点的资源发出访问请求。所有提供跨源资源请求的API都可以使用本规范中定义的算法。

出于安全性的考虑，用户代理（如浏览器）通常拒绝跨站的访问请求，但这会限制运行在用户代理的Web应用通过Ajax或者其他机制从另一个站点访问资源、获取数据。

跨源资源共享（CORS）扩充了这个模型，通过使用自定义的HTTP响应头部（HTTP Response Header），通知浏览器资源可能被哪些跨源站点以何种HTTP方法获得。

例如，浏览器在访问 http://example.com 站点的Web应用时，Web应用如果需要跨站访问另一站点的资源 http://hello-world.example，就需要使用该标准。http://hello-world.example 在HTTP的响应头部中定义 Access-Control-Allow-Origin: http://example.org，通知浏览器允许 http://example.org 跨源从 http://hello-world.example上获取资源。
