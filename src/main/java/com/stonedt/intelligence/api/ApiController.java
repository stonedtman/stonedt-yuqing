package com.stonedt.intelligence.api;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.stonedt.intelligence.entity.User;
import com.stonedt.intelligence.service.ArticleService;
import com.stonedt.intelligence.service.MonitorService;
import com.stonedt.intelligence.service.UserService;
import com.stonedt.intelligence.util.MD5Util;
import com.stonedt.intelligence.vo.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author 文轩
 * <p>接口</p>
 */
@RequestMapping("/api")
@RestController
public class ApiController {

    private final MonitorService monitorService;

    private final UserService userService;

    private final ArticleService articleService;

    public ApiController(MonitorService monitorService,
                         UserService userService,
                         ArticleService articleService) {
        this.monitorService = monitorService;
        this.userService = userService;
        this.articleService = articleService;
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
            description = "根据条件获取文章列表\n" +
                    "请求头中需要携带token，状态码0表示成功，其他表示失败",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(mediaType = "application/json",schema = @Schema(implementation = SearchCondition.class)))
    )
    /*@SystemControllerLog(module = "数据监测", submodule = "数据监测-列表", type = "查询", operation = "getarticle")*/
    @PostMapping(value = "/getArticle")
    @ResponseBody
    public ResultVO<PageInfo<ArticleData>> getArticleList(@RequestBody SearchCondition searchCondition) {

        JSONObject paramJson = searchCondition.toJsonObject();

        paramJson.put("similar", 0);
        List<String> classify = new ArrayList<>();
        classify.add("0");
        paramJson.put("classify", classify);
        paramJson.put("matchingmode", 1);
        paramJson.put("precise", 0);
        JSONObject response = monitorService.getArticleList(paramJson);
        return JSON.parseObject(response.toJSONString(), ResultVO.class);
    }


    /**
     * @param [paramJson]
     * @return com.alibaba.fastjson.JSONObject
     * @description: 获取文章列表 <br>
     * @version: 1.0 <br>
     * @date: 2020/4/16 19:34 <br>
     * @author: huajiancheng <br>
     */
    @Operation(summary = "获取文章列表(合并相似文章)",
            description = "根据条件获取文章列表(合并相似文章)。请求头中需要携带token，状态码0表示成功，其他表示失败",
//            parameters = @Parameter(name = "searchCondition", description = "搜索条件", required = true, schema = @Schema(implementation = SearchCondition.class)),
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(mediaType = "application/json",schema = @Schema(implementation = SearchCondition.class))

            )
    )
    /*@SystemControllerLog(module = "数据监测", submodule = "数据监测-列表", type = "查询", operation = "getarticle")*/
    @PostMapping(value = "/getMergeArticle")
    @ResponseBody
    public ResultVO<PageInfo<ArticleData>> getMergeArticleList(@RequestBody SearchCondition searchCondition) {
        JSONObject paramJson = searchCondition.toJsonObject();

        paramJson.put("similar", 1);
        List<String> classify = new ArrayList<>();
        classify.add("0");
        paramJson.put("classify", classify);
        paramJson.put("matchingmode", 1);
        paramJson.put("precise", 0);
        JSONObject response = monitorService.getArticleList(paramJson);
        return JSON.parseObject(response.toJSONString(), ResultVO.class);
    }

    @Operation(summary = "获取文章详情",
            description = "根据文章id、方案id和文章发布时间获取文章详情。请求头中需要携带token.状态码0表示成功，其他表示失败",
            parameters = {
                    @Parameter(name = "articleId", description = "文章id", required = true),
                    @Parameter(name = "projectId", description = "方案id", required = true),
                    @Parameter(name = "publish_time", description = "文章发布时间,string类型，格式为yyyy-MM-dd", required = true)
            }
    )
    @GetMapping(value = "/detail")
    @ResponseBody
    public ResultVO<ArticleDetail> articleDetail(String articleId,
                                Long projectId,
                                String publish_time) {
        long startTime = System.currentTimeMillis();
        Map<String, Object> articleDetail = articleService.articleDetail(articleId, projectId, "",publish_time);
        JSONObject jsonObject = new JSONObject(articleDetail);
        System.out.println(jsonObject.toJSONString());
        ArticleDetail articleData = JSON.parseObject(jsonObject.toJSONString(), ArticleDetail.class);
        System.err.println("请求详情获取时间：" + (System.currentTimeMillis() - startTime) / 1000d + "s");
        ResultVO<ArticleDetail> articleDataResultVO = new ResultVO<>();
        articleDataResultVO.setCode(0);
        articleDataResultVO.setMsg("success");
        articleDataResultVO.setData(articleData);
        return articleDataResultVO;

    }

//    /**
//     * 获取相似文章列表
//     */
//    @Operation(summary = "获取相似文章列表",
//            description = "根据条件获取相似文章列表",
//            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
//                    content = @Content(mediaType = "application/json",schema = @Schema(implementation = SimilarSearchCondition.class)))
//    )
//    @PostMapping(value = "/getSimilarArticle")
//    @ResponseBody
//    public ResultVO<PageInfo<ArticleData>> getSimilarArticle(@RequestBody JSONObject paramJson) {
//        JSONObject similarArticleList = monitorService.getSimilarArticleList(paramJson);
//        return JSON.parseObject(similarArticleList.toJSONString(), ResultVO.class);
//    }


    @PostMapping(value = "/getToken")
    @ResponseBody
    public ResultVO<String> getToken(@RequestBody LoginVO loginVO, HttpServletRequest request) {
        loginVO.trim();
        if (loginVO.getUsername() == null || loginVO.getPassword() == null
                || loginVO.getUsername().isEmpty() || loginVO.getPassword().isEmpty()) {
            return ResultVO.error("用户名或密码不能为空");
        }
        User user = null;
        try {
            user = userService.selectUserByTelephone(loginVO.getUsername());
        } catch (Exception e) {
            ResultVO.error("登录失败");
        }
        if (user == null) {
            return ResultVO.error("用户不存在");
        }
        if (!MD5Util.getMD5(loginVO.getPassword()).equals(user.getPassword())) {
            return ResultVO.error("密码错误");
        }
        if (user.getStatus() == 0) {
            return ResultVO.error("用户禁止登录");
        }
        if (user.getStatus() == 2) {
            return ResultVO.error("账户已被注销");
        }
        if (user.getTerm_of_validity().before(new Date())) {
            return ResultVO.error("账号已过期");
        }

        String token;
        try {
            token = userService.getToken(user);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultVO.error("登录失败");
        }
        return ResultVO.success(token);

    }
}
