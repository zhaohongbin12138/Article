### 基于SSM+Activiti的公文管理系统的设计与实现。 

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
