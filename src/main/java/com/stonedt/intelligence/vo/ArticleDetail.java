package com.stonedt.intelligence.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.apache.poi.ss.usermodel.charts.ChartData;

import java.util.List;
import java.util.Date;

/**
 * Auto-generated: 2024-03-11 19:18:23
 */
@Data
@Schema(name = "ArticleDetail", description = "文章详情")
public class ArticleDetail {

    @Schema(name = "emotionChart", description = "情感图表")
    private List<List<Object>> emotionChart;
    @Schema(name = "emotionText", description = "情感文本")
    private String emotionText;
    @Schema(name = "detail", description = "详情")
    private Detail detail;
    @Schema(name = "text", description = "文本")
    private String text;
    @Schema(name = "title", description = "标题")
    private String title;



    @Data
    public static class Detail {

        @Schema(name = "websitelogo", description = "网站logo")
        private String websitelogo;
        @Schema(name= "code", description = "code")
        private String code;
        @Schema(name = "city", description = "城市")
        private String city;
        @Schema(name = "positive_score", description = "正面分数")
        private String positive_score;
        @Schema(name = "sourcewebsitename", description = "来源网站名称")
        private String sourcewebsitename;
        @Schema(name="title", description = "标题")
        private String title;
        @Schema(name = "source_url", description = "来源url")
        private String source_url;
        @Schema(name = "customlable", description = "自定义标签")
        private String customlable;
        @Schema(name = "content", description = "内容")
        private String content;
        @Schema(name = "attachmentlable", description = "附件标签")
        private String attachmentlable;
        @Schema(name = "emotionalIndex", description = "情感指数")
        private String emotionalIndex;
        @Schema(name = "province", description = "省份")
        private String province;
        @Schema(name = "publish_time", description = "发布时间")
        private Date publish_time;
        @Schema(name = "policylable", description = "政策标签")
        private String policylable;
        @Schema(name = "policy_grade", description = "政策等级")
        private String policy_grade;
        private String ner;
        @Schema(name = "text", description = "文本")
        private String text;
        @Schema(name = "source_name", description = "来源名称")
        private String source_name;
        @Schema(name = "neutrality_score", description = "中立分数")
        private String neutrality_score;
        @Schema(name = "firstcategorylabel", description = "一级分类标签")
        private String firstcategorylabeltwo;
        @Schema(name = "extend_string_one", description = "扩展字符串一")
        private String extend_string_one;
        @Schema(name = "similarvolume", description = "相似文章数量")
        private String similarvolume;
        @Schema(name = "article_category", description = "文章分类")
        private String article_category;
        @Schema(name = "create_time", description = "创建时间")
        private Date create_time;
        @Schema(name = "author", description = "作者")
        private String author;
        @Schema(name = "key_words", description = "关键词")
        private String key_words;
        @Schema(name = "policy_type", description = "政策类型")
        private String policy_type;
        @Schema(name = "contenthtml", description = "内容html")
        private String contenthtml;
        @Schema(name = "author_url", description = "作者url")
        private String author_url;
        @Schema(name = "eventlable", description = "事件标签")
        private String eventlable;
        @Schema(name = "industrylabel", description = "行业标签")
        private String industrylable;
        @Schema(name = "publish_date", description = "发布日期")
        private Date publish_date;
        @Schema(name = "content_html", description = "内容html")
        private String content_html;
        @Schema(name = "negative_score", description = "负面分数")
        private String negative_score;
        @Schema(name = "type_of_article", description = "文章类型")
        private String type_of_article;

    }

}
