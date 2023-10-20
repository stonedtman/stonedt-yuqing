package com.stonedt.intelligence.api;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.stonedt.intelligence.entity.User;
import com.stonedt.intelligence.service.MonitorService;
import com.stonedt.intelligence.service.UserService;
import com.stonedt.intelligence.util.MD5Util;
import com.stonedt.intelligence.vo.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * @author 文轩
 * <p>接口</p>
 */
@RequestMapping("/api")
@RestController
public class ApiController {

    private final MonitorService monitorService;

    private final UserService userService;

    public ApiController(MonitorService monitorService,
                         UserService userService) {
        this.monitorService = monitorService;
        this.userService = userService;
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
