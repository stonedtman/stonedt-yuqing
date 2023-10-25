package com.stonedt.intelligence.service;

import com.stonedt.intelligence.dto.MailConfig;
import com.stonedt.intelligence.entity.User;
import com.stonedt.intelligence.util.ResultUtil;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;

/**
 * 邮件服务接口
 * @author 文轩
 */
public interface MailService {


    /**
     * 发送邮件
     */
    void sendMail(MailConfig mailConfig, String title,String content,String nickname,boolean isHtml) throws GeneralSecurityException, MessagingException, UnsupportedEncodingException;



    /**
     * 发送预警邮件
     */
    void sendWarningMail(User user, String content) throws MessagingException, GeneralSecurityException, UnsupportedEncodingException;

    /**
     * 发送预警邮件
     */
    void sendWarningMail(MailConfig mailConfig, String content) throws MessagingException, GeneralSecurityException, UnsupportedEncodingException;

    /**
     * 发送预警邮件，并抄送
     */
    void sendWarningMail(User user, String content, String[] cc);


    /**
     * 保存邮件配置
     *
     * @return
     */
    ResultUtil saveMailConfig(MailConfig mailConfig, HttpServletRequest request);


    /**
     * 检查是否配置了邮件
     * @param request
     * @return
     */
    ResultUtil checkMailConfig(HttpServletRequest request);

    /**
     * 获取邮件配置
     * @param request
     * @return
     */
    ResultUtil getMailConfig(HttpServletRequest request);
}
