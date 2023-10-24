package com.stonedt.intelligence.controller;

import com.stonedt.intelligence.dto.WechatUserInfo;
import com.stonedt.intelligence.dto.WxMpXmlMessage;
import com.stonedt.intelligence.service.WechatService;

import com.stonedt.intelligence.util.ResultUtil;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;


@RestController
@RequestMapping(value = "/monitor")
public class WechatController {
	
	

	private final WechatService wechatService;


    public WechatController(WechatService wechatService) {
        this.wechatService = wechatService;
    }

    /**
     * 获取微信二维码地址
     */
    @GetMapping("/getQrCode")
    public ResultUtil getQrCode() {
        return wechatService.getQRCodeUrl();
    }

    /**
     * 关注事件处理
     */
    @PostMapping("/handleSubscribe")
    public Boolean handleSubscribe(@RequestBody WxMpXmlMessage wxMpXmlMessage) {
        return wechatService.handleSubscribe(wxMpXmlMessage);
    }

    /**
     * 授权事件处理
     */
    @PostMapping("/handleAuthorize")
    public void handleAuthorize(@RequestBody WechatUserInfo wechatUserInfo) {
        wechatService.handleAuthorize(wechatUserInfo);
    }

    /**
     * 登录检查
     */
    @GetMapping("/checkLogin")
    public ResultUtil checkLogin(@RequestParam String sceneStr, HttpServletRequest request) {
        return wechatService.checkLogin(sceneStr,request);
    }

}
