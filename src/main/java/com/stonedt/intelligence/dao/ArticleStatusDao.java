package com.stonedt.intelligence.dao;

import com.alibaba.fastjson.JSONArray;
import com.stonedt.intelligence.entity.ArticleStatus;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ArticleStatusDao {


    int saveData(@Param("userId")Integer userId, @Param("dateTime")String dateTime,@Param("articleList") JSONArray articleList);

    List<ArticleStatus> findListByAid(@Param("userId")Integer userId, @Param("aidList")List<String> aidList);
    List<ArticleStatus> findListByArticle(@Param("userId")Integer userId, @Param("articleList") JSONArray articleList);


    int   removeData(@Param("userId")Integer userId, @Param("articleList") JSONArray articleList);

}
