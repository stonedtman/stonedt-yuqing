package com.stonedt.intelligence.controller;

import com.stonedt.intelligence.dto.MailConfig;
import com.stonedt.intelligence.service.MailService;
import com.stonedt.intelligence.util.ResultUtil;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * description: 邮件控制层 <br>
 * @author 文轩
 */
@RestController
@RequestMapping("/mail")
public class MailController {

    private final MailService mailService;

    public MailController(MailService mailService) {
        this.mailService = mailService;
    }


    /**
     * 保存邮件配置
     */
    @PostMapping("/saveMailConfig")
    public ResultUtil saveMailConfig(@RequestBody MailConfig mailConfig, HttpServletRequest request, HttpServletResponse response) throws Exception {
        return mailService.saveMailConfig(mailConfig, request, response);
    }

    /**
     * 检查是否配置了邮件
     */
    @PostMapping("/checkMailConfig")
    public ResultUtil checkMailConfig(HttpServletRequest request) {
        return mailService.checkMailConfig(request);
    }

    /**
     * 获取邮件配置
     */
    @PostMapping("/getMailConfig")
    public ResultUtil getMailConfig(HttpServletRequest request) {
        return mailService.getMailConfig(request);
    }

}
