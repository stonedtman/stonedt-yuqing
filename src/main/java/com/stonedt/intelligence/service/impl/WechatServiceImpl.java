package com.stonedt.intelligence.service.impl;



import cn.hutool.core.util.IdUtil;
import com.alibaba.fastjson.JSON;
import com.stonedt.intelligence.dao.UserDao;
import com.stonedt.intelligence.dao.UserWechatInfoDao;
import com.stonedt.intelligence.dto.QrCodeInput;
import com.stonedt.intelligence.dto.QrcodeData;
import com.stonedt.intelligence.dto.WechatUserInfo;
import com.stonedt.intelligence.dto.WxMpXmlMessage;
import com.stonedt.intelligence.entity.User;
import com.stonedt.intelligence.thred.ThreadPoolConst;
import com.stonedt.intelligence.util.DateUtil;
import com.stonedt.intelligence.util.ResultUtil;
import okhttp3.OkHttpClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import com.stonedt.intelligence.service.WechatService;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.net.URLEncoder;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
public class WechatServiceImpl implements WechatService {

	private final RestTemplate restTemplate;

	private final StringRedisTemplate redisTemplate;

	private final UserWechatInfoDao userWechatInfoDao;

	private final UserDao userDao;

	@Value("${wechat.qrcode.url}")
	private String getQrcodeUrl;

	public WechatServiceImpl(RestTemplate restTemplate,
							 StringRedisTemplate redisTemplate,
							 UserWechatInfoDao userWechatInfoDao,
							 UserDao userDao) {
		this.restTemplate = restTemplate;
		this.redisTemplate = redisTemplate;
		this.userWechatInfoDao = userWechatInfoDao;
		this.userDao = userDao;
	}


	/**
	 * 根据场景值生成二维码地址
	 *
	 * @return 二维码地址
	 */
	@Override
	public ResultUtil getQRCodeUrl() {
		//生成场景值,以yuqing:开头
		String sceneStr = "yuqing:" + System.nanoTime();
		QrCodeInput qrCodeInput = new QrCodeInput();
		qrCodeInput.setSceneStr(sceneStr);
		qrCodeInput.setExpireSeconds(600);
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=UTF-8");
		HttpEntity<QrCodeInput> request = new HttpEntity<>(qrCodeInput, headers);
		//生成二维码
		String qrcodeUrl;
		try {
			qrcodeUrl = restTemplate.postForObject(getQrcodeUrl, request, String.class);
		} catch (RestClientException e) {
			e.printStackTrace();
			return ResultUtil.build(500,"生成二维码失败");
		}
		if (qrcodeUrl == null) {
			return ResultUtil.build(500,"生成二维码失败");
		}
		//返回二维码地址
		QrcodeData qrcodeData = new QrcodeData(qrcodeUrl, sceneStr);
		return ResultUtil.ok(qrcodeData);
	}

	/**
	 * 关注事件处理
	 *
	 * @param wxMpXmlMessage 微信消息
	 * @return 该用户是否已经存在
	 */
	@Override
	public Boolean handleSubscribe(WxMpXmlMessage wxMpXmlMessage) {
		//获取用户openid
		String openid = wxMpXmlMessage.getFromUser();
		//获取场景值
		String eventKey = wxMpXmlMessage.getEventKey();
		//查询数据库是否存在该用户
		User user = userDao.selectUserByOpenid(openid);
		if (user != null) {
			redisTemplate.opsForValue().set(eventKey, JSON.toJSONString(user), 10, TimeUnit.MINUTES);
			return true;
		}
		//如果不存在,则将openid存入redis,并设置过期时间为10分钟
		redisTemplate.opsForValue().set(openid, eventKey, 10, TimeUnit.MINUTES);
		return false;
	}

	/**
	 * 授权事件处理
	 *
	 * @param wechatUserInfo
	 */
	@Override
	public void handleAuthorize(WechatUserInfo wechatUserInfo) {
		String openid = wechatUserInfo.getOpenid();
		//查询用户是否存在
		User user = userDao.selectUserByOpenid(openid);
		if (user == null) {
			//如果不存在,则创建用户
			user = new User();
			//生成分布式id
			long nextId = IdUtil.getSnowflake(1, 1).nextId();
			user.setUser_id(nextId);
			user.setOpenid(openid);
			user.setUsername(wechatUserInfo.getNickname());
			user.setLogin_count(0);
			user.setCreate_time(DateUtil.nowTime());
			user.setTerm_of_validity(new Date(2524608000000L));
			user.setNlp_flag(0);
			user.setXie_flag(0);
			user.setStatus(1);
			user.setUser_type(0);
			user.setUser_level(0);
			userDao.saveUser(user);
			//获取事件key
			String eventKey = redisTemplate.opsForValue().get(openid);
			//将用户信息存入redis
			redisTemplate.opsForValue().set(eventKey, JSON.toJSONString(user), 10, TimeUnit.MINUTES);
			//将用户信息存入数据库
			wechatUserInfo.setUser_id(user.getId());
			userWechatInfoDao.saveWechatUserInfo(wechatUserInfo);
		}

	}

	@Override
	public ResultUtil checkLogin(String sceneStr, HttpServletRequest request) {
		if (sceneStr == null || "".equals(sceneStr)) {
			return ResultUtil.build(500, "场景值不能为空");
		}

//        String openId = SENSE_STR.get(sceneStr);
		String result = redisTemplate.opsForValue().get(sceneStr);
		if (result == null||"".equals(result)) {
			return ResultUtil.build(204, "未获取到用户操作");
		}
		User user = JSON.parseObject(result, User.class);
		request.getSession().setAttribute("User", user);
		ThreadPoolConst.IO_EXECUTOR.execute(() -> userDao.updateUserLoginCountById(user.getId()));
		return ResultUtil.ok();
	}
}
