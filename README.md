## 说明

由于开启了https，所以本地测试时必须修改hosts映射到 www.imuster.top 才能进行本地测试。

后台管理的仓库地址：https://github.com/hhcol620/IRH_manage.git

微信小程序端的仓库地址：https://github.com/hhcol620/wechatApp.git



## 项目结构

![image-20200728145104647](https://github.com/HMingR/images/blob/master/irh/image-20200728145104647.png)



## 系统架构

![image-20200728170119794](https://github.com/HMingR/images/blob/master/irh/image-20200728170119794.png)



## 所需要的环境

1. Java 8

2. mysql 5.7

3. redis 5.0.1

4. mongodb

5. elastic search

6. 阿里的fastDFS

7. nginx

8. RabbitMQ

   

## Maven配置

如果maven下载的jar包下载不全，可以参考我的maven镜像配置

```xml
<mirror>
    <id>alimaven</id>
   <name>aliyun maven</name>
 <url>http://maven.aliyun.com/nexus/content/groups/public/</url>
   <mirrorOf>central</mirrorOf>
</mirror>

<mirror>
          <id>central</id>
          <name>Maven Repository Switchboard</name>
          <url>http://repo1.maven.org/maven2/</url>
          <mirrorOf>central</mirrorOf>
</mirror>
```

 

## 修改环境配置

可以直接在最外层的 pom.xml 中的 <profiles> 中修改成自己的配置,默认是使用 id 为 hmr 的配置。

## 配置第三方API

说明：第三方API需要自己去相应的官网注册申请，并查看对应的SDK文档；一般来说，使用以下API都可以免费使用限量次数。

1. 配置图像内容识别第三方API

   图像内容识别API主要是使用在用户实名认证时提交的学生证或者身份证，用于提取出姓名和身份号。

   去**百度云AI平台**注册账号之后搜索 **OCR** ，在控制台创建 **iOCR通用版** 即可自定义图片识别模版。

   ![image-20200728155320867](https://github.com/HMingR/images/blob/master/irh/image-20200728155320867.png)

   申请成功之后可根据官方提供的文档申请**accessToken** ，然后将accessToken、创建的模版签名、填入 **irh-security**模块下的application.yml中，如下图

   ![image-20200728153145952](https://github.com/HMingR/images/blob/master/irh/image-20200728153145952.png)

2. 配置内容审核第三方API

   内容审核主要是使用在发布商品时，对用户填写的标题、主体信息、图片进行审核；主要审核的是内容是否含有暴力、色情、政治相关的信息。

   使用的是**华为云内容审核** 

   ![image-20200728155244041](https://github.com/HMingR/images/blob/master/irh/image-20200728155244041.png)

   申请成功之后修改 **irh-common-core模块中的HuaweiModerationTextContentUtil.java**下无参构造函数中的**ak和sk**，如下图

   ![image-20200728153837040](https://github.com/HMingR/images/blob/master/irh/image-20200728153837040.png)

## 

## 小程度截图：

<img src="https://github.com/HMingR/images/blob/master/irh/image-20200728171129146.png" alt="image-20200728171129146" style="zoom:30%;" />

​																										首页



<img src="https://github.com/HMingR/images/blob/master/irh/image-20200728171231041.png" alt="image-20200728171231041" style="zoom:30%;" />

​																										公益页面



<img src="https://github.com/HMingR/images/blob/master/irh/2FBC5F03BDF7D9C0658A89B1A1FCE007.jpg" alt="2FBC5F03BDF7D9C0658A89B1A1FCE007" style="zoom:30%;" />

​																										个人中心



<img src="https://github.com/HMingR/images/blob/master/irh/EDAF65BF9A771E40C85A03362DCB9979.jpg" alt="EDAF65BF9A771E40C85A03362DCB9979" style="zoom:30%;" />

​																											登陆页面





后台管理页面截图：

​	![image-20200728180132543](https://github.com/HMingR/images/blob/master/irh/image-20200728180132543.png)





![image-20200728180157893](https://github.com/HMingR/images/blob/master/irh/image-20200728180157893.png)

