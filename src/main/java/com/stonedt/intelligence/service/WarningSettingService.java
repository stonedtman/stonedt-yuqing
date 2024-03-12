package com.stonedt.intelligence.service;


import com.stonedt.intelligence.entity.MonitorWarningSetting;
import com.stonedt.intelligence.vo.ResultVO;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface WarningSettingService {
    ResultVO<Void> warningSetting(MonitorWarningSetting warningSetting,
                                  HttpServletRequest request, HttpServletResponse response);

    ResultVO<MonitorWarningSetting> getWarningSetting(Long projectId, HttpServletRequest request);
}
