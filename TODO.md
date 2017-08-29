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
18. 使用GSON生成Json字符串，以后为了压缩包可替换
19. 添加临时文件管理器FileManager来管理上传的文件
15. 为Request解析Cookie
~~16. 为Request_multi解析para~~
17. multipart/form-date中的中文问题
~~18. 重写multipart解析部分，增加更多的可读性，修改变量名称~~
19. 将Request中的Head类提取出来，不再作为一个类，而作为一个单独字段，使用map存储




条件：
6. 只需要支持urlencode和多表单提交
7. 请求体只支持http/1.1中的keep-alive，且只支持含有content-length的报文
8. 凡事带有filename的表单都保存到文件中，且以二进制保存

最佳实践：
toString()生成Json