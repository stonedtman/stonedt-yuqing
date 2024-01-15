package com.stonedt.intelligence.dao;

import com.stonedt.intelligence.entity.UserPopUp;
import org.apache.ibatis.annotations.Mapper;

/**
* @author 文轩
* @description 针对表【user_pop_up】的数据库操作Mapper
* @createDate 2024-01-15 16:13:23
* @Entity com.stonedt.intelligence.entity.UserPopUp
*/
@Mapper
public interface UserPopUpDao {

    UserPopUp selectOne(Integer id);

    void insert(UserPopUp userPopUp);

    void plusOne(Integer id);
}




