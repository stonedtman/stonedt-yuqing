package com.stonedt.intelligence.quartz;

import com.stonedt.intelligence.dao.MonitorWarningSettingDao;
import com.stonedt.intelligence.service.WarningSettingService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author 文轩
 */
@Component
public class MonitorWarningQuartz {

    @Value("${schedule.monitor-warning.open}")
    private boolean open;

    private final WarningSettingService warningSettingService;

    public MonitorWarningQuartz(WarningSettingService warningSettingService) {
        this.warningSettingService = warningSettingService;
    }


    /**
     * 每小时检查一次
     */
    @Scheduled(cron = "0 0 * * * ?")
    public void checkWarningSetting() {
        if (!open) {
            return;
        }
    }


}
