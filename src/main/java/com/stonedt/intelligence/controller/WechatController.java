package com.stonedt.intelligence.controller;

import com.stonedt.intelligence.dto.WechatUserInfo;
import com.stonedt.intelligence.dto.WxMpXmlMessage;
import com.stonedt.intelligence.entity.User;
import com.stonedt.intelligence.service.WechatService;

import com.stonedt.intelligence.util.ResultUtil;
import com.stonedt.intelligence.util.ShaUtil;
import com.stonedt.intelligence.util.UserUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@RestController
@RequestMapping(value = "/wechat")
public class WechatController {

    @Value("${token.private-key}")
    private String privateKey;
	
	

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
    public ResultUtil checkBind(HttpServletRequest request, HttpServletResponse response) throws Exception {
        User user = userUtil.getuser(request);
        return wechatService.checkBind(user,request,response);
    }

    /**
     * wasBind
     */
    @GetMapping("/wasBind")
    public ResultUtil wasBind(@RequestParam String sceneStr,HttpServletRequest request, HttpServletResponse response) throws Exception {
        return wechatService.wasBind(sceneStr,request,response);
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

    /**
     * 获取token
     */
    @GetMapping("/token")
    public String getToken(@RequestParam String openid,@RequestParam Long time,@RequestParam String ciphering) throws Exception {
        //私钥加密
        String sha1 = ShaUtil.getSHA1(openid + time + privateKey, false);
        //如果密文不一致，就不让登录
        if(!sha1.equals(ciphering)){
            return null;
        }

        return wechatService.getToken(openid);
    }

}
