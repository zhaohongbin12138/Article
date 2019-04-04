### 基于SSM+Activiti的公文管理系统的设计与实现。 

 **注意：请有偿销售源码的人不要在评论区做广告，一经发现，一律删除，绝不姑息！这是开源社区，不是你们卖源码的市场！卖源码请滚回你的淘宝店铺去。** 

### 本项目的相关文件在附件部分有提供。请大家移步附件下载，感谢大家的支持！

项目只是实现了最基本的公文流程的处理，做的不好的地方请大家谅解。 

![输入图片说明](https://gitee.com/uploads/images/2018/0611/150351_13cf4408_884973.png "TIM截图20180611150311.png")

本项目以后会根据大家的意见不定期更新。大家也可以提交对本项目的修改请求，我会采纳好的建议

### 项目实现了以下功能：


### 1) 网站平面设计：

    设计精美但是简洁，清爽的网站页面。 
    公文管理系统主要是方便机关单位工作人员方便的发送公文，该系统包括：组织机构管理，人员管理，权限管理，公文管理 

### 2) 系统模块 


   系统首先默认一个超级管理员，超级管理人员通过excel导入人员机构信息

   机构管理:有权限的用户对机构信息进行增加，编辑，如果机构下面没有人员， 则可以删除，机构合并，可以为该机构分配人员 

   人员管理:有权限的用户对人员进行基本信息的修改，增加，停用不在岗人员账号 

### 3) 公文管理功能模块 


   1 有权限的工作人员进行公文拟稿，附件上传，当用户保存信息，则可以修改，可以删除，但是一旦提交，则不可再修改变动。
 
   2 当公文被提交时，审核流程启动，那么审核功能开启，有权限的人就可以对提交的公文信息进行审核，审核通过则可以发布，打印，审核未通过打回去，又回到1的过程可以编辑再提交，或者直接删除。

项目基于eclipse开发，使用的技术：Spring、SpringMVC、Mybatis、Activiti、Maven、JackRabbit（保存上传文件）

 **系统内所有文件全部采用UTF-8格式编码，如出现乱码，请检查数据库是否设置编码为UTF-8，工程是否设置默认编码为UTF-8。** 

### 系统界面

![公文历史信息查看](https://git.oschina.net/uploads/images/2017/0710/094637_2d8bee24_884973.png "公文历史信息查看")

![公文审核界面](https://git.oschina.net/uploads/images/2017/0710/094737_82c9c715_884973.png "公文审核界面")

![公文审核结果](https://git.oschina.net/uploads/images/2017/0710/094758_da65302d_884973.png "公文审核结果")

### 系统的部署流程：


1.首先创建db_article数据库，注意创建时须采用UTF-8编码，然后运行sql文件创建相关数据表。

2.向Tomcat中部署article.war到webapps目录下，如果需要修改数据库密码请修改WEB-INF\classes目录下的jdbc.properties文件，修改mysql.password的值为当前数据库密码

3.配置tomcat的get请求url默认编码方式为utf-8

4.“启动jackrabbit.bat”启动Jackrabbit

5.启动tomcat，如果没有异常信息则视为启动成功

6.访问“http://localhost:8080/Article/”,使用用户名"zhangsan"和密码“123456”登录系统，若能登录成功则没问题。

### 本系统的部署文件、数据库脚本和相关文档已经全部上传：


1.一切所需资源都在附件里面，去附件里找。

2.初始用户zhangsan,密码123456，密码使用了加密机制，请不要直接修改数据库密码字段，可以运行
article/src/main/java/cn/edu/nuc/article/util/MD5Helper.java来生成原文对应的密码。

3.启动Web容器之前需要先启动JackRabbit，jackrabbit-standalone-2.4.3.jar必须与【启动jackrabbit.bat】放在同一目录下，然后双击【启动jackrabbit.bat】即可启动JackRabbit，需要说明的是：7000端口不可以被占用，运行的机器上必须安装jdk并配置好环境变量。

4.默认情况下：JackRabbit必须和Tomcat在同一台机器上，这个可以改，需要的话请修改FileService这个类的RMI地址：

    private final static String RMI = "http://localhost:7000/rmi";

  把localhost改为你的Jackrabbit所在服务器的IP地址即可！ 