package com.stonedt.intelligence.controller;

import com.stonedt.intelligence.dto.WechatUserInfo;
import com.stonedt.intelligence.dto.WxMpXmlMessage;
import com.stonedt.intelligence.entity.User;
import com.stonedt.intelligence.service.WechatService;

import com.stonedt.intelligence.util.ResultUtil;
import com.stonedt.intelligence.util.UserUtil;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@RestController
@RequestMapping(value = "/wechat")
public class WechatController {
	
	

	private final WechatService wechatService;

    private final UserUtil userUtil;


    public WechatController(WechatService wechatService, UserUtil userUtil) {
        this.wechatService = wechatService;
        this.userUtil = userUtil;
    }

    /**
     * 获取微信二维码地址
     */
    @GetMapping("/getQrCode")
    public ResultUtil getQrCode() {
        return wechatService.getQRCodeUrl();
    }

    /**
     * 获取绑定二维码
     */
    @GetMapping("/getBindQrCode")
    public ResultUtil getBindQrCode(HttpServletRequest request) {
        User user = userUtil.getuser(request);
        return wechatService.getBindQRCodeUrl(user);
    }

    /**
     * 检查是否已经绑定
     */
    @GetMapping("/checkBind")
    public ResultUtil checkBind(HttpServletRequest request, HttpServletResponse response) {
        User user = userUtil.getuser(request);
        return wechatService.checkBind(user,request,response);
    }

    /**
     * 关注事件处理
     */
    @PostMapping("/handleSubscribe")
    public Boolean handleSubscribe(@RequestBody WxMpXmlMessage wxMpXmlMessage) {
        return wechatService.handleSubscribe(wxMpXmlMessage);
    }

    /**
     * 取消关注事件处理
     */
    @GetMapping("/handleUnsubscribe")
    public void handleUnsubscribe(@RequestParam String openId) {
        wechatService.handleUnsubscribe(openId);
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
    public ResultUtil checkLogin(@RequestParam String sceneStr, HttpServletRequest request, HttpServletResponse response) throws Exception {
        return wechatService.checkLogin(sceneStr,request,response);
    }

}
