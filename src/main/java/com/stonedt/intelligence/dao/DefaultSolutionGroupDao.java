package com.stonedt.intelligence.dao;

import com.stonedt.intelligence.entity.DefaultSolutionGroup;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
* @author 文轩
* @description 针对表【default_solution_group】的数据库操作Mapper
* @createDate 2023-10-27 14:47:17
* @Entity com.stonedt.intelligence.entity.DefaultSolutionGroup
*/
@Mapper
public interface DefaultSolutionGroupDao {

    /**
     * 查询所有数据
     * @return 对象列表
     */
    List<DefaultSolutionGroup> selectAll();
}




