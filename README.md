# dp-BOOT(账户:admin/scott/张三/test1/123/scott1   密码:123456)
基于SpringBoot框架的权限管理系统，支持操作权限和数据权限，后端采用SpringBoot、Mybatis、Shiro，前端采用adminLTE、vue.js、bootstrap-table、tree-grid、layer，对前后端进行封装，可快速完成CRUD的开发，基于项目结构通过代码生成器可生成前端后台部分代码，更加方便地进行二次开发。项目采用Maven分模块构建，方便扩展自定义模块。

### 项目介绍
- 一个轻量级的Java快速开发框架，基于SpringBoot开发，能快速开发项目并交付
- 友好的代码结构及注释，便于阅读及二次开发，命名规范和工程分层规约参考阿里巴巴JAVA开发规范
- 前后端开发封装，快速实现CRUD开发
- 支持通过beetl模板生成部分代码，可直接生成到项目路径，无须二次部署（见文档）
- 基于角色的权限管理，支持操作权限和持数据权限
- 基于Maven模块化开发，可快速扩展个性化业务模块
- 封装常用开发组件，目前已集成select2、switchery及富文本组件
### REST接口支持
- 采用jwt方案解决授权信息加密传输，并兼容服务端校验
- 接口请求链接拦截模式：/rest/**，所有接口请求地址以/rest为前缀即可
- 匿名访问接口通过增加@RestAnon注解即可
- 登录地址/rest/auth，通过校验后可获取token
- 调用/rest/authStatus异步校验token状态
- token所有合法性参数在服务端管理，可自行扩展校验，比如时间戳，限制ip，UA检测等策略
### 项目拓展
- [基于系统参数管理实现动态select控件](下拉选择组件)
- [基于ajaxfileupload.js实现文件上传](https://my.oschina.net/Mr.薛/blog/1615214)
- [switchery]（开关组件）
- [editor]（富文本编辑器）
- [diyUpload] （文件上传）
### 技术方案
- 核心框架：SpringBoot
- ORM框架：Mybatis
- 安全框架：Shiro
- 模板框架：beetl
- 主页框架：adminLTE(Bootstrap)
- JS框架：vue.js
- 表格插件：bootstrap-table(扩展分页跳转)
- 树形表格：tree-grid(基于bootstrap扩展)
- 树形插件：ztree
- 弹窗组件：layer
- 日期组件：laydate
- 下拉选择组件：select2
- 开关组件：switchery
- 富文本组件：wangEditor
- 表单校验：validator

### 命名规范（参考阿里巴巴Java开发手册）
-  获取单个对象的方法用 get 做前缀
-  获取多个对象的方法用 list 做前缀
-  获取统计值的方法用 count 做前缀
-  插入的方法用 save(推荐) 或 insert 做前缀
-  删除的方法用 remove(推荐) 或 delete 做前缀
-  修改的方法用 update 做前缀

### 备注
-  doc:说明文件以及数据库
- jars-lib:外部引入的jar(暂时没有)