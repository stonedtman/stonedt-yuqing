思通舆情 是一款开源免费的舆情系统。

支持本地化部署，支持在线体验。

支持对海量舆情数据分析和挖掘。

[后台管理](https://gitee.com/stonedtx/yuqing-manager) 系统已经开源，预计2022年4月完成开源计划。
<br>

[数据采集](https://gitee.com/stonedtx/open-spider) 系统已经开源，预计2022年6月完成开源计划。

 
无论你是使用者还是共同完善的开发者，欢迎 pull request 或者 留言对我们提出建议。 
<br>
您的支持和参与就是我们坚持开源的动力！请 :star:  [star](https://gitee.com/stonedtx/yuqing) 或者 [fork](https://gitee.com/stonedtx/yuqing)!  

无论你是有相关经验、技术可以一起加入我们的开源项目。

 **[进一步了解，关于我们的一切，请看这里！](https://gitee.com/stonedtx/yuqing/wikis/pages)** 

<br>

同时还会将项目计划和关键性技术讲解，毫无保留的公布于众，让大家对我们有更多的认识和了解。<br>
关于数据采集和数据处理相关技术和开源计划，可以看这两篇文章：

 [【数据采集】技术架构说明文档](https://gitee.com/stonedtx/yuqing/blob/master/dataCollection.md) 和 [【数据处理】技术架构说明文档](https://gitee.com/stonedtx/yuqing/blob/master/dataProcessing.md) 。
<br>
<br><br>

### 思通舆情 的功能：

-   舆情监测：通过全文搜索、来源搜索、热搜监测等多重功能实现对全网文本、图片、视频舆情实时发现;
-   舆情预警：根据用户设置预警条件，判别舆情信息，并第一时间通过多渠道告知用户;
-   舆情分析：全网事件分析、事件分析、传播分析、竞品分析、评论分析、热度指数，6类大数据分析;
-   舆情报告：一键快速生成日、周、月、季度报告,自带行业舆情案例库，为舆情应对提供参考。


### 思通舆情 的优势：

-   开源开放：零门槛，线上快速获取和安装；快速获取用户反馈、按月发布新版本；
-   简单易用：极易上手，通过鼠标点击和拖拽即可完成分析；
-   秒级响应：超大数据量下秒级查询返回延时；


### 思通舆情 的数据：

-   新闻媒体：新闻、app、电子报、境外新闻；
-   网络自媒体：搜狐、百家号、今日头条、博客、企鹅号、微信公众号、微博；
-   境外社交媒体：twitter、facebook、及其他境外社交媒体；
-   论坛：贴吧、论坛、问答、知乎；
-   短视频：抖音、快手，等众多平台。

### 思通舆情 的百科：

关于本舆情系统项目的 心路历程、开发计划、入门文档、团队介绍、实际案例，等等，

你想知道的一切都在这里： **[https://gitee.com/stonedtx/yuqing/wikis/pages](https://gitee.com/stonedtx/yuqing/wikis/pages)** 

<br>

### 思通舆情 技术栈：

- 数据库：MySQL
- 数据检索：Elasticsearch
- 文章储存：Mongodb
- 系统缓存：Redis
- 消息队列：kafak & rabbitMQ
- 深度学习：PaddlePaddle
- 网络爬虫：WebMagic(java) & scrapy（python）
- 开发框架：SpringBoot
- 开发语言：Java JEE



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


## 开源计划

-  整个舆情系统分为四大部分：1.舆情数据展示，2.舆情数据处理，3.舆情数据采集，4.管理后台。

-  由于整个舆情系统非常庞大，目前只开源了第一部分 “舆情数据展示”。

-  舆情数据处理 和 舆情数据采集，我们会先开源文档以及部分代码。

-  舆情数据处理 和 舆情数据采集，目前(2022-2-19)文档和代码正在规整中。

-  舆情系统管理后台 ，将于2022年4月份之前开放源代码。

###  舆情数据采集 
-   目前正在整理编写文档，正式发布时间待定。

    [【数据采集】技术架构说明文档](https://gitee.com/stonedtx/yuqing/blob/master/dataCollection.md)

    [数据采集](https://gitee.com/stonedtx/open-spider) 系统已经开源，预计2022年6月完成开源计划。


### 舆情数据处理 

-   文档预计在2022年3月份提到开源社区。

    [【数据处理】技术架构说明文档](https://gitee.com/stonedtx/yuqing/blob/master/dataProcessing.md)


### 系统管理后台

-  系统很容易上手，简单易懂。2022年4月份 会全部开放源代码，同时也会提供运行部署程序下载。
   
   开源代码访问地址：https://gitee.com/stonedtx/yuqing-manager


## 在线体验

-   环境地址：<http://open-yuqing.stonedt.com/>
-   用户名：13900000000
-   密码：stonedt


## 安装依赖
1. JavaEE 8 以上版本；
2. MySQL 5.5 以上版本；
3. Redis 4.0 以上版本；
 

## 运行版本

仅需三步快速安装 思通舆情：

-  1.安装 MySQL 5.5+、redis 4.0+

-  2.下载 [stonedt-yuqing.zip](https://gitee.com/stonedtx/yuqing/attach_files/994147/download)，解压zip包，执行  _java -jar stonedt-yuqing.jar_，启动舆情系统。 

-  3.本地访问：http://127.0.0.1:8084/
 用户名：13900000000，  密码：stonedt 

_备注：修改配置文件[[application.yml](https://gitee.com/stonedtx/yuqing/blob/master/config/application.yml)]，设定 MySQL、redis 用户和密码等。_ 


## 产品手册

https://gitee.com/stonedtx/yuqing/raw/master/产品手册V1.0.pdf


## 版本更新

   每次版本更新的记录说明都在此，[查看详情](https://gitee.com/stonedtx/yuqing/blob/master/releasenote.md) 。




##  按需定制|数据定制
  当您在开发与研究中遇到  **数据采集、数据处理、舆情系统定制**  等方面的问题，请联系我们，我们会以最快的速度提供专业的解决方案。为您提供必要的专业技术支持。

  服务流程如下

![输入图片说明](https://gitee.com/stonedtx/yuqing/raw/master/ProIMG/data-plan.png)



## 产品经理微信
   扫描微信二维码，技术交流。

<img src="https://gitee.com/stonedtx/yuqing/raw/master/ProIMG/%E8%81%94%E7%B3%BB%E6%88%91%E4%BB%AC-%E4%B8%AA%E4%BA%BA%E5%BE%AE%E4%BF%A1.jpg" title="Logo"  width="220">

##  技术交流群

<img src="https://gitee.com/stonedtx/yuqing/raw/master/ProIMG/weixin-group.png" title="Logo"  width="220">


## 捐赠方式

<img src="https://gitee.com/stonedtx/yuqing/raw/master/ProIMG/Wechat.png" title="Logo"  width="200">

<img src="https://gitee.com/stonedtx/yuqing/raw/master/ProIMG/zhifubao-pay.png" title="Logo"  width="200">


## 感谢捐赠

| 捐赠者   | 日期       | 捐赠寄语      | 金额 |
|---------|-----------|--------------|-----|
| Fra***k | 2022-2-21 | 支持一下国产开源|50元 |



## 联系我们

+ 微信号： techflag  

+ 电话： 13505146123

+ 邮箱： wangtao@stonedt.com

+ 公司官网：[www.stonedt.com](http://www.stonedt.com)

欢迎您在下方留言，或添加微信与我们交流。


## License & Copyright

Copyright (c) 2014-2022 思通数科 StoneDT, All rights reserved.

Licensed under The GNU General Public License version 3 (GPLv3)  (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at

<https://www.gnu.org/licenses/gpl-3.0.html>

Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.