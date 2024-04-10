package com.stonedt.intelligence.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.stonedt.intelligence.dao.SolutionGroupDao;
import com.stonedt.intelligence.entity.MonitorWarningSetting;
import com.stonedt.intelligence.entity.User;
import com.stonedt.intelligence.entity.WarningSetting;
import com.stonedt.intelligence.service.*;
import com.stonedt.intelligence.util.UserUtil;
import com.stonedt.intelligence.aop.SystemControllerLog;

import com.stonedt.intelligence.vo.*;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.List;
import java.util.Map;

/**
 * description: MonitorController <br>
 * date: 2020/4/13 10:53 <br>
 * author: xiaomi <br>
 * version: 1.0 <br>
 */
@Controller
@RequestMapping(value = "/monitor")
public class MonitorController {
	@Autowired
	OpinionConditionService opinionConditionService;
	@Autowired
	private ArticleService articleService;
	@Autowired
	MonitorService monitorService;
	@Autowired
	ProjectService projectService;
	@Autowired
	private UserUtil userUtil;
	@Autowired
	private EarlyWarningService warningService;
	@Autowired
	private SolutionGroupDao solutionGroupDao;

	@Autowired
	private WarningSettingService warningSettingService;
	
	@Value("${insertnewwords.url}")
    private String insert_new_words_url;

	/**
	 * @param groupid       方案组id
	 * @param projectid     方案id
	 * @param monitorsearch 搜索关键词
	 * @param start         开始时间
	 * @param end           结束时间
	 * @param emotion       情感
	 * @param sort          排序方式
	 * @param match         匹配方式
	 * @param precise       精准
	 * @param merge         合并
	 * @param page          分页参数
	 * @param menu          顶部菜单导航
	 * @param               mv]
	 * @return org.springframework.web.servlet.ModelAndView
	 * @description: 页面跳转 <br>
	 * @version: 1.0 <br>
	 * @date: 2020/4/13 14:17 <br>
	 * @author: huajiancheng <br>
	 */
	/*@SystemControllerLog(module = "数据监测", submodule = "数据监测-列表", type = "查询", operation = "")*/
	@Operation(summary = "数据监测-列表", description = "数据监测-列表页面",
			parameters = {
			@Parameter(name = "groupid", description = "方案组id", required = false),
			@Parameter(name = "projectid", description = "方案id", required = false),
			@Parameter(name = "page", description = "分页参数", required = false),
			@Parameter(name = "searchflag", description = "搜索标识", required = false),
			@Parameter(name = "searchword", description = "搜索关键词", required = false)
			},
			responses = @ApiResponse(content = @Content(mediaType = "html/text"),description = "数据监测列表页面")
	)
	@GetMapping(value = "")
	public ModelAndView monitor(@RequestParam(value = "groupid", required = false) String groupid,
			@RequestParam(value = "projectid", required = false) String projectid,
			@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "searchflag", required = false, defaultValue = "false") boolean searchflag,
			@RequestParam(value = "searchword", required = false) String searchword, ModelAndView mv,
			HttpServletRequest request) {

		boolean projectFlag = monitorService.boolUserProjectByUserId(request);
		String search = request.getParameter("search");
		mv.addObject("search", StringUtils.isBlank(search) ? "" : search);
		mv.addObject("menu", "monitor");
		mv.addObject("groupid", groupid);
		mv.addObject("projectid", projectid);
		mv.addObject("searchflag", searchflag);
		mv.addObject("searchword", searchword);
		mv.addObject("page", page);
		mv.addObject("projectFlag", projectFlag);
		mv.setViewName("monitor/monitor");
		return mv;
	}

	@RequestMapping("/wxGroup")
	public ModelAndView wxGroup(ModelAndView modelAndView) {
		modelAndView.setViewName("monitor/wxGroup");
		modelAndView.addObject("settingLeft", "warning");
		return modelAndView;
	}

	/**
	 * @param projectid     方案id
	 * @param monitorsearch 搜索关键词
	 * @param start         开始时间
	 * @param end           结束时间
	 * @param emotion       情感
	 * @param sort          排序
	 * @param match         匹配方式
	 * @param precise       精准
	 * @param merge         合并
	 * @param page          分页参数
	 * @return java.lang.String
	 * @description: 获取文章列表 <br>
	 * @version: 1.0 <br>
	 * @date: 2020/4/13 14:22 <br>
	 * @author: huajiancheng <br>
	 */
	@Deprecated

	/*@SystemControllerLog(module = "数据监测", submodule = "数据监测-列表", type = "查询", operation = "listarticle")*/
	@PostMapping(value = "/listarticle")
	@ResponseBody
	public String listArticle(@RequestParam("projectid") Integer projectid,
			@RequestParam("monitorsearch") String monitorsearch, @RequestParam("start") String start,
			@RequestParam("end") String end, @RequestParam("emotion") String emotion, @RequestParam("sort") String sort,
			@RequestParam("match") String match, @RequestParam("precise") String precise,
			@RequestParam("merge") String merge, @RequestParam("page") Integer page) {
		return "";
	}

	/**
	 * @param [articleid, groupid, projectid, menu, mv]
	 * @return org.springframework.web.servlet.ModelAndView
	 * @description: 跳转文章详情页面 <br>
	 * @version: 1.0 <br>
	 * @date: 2020/4/13 14:32 <br>
	 * @author: huajiancheng <br>
	 */
	@Operation(summary = "数据监测-详情", description = "数据监测-详情页面",
			parameters = {
				@Parameter(name = "groupid", description = "方案组id", required = false),
				@Parameter(name = "projectid", description = "方案id", required = false),
				@Parameter(name = "relatedWord", description = "相关词", required = false),
				@Parameter(name = "publish_time", description = "发布时间", required = false),
				@Parameter(name = "menu", description = "顶部菜单导航", required = false)
			},
			responses = @ApiResponse(content = @Content(mediaType = "html/text"),description = "数据监测详情页面")
	)

	@SystemControllerLog(module = "数据监测", submodule = "数据监测-详情", type = "详情" /*type = "查询"*/, operation = "detail")
	@GetMapping(value = "/detail/{articleid}")
	public ModelAndView skiparticle(@PathVariable() String articleid, String groupid, String projectid,
			String relatedWord,String publish_time, String menu, ModelAndView mv, HttpServletRequest request) {
//        String groupid = request.getParameter("groupid");
//        String projectid = request.getParameter("projectid");
//        String menu = request.getParameter("menu");
//        String page = request.getParameter("page");
//        String searchkeyword = request.getParameter("searchkeyword");
		if (StringUtils.isBlank(groupid))
			groupid = "";
		if (StringUtils.isBlank(projectid))
			projectid = "";
		if (StringUtils.isBlank(articleid))
			articleid = "";
		if (StringUtils.isBlank(relatedWord))
			relatedWord = "";
		
		if (StringUtils.isBlank(publish_time))
			publish_time = "";
		if (StringUtils.isBlank(menu))
			menu = "monitor";
		mv.addObject("articleid", articleid);
//        mv.addObject("searchkeyword", searchkeyword);
//        mv.addObject("currentPageByDetail", page);
		mv.addObject("groupid", groupid);
		mv.addObject("projectid", projectid);
		mv.addObject("relatedword", relatedWord);
		mv.addObject("publish_time", publish_time);
		mv.addObject("menu", "monitor");
		mv.setViewName("monitor/detail");
		return mv;
	}

	/**
	 * @param [articleid]
	 * @return java.lang.String
	 * @description: 获取文章详情数据 <br>
	 * @version: 1.0 <br>
	 * @date: 2020/4/13 14:35 <br>
	 * @author: huajiancheng <br>
	 */
	@Operation(summary = "数据监测-详情", description = "数据监测-详情数据",
			parameters = {
				@Parameter(name = "articleid", description = "文章id", required = false),
				@Parameter(name = "groupid", description = "方案组id", required = false),
				@Parameter(name = "projectid", description = "方案id", required = false),
				@Parameter(name = "publish_time", description = "发布时间", required = false),
			},
			responses = @ApiResponse(content = @Content(mediaType = "application/json"),description = "数据监测详情数据")
	)
	/*@SystemControllerLog(module = "数据监测", submodule = "数据监测-详情", type = "查询", operation = "articleDetail")*/
	@PostMapping(value = "/articleDetail")
	@ResponseBody
	public String articleDetail(String articleId,
								Long projectId,
								String relatedword,
								String publish_time,
								@RequestParam(required = false,defaultValue = "false") Boolean isNeedWarnWord,
			HttpServletRequest resRequest) {
		long startTime = System.currentTimeMillis();
		long userId = userUtil.getUserId(resRequest);
		try {
			warningService.updateWarningArticle(articleId, userId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		Map<String, Object> articleDetail = articleService.articleDetail(articleId, projectId, relatedword,publish_time);
		if (Boolean.TRUE.equals(isNeedWarnWord) && projectId != null) {
			List<String> warnWordList = articleService.getHaveWarnWord((String) articleDetail.get("title"), (String) articleDetail.get("text"), projectId);
			articleDetail.put("warnWordList", warnWordList);
		}

		System.err.println("请求详情获取时间：" + (System.currentTimeMillis() - startTime) / 1000d + "s");
		return JSONObject.toJSONString(articleDetail);
	}

	/**
	 * 文章详情 相关文章
	 */
	/*@SystemControllerLog(module = "数据监测", submodule = "数据监测-详情", type = "查询", operation = "relatedArticles")*/
	@PostMapping(value = "/relatedArticles")
	@ResponseBody
	public String relatedArticles(String keywords) {
		List<Map<String, Object>> relatedArticles = articleService.relatedArticles(keywords);
		return JSON.toJSONString(relatedArticles);
	}

	/**
	 * @param [paramJson]
	 * @return com.alibaba.fastjson.JSONObject
	 * @description: 第一次进入数据监测加载默认用户条件 <br>
	 * @version: 1.0 <br>
	 * @date: 2020/4/16 18:03 <br>
	 * @author: huajiancheng <br>
	 */

	@SystemControllerLog(module = "数据监测", submodule = "数据监测-列表", type = "查询", operation = "getCondition")
	@PostMapping(value = "/getCondition")
	@ResponseBody
	public JSONObject getCondition(@RequestBody JSONObject paramJson) {
		JSONObject response = monitorService.getCondition(paramJson);
		return response;
	}

	/**
	 * @param [paramJson]
	 * @return com.alibaba.fastjson.JSONObject
	 * @description: 获取文章列表 <br>
	 * @version: 1.0 <br>
	 * @date: 2020/4/16 19:34 <br>
	 * @author: huajiancheng <br>
	 */
	@Operation(summary = "获取文章列表",
			description = "根据条件获取文章列表",
			requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
					content = @Content(mediaType = "application/json",schema = @Schema(implementation = SearchCondition.class)))
	)
	/*@SystemControllerLog(module = "数据监测", submodule = "数据监测-列表", type = "查询", operation = "getarticle")*/
	@PostMapping(value = "/getarticle")
	@ResponseBody
	public ResultVO<PageInfo<ArticleData>> getArticleList(@RequestBody JSONObject paramJson) {
		JSONObject response = monitorService.getArticleList(paramJson);
		return JSON.parseObject(response.toJSONString(), ResultVO.class);
	}

	/**
	 * 获取相似文章列表
	 */
	@Operation(summary = "获取相似文章列表",
			description = "根据条件获取相似文章列表",
			requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
					content = @Content(mediaType = "application/json",schema = @Schema(implementation = SimilarSearchCondition.class)))
	)
	@PostMapping(value = "/getSimilarArticle")
	@ResponseBody
	public ResultVO<PageInfo<ArticleData>> getSimilarArticle(@RequestBody JSONObject paramJson) {
		JSONObject similarArticleList = monitorService.getSimilarArticleList(paramJson);
		return JSON.parseObject(similarArticleList.toJSONString(), ResultVO.class);
	}
	
	/**
	 * 
	 */
	/*@SystemControllerLog(module = "数据监测", submodule = "数据监测-列表", type = "查询", operation = "getarticle")*/
	@PostMapping(value = "/getanalysisarticle")
	@ResponseBody
	public JSONObject getanalysisarticle(@RequestBody JSONObject paramJson) {
		JSONObject response = monitorService.getAnalysisArticleList(paramJson);

		return response;
	}
	
	
	
	
	/**
	 * 
	 * @param paramJson
	 * @return
	 */
	/*@SystemControllerLog(module = "数据监测", submodule = "数据监测-行业标签", type = "查询", operation = "getindustry")*/
	@PostMapping(value = "/getindustry")
	@ResponseBody
	public JSONObject getArticleIndustry(@RequestBody JSONObject paramJson) {

		JSONObject response = monitorService.getArticleIndustryList(paramJson);

		return response;
	}
	
	/**
	 * wangyi
	 * @param paramJson
	 * @return
	 */
	/*@SystemControllerLog(module = "数据监测", submodule = "数据监测-事件标签", type = "查询", operation = "getevent")*/
	@PostMapping(value = "/getevent")
	@ResponseBody
	public JSONObject getArticleEvent(@RequestBody JSONObject paramJson) {
		JSONObject response = monitorService.getArticleEventList(paramJson);
		return response;
	}
	
	/**
	 * wangyi
	 * @param paramJson
	 * @return
	 */
	/*@SystemControllerLog(module = "数据监测", submodule = "数据监测-省份", type = "查询", operation = "getprovince")*/
	@PostMapping(value = "/getprovince")
	@ResponseBody
	public JSONObject getArticleProvince(@RequestBody JSONObject paramJson) {
		JSONObject response = monitorService.getArticleProvinceList(paramJson);
		return response;
	}
	
	/**
	 * wangyi
	 * @param paramJson
	 * @return
	 */
	/*@SystemControllerLog(module = "数据监测", submodule = "数据监测-市", type = "查询", operation = "getcity")*/
	@PostMapping(value = "/getcity")
	@ResponseBody
	public JSONObject getArticleCity(@RequestBody JSONObject paramJson) {
		JSONObject response = monitorService.getArticleCityList(paramJson);
		return response;
	}
	

	/**
	 * @param [paramJson]
	 * @return com.alibaba.fastjson.JSONObject
	 * @description: App获取文章列表 <br>
	 * @version: 1.0 <br>
	 * @date: 2020/4/16 19:34 <br>
	 * @author: huajiancheng <br>
	 */
	/*@SystemControllerLog(module = "数据监测", submodule = "数据监测-列表", type = "查询", operation = "getarticle")*/
	@PostMapping(value = "/getapparticle")
	@ResponseBody
	public JSONObject getAppArticleList(
			@RequestParam(value = "times", required = false, defaultValue = "") String times,
			@RequestParam(value = "timee", required = false, defaultValue = "") String timee,
			@RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
			@RequestParam(value = "searchType", required = false, defaultValue = "1") Integer searchType,
			@RequestParam(value = "code", required = false, defaultValue = "") String code,
			@RequestParam(value = "similar", required = false, defaultValue = "0") String similar,
			@RequestParam(value = "matchingmode", required = false, defaultValue = "1") String matchingmode,
			@RequestParam(value = "emotionalIndex", required = false, defaultValue = "") String emotionalIndex,
			@RequestParam(value = "searchkeyword", required = false, defaultValue = "") String searchkeyword,
			@RequestParam(value = "groupid", required = false, defaultValue = "") String groupid,
			@RequestParam(value = "group_id", required = false, defaultValue = "") String group_id,
			@RequestParam(value = "projectid", required = false, defaultValue = "") String projectid,
			@RequestParam(value = "projectId", required = false, defaultValue = "") String projectId,
			@RequestParam(value = "timeType", required = false, defaultValue = "4") String timeType,
			@RequestParam(value = "precise", required = false, defaultValue = "0") String precise) {
		JSONObject paramJson = new JSONObject();
		paramJson.put("times", times);
		paramJson.put("timee", timee);
		paramJson.put("page", page);
		paramJson.put("searchType", searchType);
		paramJson.put("code", code);
		paramJson.put("similar", similar);
		paramJson.put("matchingmode", matchingmode);
		paramJson.put("emotionalIndex", emotionalIndex);
		paramJson.put("searchkeyword", searchkeyword);
		//paramJson.put("groupid", groupid);
		//paramJson.put("group_id", group_id);
		paramJson.put("projectId", projectId);
		paramJson.put("projectid", projectid);
		paramJson.put("timeType", timeType);
		paramJson.put("precise", precise);

		JSONObject response = monitorService.getArticleList(paramJson);

		
		//String groupName = solutionGroupDao.getGroupName(Long.parseLong(groupid));
		
		
		Map<String,Object> map = solutionGroupDao.getGroupNameByprojectId(projectId);
		
		
		response.getJSONObject("data").put("groupName", map.get("group_name").toString());
		
		
		try {
			RestTemplate template = new RestTemplate();
		       
			MultiValueMap<String, Object> paramMap = new LinkedMultiValueMap<String, Object>();
	        paramMap.add("text", searchkeyword);
	        String result = template.postForObject(insert_new_words_url, paramMap, String.class);
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		
		
		return response;
	}

	
	
	
	
	/**
	 * @param [paramJson]
	 * @return com.alibaba.fastjson.JSONObject
	 * @description: 导出数据 <br>
	 * @version: 1.0 <br>
	 * @date: 2020/4/16 19:34 <br>
	 * @author: huajiancheng <br>
	 */
	@SystemControllerLog(module = "数据监测", submodule = "数据监测-列表", type = "导出" /*type = "查询"*/, operation = "exportarticle")
	@PostMapping(value = "/exportarticle")
	public void exportArticleList(String data, HttpServletResponse response, HttpServletRequest request) {
		JSONObject paramJson = JSON.parseObject(data);
		monitorService.exportArticleList(new JSONObject(paramJson), response, request);
		return;
	}

	/**
	 * @param [paramJson]
	 * @return com.alibaba.fastjson.JSONObject
	 * @description: 获取文章列表 <br>
	 * @version: 1.0 <br>
	 * @date: 2020/4/16 19:34 <br>
	 * @author: huajiancheng <br>
	 */
	/*@SystemControllerLog(module = "数据监测", submodule = "数据监测-列表", type = "查询", operation = "getgroupname")*/
	@PostMapping(value = "/getgroupname")
	@ResponseBody
	public JSONObject getGroupNameById(@RequestBody JSONObject paramJson, HttpServletRequest request) {
		User user = userUtil.getuser(request);
		Long user_id = user.getUser_id();
		paramJson.put("user_id", user_id);
		JSONObject response = monitorService.getGroupNameById(paramJson);
		return response;
	}


	@PostMapping("/warningSetting")
	@ResponseBody
	public ResultVO<Void> warningSetting(@RequestBody MonitorWarningSetting monitorWarningSetting,
										 HttpServletRequest request,
										 HttpServletResponse response) {
		return warningSettingService.warningSetting(monitorWarningSetting, request, response);
	}

	@GetMapping("/warningSetting/{projectId}")
	@ResponseBody
	public ResultVO<MonitorWarningSetting> warningSetting(@PathVariable Long projectId, HttpServletRequest request) {
		return warningSettingService.getWarningSetting(projectId, request);
	}

}
