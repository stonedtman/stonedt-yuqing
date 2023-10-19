
package com.stonedt.intelligence.vo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;
import java.util.Date;

/**
 * @author 文轩
 * 文章数据
 */
@Data
@Schema(name = "ArticleData", description = "文章数据")
public class ArticleData {

    /**
     * 网站logo
     */
    @Schema(name = "websitelogo", description = "网站logo")
    private String websitelogo;
    /**
     * 来源网站名称
     */
    @Schema(name = "sourcewebsitename", description = "来源网站名称")
    private String sourcewebsitename;

    private String _score;

    /**
     * 文章涉及词列表
     */
    @Schema(name = "relatedWord", description = "文章涉及词列表")
    private List<String> relatedWord;
    /**
     * 来源url
     */
    @Schema(name = "source_url", description = "来源url")
    private String source_url;
    /**
     * 情感标签
     */
    @Schema(name = "emotionalIndex", description = "情感标签")
    private Integer emotionalIndex;
    /**
     * 政策标签
     */
    @Schema(name = "policylableflag", description = "政策标签")
    private String policylableflag;

    /**
     * 标签
     */
    @Schema(name = "attachmentlable", description = "标签")
    private String attachmentlable;
    /**
     * 发布时间
     */
    @Schema(name = "publish_time", description = "发布时间")
    private Date publish_time;
    /**
     * 数据来源类型
     */
    @Schema(name = "datasource_type", description = "数据来源类型")
    private String datasource_type;

    /**
     * 政策标签
     */
    @Schema(name = "policylable", description = "政策标签")
    private String policylable;

    /**
     * 实体
     */
    @Schema(name = "ner", description = "实体")
    private String ner;

    /**
     * 涉及组织机构类型列表
     * 使用空格分隔
     */
    @Schema(name = "orgtypelist", description = "涉及组织机构类型列表,使用空格分隔")
    private String orgtypelist;

    /**
     * 中性情感分数
     * 0-1
     */
    @Schema(name = "neutrality_score", description = "中性情感分数,浮点数,0-1")
    private String neutrality_score;

    /**
     * 文章id
     */
    @Schema(name = "article_id", description = "文章id")
    private String article_public_id;

    /**
     * 创建时间
     */
    @Schema(name = "create_time", description = "创建时间")
    private Date create_time;

    /**
     * 文章作者
     */
    @Schema(name = "author", description = "文章作者")
    private String author;

    /**
     * es类型
     */
    @Schema(name = "es_type", description = "es类型")
    private String es_type;

    /**
     * 处理标记
     */
    @Schema(name = "dealflag", description = "处理标记")
    private Integer dealflag;

    /**
     * 种子id
     */
    @Schema(name = "seed_id", description = "种子id")
    private Long seed_id;

    /**
     * 热度值
     */
    @Schema(name = "heatvolume", description = "热度值")
    private String heatvolume;

    /**
     * 标题关键词
     */
    @Schema(name = "titlekeyword", description = "标题关键词")
    private String titlekeyword;

    /**
     * 作者url
     */
    @Schema(name = "author_url", description = "作者url")
    private String author_url;

    /**
     * 事件标签
     */
    @Schema(name = "eventlable", description = "事件标签")
    private String eventlable;

    /**
     * 发布日期
     */
    @Schema(name = "publish_date", description = "发布日期")
    private Date publish_date;

    /**
     * 网站id
     */
    private Integer website_id;

    /**
     * 文章类型
     */
    @Schema(name = "type_of_article", description = "文章类型")
    private String type_of_article;

    /**
     * 负面分数
     */
    @Schema(name = "negative_score", description = "负面分数")
    private String negative_score;

    /**
     * 数据来源
     * 0:全部
     * 1:微信
     * 2:微博
     * 3:政务
     * 4:论坛
     * 5:新闻
     * 6:报刊
     * 7:客户端
     * 8:网站
     * 9:外媒
     * 10:视频
     * 11:博客
     */
    @Schema(name = "classify", description = "数据来源,0:全部,1:微信,2:微博,3:政务,4:论坛,5:新闻,6:报刊,7:客户端,8:网站,9:外媒,10:视频,11:博客")
    private Integer classify;

    /**
     * 网站id
     */
    @Schema(name = "otherwebsiteid", description = "网站id")
    private Integer otherwebsiteid;


    private String kafka_queue_name;

    /**
     * 正面情感分数
     */
    @Schema(name = "positive_score", description = "正面情感分数")
    private String positive_score;


    private String extend_string_five;


    private String otherseedid;

    /**
     * 标题
     */
    @Schema(name = "title", description = "标题")
    private String title;

    /**
     * 客户标签
     */
    @Schema(name = "customlable", description = "客户标签")
    private String customlable;

    /**
     * 文章内容
     */
    @Schema(name = "content", description = "文章内容")
    private String content;

    /**
     * 涉及高科技企业类型列表
     */
    @Schema(name = "hightechtypelist", description = "涉及高科技企业类型列表")
    private String hightechtypelist;

    /**
     * 抓取时间
     */
    @Schema(name = "spider_time", description = "抓取时间")
    private Date spider_time;

    /**
     * 涉及企业类型列表
     */
    @Schema(name = "enterprisetypelist", description = "涉及企业类型列表")
    private String enterprisetypelist;


    private String hbase_table;

    /**
     * 发布日期
     */
    @Schema(name = "publishdate", description = "发布日期")
    private Date publishdate;

    private String es_index;

    /**
     * 来源
     */
    @Schema(name = "source_name", description = "来源")
    private String source_name;

    /**
     * 相似文章数量
     */
    @Schema(name = "similarvolume", description = "相似文章数量")
    private String similarvolume;

    /**
     * 附加信息
     */
    @Schema(name = "attachment", description = "附加信息")
    private Extend_string_one extend_string_one;

    /**
     * 文章分类
     */
    @Schema(name = "article_category", description = "文章分类")
    private String article_category;

    /**
     * 关键词
     */
    @Schema(name = "key_words", description = "关键词")
    private String key_words;

    /**
     * 分类标签
     */
    @Schema(name = "categorylable", description = "分类标签")
    private String categorylable;

    /**
     * 作者头像
     */
    @Schema(name = "author_avatar", description = "作者头像")
    private String author_avatar;

    /**
     * 行业标签
     */
    @Schema(name = "industrylable", description = "行业标签")
    private String industrylable;

}