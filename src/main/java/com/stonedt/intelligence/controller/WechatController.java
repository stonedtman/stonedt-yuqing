package com.stonedt.intelligence.controller;

import com.stonedt.intelligence.service.WechatService;
import com.stonedt.intelligence.util.CheckoutUtil;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping(value = "/monitor")
public class WechatController {
	
	
	@Autowired
	private WechatService wechatService;
	
	
	
	@RequestMapping("/weChatToken")
    public void weChat(HttpServletRequest request, HttpServletResponse response) throws IOException {
        wechatService.dealevent(request,response);
    }

    @RequestMapping("/wxGroup")
    public ModelAndView wxGroup(HttpServletRequest request, HttpServletResponse response,ModelAndView mv) throws IOException {
        String groupid = request.getParameter("groupid");
        mv.addObject("groupid", groupid);
        mv.addObject("settingLeft", "wxGroup");
        mv.setViewName("user/wxGroup");
        return mv;
    }
	

}
