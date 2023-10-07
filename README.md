# 工学院信息管理系统

#### 一、准备工作
1. JDK 1.8.0_152
2. maven 3.8.4
3. redis 3.2.1
4. mysql 8.0.25
5. SQLyog
6. IDEA 2023
7. Postman


#### 二、项目搭建和运行

1. 启动redis和mysql服务；
2. 在SQLyog工具中导入数据库脚本文件；
3. 拷贝maven仓库到本地；
4. 在IDEA上配置好Maven；
5. 从github上下载项目到本地，然后导入到IDEA；
6. 修改配置文件（数据库的地址、账号、密码之类）
7. 运行cn.edu.gzgs.ims.Application类，启动项目。

#### 三、测试

1.  首先在Postman上新建请求<br/>
  - 登录：<br/>
  请求地址：http://localhost:8888/login<br/>
  请求方式：post<br/>
  请求体：<br/>
  {
     "pwd": "123",
     "userName": "user"
  }<br/>

  - 退出登录<br/>
  请求地址：http://localhost:8888/logout<br/>
  请求方式：get<br/>
  请求头：token=xxxxxxxxxxxxxx<br/>

  - 查询<br/>
  请求地址：http://localhost:8888/sys/query<br/>
  请求方式：post<br/>
  请求体：name=user<br/>
  请求头：token=xxxxxxxxxxxxxx<br/>

  - 删除
  请求地址：http://localhost:8888/sys/delete<br/>
  请求方式：post<br/>
  请求体：userId=3<br/>
  请求头：token=xxxxxxxxxxxxxx<br/>


2.  测试用户登录认证（分四步）<br/>
  第一步：先测试登录，登录成功后返回以下内容：<br/>
  {
    "code": 0,
    "data": "eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiJiY2U3YTVlOTg2ZTc0YmE5OWZlMjBlZWJjYTllM2Y2ZSIsInN1YiI6IjgxM2IwMzVlLTM3NzQtNDY4Zi05NGM4LTJmZjE2NzNjODE5MyIsImlzcyI6InhpYW96aGkiLCJpYXQiOjE2OTU5Njc5ODUsImV4cCI6MTY5NTk2OTc4NX0.2_bGH_fDz8gu1bfGvmkE3EZoUn-ZTo2kOmx88-y5owQ",
    "message": "登录成功"
  }<br/>
  第二步：测试查询和删除，查看返回结果；<br/>
  第三步：测试退出登录；<br/>
  {
    "code": 0,
    "message": "退出成功"
  }<br/>
  第四步：再次测试查询，这时候会提示：<br/>
  {
    "code": -1,
    "message": "未登录或登陆已失效，请先登录",
    "data": null
  }<br/>

3.  测试权限（分三步）<br/>
  第一步：先用user用户登录；<br/>
  第二步：修改菜单表或角色表的状态，0代表可用，1代表不可用；<br/>
  第三步：重新测试查询和删除，观察不同状态下的返回结果：<br/>

#### 四、参考资料
[Springboot + Spring Security + Jwt 前后端分离认证授权](https://blog.csdn.net/qq_46090071/article/details/126891101?ops_request_misc=%257B%2522request%255Fid%2522%253A%2522169576932716800192260228%2522%252C%2522scm%2522%253A%252220140713.130102334.pc%255Fall.%2522%257D&request_id=169576932716800192260228&biz_id=0&utm_medium=distribute.pc_search_result.none-task-blog-2~all~first_rank_ecpm_v1~rank_v31_ecpm-1-126891101-null-null.142^v94^insert_down28v1&utm_term=springboot%20mybatisplus%20Spring%20Security%20jwt%20swaggerui&spm=1018.2226.3001.4187)


#### 五、项目结构
cn.edu.gzgs.ims.common：存放一些通用的类<br/>
cn.edu.gzgs.ims.config：存放与配置相关的类<br/>
cn.edu.gzgs.ims.controller：存放控制器类<br/>
cn.edu.gzgs.ims.dao：存放数据访问类<br/>
cn.edu.gzgs.ims.domain：存放实体类，例如封装请求参数的类<br/>
cn.edu.gzgs.ims.entity：用于存放与数据库表相对应的实体类<br/>
cn.edu.gzgs.ims.filter：存放过滤器类，比如登录认证过滤器<br/>
cn.edu.gzgs.ims.handlers：存放处理器类，比如异常处理器、权限处理器等<br/>
cn.edu.gzgs.ims.service：存放业务类<br/>
cn.edu.gzgs.ims.utils：存放一些工具类<br/>
resources/sql：存放数据库的映射文件<br/>
resoucess/application.yml 项目配置文件<br/>
