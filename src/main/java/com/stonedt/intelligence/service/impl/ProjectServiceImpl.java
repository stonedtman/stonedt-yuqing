package com.stonedt.intelligence.service.impl;

import java.awt.event.WindowAdapter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.stonedt.intelligence.dao.DatafavoriteDao;
import com.stonedt.intelligence.dao.OpinionConditionDao;
import com.stonedt.intelligence.entity.ReportCustom;
import com.stonedt.intelligence.entity.User;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.stonedt.intelligence.dao.ProjectDao;
import com.stonedt.intelligence.entity.Project;
import com.stonedt.intelligence.service.ProjectService;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProjectServiceImpl implements ProjectService {

    @Autowired
    private ProjectDao projectDao;
    @Autowired
    private OpinionConditionDao opinionConditionDao;
    @Autowired
    private DatafavoriteDao datafavoriteDao;

    @Override
    public List<Map<String, Object>> getGroupInfoByUserId(Long userId) {

        return projectDao.getGroupInfoByUserId(userId, 0);
    }


    public int insertProject(Project p) {
        // TODO Auto-generated method stub
        return projectDao.insertProject(p);
    }


    @Override
    public List<Map<String, Object>> getProjectInfoByGroupIdAndUserId(Map<String, Object> map) {
        List<Map<String, Object>> list = projectDao.getProjectAndGroupInfoByUserId(map);
        return list;
    }

    @Override
    public List<Project> getInfoByGroupId(Long groupId) {
        return null;
    }


    @Override
    public List<Project> listProjects() {
        return projectDao.listProjects();
    }


    /**
     * @param [map]
     * @return java.util.Map<java.lang.String,java.lang.Object>
     * @description: 根据方案id获取方案信息 <br>
     * @version: 1.0 <br>
     * @date: 2020/4/15 15:16 <br>
     * @author: huajiancheng <br>
     */

    @Override
    public Map<String, Object> getProjectByProjectId(Map<String, Object> map) {
        return projectDao.getProjectByProjectId(map);
    }


    @Override
    public int timingProject(Map<String, Object> map) {
        return projectDao.timingProject(map);
    }


    @Override
    public String getProjectName(Long projectId) {
        return projectDao.getProjectName(projectId);
    }

    @Override
    public Map<String, Object> getProjectByProId(Long projectId) {
        return projectDao.getProjectByProId(projectId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer delProject(Map<String, Object> map) {
        datafavoriteDao.delDatafavoriteByProjectId(Long.valueOf((String) map.get("project_id")));
        return projectDao.delProject(map);
    }

    @Override
    public Integer editProjectInfo(Map<String, Object> map) {
        Integer count = projectDao.editProjectInfo(map);
        return count;
    }

    @Override
    public Integer getProjectCount(Map<String, Object> map) {
        return projectDao.getProjectCount(map);
    }

    @Override
    public Integer updateOpinionConditionById(Map<String, Object> map) {
        return opinionConditionDao.updateOpinionConditionById(map);
    }

    @Override
    public Map<String, Object> getOpinionConditionById(Map<String, Object> map) {
        return opinionConditionDao.getOpinionConditionById(map);
    }

    @Override
    public List<Project> listProjectByGroupId(Long groupId) {
        return projectDao.listProjectByGroupId(groupId);
    }


    @Override
    public Map<String, Object> queryUserid(Long user_id) {
        return projectDao.queryUserid(user_id);
    }


    @Override
    public Map<String, Object> batchUpdateProject(Long userId, String list) {
        Map<String, Object> result = new HashMap<String, Object>();
        try {
            if (StringUtils.isBlank(list)) {
                result.put("state", true);
                result.put("message", "删除方案成功！");
                return result;
            }
            List<Long> parseArray = JSON.parseArray(list, Long.class);
            if (parseArray.isEmpty()) {
                result.put("state", true);
                result.put("message", "删除方案成功！");
                return result;
            }
            int batchUpdateProject = projectDao.batchUpdateProject(userId, parseArray);
            if (batchUpdateProject > 0) {
                result.put("state", true);
                result.put("message", "删除方案成功！");
            } else {
                result.put("state", false);
                result.put("message", "删除方案失败！");
            }
        } catch (Exception e) {
            e.printStackTrace();
            result.put("state", false);
            result.put("message", "删除方案失败！");
        }
        return result;
    }


    @Override
    public Map<String, Object> getProjectCountByGroupId(Long groupId) {
        Map<String, Object> result = new HashMap<>();
        Integer projectCountByGroupId = projectDao.getProjectCountByGroupId(groupId);
        result.put("state", true);
        result.put("count", projectCountByGroupId);
        return result;
    }

    @Override
//    public JSONObject getAllKeywords(Integer page, Integer pageSize) {
    public JSONObject getAllKeywords() {
        JSONObject response = new JSONObject();
        try {
            List keywordsList = new ArrayList();
//            PageHelper.startPage(page, pageSize);
            List<Map<String, Object>> list = projectDao.getAllKeywords();
//            PageInfo<Map<String, Object>> pageInfo = new PageInfo<>(list);
//            Integer totalPage = pageInfo.getPages();
//            Long totalData = pageInfo.getTotal();
//            Integer currentPage = page;
            for (int i = 0; i < list.size(); i++) {
                try {
                    Map<String, Object> map = list.get(i);
                    String subject_word = String.valueOf(map.get("subject_word"));
                    String subject_words[] = subject_word.split(",");
                    for (int j = 0; j < subject_words.length; j++) {
                        String keyword = subject_words[j];
                        keywordsList.add(keyword);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            String responseKeywords = StringUtils.join(keywordsList, ",");
            response.put("code", 200);
//            response.put("totalPage", totalPage);
//            response.put("totalData", totalData);
//            response.put("page", currentPage);
            response.put("data", responseKeywords);
            response.put("msg", "获取关键词成功！");
        } catch (Exception e) {
            e.printStackTrace();
            response.put("code", 500);
            response.put("data", "");
//            response.put("totalPage", 1);
//            response.put("totalData", 0);
//            response.put("page", 1);
            response.put("msg", e.getMessage());
        }
        return response;
    }

    @Override
    public Map<String, Object> getProjectInfoById(Map<String, Object> map) {
        return projectDao.getProjectInfoById(map);
    }

    /**
     * 根据用户id获取方案列表
     *
     * @param userId 用户id
     * @return 方案列表
     */
    @Override
    public List<Project> listProjectByUserId(long userId) {
        return projectDao.listProjectByUserId(userId);
    }

    @Override
    public Map<String, Object> getMobileGroupAndProject(User user) {
        Map<String, Object> result = new HashMap<>();
        result.put("code", 200);
        result.put("msg", "用户方案和方案组返回成功");

        List<Long> projectIdListByUserId = projectDao.getProjectIdListByUserId(user.getUser_id());
        if (projectIdListByUserId.isEmpty()) {
            result.put("data", new ArrayList<>());
            return result;
        }

        List<Map<String, Object>> projectList = projectDao.getMobileGroupAndProject(projectIdListByUserId);
        //组装，获取方案组和方案
        List<Map<String, List<Map<String, Object>>>> groupList = new ArrayList<>();

        for (Map<String, Object> project : projectList) {
            Long groupId = (Long) project.get("group_id");
            String groupName = (String) project.get("group_name");
            project.put("group_id", groupId.toString());
            project.put("project_id", project.get("project_id").toString());
            String key = groupId + "-" + groupName;
            boolean flag = false;
            for (Map<String, List<Map<String, Object>>> stringListMap : groupList) {
                if (stringListMap.containsKey(key)) {
                    stringListMap.get(key).add(project);
                    flag = true;
                    break;
                }
            }

            if (!flag) {
                Map<String, List<Map<String, Object>>> groupMap = new HashMap<>();
                List<Map<String, Object>> projectMapList = new ArrayList<>();
                groupMap.put(key, projectMapList);
                projectMapList.add(project);
                groupList.add(groupMap);
            }

        }
        result.put("data", groupList);
        return result;
    }

}
