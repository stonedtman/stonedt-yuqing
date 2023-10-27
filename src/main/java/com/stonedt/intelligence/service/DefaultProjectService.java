package com.stonedt.intelligence.service;

import com.stonedt.intelligence.entity.DefaultProject;
import com.stonedt.intelligence.entity.DefaultSolutionGroup;

import java.util.List;

/**
 * 默认方案service
 * @author 文轩
 */
public interface DefaultProjectService {


    /**
     * 获取默认方案组列表
     */
    List<DefaultSolutionGroup> getDefaultSolutionGroupList();

    /**
     * 获取默认方案列表
     */
    List<DefaultProject> getDefaultSolutionList();

    /**
     * 根据方案组id获取方案列表
     */
    List<DefaultProject> getDefaultSolutionListByGroupId(Long groupId);

}
