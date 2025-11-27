package com.stonedt.intelligence.dao;

import com.alibaba.fastjson.JSONArray;
import com.stonedt.intelligence.entity.ArticleRead;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ArticleReadDao {


    int saveData(@Param("userId")Integer userId, @Param("dateTime")String dateTime, @Param("articleList") JSONArray articleList);

    List<ArticleRead> findListByAid(@Param("userId")Integer userId, @Param("aidList")List<String> aidList);
    List<ArticleRead> findListByArticle(@Param("userId")Integer userId, @Param("articleList") JSONArray articleList);

    List<String> findListByEvent(@Param("userId")Integer userId, @Param("eventLabel")String eventLabel, @Param("startTime")String startTime, @Param("endTime")String endTime);


    int removeData(@Param("userId")Integer userId, @Param("articleList") JSONArray articleList);

}
