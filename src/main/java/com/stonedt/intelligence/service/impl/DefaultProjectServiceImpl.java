package com.stonedt.intelligence.service.impl;

import com.stonedt.intelligence.dao.DefaultProjectDao;
import com.stonedt.intelligence.dao.DefaultSolutionGroupDao;
import com.stonedt.intelligence.entity.DefaultProject;
import com.stonedt.intelligence.entity.DefaultSolutionGroup;
import com.stonedt.intelligence.service.DefaultProjectService;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * @author 文轩
 */
@Service
public class DefaultProjectServiceImpl implements DefaultProjectService {

    private final DefaultSolutionGroupDao defaultSolutionGroupDao;

    private final DefaultProjectDao defaultProjectDao;

    public DefaultProjectServiceImpl(DefaultSolutionGroupDao defaultSolutionGroupDao,
                                     DefaultProjectDao defaultProjectDao) {
        this.defaultSolutionGroupDao = defaultSolutionGroupDao;
        this.defaultProjectDao = defaultProjectDao;
    }

    /**
     * 获取默认方案组列表
     */
    @Override
    public List<DefaultSolutionGroup> getDefaultSolutionGroupList() {
        return defaultSolutionGroupDao.selectAll();
    }

    /**
     * 获取默认方案列表
     */
    @Override
    public List<DefaultProject> getDefaultSolutionList() {
        return defaultProjectDao.selectAll();
    }

    /**
     * 根据方案组id获取方案列表
     *
     * @param groupId
     */
    @Override
    public List<DefaultProject> getDefaultSolutionListByGroupId(Long groupId) {
        return defaultProjectDao.selectByGroupId(groupId);
    }
}
