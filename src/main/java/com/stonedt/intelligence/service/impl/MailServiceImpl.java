package com.stonedt.intelligence.service.impl;

import com.alibaba.fastjson.JSON;
import com.stonedt.intelligence.dao.UserDao;
import com.stonedt.intelligence.dto.MailConfig;
import com.stonedt.intelligence.entity.User;
import com.stonedt.intelligence.service.MailService;
import com.stonedt.intelligence.util.ResultUtil;
import com.stonedt.intelligence.util.UserUtil;
import com.sun.mail.util.MailSSLSocketFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.util.Properties;


/**
 * 邮件服务接口实现类
 * @author 文轩
 */
@Service
@Slf4j
public class MailServiceImpl implements MailService {

    private final UserDao userDao;

    private final UserUtil userUtil;

    public MailServiceImpl(UserDao userDao,
                           UserUtil userUtil) {
        this.userDao = userDao;
        this.userUtil = userUtil;
    }


    /**
     * 发送邮件
     *
     * @param mailConfig
     * @param title
     * @param content
     * @param nickname
     * @param isHtml
     */
    @Override
    public void sendMail(MailConfig mailConfig, String title, String content, String nickname, boolean isHtml) throws GeneralSecurityException, MessagingException, UnsupportedEncodingException {
        Properties prop = new Properties();
        // 开启debug调试，以便在控制台查看
        prop.setProperty("mail.debug", "true");
        // 设置邮件服务器主机名
        prop.setProperty("mail.host", mailConfig.getHost());
        // 发送服务器需要身份验证
        prop.setProperty("mail.smtp.auth", "true");
        // 发送邮件协议名称
        prop.setProperty("mail.transport.protocol", "smtp");

        // 开启SSL加密，否则会失败
        MailSSLSocketFactory sf = new MailSSLSocketFactory();
        sf.setTrustAllHosts(true);
        prop.put("mail.smtp.ssl.enable", "true");
        prop.put("mail.smtp.ssl.socketFactory", sf);

        // 创建session
        Session session = Session.getInstance(prop);
        // 通过session得到transport对象
        Transport ts = session.getTransport();
        // 连接邮件服务器：邮箱类型，帐号，授权码代替密码（更安全）
        ts.connect(mailConfig.getHost(), mailConfig.getUsername(), mailConfig.getPassword());// 后面的字符是授权码，用qq密码反正我是失败了（用自己的，别用我的，这个号是我瞎编的，为了。。。。）
        // 创建邮件对象
        MimeMessage message = new MimeMessage(session);
        // 指明邮件的发件人
        message.setFrom(new InternetAddress(mailConfig.getUsername(), nickname));
        // 指明邮件的收件人，现在发件人和收件人是一样的，那就是自己给自己发
        message.setRecipient(Message.RecipientType.TO, new InternetAddress(mailConfig.getTo()));
        // 邮件的标题
        message.setSubject(title);
        // 邮件的文本内容
        if (isHtml){
            message.setContent(content,"text/html;charset=utf-8");//用text/html的话换行符是<br>;用plain是纯文本
        }else {
            message.setText(content);
        }
        // 抄送
        if (mailConfig.getCc()!=null){
            for (String cc : mailConfig.getCc()) {
                message.addRecipient(Message.RecipientType.CC, new InternetAddress(cc));
            }
        }
        // 发送邮件
        ts.sendMessage(message, message.getAllRecipients());
        ts.close();
    }



    /**
     * 发送预警邮件
     *
     * @param user
     * @param content
     */
    @Override
    public void sendWarningMail(User user, String content) throws MessagingException, GeneralSecurityException, UnsupportedEncodingException {
        String mailJson = user.getMail_json();
        MailConfig mailConfig = JSON.parseObject(mailJson, MailConfig.class);
        sendWarningMail(mailConfig,content);

    }

    /**
     * 发送预警邮件
     *
     * @param mailConfig
     * @param content
     */
    @Override
    public void sendWarningMail(MailConfig mailConfig, String content) throws MessagingException, GeneralSecurityException, UnsupportedEncodingException {
        sendMail(mailConfig,"思通舆情  预警邮件推送",content,"思通舆情",true);
    }

    /**
     * 发送预警邮件，并抄送
     *
     * @param user
     * @param content
     * @param cc
     */
    @Override
    public void sendWarningMail(User user, String content, String[] cc) {
        String mailJson = user.getMail_json();
        MailConfig mailConfig = JSON.parseObject(mailJson, MailConfig.class);
        mailConfig.setCc(cc);
        try {
            sendMail(mailConfig,"思通舆情  预警邮件推送",content,"思通舆情",true);
        } catch (GeneralSecurityException | UnsupportedEncodingException | MessagingException e) {
            log.error("邮件发送失败",e);
        }
    }

    /**
     * 保存邮件配置
     *
     * @param mailConfig
     * @param request
     * @return
     */
    @Override
    public ResultUtil saveMailConfig(MailConfig mailConfig, HttpServletRequest request) {
        User user = userUtil.getuser(request);

        if (mailConfig.getTo()==null|| mailConfig.getTo().isEmpty()){
            mailConfig.setTo(mailConfig.getUsername());
        }
        // 发送测试邮件
        log.info("发送测试邮件");
        try {
            sendMail(mailConfig,"思通舆情  邮件配置测试","邮件配置测试","思通舆情",true);
        } catch (GeneralSecurityException | UnsupportedEncodingException | MessagingException e) {
            log.error("邮件发送失败",e);
            return ResultUtil.build(500,"测试邮件发送失败,请检查配置");
        }
        log.info("测试邮件发送成功");
        String mailJson = JSON.toJSONString(mailConfig);
        userDao.saveMailConfig(user.getId(),mailJson);
        User newUser = userDao.selectById(user.getId());
        request.getSession().setAttribute("User",newUser);
        return ResultUtil.ok();
    }

    /**
     * 检查是否配置了邮件
     *
     * @param request
     * @return
     */
    @Override
    public ResultUtil checkMailConfig(HttpServletRequest request) {
        User user = userUtil.getuser(request);
        String mailJson = user.getMail_json();
        if (mailJson==null||mailJson.isEmpty()){
            return ResultUtil.build(500,"未配置邮件");
        }
        return ResultUtil.ok();
    }

    /**
     * 获取邮件配置
     * @param request
     * @return
     */
    @Override
    public ResultUtil getMailConfig(HttpServletRequest request) {
        User user = userUtil.getuser(request);
        String mailJson = user.getMail_json();
        MailConfig mailConfig = JSON.parseObject(mailJson, MailConfig.class);
        return ResultUtil.ok(mailConfig);
    }
}
