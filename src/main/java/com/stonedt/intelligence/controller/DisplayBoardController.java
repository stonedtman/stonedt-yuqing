package com.stonedt.intelligence.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.stonedt.intelligence.aop.SystemControllerLog;
import com.stonedt.intelligence.entity.User;
import com.stonedt.intelligence.service.PlatformService;
import com.stonedt.intelligence.util.UserUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @author 文轩
 */
@Controller
@RequestMapping("/displayboard")
public class DisplayBoardController {

    private final PlatformService platformService;

    private final UserUtil userUtil;

    public DisplayBoardController(PlatformService platformService,
                                  UserUtil userUtil) {
        this.platformService = platformService;
        this.userUtil = userUtil;
    }

    @SystemControllerLog(module = "综合看板", submodule = "综合看板", type = "查询", operation = "displayboardlist")
    @RequestMapping("")
    public ModelAndView displayBoard(HttpServletRequest request) {
        String groupId = request.getParameter("groupid");
        String projectId = request.getParameter("projectid");
        if (StringUtils.isBlank(groupId))
            groupId = "";
        if (StringUtils.isBlank(projectId))
            projectId = "";
        User user = userUtil.getuser(request);
        JSONObject newSynthesize = platformService.getNewSynthesize();
        // 转码html
        Map<String, Object> map = newSynthesize.getInnerMap();
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();
            if (value instanceof String) {
                String s = (String) value;
                s = s.replaceAll("&lt;", "<");
                s = s.replaceAll("&gt;", ">");
                s = s.replaceAll("&quot;", "\"");
                s = s.replaceAll("&amp;", "&");
                map.put(key, s);
            }
        }
        ModelAndView modelAndView = new ModelAndView();
        // 将数据存入modelAndView
        newSynthesize.forEach(modelAndView::addObject);
        modelAndView.addObject("menu", "displayboard");
        modelAndView.addObject("user",user);
        modelAndView.addObject("groupid",groupId);
        modelAndView.addObject("projectid",projectId);
        modelAndView.setViewName("displayboard/displayboard");
        return modelAndView;
    }
}
