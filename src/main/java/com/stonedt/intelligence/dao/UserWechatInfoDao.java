package com.stonedt.intelligence.dao;

import com.stonedt.intelligence.entity.UserWechatInfo;
import org.apache.ibatis.annotations.Mapper;

/**
* @author 文轩
* @description 针对表【user_wechat_info】的数据库操作Mapper
* @createDate 2023-10-24 16:38:16
* @Entity com.stonedt.intelligence.entity.UserWechatInfo
*/
@Mapper
public interface UserWechatInfoDao {

    UserWechatInfo selectByOpenid(String openid);

    /**
     * 保存微信用户信息
     * @param userWechatInfo 微信用户信息
     */
    void saveWechatUserInfo(UserWechatInfo userWechatInfo);
}




