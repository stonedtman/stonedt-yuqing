package com.stonedt.intelligence.quartz;

import com.alibaba.fastjson.JSON;
import com.stonedt.intelligence.dao.MonitorWarningSettingDao;
import com.stonedt.intelligence.dao.OpinionConditionDao;
import com.stonedt.intelligence.dao.ProjectDao;
import com.stonedt.intelligence.dao.UserDao;
import com.stonedt.intelligence.dto.MailConfig;
import com.stonedt.intelligence.entity.MonitorWarningSetting;
import com.stonedt.intelligence.entity.OpinionCondition;
import com.stonedt.intelligence.entity.Project;
import com.stonedt.intelligence.entity.WarningSetting;
import com.stonedt.intelligence.service.ExcelService;
import com.stonedt.intelligence.service.MailService;
import com.stonedt.intelligence.service.MonitorService;
import com.stonedt.intelligence.service.WarningSettingService;
import com.stonedt.intelligence.thred.ThreadPoolConst;
import com.stonedt.intelligence.vo.ArticleData;
import com.stonedt.intelligence.vo.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * @author 文轩
 */
@Component
@Slf4j
public class MonitorWarningQuartz {

    @Value("${schedule.monitor-warning.open}")
    private boolean open;

    private final WarningSettingService warningSettingService;

    private final OpinionConditionDao opinionConditionDao;

    private final ProjectDao projectDao;

    private final MonitorService monitorService;

    private final MailService mailService;

    private final UserDao userDao;

    private final ExcelService excelService;

    private final DateTimeFormatter sdf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");


    public MonitorWarningQuartz(WarningSettingService warningSettingService,
                                OpinionConditionDao opinionConditionDao,
                                ProjectDao projectDao,
                                MonitorService monitorService,
                                MailService mailService,
                                UserDao userDao,
                                ExcelService excelService) {
        this.warningSettingService = warningSettingService;
        this.opinionConditionDao = opinionConditionDao;
        this.projectDao = projectDao;
        this.monitorService = monitorService;
        this.mailService = mailService;
        this.userDao = userDao;
        this.excelService = excelService;
    }


    /**
     * 每小时检查一次
     */
    @Scheduled(cron = "0 0 * * * ?")
    public void checkWarningSetting() {
        if (!open) {
            return;
        }
        List<MonitorWarningSetting> waitWarningSetting = warningSettingService.getWaitWarningSetting();
        log.info("监控预警任务开始......，共有{}个预警设置", waitWarningSetting.size());

        for (MonitorWarningSetting monitorWarningSetting : waitWarningSetting) {
            //查询偏好设置
            OpinionCondition opinionCondition = opinionConditionDao.selectByProjectId(monitorWarningSetting.getProjectId());
            if (opinionCondition == null) {
                continue;
            }

            Project project = projectDao.selectByProjectId(monitorWarningSetting.getProjectId());
            if (project == null) {
                continue;
            }

            LocalDateTime now = LocalDateTime.now();
            String timee = sdf.format(now);
            //24小时前
            LocalDateTime before = now.minusHours(24);
            String times = sdf.format(before);

            opinionCondition.setTimes(times);
            opinionCondition.setTimee(timee);

            //查询文章
            PageInfo<ArticleData> articleListByOpinionCondition = monitorService.getArticleListByOpinionCondition(opinionCondition, project, 1);
            if (articleListByOpinionCondition == null) {
                continue;
            }
            List<ArticleData> articleDataList = articleListByOpinionCondition.getData();
            //查询剩余页数
            int totalPage = articleListByOpinionCondition.getTotalPage();
            if (totalPage > 1) {
                for (int i = 2; i <= totalPage; i++) {
                    PageInfo<ArticleData> articleListByOpinionCondition1 = monitorService.getArticleListByOpinionCondition(opinionCondition, project, i);
                    if (articleListByOpinionCondition1 != null) {
                        articleDataList.addAll(articleListByOpinionCondition1.getData());
                    }
                }
            }



            final byte[] bytes;
            try {
                bytes = excelService.assembleExcel(articleDataList);
            } catch (IOException e) {
                log.error("监控预警任务失败......{}", e.getMessage());
                continue;
            }

            Long userId = project.getUserId();

            ThreadPoolConst.IO_EXECUTOR.execute(()->{
                try {
                    String mailJson = userDao.selectMailJsonByUserId(userId);
                    if (mailJson == null || mailJson.isEmpty()) {
                        log.info("监控预警邮件发送失败......用户未设置邮箱......");
                        return;
                    }
                    MailConfig mailConfig = JSON.parseObject(mailJson, MailConfig.class);
                    Set<String> toList = monitorWarningSetting.getToList();
                    String[] toArray = toList.toArray(new String[0]);
                    mailConfig.setTo(toArray[0]);
                    if (toArray.length > 1) {
                        mailConfig.setCc(Arrays.copyOfRange(toArray, 1, toArray.length));
                    }
                    if (mailConfig.getTo() == null || mailConfig.getTo().isEmpty()) {
                        log.info("监控预警邮件发送失败......用户未指定收件人......");
                        return;
                    }
                    mailService.sendWarningMail(mailConfig,bytes);
                    log.info("监控预警邮件发送成功......");
                } catch (Exception e) {
                    log.info("监控预警邮件发送失败......{}", e.getMessage());
                }
            });
        }

    }



    /**
     * @param nowTime
     * @param projectName 项目名称
     * @param warnSize       预警数量
     */
    private StringBuilder emailHtml(String nowTime, String projectName, int warnSize) {
        return new StringBuilder("<html>\r\n" +
                "	<head>\r\n" +
                "		<meta charset=\"utf-8\">\r\n" +
                "		<title>email</title>\r\n" +
                "		<style type=\"text/css\">\r\n" +
                "			body{\r\n" +
                "				background: url(https://cdn-a-files.yqt365.com/images/wap/bg.jpg) no-repeat;\r\n" +
                "				background-size: cover;\r\n" +
                "			}\r\n" +
                "			.main{\r\n" +
                "				width: 750px;\r\n" +
                "				margin: 0 auto;\r\n" +
                "				background: #ffffff;\r\n" +
                "				padding: 15px;\r\n" +
                "			}\r\n" +
                "			.imglogo{\r\n" +
                "				border-bottom: dashed 1px #ccc;\r\n" +
                "			}\r\n" +
                "			.title{\r\n" +
                "				margin-top:20px;\r\n" +
                "				border-bottom: #CCCCCC dashed 1px;\r\n" +
                "			}\r\n" +
                "			.content{\r\n" +
                "				width: 100%;\r\n" +
                "				border-bottom: #CCCCCC dashed 1px;\r\n" +
                "				padding-bottom: 10px;\r\n" +
                "			}\r\n" +
                "			.content:hover{\r\n" +
                "				background: #f2f2f2;\r\n" +
                "			}\r\n" +
                "			.content p{\r\n" +
                "				color: #9f9f9f;\r\n" +
                "				font-size: 10px;\r\n" +
                "			}\r\n" +
                "			.con{\r\n" +
                "				margin-top:10px;\r\n" +
                "				color: #676767;\r\n" +
                "			}\r\n" +
                "			.content a{\r\n" +
                "				text-decoration:  none;\r\n" +
                "				color: #0e7cd8;\r\n" +
                "			}\r\n" +
                "			.time{\r\n" +
                "				color:#e9610f !important;\r\n" +
                "			}\r\n" +
                "			.title span{\r\n" +
                "				color:#e02222;\r\n" +
                "			}\r\n" +
                "		</style>\r\n" +
                "	</head>\r\n" +
                "	<body>\r\n" +
                "		<div class=\"main\">\r\n" +
                "			<div class=\"title\">" +
                "				<p>截止时间:<span class=\"time\">" + nowTime + "</span></p>	\r\n" +
                "				<p>您的监测方案<span>［" + projectName + "］</span>新增<span>" + warnSize + "</span>条预警。显示如下:</p>	\r\n" +
                "			</div>");
    }


}
