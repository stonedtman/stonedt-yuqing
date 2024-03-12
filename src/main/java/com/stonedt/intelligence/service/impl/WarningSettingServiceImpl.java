package com.stonedt.intelligence.service.impl;

import com.alibaba.fastjson2.JSON;
import com.stonedt.intelligence.dao.MonitorWarningSettingDao;
import com.stonedt.intelligence.dao.ProjectDao;
import com.stonedt.intelligence.dao.UserDao;
import com.stonedt.intelligence.dto.MailConfig;
import com.stonedt.intelligence.entity.MonitorWarningSetting;
import com.stonedt.intelligence.entity.User;
import com.stonedt.intelligence.service.WarningSettingService;
import com.stonedt.intelligence.util.UserUtil;
import com.stonedt.intelligence.vo.ResultVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * @author 文轩
 */
@Service
public class WarningSettingServiceImpl implements WarningSettingService {

    private final  MonitorWarningSettingDao monitorWarningSettingDao;

    private final ProjectDao projectDao;

    private final UserDao userDao;

    private final UserUtil userUtil;

    public WarningSettingServiceImpl(MonitorWarningSettingDao monitorWarningSettingDao,
                                     ProjectDao projectDao,
                                     UserDao userDao,
                                     UserUtil userUtil) {
        this.monitorWarningSettingDao = monitorWarningSettingDao;
        this.projectDao = projectDao;
        this.userDao = userDao;
        this.userUtil = userUtil;
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultVO<Void> warningSetting(MonitorWarningSetting monitorWarningSetting,
                                         HttpServletRequest request, HttpServletResponse response) {
        Date popUpTime = monitorWarningSetting.getPopUpTime();
        if (popUpTime == null) {
            return ResultVO.error("推送时间不能为空");
        }
        //检查是否是准点
        if (popUpTime.getMinutes() != 0 || popUpTime.getSeconds() != 0) {
            return ResultVO.error("推送时间必须是准点");
        }
        //检查收件人列表
        Set<String> toList = monitorWarningSetting.getToList();
        if (toList == null || toList.isEmpty()) {
            return ResultVO.error("收件人不能为空");
        }


        User user = userUtil.getuser(request);

        //检查
        Long projectId = monitorWarningSetting.getProjectId();
        Long userId = user.getUser_id();
        boolean exist = projectDao.existByProjectIdAndUserId(projectId, userId);
        if (!exist) {
            return ResultVO.error("项目不存在");
        }
        String mailJson = user.getMail_json();
        if (mailJson == null) {
            return ResultVO.error("请先配置邮箱");
        }
        MailConfig mailConfig = JSON.parseObject(mailJson, MailConfig.class);
        mailConfig.setToList(toList);
        String mailConfigJsonString = JSON.toJSONString(mailConfig);

        user.setMail_json(mailConfigJsonString);
        try {
            userUtil.setUser(request,response, user);
        } catch (Exception e) {
            return ResultVO.error("设置失败");
        }
        //插入

        userDao.saveMailConfig(user.getId(), mailConfigJsonString);
        if (monitorWarningSettingDao.exitByProjectId(projectId)) {
            monitorWarningSettingDao.update(monitorWarningSetting);
        }else {
            monitorWarningSettingDao.insert(monitorWarningSetting);
        }

        return ResultVO.success();
    }

    @Override
    public ResultVO<MonitorWarningSetting> getWarningSetting(Long projectId, HttpServletRequest request) {
        User user = userUtil.getuser(request);
        Long userId = user.getUser_id();
        boolean exist = projectDao.existByProjectIdAndUserId(projectId, userId);
        if (!exist) {
            return ResultVO.error("项目不存在");
        }
        MonitorWarningSetting monitorWarningSetting = monitorWarningSettingDao.SelectByProjectId(projectId);
        if (monitorWarningSetting == null) {
            monitorWarningSetting = new MonitorWarningSetting();
            monitorWarningSetting.setProjectId(projectId);
            String mailJson = user.getMail_json();
            if (mailJson != null) {
                MailConfig mailConfig = JSON.parseObject(mailJson, MailConfig.class);
                monitorWarningSetting.setToList(mailConfig.getToList());
            }
        }
        return ResultVO.success(monitorWarningSetting);
    }

    /**
     * 查询等待推送的预警设置
     */
    @Override
    public List<MonitorWarningSetting> getWaitWarningSetting() {
        return monitorWarningSettingDao.selectWaitWarningSetting();
    }
}
