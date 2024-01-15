package com.stonedt.intelligence.controller;

import com.google.zxing.WriterException;
import com.nimbusds.jose.JOSEException;
import com.stonedt.intelligence.aop.SystemControllerLog;
import com.stonedt.intelligence.entity.User;
import com.stonedt.intelligence.service.MobileService;
import com.stonedt.intelligence.service.ProjectService;
import com.stonedt.intelligence.util.UserUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@Controller
@RequestMapping("/mobile")
public class MobileController {


    private final UserUtil userUtil;

    private final ProjectService projectService;

    private final MobileService mobileService;

    public MobileController(UserUtil userUtil,
                            ProjectService projectService, MobileService mobileService) {
        this.userUtil = userUtil;
        this.projectService = projectService;
        this.mobileService = mobileService;
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

    /**
     * 获取移动端二维码
     */
    @GetMapping("/mobileQRCode")
    @ResponseBody
    public void getMobileQRCode(HttpServletRequest request, HttpServletResponse response) throws IOException, JOSEException, WriterException {
        User user = userUtil.getuser(request);
        mobileService.getMobileQRCode(user, response);
    }

    /**
     * 跳转到移动端
     */
    @GetMapping("/uuid/{uuid}/{key}")
    public RedirectView mobile(@PathVariable String uuid, @PathVariable String key) {
        return mobileService.redirectBy(uuid, key);
    }
}
