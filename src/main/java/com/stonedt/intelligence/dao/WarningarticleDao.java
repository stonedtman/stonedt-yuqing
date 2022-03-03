package com.stonedt.intelligence.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;


@Mapper
public interface WarningarticleDao {

	List<Map<String, Object>> selectWAlsitByUser(@Param("user_id")Long user_id);
	
}
