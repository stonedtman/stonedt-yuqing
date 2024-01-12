package com.stonedt.intelligence.controller;

import com.stonedt.intelligence.entity.User;
import com.stonedt.intelligence.service.ProjectService;
import com.stonedt.intelligence.util.UserUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Controller
@RequestMapping("/mobile")
public class MobileController {


    private final UserUtil userUtil;

    private final ProjectService projectService;

    public MobileController(UserUtil userUtil,
                            ProjectService projectService) {
        this.userUtil = userUtil;
        this.projectService = projectService;
    }

    @RequestMapping("/monitor")
    public ModelAndView monitor(ModelAndView modelAndView) {
        modelAndView.setViewName("mobile/monitor");
        return modelAndView;
    }

    @RequestMapping("/monitor/detail")
    public ModelAndView monitorDetail(ModelAndView modelAndView) {
        modelAndView.setViewName("mobile/detail");
        return modelAndView;
    }

    @RequestMapping("/warning")
    public ModelAndView monitorWarning(ModelAndView modelAndView) {
        modelAndView.setViewName("mobile/warning");
        return modelAndView;
    }

    @GetMapping("/getGroupAndProject")
    @ResponseBody
    public Map<String, Object> getGroupAndProject(HttpServletRequest request) {
        User user = userUtil.getuser(request);
        return projectService.getMobileGroupAndProject(user);

    }
}
