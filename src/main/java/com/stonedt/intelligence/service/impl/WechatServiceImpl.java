package com.stonedt.intelligence.service.impl;



import cn.hutool.core.util.IdUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.stonedt.intelligence.aop.SystemLogAspect;
import com.stonedt.intelligence.constant.WechatConstant;
import com.stonedt.intelligence.dao.*;
import com.stonedt.intelligence.dto.*;
import com.stonedt.intelligence.entity.*;
import com.stonedt.intelligence.service.*;
import com.stonedt.intelligence.thred.ThreadPoolConst;
import com.stonedt.intelligence.util.DateUtil;
import com.stonedt.intelligence.util.ResultUtil;
import com.stonedt.intelligence.util.UserUtil;
import eu.bitwalker.useragentutils.UserAgent;
import eu.bitwalker.useragentutils.Version;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author 文轩
 */
@Service
@Slf4j
public class WechatServiceImpl implements WechatService {

	private final RestTemplate restTemplate;

	private final StringRedisTemplate redisTemplate;

	private final UserWechatInfoDao userWechatInfoDao;

	private final UserDao userDao;

	private final ProjectService projectService;

	private final DefaultProjectService defaultProjectService;

	private final SolutionGroupService solutionGroupService;

	private final OpinionConditionService opinionConditionService;

	private final SystemService systemService;

	private final ProjectTaskDao projectTaskDao;

	private final DefaultOpinionConditionDao defaultOpinionConditionDao;

	private final SystemLogService systemLogService;

	private final UserUtil userUtil;

	private final WechatConfigDao wechatConfigDao;

	@Value("${wechat.url}")
	private String wechatUrl;


	@Value("${account.effective-days}")
	private Integer effectiveDays;

	public WechatServiceImpl(RestTemplate restTemplate,
                             StringRedisTemplate redisTemplate,
                             UserWechatInfoDao userWechatInfoDao,
                             UserDao userDao, ProjectService projectService,
                             DefaultProjectService defaultProjectService,
                             SolutionGroupService solutionGroupService,
                             OpinionConditionService opinionConditionService,
                             SystemService systemService,
                             ProjectTaskDao projectTaskDao,
                             DefaultOpinionConditionDao defaultOpinionConditionDao,
                             SystemLogService systemLogService,
                             UserUtil userUtil, WechatConfigDao wechatConfigDao) {
		this.restTemplate = restTemplate;
		this.redisTemplate = redisTemplate;
		this.userWechatInfoDao = userWechatInfoDao;
		this.userDao = userDao;
		this.projectService = projectService;
		this.defaultProjectService = defaultProjectService;
		this.solutionGroupService = solutionGroupService;
		this.opinionConditionService = opinionConditionService;
		this.systemService = systemService;
		this.projectTaskDao = projectTaskDao;
		this.defaultOpinionConditionDao = defaultOpinionConditionDao;
		this.systemLogService = systemLogService;
		this.userUtil = userUtil;
        this.wechatConfigDao = wechatConfigDao;
    }


	/**
	 * 根据场景值生成二维码地址
	 *
	 * @return 二维码地址
	 */
	@Override
	public ResultUtil getQRCodeUrl() {
		//生成场景值,以yuqing:开头
		long nanoedTime = System.nanoTime();
		//转换成16进制
		String suffix = Long.toHexString(nanoedTime);
		String sceneStr = "yuqing:" + suffix;
		QrcodeData qrcodeData = getQRCodeUrl(sceneStr);
		if (qrcodeData == null) {
			return ResultUtil.build(500, "生成二维码失败");
		}
		return ResultUtil.ok(qrcodeData);
	}


	/**
	 * 根据场景值生成二维码地址
	 */
	private QrcodeData getQRCodeUrl(String sceneStr) {
		QrCodeInput qrCodeInput = new QrCodeInput();
		qrCodeInput.setSceneStr(sceneStr);
		qrCodeInput.setExpireSeconds(600);
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=UTF-8");
		HttpEntity<QrCodeInput> request = new HttpEntity<>(qrCodeInput, headers);
		//生成二维码
		String qrcodeUrl;
		try {
			qrcodeUrl = restTemplate.postForObject(wechatUrl + WechatConstant.GET_QRCODE, request, String.class);
		} catch (RestClientException e) {
			return null;
		}
		if (qrcodeUrl == null) {
			return null;
		}
        return new QrcodeData(qrcodeUrl, sceneStr);
	}


	/**
	 * 关注事件处理
	 *
	 * @param wxMpXmlMessage 微信消息
	 * @return 该用户是否已经存在
	 */
	@Override
	public Boolean handleSubscribe(WxMpXmlMessage wxMpXmlMessage) {

		//获取场景值
		String eventKey = wxMpXmlMessage.getEventKey();
		//删除qrscene_前缀
		eventKey = eventKey.replace("qrscene_", "");

		if (eventKey.endsWith("#pass")) {
			return true;
		}

		//判断是登陆扫码还是绑定扫码
		if (eventKey.endsWith("#bind")) {
			//绑定扫码
			return handleBind(wxMpXmlMessage);
		}else {
			//登陆扫码
			return handleLogin(wxMpXmlMessage);
		}


	}

/**
	 * 绑定扫码事件处理
	 * @param wxMpXmlMessage 微信消息
	 * @return 该用户是否已经存在
	 */
	private boolean handleBind(WxMpXmlMessage wxMpXmlMessage) {
		//获取用户openid
		String openid = wxMpXmlMessage.getFromUser();
		//获取场景值
		String eventKey = wxMpXmlMessage.getEventKey();
		//删除qrscene_前缀
		eventKey = eventKey.replace("qrscene_", "");
		//获取userid 删除yuqing:前缀 #bind后缀
		String userIdStr = eventKey.substring(7, eventKey.length() - 5);
		//转换为10进制int
		int userId = Integer.parseInt(userIdStr, 16);

		//查询数据库是否存在该用户
		User user = userDao.selectUserByOpenid(openid);
		if (user != null && user.getId() == userId) {
			if (user.getWechatflag() == 0) {
				//则更新用户状态
				ThreadPoolConst.IO_EXECUTOR.execute(() -> userDao.updateUserWechatFlagByOpenid(openid, 1));
				user.setWechatflag(1);
			}
			redisTemplate.opsForValue().set(eventKey, JSON.toJSONString(user), 10, TimeUnit.MINUTES);
			return true;
		}
		//如果不存在,则将openid存入redis,并设置过期时间为10分钟
		redisTemplate.opsForValue().set(openid, eventKey, 10, TimeUnit.MINUTES);
		return false;
	}

	/**
	 * 登录扫码事件处理
	 * @param wxMpXmlMessage 微信消息
	 * @return 该用户是否已经存在
	 */
	private boolean handleLogin(WxMpXmlMessage wxMpXmlMessage) {
		//获取用户openid
		String openid = wxMpXmlMessage.getFromUser();
		//获取场景值
		String eventKey = wxMpXmlMessage.getEventKey();
		//删除qrscene_前缀
		eventKey = eventKey.replace("qrscene_", "");
		//查询数据库是否存在该用户
		User user = userDao.selectUserByOpenid(openid);
		if (user != null) {
			if (user.getWechatflag() == 0) {
				//则更新用户状态
				ThreadPoolConst.IO_EXECUTOR.execute(() -> userDao.updateUserWechatFlagByOpenid(openid, 1));
				user.setWechatflag(1);
			}
			redisTemplate.opsForValue().set(eventKey, JSON.toJSONString(user), 10, TimeUnit.MINUTES);
			return true;
		}
		//如果不存在,则将openid存入redis,并设置过期时间为10分钟
		redisTemplate.opsForValue().set(openid, eventKey, 10, TimeUnit.MINUTES);
		return false;
	}

	/**
	 * 取消关注事件处理
	 *
	 * @param openId 用户的openId
	 */
	@Override
	public void handleUnsubscribe(String openId) {
		//更新用户状态
		userDao.updateUserWechatFlagByOpenid(openId,0);
	}

	/**
	 * 授权事件处理
	 *
	 * @param wechatUserInfo
	 */
	@Override
	public void handleAuthorize(WechatUserInfo wechatUserInfo) {


		String openid = wechatUserInfo.getOpenid();

		//获取事件key
		String eventKey = redisTemplate.opsForValue().get(openid);

		if (eventKey == null) {
			return;
		}

		if (eventKey.endsWith("#pass")) {
			return;
		}
		//判断是否是绑定扫码
		if (eventKey.endsWith("#bind")) {
			//绑定扫码
			handleBindAuthorize(wechatUserInfo, eventKey);
		} else {
			//登陆扫码
			handleLoginAuthorize(wechatUserInfo, eventKey);
		}



	}

	/**
	 * 事务处理绑定扫码事件
	 * @param wechatUserInfo
	 * @param eventKey
	 */
	public void handleBindAuthorize(WechatUserInfo wechatUserInfo, String eventKey) {
		//获取userid 删除yuqing:前缀 #bind后缀
		String userIdStr = eventKey.substring(7, eventKey.length() - 5);
		//转换为10进制int
		int userId = Integer.parseInt(userIdStr, 16);
		//插入用户微信信息
		UserWechatInfo userWechatUserInfo = new UserWechatInfo(wechatUserInfo);
		userWechatUserInfo.setUser_id(userId);
		userWechatInfoDao.saveWechatUserInfo(userWechatUserInfo);
		//更新用户openid
		userDao.updateOpenidById(userId, wechatUserInfo.getOpenid());
	}

	public void handleLoginAuthorize(WechatUserInfo wechatUserInfo,String eventKey) {
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
			user.setTerm_of_validity(new Date(System.currentTimeMillis()+effectiveDays*24L*60*60*1000));
			user.setNlp_flag(0);
			user.setXie_flag(0);
			user.setStatus(1);
			user.setUser_type(0);
			user.setUser_level(0);
			user.setWechatflag(1);
			userDao.saveUser(user);
			//删除qrscene_前缀
			eventKey = eventKey.replace("qrscene_", "");
			//将用户信息存入redis
			redisTemplate.opsForValue().set(eventKey, JSON.toJSONString(user), 10, TimeUnit.MINUTES);
			//将用户信息存入数据库
			UserWechatInfo userWechatUserInfo = new UserWechatInfo(wechatUserInfo);
			Integer userId = user.getId();
			userWechatUserInfo.setUser_id(userId);
			userWechatInfoDao.saveWechatUserInfo(userWechatUserInfo);
			//单线程线程池,确保有序执行
			ThreadPoolConst.SINGLE_EXECUTOR.execute(() ->{
				//获取默认方案组列表
				List<DefaultSolutionGroup> defaultSolutionGroupList = defaultProjectService.getDefaultSolutionGroupList();
				String create_time = DateUtil.getNowTime();
				for (DefaultSolutionGroup defaultSolutionGroup : defaultSolutionGroupList) {
					//获取默认方案列表
					List<DefaultProject> defaultProjectList = defaultProjectService.getDefaultSolutionListByGroupId(defaultSolutionGroup.getGroup_id());
					//插入方案组
					SolutionGroup solutionGroup = new SolutionGroup();
					solutionGroup.setGroupName(defaultSolutionGroup.getGroup_name());
					solutionGroup.setDelStatus(0);
					solutionGroup.setUserId(nextId);
					long groupId = IdUtil.getSnowflake(2, 1).nextId();
					solutionGroup.setGroupId(groupId);
					solutionGroupService.addSolutionGroup(solutionGroup);

					for (DefaultProject defaultProject : defaultProjectList) {
						//查询默认方案的偏好设置
						DefaultOpinionCondition defaultOpinionCondition = defaultOpinionConditionDao.getByProjectId(defaultProject.getProject_id());
						//插入方案
						long projectId = IdUtil.getSnowflake(3, 1).nextId();
						Project project = new Project();
						project.setProjectDescription(defaultProject.getProject_description());
						project.setProjectName(defaultProject.getProject_name());
						project.setProjectType(defaultProject.getProject_type());
						project.setCharacterWord(defaultProject.getCharacter_word());
						project.setEventWord(defaultProject.getEvent_word());
						project.setRegionalWord(defaultProject.getRegional_word());
						project.setStopWord(defaultProject.getStop_word());
						project.setSubjectWord(defaultProject.getSubject_word());
						project.setDelStatus(0);
						project.setProjectId(projectId);
						project.setGroupId(groupId);
						project.setUserId(nextId);
						projectService.insertProject(project);
						//插入偏好设置
						OpinionCondition opinionCondition = new OpinionCondition();
						opinionCondition.setProject_id(projectId);
						opinionCondition.setOpinion_condition_id(IdUtil.getSnowflake(4, 1).nextId());
						if (defaultOpinionCondition == null) {
							opinionCondition.setMatchs(1);
							opinionCondition.setPrecise(0);
							opinionCondition.setEmotion("[1,2,3]");
							opinionCondition.setSimilar(0);
							opinionCondition.setSort(1);
							opinionCondition.setTime(1);
						}else {
							BeanUtils.copyProperties(defaultOpinionCondition, opinionCondition);

						}

						addOpinionCondition(opinionCondition, create_time);
						//插入预警设置
						addWarningCondition(projectId, create_time);
						//插入方案计划
						addProjectPlan(project);
					}
				}
			});
		}
	}




	@Override
	public ResultUtil checkLogin(String sceneStr, HttpServletRequest request, HttpServletResponse response) throws Exception {
		if (sceneStr == null || "".equals(sceneStr)) {
			return ResultUtil.build(500, "场景值不能为空");
		}

//        String openId = SENSE_STR.get(sceneStr);
		String result = redisTemplate.opsForValue().get(sceneStr);
		if (result == null||"".equals(result)) {
			return ResultUtil.build(204, "未获取到用户操作");
		}
		User user = JSON.parseObject(result, User.class);
		if (user.getStatus() == 2) {
			return ResultUtil.build(500, "很抱歉，您的账号已被禁用，请联系管理员");
		}
		if (user.getTerm_of_validity().before(new Date())) {
			return ResultUtil.build(504, "很抱歉，您的账号已过期，请联系管理员");
		}

		userUtil.setUser(request,response,user);
		ThreadPoolConst.IO_EXECUTOR.execute(() -> {
			redisTemplate.delete(sceneStr);
			userDao.updateUserLoginCountById(user.getId());
			//插入日志
			SystemLogEntity systemLog = new SystemLogEntity();
			UserAgent userAgent = UserAgent.parseUserAgentString(request.getHeader("User-Agent"));
			systemLog.setUser_browser(userAgent.getBrowser().toString());
			Version browserVersion = userAgent.getBrowserVersion();
			if (browserVersion != null) {
				systemLog.setUser_browser_version(browserVersion.getVersion());
			}
			systemLog.setOperatingSystem(userAgent.getOperatingSystem().toString());
			systemLog.setUser_id(user.getId());
			systemLog.setUsername(user.getUsername());
			systemLog.setLoginip(SystemLogAspect.getIpAddr(request));
			systemLog.setModule("微信公众号");
			systemLog.setSubmodule("扫码登录");
			systemLog.setType("登录");
			systemLogService.addData(systemLog);
		});
		return ResultUtil.ok();
	}

	@Override
	public void send(WxMpTemplateMessage wxMpTemplateMessage) {

		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json; charset=UTF-8");
		HttpEntity<WxMpTemplateMessage> request = new HttpEntity<>(wxMpTemplateMessage, headers);
		String string = restTemplate.postForObject(wechatUrl + WechatConstant.SEND, request, String.class);
		log.info("微信消息发送完毕,返回内容为:{}",string);
	}

	@Override
	public ResultUtil getBindQRCodeUrl(User user) {
		//生成场景值,以yuqing:开头
		int userId = user.getId();
		//转换成16进制
		String suffix = Integer.toHexString(userId);
		String sceneStr = "yuqing:" + suffix + "#bind";

		QrcodeData qrcodeData = getQRCodeUrl(sceneStr);
		if (qrcodeData == null) {
			return ResultUtil.build(500, "生成二维码失败");
		}
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("sceneStr",sceneStr);
		jsonObject.put("qrcodeUrl",qrcodeData.getQrcodeUrl());
		jsonObject.put("name",wechatConfigDao.selectLast().getName());
		return ResultUtil.ok(jsonObject);
	}

	@Override
	public ResultUtil checkBind(User user, HttpServletRequest request, HttpServletResponse response) throws Exception {
		//查询用户信息
		User checkUser = userDao.selectUserByUserId(user.getUser_id());
		userUtil.setUser(request,response,checkUser);
		if (checkUser == null) {
			return ResultUtil.build(500, "用户不存在");
		}
		if (checkUser.getWechatflag() == null || checkUser.getWechatflag() == 0) {
			return ResultUtil.build(500, "用户未绑定");
		}
		if (checkUser.getStatus() == 2) {
			return ResultUtil.build(500, "很抱歉，您的账号已被禁用，请联系管理员");
		}
		if (checkUser.getTerm_of_validity().before(new Date())) {
			return ResultUtil.build(504, "很抱歉，您的账号已过期，请联系管理员");
		}
		return ResultUtil.ok();
	}


	/**
	 * 增加偏好设置
	 */
	public void addOpinionCondition(OpinionCondition opinionCondition, String create_time) {
		Map<String, Object> paramOpinionMap = new HashMap<String, Object>();
		Long opinion_condition_id = IdUtil.getSnowflake(4, 1).nextId();
		paramOpinionMap.put("create_time", create_time);
		paramOpinionMap.put("opinion_condition_id", opinion_condition_id);
		paramOpinionMap.put("project_id", opinionCondition.getProject_id());
		paramOpinionMap.put("time", opinionCondition.getTime());
		paramOpinionMap.put("precise", opinionCondition.getPrecise());
		paramOpinionMap.put("emotion", opinionCondition.getEmotion());
		paramOpinionMap.put("similar", opinionCondition.getSimilar());
		paramOpinionMap.put("sort", opinionCondition.getSort());
		paramOpinionMap.put("matchs", opinionCondition.getMatchs());

		paramOpinionMap.put("websitename", opinionCondition.getWebsitename());
		paramOpinionMap.put("author", opinionCondition.getAuthor());
		opinionConditionService.addOpinionConditionById(paramOpinionMap);

	}

	/**
	 * 插入预警设置
	 */
	public void addWarningCondition(Long projectid, String create_time) {
		Map<String, Object> warningMap = new HashMap<String, Object>();
		Long warning_setting_id = IdUtil.getSnowflake(5, 1).nextId();
		warningMap.put("create_time", create_time);
		warningMap.put("project_id", projectid);
		warningMap.put("warning_setting_id", warning_setting_id);
		warningMap.put("warning_status", 0);
		warningMap.put("warning_name", "预警");
		warningMap.put("warning_word", "");
		warningMap.put("warning_classify", "1,2,3,4,5,6,7,8,9,10,11");
		warningMap.put("warning_content", 0);
		warningMap.put("warning_similar", 0);
		warningMap.put("warning_match", 2);
		warningMap.put("warning_deduplication", 0);
		warningMap.put("weekend_warning", 1);
		warningMap.put("warning_interval", "{\"type\":\"1\",\"time\":\"1\"}");
		warningMap.put("warning_source", "{\"type\":\"1\",\"email\":\"\"}");
		warningMap.put("warning_receive_time", "{\"start\":\"00:00\",\"end\":\"23:00\"}");
		systemService.addWarning(warningMap);
	}

	/**
	 * 插入方案计划
	 */
	public void addProjectPlan(Project project) {
		ProjectTask projectTask = new ProjectTask();
		projectTask.setAnalysis_flag(0);
		projectTask.setCharacter_word(project.getCharacterWord());
		projectTask.setEvent_word(project.getEventWord());
		projectTask.setProject_id(project.getProjectId());
		projectTask.setProject_type(project.getProjectType());
		projectTask.setRegional_word(project.getRegionalWord());
		projectTask.setStop_word(project.getStopWord());
		projectTask.setSubject_word(project.getSubjectWord());
		projectTask.setVolume_flag(0);
		projectTaskDao.saveProjectTask(projectTask);
	}
}
