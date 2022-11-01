package com.stonedt.intelligence.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.stonedt.intelligence.entity.TimelyPolymerization;


@Mapper
public interface TimelysearchDao {

	List<TimelyPolymerization> listFullPolymerization();

	List<Map<String, Object>> getTemplete(String stype);

}
