package com.stonedt.intelligence.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stonedt.intelligence.dao.TimelysearchDao;
import com.stonedt.intelligence.entity.TimelyPolymerization;
import com.stonedt.intelligence.service.TimelysearchService;





@Service
public class TimelysearchServiceImpl implements TimelysearchService{
	
	
	
	@Autowired
	private TimelysearchDao timelysearchDao;

	@Override
	public List<TimelyPolymerization> listFullPolymerization() {
		// TODO Auto-generated method stub
		return timelysearchDao.listFullPolymerization();
	}

	@Override
	public List<Map<String, Object>> getTemplete(String stype) {
		return timelysearchDao.getTemplete(stype);
	}
	
	

	

}
