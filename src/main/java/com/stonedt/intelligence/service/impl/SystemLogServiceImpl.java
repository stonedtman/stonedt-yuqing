package com.stonedt.intelligence.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stonedt.intelligence.dao.SystemLogDao;
import com.stonedt.intelligence.entity.SystemLogEntity;
import com.stonedt.intelligence.service.SystemLogService;

@Service
@Slf4j
public class SystemLogServiceImpl implements SystemLogService{

	@Autowired
	private SystemLogDao systemLogDao;
	@Override
	public void addData(SystemLogEntity systemlog) {
		log.info("开始保存日志信息");
		systemLogDao.addData(systemlog);
		log.info("保存日志信息结束");
		
	}
	

}
