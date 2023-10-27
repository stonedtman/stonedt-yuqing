package com.stonedt.intelligence.service.impl;



import cn.hutool.core.util.IdUtil;
import com.alibaba.fastjson.JSON;
import com.stonedt.intelligence.dao.ProjectTaskDao;
import com.stonedt.intelligence.dao.UserDao;
import com.stonedt.intelligence.dao.UserWechatInfoDao;
import com.stonedt.intelligence.dto.QrCodeInput;
import com.stonedt.intelligence.dto.QrcodeData;
import com.stonedt.intelligence.dto.WechatUserInfo;
import com.stonedt.intelligence.dto.WxMpXmlMessage;
import com.stonedt.intelligence.entity.*;
import com.stonedt.intelligence.service.*;
import com.stonedt.intelligence.thred.ThreadPoolConst;
import com.stonedt.intelligence.util.DateUtil;
import com.stonedt.intelligence.util.ResultUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
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

	@Value("${wechat.qrcode.url}")
	private String getQrcodeUrl;

	public WechatServiceImpl(RestTemplate restTemplate,
							 StringRedisTemplate redisTemplate,
							 UserWechatInfoDao userWechatInfoDao,
							 UserDao userDao, ProjectService projectService,
							 DefaultProjectService defaultProjectService,
							 SolutionGroupService solutionGroupService,
							 OpinionConditionService opinionConditionService,
							 SystemService systemService,
							 ProjectTaskDao projectTaskDao) {
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
			user.setWechatflag(1);
			userDao.saveUser(user);
			//获取事件key
			String eventKey = redisTemplate.opsForValue().get(openid);
			//将用户信息存入redis
			redisTemplate.opsForValue().set(eventKey, JSON.toJSONString(user), 10, TimeUnit.MINUTES);
			//将用户信息存入数据库
			UserWechatInfo userWechatUserInfo = new UserWechatInfo(wechatUserInfo);
			userWechatUserInfo.setUser_id(user.getId());
			userWechatInfoDao.saveWechatUserInfo(userWechatUserInfo);
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
		if (user.getStatus() == 2) {
			return ResultUtil.build(500, "很抱歉，您的账号已被禁用，请联系管理员");
		}
		request.getSession().setAttribute("User", user);
		//单线程线程池,确保有序执行
		ThreadPoolConst.SINGLE_EXECUTOR.execute(() ->{
			//更新用户登录次数
			userDao.updateUserLoginCountById(user.getId());
			//获取默认方案组列表
			List<DefaultSolutionGroup> defaultSolutionGroupList = defaultProjectService.getDefaultSolutionGroupList();
			String create_time = DateUtil.getNowTime();
			for (DefaultSolutionGroup defaultSolutionGroup : defaultSolutionGroupList) {
				//获取默认方案列表
				List<DefaultProject> defaultProjectList = defaultProjectService.getDefaultSolutionListByGroupId(defaultSolutionGroup.getGroup_id());
				//插入方案组
				SolutionGroup solutionGroup = new SolutionGroup();
				BeanUtils.copyProperties(defaultSolutionGroup, solutionGroup);
				long groupId = IdUtil.getSnowflake(2, 1).nextId();
				solutionGroup.setGroupId(groupId);
				solutionGroup.setUserId(user.getUser_id());
				solutionGroupService.addSolutionGroup(solutionGroup);

				for (DefaultProject defaultProject : defaultProjectList) {
					//插入方案
					long projectId = IdUtil.getSnowflake(3, 1).nextId();
					Project project = new Project();
					BeanUtils.copyProperties(defaultProject, project);
					project.setProjectId(projectId);
					project.setGroupId(groupId);
					project.setUserId(user.getUser_id());
					projectService.insertProject(project);
					//插入偏好设置
					addOpinionCondition(defaultProject.getStop_word(), projectId, create_time);
					//插入预警设置
					addWarningCondition(projectId, create_time);
					//插入方案计划
					addProjectPlan(project);
				}
			}
		});
		return ResultUtil.ok();
	}

	/**
	 * 增加偏好设置
	 */
	public void addOpinionCondition(String stop_word, Long projectid, String create_time) {
		Map<String, Object> paramOpinionMap = new HashMap<String, Object>();
		Long opinion_condition_id = IdUtil.getSnowflake(4, 1).nextId();
		paramOpinionMap.put("create_time", create_time);
		paramOpinionMap.put("opinion_condition_id", opinion_condition_id);
		paramOpinionMap.put("project_id", projectid);
		paramOpinionMap.put("time", 4);
		if (stop_word.equals("")) {
			paramOpinionMap.put("precise", 0);
		} else {
			paramOpinionMap.put("precise", 1);
		}
		paramOpinionMap.put("emotion", "[1,2,3]");
		paramOpinionMap.put("similar", 0);
		paramOpinionMap.put("sort", 1);
		paramOpinionMap.put("matchs", 1);
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
