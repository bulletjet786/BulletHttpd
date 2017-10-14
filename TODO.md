~~1. 编写GET方法解析测试~~
~~2. URL的编码问题（只需要支持utf-8）~~
~~3. 添加queryString字段，然后重新生成hashcode和equals方法~~
~~4. 图片传输（解析二进制流）~~
~~5. 使用ByteArrayOutputStream缓存数据，重写方法~~
~~8.请求头中的URL编码~~
~~9.请求体中的编码转换问题~~
~~10. 重写request类，加入更多的元素：queryString~~
~~11. 为request类添加paras用来保存请求行和请求头中的参数信息，重新生成hashcode和同String~~
~~12. 重写RequestResolverTest中的GET和POST_encode测试类，添加对queryString和paras的测试~~
~~17. 重写Request的toString方法，生成Json字符串来辅助测试~~
~~20. 编写Utils.find(byte[] bytes, byte[] found,int count)用来查找第count个found的方法，并进行测试~~
~~21. 完成Utils.split(byte[] bytes, byte[] split)方法并进行测试~~
~~16. 为Request_multi解析para~~
~~18. 重写multipart解析部分，增加更多的可读性，修改变量名称~~
~~19. 将Request中的Head类提取出来，不再作为一个类，而作为一个单独字段，使用map存储~~
~~19. 添加临时文件管理器FileManager来管理上传的文件~~
~~15. 为Request解析Cookie~~
17. multipart/form-date中的中文问题
~~20. 为FileManager添加删除所有文件的功能~~
21. 添加关于FileManager的测试代码
22. 为Request添加FileManager
23. 修改FileManager，使其更加易于测试
~~(不适用Random，使用计数器)~~
~~24. get.txt测试文件出错！~~
24. 重写Response,将headers取出
~~25. 在执行器中使用keepAlive特性~~
~~25. 将FileManager的初始化操作提前~~
~~26. 提取出Context和Configuration类~~
~~27. 去除所有的初始化块~~
~~28. Cookie的expire的格式化~~
~~30. 为了方便，CookieManager改为继承特化的Map，不再使用~~
31. 重写Request和Response的cookie逻辑
32. 不应该使用spilt分割"="，而应该使用先查找到第一个"="，然后以此下标进行切分，
    考虑字符串：BAIDUID=FE8DCF7A6F252B926ED7309BABAF1F3E:FG=1;
    包括： cookie(1), param(0), body(0), header(0)
33. 构建工具包(Httpd)封装
33. 将RequestResolver和ResponseGenerator改写成无副作用的类
~~34. 重写FileManager~~
35. 使用FileManager桩对象测试RequestResolver
~~36. 添加RequestResolverTest清理文件的功能~~
36. 添加响应结束后删除上传文件的功能
37. KeepAlive有Bug，暂时移除（只有一次请求时仍然创建两个Request对象）
    原因： 第一个Request的请求会将请求的数据读完，但是不包括EOF
          由于没有检测到EOF，会创建第二个请求，然后读出EOF
    难点： 暂时无法判断是否还有第二次请求，读完第一个请求后不知道后续是EOF还是第二个请求的数据
    候选方案： 读出新请求的第一个字节，然后传入解析器，连接后续流
38. 检查访问权限进行优化

条件：
6. 只需要支持urlencode和多表单提交
7. 请求体只支持http/1.1中的keep-alive，且只支持含有content-length的报文
8. 凡事带有filename的表单都保存到文件中，且以二进制保存

当前限制：

感受：
1. 别想这么多
2. 要测试