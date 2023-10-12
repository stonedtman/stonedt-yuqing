package com.stonedt.intelligence.vo;

import lombok.Data;

import java.util.HashMap;

/**
 * @author 文轩
 */
@Data
public class CopyWriting {

    /**
     * 模板id
     */
    private Integer promptId;

    /**
     * 模型想象力
     */
    private Float temperature;

    /**
     * 知识库id
     */
    private String knowledgeBaseId;

    /**
     * 动态参数
     */
    private HashMap<String,String> params;
}
