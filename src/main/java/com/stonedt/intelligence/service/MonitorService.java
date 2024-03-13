package com.stonedt.intelligence.service;

import com.alibaba.fastjson.JSONObject;
import com.stonedt.intelligence.entity.OpinionCondition;
import com.stonedt.intelligence.entity.Project;
import com.stonedt.intelligence.vo.ArticleData;
import com.stonedt.intelligence.vo.PageInfo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * description: 数据监测服务 <br>
 * date: 2020/4/16 18:47 <br>
 * author: huajiancheng <br>
 * version: 1.0 <br>
 */
public interface MonitorService {
    JSONObject getCondition(JSONObject paramJson);

    JSONObject getArticleList(JSONObject paramJson);

    /**
     * 获取相似文章列表
     * @param paramJson
     * @return
     */
    JSONObject getSimilarArticleList(JSONObject paramJson);

    JSONObject getGroupNameById(JSONObject paramJson);

    void exportArticleList(JSONObject paramJson, HttpServletResponse response, HttpServletRequest request);

    boolean boolUserProjectByUserId(HttpServletRequest request);

	JSONObject getArticleSynthesizeList(JSONObject paramJson);

	JSONObject getArticleIndustryList(JSONObject paramJson);

	JSONObject getArticleEventList(JSONObject paramJson);

	JSONObject getArticleProvinceList(JSONObject paramJson);

	JSONObject getArticleCityList(JSONObject paramJson);

	JSONObject getAnalysisArticleList(JSONObject paramJson);

    /**
     * 根据偏好设置获取文章列表
     */
    PageInfo<ArticleData> getArticleListByOpinionCondition(OpinionCondition opinionCondition, Project project,Integer pageNum);
}
