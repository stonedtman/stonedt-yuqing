package com.stonedt.intelligence.dao;

import com.stonedt.intelligence.entity.FullPolymerization;
import com.stonedt.intelligence.entity.WechatConfig;
import org.apache.ibatis.annotations.Mapper;

/**
* @author 文轩
* @description 针对表【wechat_config】的数据库操作Mapper
* @createDate 2024-01-11 14:56:37
* @Entity com.stonedt.intelligence.entity.WechatConfig
*/
@Mapper
public interface WechatConfigDao {

     WechatConfig selectLast();
}




