package com.stonedt.intelligence.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.stonedt.intelligence.entity.DatafavoriteEntity;

@Mapper
public interface DatafavoriteDao {  

	int adddata(DatafavoriteEntity favorite);

	List<DatafavoriteEntity> getdatafavoriteByUser(@Param("user_id")Long user_id);
	

	void updatedata(DatafavoriteEntity favorite);

	DatafavoriteEntity selectdata(Map<String, Object> map);

	/**
	 * 获取收藏夹列表
	 * @param userId 用户id
	 * @param projectid 方案id
	 * @return
	 */
	List<DatafavoriteEntity> getdatafavoriteByUserAndProjectid(Long userId, Long projectid);

	/**
	 * 根据方案id删除收藏夹
	 * @param projectId 方案id
	 */
	void delDatafavoriteByProjectId(Long projectId);

	/**
	 * 根据方案组id删除收藏夹
	 * @param groupId 方案组id
	 */
	void delDatafavoriteByGroupid(Long groupId);
}
