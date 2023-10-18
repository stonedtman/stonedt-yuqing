package com.stonedt.intelligence.controller;

import com.stonedt.intelligence.entity.User;
import com.stonedt.intelligence.service.PlatformService;
import com.stonedt.intelligence.util.ResultUtil;
import com.stonedt.intelligence.util.UserUtil;
import com.stonedt.intelligence.vo.BindParamsVo;
import com.stonedt.intelligence.vo.CopyWriting;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 跨平台服务控制层
 * @author 文轩
 */
@RestController
@RequestMapping(value = "/platform")
public class PlatformController {

    private final UserUtil userUtil;

    private final PlatformService platformService;

    public PlatformController(UserUtil userUtil,
                              PlatformService platformService) {
        this.userUtil = userUtil;
        this.platformService = platformService;
    }

    /**
     * nlp服务绑定
     */
    @PostMapping(value = "/nlp/bind")
    public ResultUtil nlpBind(@RequestBody BindParamsVo bindParamsVo, HttpServletRequest request) {
                // 绑定
        return platformService.nlpBind(bindParamsVo,request);
    }

    /**
     * nlp光学字符识别
     */
    @PostMapping(value = "/nlp/ocr")
    public ResultUtil nlpOcr(MultipartFile images,@RequestParam String imageUrl, HttpServletRequest request) {

        // 获取用户id
        User user = userUtil.getuser(request);
        if (!user.getNlp_flag().equals(1)){
            return ResultUtil.build(424, "未绑定nlp服务");
        }
        // 调用
        try {
            return platformService.nlpOcr(user, images,imageUrl);
        } catch (IOException e) {
            e.printStackTrace();
            return ResultUtil.build(500, "ocr识别失败");
        }

    }

    /**
     * nlp图像识别
     */
    @PostMapping(value = "/nlp/image")
    public ResultUtil nlpImage(MultipartFile images, @RequestParam String imageUrl, HttpServletRequest request) {

            // 获取用户id
            User user = userUtil.getuser(request);
            if (!user.getNlp_flag().equals(1)){
                return ResultUtil.build(424, "未绑定nlp服务");
            }
            // 调用
            try {
                return platformService.nlpImage(user, images,imageUrl);
            } catch (IOException e) {
                e.printStackTrace();
                return ResultUtil.build(500, "图像识别失败");
            }

    }

    /**
     * 写作宝绑定
     */
    @PostMapping(value = "/xie/bind")
    public ResultUtil xieBind(@RequestBody BindParamsVo bindParamsVo, HttpServletRequest request) {

        // 绑定
        return platformService.xieBind(bindParamsVo,request);
    }

    /**
     * 写作宝绑定检查
     */
    @GetMapping(value = "/xie/checkBind")
    public ResultUtil xieCheckBind(HttpServletRequest request) {
        // 获取用户id
        User user = userUtil.getuser(request);
        // 绑定
        if (user.getXie_flag().equals(1)){
            return ResultUtil.ok();
        }else {
            return ResultUtil.build(424, "未绑定写作宝服务");
        }
    }

    /**
     * 写作宝标题生成
     */
    @PostMapping(value = "/xie/title/{articleId}")
    public ResultUtil xieReportTitle(@RequestBody CopyWriting copyWriting,
                                     @PathVariable String articleId,
                                     HttpServletRequest request,
                                     HttpServletResponse response) {

        response.addHeader("X-Accel-Buffering", "no");
        response.setHeader("Content-Encoding", "none");
        // 获取用户
        User user = userUtil.getuser(request);

        // 调用
        return platformService.xieReportTitle(user, copyWriting,articleId);
    }


    /**
     * 写作宝智写报告
     */
    @PostMapping(value = "/xie/report/{articleId}")
    public SseEmitter xieReport(@RequestBody CopyWriting copyWriting,@PathVariable String articleId, HttpServletRequest request, HttpServletResponse response) {
        response.addHeader("X-Accel-Buffering", "no");
        response.setHeader("Content-Encoding", "none");
        // 获取用户
        User user = userUtil.getuser(request);

        // 调用
        return platformService.xieReport(user, copyWriting,articleId);
    }

    /**
     * 写作宝智写报告,get请求
     */
    @GetMapping(value = "/xie/report")
    public SseEmitter xieReportGet(String articleId,
                                   Long projectId,
                                   String relatedword,
                                   String publishTime,
                                   String title,
                                   HttpServletRequest request,
                                   HttpServletResponse response) {
        response.addHeader("X-Accel-Buffering", "no");
        response.setHeader("Content-Encoding", "none");
        // 获取用户
        User user = userUtil.getuser(request);

        // 调用
        return platformService.xieReport(user, articleId, projectId, relatedword,publishTime,title);
    }

    /**
     * 获取公告
     */
    @GetMapping(value = "/notice")
    public ResultUtil getNotice() {
        return platformService.getNewNotice();
    }

}
