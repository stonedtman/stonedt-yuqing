package com.stonedt.intelligence.dao;

import com.stonedt.intelligence.entity.Analysis;
import com.stonedt.intelligence.entity.KeywordHandler;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface KeywordHandlerDao {

     void insert(@Param("keywordHandler") KeywordHandler keywordHandler);

     KeywordHandler getByAnalysisId(@Param("analysisId") Integer analysisId);

     void updateMonitorAnalysis(Analysis anlysisByProjectidAndTimeperiod);
}
