package com.stonedt.intelligence.service;

import com.github.pagehelper.PageInfo;
import com.stonedt.intelligence.entity.DatafavoriteEntity;

import java.util.List;
import java.util.Map;

public interface DatafavoriteService {

	String adddata(Long user_id, String id, Long projectid, Long groupid, String title, String source_name,
			String emotionalIndex, String publish_time);

	void updateemtion(String id, int flag, String es_search_url,String publish_time);

	void deletedata(String id, int flag, String es_search_url,String publish_time);

	String updatedata(Long user_id, String id, Long projectid, Long groupid, String title, String source_name,
			String emotionalIndex, String publish_time);

	DatafavoriteEntity selectdata(Long user_id, String id);


	/**
	 * 获取收藏夹列表
	 * @param userId 用户id
	 * @param projectid 方案id
	 * @param pageNum 页码
	 */
	Map<String, Object> getFavoriteList(Integer pageNum, long userId, Long projectid);

}
