思通舆情 是一款开源免费的舆情系统。

支持本地化部署，支持在线体验。

支持对海量舆情数据分析和挖掘。

无论你是使用者还是共同完善的开发者，欢迎 pull request 或者 留言对我们提出建议。 
<br>
您的支持和参与就是我们坚持开源的动力！请 :star:  [star](https://gitee.com/stonedtx/yuqing) 或者 [fork](https://gitee.com/stonedtx/yuqing)!  

如果您在使用思通舆情，请让我们知道，您的使用对我们非常重要：**[查看](https://gitee.com/stonedtx/yuqing/blob/master/feedback.md)
https://gitee.com/stonedtx/yuqing/blob/master/feedback.md**

<br>

### 思通舆情 的功能：

-   舆情监测：通过全文搜索、来源搜索、热搜监测等多重功能实现对全网文本、图片、视频舆情实时发现;
-   舆情预警：根据用户设置预警条件，判别舆情信息，并第一时间通过多渠道告知用户;
-   舆情分析：全网事件分析、事件分析、传播分析、竞品分析、评论分析、热度指数，6类大数据分析;
-   舆情报告：一键快速生成日、周、月、季度报告,自带行业舆情案例库，为舆情应对提供参考。


## 在线体验

-   环境地址：**<http://open-yuqing.stonedt.com/>**
-   用户：13900000000
-   密码：点击登录面“获取体验账号”，关注微信公众号“思通数据”, 在公众号对话框输入“demo”，将会自动返回密码。



### 思通舆情 的优势：

-   开源开放：零门槛，线上快速获取和安装；快速获取用户反馈、按月发布新版本；
-   简单易用：极易上手，通过鼠标点击和拖拽即可完成分析；
-   秒级响应：超大数据量下秒级查询返回延时；


### 思通舆情 的数据：

-   新闻媒体：新闻、app、电子报；
-   网络自媒体：搜狐、百家号、今日头条、博客、企鹅号、微信公众号、微博；
-   论坛：贴吧、论坛、问答、知乎；
-   短视频：抖音、快手，等众多平台。


### 思通舆情 的百科：

关于本舆情系统项目的 心路历程、开发计划、入门文档、团队介绍、实际案例，等等，

你想知道的一切都在这里： **[https://gitee.com/stonedtx/yuqing/wikis/pages](https://gitee.com/stonedtx/yuqing/wikis/pages)** 


<br>

### 思通舆情 技术栈：

#### 数据采集
- 开发平台：Java EE & SpringBoot
- 采集框架：Spider-flow & WebMagic & HttpClient
- APP采集：Xposed框架
- URL仓库：Redis
- 网页渲染解析：Jvppeteer & Playwright（微软开源）
- web应用服务器：Nginx & Tomcat
- 储存任务发送：Kafka & Zookeeper
- 抓取任务发送：RabbitMQ
- 配置管理：MySQL
- 前端展示：Bootstrap & VUE


#### 数据处理
- 开发框架：SpringBoot
- 开发语言：Java JEE
- 数据暂存：MySQL
- 数据索引：Redis
- 深度学习：PaddlePaddle
- 自然语言处理：HaNLP & THUCTC
- 数据处理和储存任务发送：Kafka & Zookeeper
- 数据中台：自研 & DataEase 
- 数据初始化：集成FlyWay，自动初始化MySQL
- 数据总线：RockAPI （进行了二次开发）

#### 数据分析
- 数据库：MySQL
- 数据检索：Elasticsearch
- 中文分词器：IK分词
- 相似度计算：Clickhouse
- 数据同步：DataX
- 文章储存：Mongodb
- 数据缓存：Redis
- 消息队列：kafak & rabbitMQ
- 开发框架：SpringBoot
- 开发语言：Java JEE
- 图表展示：Apache  Echarts & anyCharts



## UI 展示
![登录页面](https://gitee.com/stonedtx/yuqing/raw/master/ProIMG/login.png)

<img src="https://gitee.com/stonedtx/yuqing/raw/master/ProIMG/main2.png" />

<img src="https://gitee.com/stonedtx/yuqing/raw/master/ProIMG/main.png" />

<img src="https://gitee.com/stonedtx/yuqing/raw/master/ProIMG/main3.png" />

<img src="https://gitee.com/stonedtx/yuqing/raw/master/ProIMG/main4.png" />

<img src="https://gitee.com/stonedtx/yuqing/raw/master/ProIMG/report.png" />

<img src="https://gitee.com/stonedtx/yuqing/raw/master/ProIMG/shijian.png" />

<img src="https://gitee.com/stonedtx/yuqing/raw/master/ProIMG/xiangqing.png" />
 
<img src="https://gitee.com/stonedtx/yuqing/raw/master/ProIMG/secher.png" />

<img src="https://gitee.com/stonedtx/yuqing/raw/master/ProIMG/jiance.png" />

<img src="https://gitee.com/stonedtx/yuqing/raw/master/ProIMG/WechatIMG12912.png" />

<img src="https://gitee.com/stonedtx/yuqing/raw/master/ProIMG/WechatIMG12910.png" />


## 系统架构

![输入图片说明](https://gitee.com/stonedtx/yuqing/raw/master/ProIMG/gongneng-jiagou.png)


## 功能架构
![输入图片说明](https://gitee.com/stonedtx/yuqing/raw/master/ProIMG/%E7%B3%BB%E7%BB%9F%E5%8A%9F%E8%83%BD.png)