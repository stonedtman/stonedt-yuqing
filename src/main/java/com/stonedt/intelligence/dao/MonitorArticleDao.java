package com.stonedt.intelligence.dao;

import com.stonedt.intelligence.entity.MonitorArticle;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface MonitorArticleDao {

    int insert(MonitorArticle monitorArticle);

    int selectCountByProjectIdAndSourceUrl(@Param("projectId") Long projectId,
                                           @Param("sourceUrl") String sourceUrl);

}




