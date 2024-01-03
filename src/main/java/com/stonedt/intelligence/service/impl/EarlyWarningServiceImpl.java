
package com.stonedt.intelligence.service.impl;

import java.util.List;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.stonedt.intelligence.dao.SolutionGroupDao;
import com.stonedt.intelligence.dao.SystemDao;
import com.stonedt.intelligence.service.EarlyWarningService;

/**
* <p></p>
* <p>Title: EarlyWarningServiceImpl</p>  
* <p>Description: </p>  
* @author Mapeng 
* @date Apr 18, 2020  
*/
@Slf4j
@Service
public class EarlyWarningServiceImpl implements EarlyWarningService{

	@Autowired
	private SystemDao systemDao;
	@Autowired
	private SolutionGroupDao solutionGroupDao;
	
	@Override
	public boolean saveWarningPopup(Map<String, Object> warning_popup) {
		if (systemDao.exitsWarningPopup(warning_popup)) {
			log.info("预警信息已存在，不再重复预警");
			return false;
		}
		return systemDao.saveWarningPopup(warning_popup);
	}

	@Override
	public Map<String, Object> getWarningArticle(Integer pageNum, Long user_id, Long project_id, Integer openFlag, String keyword) {
		Map<String, Object> resMap = new HashedMap<String, Object>();
		PageHelper.startPage(pageNum, 10);
		List<Map<String, Object>> warningArticle = systemDao.getWarningArticle(user_id,project_id,openFlag,keyword);
		for (int i = 0; i < warningArticle.size(); i++) {
			Long group_id = Long.valueOf(String.valueOf(warningArticle.get(i).get("group_id")));
			String groupName = solutionGroupDao.getGroupName(group_id);
			warningArticle.get(i).put("groupName", groupName);
			Long projectId = (Long)warningArticle.get(i).get("project_id");
			warningArticle.get(i).put("project_id", String.valueOf(projectId));
			Long groupId = (Long)warningArticle.get(i).get("group_id");
			warningArticle.get(i).put("group_id", String.valueOf(groupId));
			String articleTitle = (String) warningArticle.get(i).get("article_title");
			if (articleTitle != null) {
				articleTitle = articleTitle.replaceAll(keyword, "<b style=\"color:red\">" + keyword + "</b>");
				warningArticle.get(i).put("article_title", articleTitle);
			}
		}
		PageInfo<Map<String, Object>> pageInfo = new PageInfo<Map<String,Object>>(warningArticle);
		resMap.put("warningArticle", warningArticle);
		resMap.put("pageInfo", pageInfo);
		return resMap;
	}

	@Override
	public boolean updateWarningArticle(String article_id,Long user_id) {
		return systemDao.updateWarningArticle(article_id, user_id);
	}

	@Override
	public boolean readSign(Map<String, Object> readsign) {
		// TODO 自动生成的方法存根
		return systemDao.readSign(readsign);
	}

	@Override
	public void delReadSign(Map<String, Object> readsign) {
		// TODO 自动生成的方法存根
		systemDao.delReadSign(readsign);
	}

	@Override
	public Map<String, Object> selectReadSign(Map<String, Object> selectreadsign) {
		// TODO 自动生成的方法存根
		return systemDao.selectReadSign(selectreadsign);
	}
}
