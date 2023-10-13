package com.stonedt.intelligence.vo;


import java.util.HashMap;

/**
 * @author 文轩
 */
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

    public CopyWriting() {
    }

    public CopyWriting(Integer promptId, Float temperature, String knowledgeBaseId, HashMap<String, String> params) {
        this.promptId = promptId;
        this.temperature = temperature;
        this.knowledgeBaseId = knowledgeBaseId;
        this.params = params;
    }

    public Integer getPromptId() {
        return promptId;
    }

    public void setPromptId(Integer promptId) {
        this.promptId = promptId;
    }

    public Float getTemperature() {
        return temperature;
    }

    public void setTemperature(Float temperature) {
        this.temperature = temperature;
    }

    public String getKnowledgeBaseId() {
        return knowledgeBaseId;
    }

    public void setKnowledgeBaseId(String knowledgeBaseId) {
        this.knowledgeBaseId = knowledgeBaseId;
    }

    public HashMap<String, String> getParams() {
        return params;
    }

    public void setParams(HashMap<String, String> params) {
        this.params = params;
    }
}
