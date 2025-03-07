package com.stonedt.intelligence.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.Date;

/**
 * 实时监测文章去重表
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MonitorArticle {

    /**
     * 主键id
     */
    private Integer id;

    /**
     * 检测方案id
     */
    private Long projectId;

    /**
     * 文章链接
     */
    private String articleSourceUrl;

    /**
     * 创建时间
     */
    private Date createTime;

}
