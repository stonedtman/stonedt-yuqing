package com.stonedt.intelligence.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * 
 * @author 文轩
 * @TableName user_wechat_info
 */
@Data
public class UserWechatInfo implements Serializable {
    /**
     * 用户id
     */
    private Integer user_id;

    /**
     * 用户微信id
     */
    private String openid;

    /**
     * 用户微信昵称
     */
    private String nickname;

    /**
     * 普通用户性别，1为男性，2为女性
     */
    private Integer sex;

    /**
     * 普通用户个人资料填写的城市
     */
    private String city;

    /**
     * 普通用户个人资料填写的省份
     */
    private String province;

    /**
     * 国家，如中国为CN
     */
    private String country;

    /**
     * 用户头像，最后一个数值代表正方形头像大小（有0、46、64、96、132数值可选，0代表640*640正方形头像）， 用户没有头像时该项为空
     */
    private String head_img_url;

    /**
     * 用户统一标识。针对一个微信开放平台帐号下的应用，同一用户的unionid是唯一的。
     */
    private Integer unionid;

    /**
     * 用户特权信息，json数组，如微信沃卡用户为（chinaunicom）
     */
    private String privileges;

    /**
     * is_snapshotuser 是否为快照页模式虚拟账号，值为0时是普通用户，1时是虚拟帐号
     */
    private Integer snapshot_user;

    private static final long serialVersionUID = 1L;


}