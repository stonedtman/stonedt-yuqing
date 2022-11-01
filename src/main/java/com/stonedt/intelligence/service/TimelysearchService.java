package com.stonedt.intelligence.service;

import java.util.List;
import java.util.Map;

import com.stonedt.intelligence.entity.FullPolymerization;
import com.stonedt.intelligence.entity.TimelyPolymerization;

public interface TimelysearchService {

	List<TimelyPolymerization> listFullPolymerization();

	List<Map<String, Object>> getTemplete(String stype);

}
