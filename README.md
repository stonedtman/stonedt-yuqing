思通舆情 是一款开源免费的舆情系统。

支持本地化部署，支持在线体验。

支持对海量舆情数据分析和挖掘。

无论你是使用者还是共同完善的开发者，欢迎 pull request 或者 留言对我们提出建议。 
<br>
您的支持和参与就是我们坚持开源的动力！请 :star:  [star](https://gitee.com/stonedtx/yuqing) 或者 [fork](https://gitee.com/stonedtx/yuqing)!  


<br>

### 思通舆情 的功能：

-   舆情监测：通过全文搜索、来源搜索、热搜监测等多重功能实现对全网文本、图片、视频舆情实时发现;
-   舆情预警：根据用户设置预警条件，判别舆情信息，并第一时间通过多渠道告知用户;
-   舆情分析：全网事件分析、事件分析、传播分析、竞品分析、评论分析、热度指数，6类大数据分析;
-   舆情报告：一键快速生成日、周、月、季度报告,自带行业舆情案例库，为舆情应对提供参考。
-   智写报告：提供了丰富的写作模板和样式,用户只需根据模板填充相应内容，后端基于AI技术即可快速生成报告。


## 在线体验

-   环境地址：**<https://open-yuqing.stonedt.com/>**
-   打开页面使用微信扫码，关注微信公众号后，直接登录在线体验。



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
- 开发平台：Java J2EE & SpringBoot
- 采集框架：Spider-flow & WebMagic & HttpClient
- APP采集：Xposed框架
- URL仓库：布隆过滤器 + Redis
- 网页渲染解析：Jvppeteer & Playwright（微软开源） & Selenium
- web应用服务器：Nginx & Tomcat
- 储存任务发送：Kafka & Zookeeper
- 抓取任务发送：RabbitMQ
- 配置管理：MySQL
- 前端展示：Bootstrap & VUE


#### 数据处理
- 开发框架：SpringBoot
- 开发语言：Java J2EE
- 数据暂存：MySQL
- 数据索引：Redis
- 深度学习：PaddlePaddle
- 自然语言处理：HaNLP & THUCTC
- 数据处理和储存任务发送：Kafka & Zookeeper
- 数据中台：自研 & DataEase 
- 数据初始化：集成FlyWay，自动初始化MySQL
- 数据总线：RocketAPI （进行了二次开发）

#### 数据分析
- 数据库：MySQL
- 数据检索：Elasticsearch
- 中文分词器：IK分词
- 相似度计算：Clickhouse
- 数据同步：DataX
- 文章储存：Mongodb
- 数据缓存：Redis
- 消息队列：Kafka & RabbitMQ
- 开发框架：SpringBoot
- 开发语言：Java J2EE
- 图表展示：Apache Echarts & AnyCharts



## UI 展示
![登录页面](https://gitee.com/stonedtx/yuqing/raw/master/ProIMG/login.png)

<img src="https://gitee.com/stonedtx/yuqing/raw/master/ProIMG/jinriredian.png" />

<img src="https://gitee.com/stonedtx/yuqing/raw/master/ProIMG/main2.png" />

<img src="https://gitee.com/stonedtx/yuqing/raw/master/ProIMG/main.png" />

<img src="https://gitee.com/stonedtx/yuqing/raw/master/ProIMG/main3.png" />

<img src="https://gitee.com/stonedtx/yuqing/raw/master/ProIMG/main4.png" />

<img src="https://gitee.com/stonedtx/yuqing/raw/master/ProIMG/report.png" />

<img src="https://gitee.com/stonedtx/yuqing/raw/master/ProIMG/xiangqing.png" />
 
<img src="https://gitee.com/stonedtx/yuqing/raw/master/ProIMG/secher.png" />

<img src="https://gitee.com/stonedtx/yuqing/raw/master/ProIMG/jiance.png" />

<img src="https://gitee.com/stonedtx/yuqing/raw/master/ProIMG/WechatIMG3.png" />


## 系统架构

![输入图片说明](https://gitee.com/stonedtx/yuqing/raw/master/ProIMG/gongneng-jiagou.png)


## 功能架构
![输入图片说明](https://gitee.com/stonedtx/yuqing/raw/master/ProIMG/%E7%B3%BB%E7%BB%9F%E5%8A%9F%E8%83%BD.png)


## 开源计划

-  整个系统分为五个部分：1.数据展示，2.数据处理，3.数据采集，4.数据大屏，5.管理后台。

-  由于整个系统非常庞大，目前只开源了第一部分 “数据展示”。

-  数据处理 和 数据采集，我们会先开源文档以及部分代码。

-  数据大屏 有开源计划，但是具体时间尚未明确。

-  数据处理 和 数据采集，目前(2022-2-19)文档和代码正在规整中。

-  系统管理后台 ，将于2022年4月份之前开放源代码。

-  智写报告 ，将于2023年10月份之前开放源代码。(后端引擎由我司[写作宝](https://www.xiezuobao.net/)团队提供服务与支持)


###  舆情数据采集 
-   目前正在整理编写文档，正式发布时间待定。

    [【数据采集】技术架构说明文档](https://gitee.com/stonedtx/yuqing/blob/master/dataCollection.md)

    [数据采集](https://gitee.com/stonedtx/open-spider) 系统已经开源，预计2022年6月完成开源计划。
    
    【数据采集】已经在2023年9月份，重新架构，预计在2024年打算重新开源。


### 舆情数据处理 

-   文档预计在2022年3月份提到开源社区。

    [【数据处理】技术架构说明文档](https://gitee.com/stonedtx/yuqing/blob/master/dataProcessing.md)

     【数据处理】 中的核心部分已经开源，请查阅 [nlp.stonedt.com](https://nlp.stonedt.com)  和 [https://gitee.com/stonedtx/free-nlp-api](https://gitee.com/stonedtx/free-nlp-api)


### 舆情数据大屏 

-   基础版舆情大屏，目前已于2023年10月份开源。


### 自然语言处理

-   [思通数科-自然语言处理文本挖掘引擎](https://gitee.com/stonedtx/free-nlp-api)


### 系统管理后台

-  系统很容易上手，简单易懂。2022年4月份 会全部开放源代码，同时也会提供运行部署程序下载。
   
   开源代码访问地址：https://gitee.com/stonedtx/yuqing-manager


## Docker 安装

-   1.Docker拉取镜像并运行

在命令行输入下面一行命令即可完成镜像的拉取以及运行,拉取镜像大概需要花费5-10分钟。

```
docker run -itd --name stonedt_yuqing -p 8085:8085 registry.cn-beijing.aliyuncs.com/stonedt_yuqing/stonedt_yuqing:1.0.6
```

-   2.验证是否成功运行

使用docker ps命令获取我们运行的容器ID

```
docker ps
```


使用docker logs 容器ID -f 查看容器日志

```
docker logs 容器ID -f
```
屏幕出现运行日志即为部署成功
<img src="https://gitee.com/stonedtx/yuqing/raw/master/ProIMG/docker_log.png">
访问地址 http://ip:8085 
用户名13900000000 密码stonedt

## 安装依赖
1. JavaEE 8 以上版本；
2. MySQL 5.5 以上版本；
3. Redis 4.0 以上版本；

详见思通舆情安装部署手册：https://gitee.com/stonedtx/yuqing/blob/master/install_guide.md


## 运行版本

仅需三步快速安装 思通舆情：

-  1.安装 MySQL 5.5+、redis 4.0+

-  2.下载 [stonedt-yuqing.zip](https://gitee.com/stonedtx/yuqing/attach_files/1629685/download)，解压zip包，执行  _java -jar stonedt-yuqing.jar_，启动舆情系统。 
-  3.下载 [opinion_screen.zip](https://gitee.com/stonedtx/yuqing/attach_files/1640495/download)，解压zip包，进入apache-tomcat-8.5.46/bin文件夹.执行 ./startup.sh
-  4.配置nginx,在http块中增加如下内容:
    ```text
    server {
            listen       任意端口;
            server_name  域名或者ip;
    
            location / {
                proxy_pass http://127.0.0.1:8084;
            }
            location /opinion_screen {
                proxy_pass http://127.0.0.1:8080;
            }
    }
    ```

-  5.本地访问：http://127.0.0.1:8084/ 请将端口号8084替换为您上一步配置的端口
 用户名：13900000000，  密码：stonedt 

详见思通舆情安装部署手册：https://gitee.com/stonedtx/yuqing/blob/master/install_guide.md

_备注：修改配置文件[[application.yml](https://gitee.com/stonedtx/yuqing/blob/master/config/application.yml)]，设定 MySQL、Redis 用户和密码等。
      修改配置文件[[application.properties](https://gitee.com/stonedtx/yuqing/blob/master/config/application.properties)]，修改xmlFilePath文件路径，并且拷贝config文件下的xml文件到指定路径等。
      舆情大屏下载地址[opinion_screen.zip](https://gitee.com/stonedtx/yuqing/attach_files/1633832/download)_ 


## 安装手册

我们把用户在安装部署中遇到的问题归纳整理成一份文档手册，

详见思通舆情安装部署手册 及 常见问题：

https://gitee.com/stonedtx/yuqing/blob/master/install_guide.md


## 产品手册

https://gitee.com/stonedtx/yuqing/raw/master/产品手册V1.0.pdf


## 版本更新

   每次版本更新的记录说明都在此，[查看详情](https://gitee.com/stonedtx/yuqing/blob/master/releasenote.md) 。



## 联系我们

+ 微信号： javabloger  

+ 电话： 13913853100

+ 邮箱： huangyi@stonedt.com

+ 公司官网：[www.stonedt.com](http://www.stonedt.com)

+ 技术博客：[blog.stonedt.com](http://blog.stonedt.com)

欢迎您在下方留言，或添加微信与我们交流。

### 用户反馈

1.没有找到您需要的数据，可以告诉我们？

2.您在使用产品上遇到的哪些问题？

3.在产品上还有哪些未满足您的需求？


可以在 issues list 中发布您的提问 ([点击这里](https://gitee.com/stonedtx/yuqing/issues/new?issue))

或者

填写 思通舆情·用户反馈表 提交您的提问[https://docs.qq.com/form/page/DQWFFWUtIQWhXQkZB](https://docs.qq.com/form/page/DQWFFWUtIQWhXQkZB)

还可以

直接拨打电话( **13913853100** )或者添加产品经理微信( **javabloger** )， 直接与我们联系。



###  按需定制|数据定制
  当您在开发与研究中遇到  **数据采集、数据处理、舆情系统定制**  等方面的问题，请联系我们，我们会以最快的速度提供专业的解决方案。为您提供必要的专业技术支持。

  服务流程如下

![输入图片说明](https://gitee.com/stonedtx/yuqing/raw/master/ProIMG/data-plan.png)



### 产品经理微信
   扫描微信二维码，技术交流。

<img src="https://gitee.com/stonedtx/yuqing/raw/master/ProIMG/%E8%81%94%E7%B3%BB%E6%88%91%E4%BB%AC-%E4%B8%AA%E4%BA%BA%E5%BE%AE%E4%BF%A1.jpg" title="Logo"  width="220">


###  技术博客

   **博客地址：[http://blog.stonedt.com/](http://blog.stonedt.com/)** 


经过多年耕耘和沉淀，基于海量数据、主流开源技术及自研大数据、人工智能、基础系统架构，目前团队已经建成了完备的系统与技术体系。在此我们将会把自己的实践与经验与大家不断的分享。您可与我们直接沟通技术和产品问题，并获得技术资源、优质文章、用户社群、技术活动等信息。同时也欢迎您能加入我们！

  **微信公众号**

<img src="https://gitee.com/stonedtx/yuqing/raw/master/ProIMG/wxOfficialAccount.jpg" title="Logo"  width="220">
 
###  技术交流群

   微信群已超过200人，无法生成二维码，请添加产品经理微信入群：javabloger 
<img src="https://gitee.com/stonedtx/yuqing/raw/master/ProIMG/%E8%81%94%E7%B3%BB%E6%88%91%E4%BB%AC-%E4%B8%AA%E4%BA%BA%E5%BE%AE%E4%BF%A1.jpg" title="Logo"  width="220">


## 其他开源项目


免费的自然语言处理、情感分析、实体识别、图像识别与分类、OCR识别、语音识别接口，功能强大，欢迎体验。
<br>

访问地址： [https://gitee.com/stonedtx/free-nlp-api](https://gitee.com/stonedtx/free-nlp-api)

<br>

微信扫码在线体验： [https://nlp.stonedt.com](https://nlp.stonedt.com)

<br>


## License & Copyright

Copyright (c) 2014-2023 思通数科 StoneDT, All rights reserved.

Licensed under The GNU General Public License version 3 (GPLv3)  (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at

<https://www.gnu.org/licenses/gpl-3.0.html>

Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.