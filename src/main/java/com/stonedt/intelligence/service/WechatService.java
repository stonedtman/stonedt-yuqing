package com.stonedt.intelligence.service;

import com.stonedt.intelligence.dto.WechatUserInfo;
import com.stonedt.intelligence.dto.WxMpTemplateMessage;
import com.stonedt.intelligence.dto.WxMpXmlMessage;
import com.stonedt.intelligence.entity.User;
import com.stonedt.intelligence.util.ResultUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public interface WechatService {

	/**
	 * 根据场景值生成二维码地址
	 *
	 * @return 二维码地址
	 */
	ResultUtil getQRCodeUrl();

	/**
	 * 关注事件处理
	 * @return 该用户是否已经存在
	 */
	Boolean handleSubscribe(WxMpXmlMessage wxMpXmlMessage);


	/**
	 * 取消关注事件处理
	 * @param openId 用户的openId
	 */
	void handleUnsubscribe(String openId);

	/**
	 * 授权事件处理
	 */
	void handleAuthorize(WechatUserInfo wechatUserInfo);

	ResultUtil checkLogin(String sceneStr, HttpServletRequest request, HttpServletResponse response) throws Exception;

	void send(WxMpTemplateMessage wxMpTemplateMessage);

	ResultUtil getBindQRCodeUrl(User user);


	ResultUtil checkBind(User user, HttpServletRequest request, HttpServletResponse response) throws Exception;

	ResultUtil wasBind(String sceneStr, HttpServletRequest request, HttpServletResponse response) throws Exception;
}
