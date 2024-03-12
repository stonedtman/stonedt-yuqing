package com.stonedt.intelligence.dao;

import com.stonedt.intelligence.entity.MonitorWarningSetting;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
* @author 文轩
* @description 针对表【monitor_warning_setting】的数据库操作Mapper
* @createDate 2024-03-12 11:30:03
* @Entity com.stonedt.intelligence.entity.MonitorWarningSetting
*/
@Mapper
public interface MonitorWarningSettingDao {

    void insert(MonitorWarningSetting monitorWarningSetting);

    boolean exitByProjectId(Long projectId);

    void update(MonitorWarningSetting monitorWarningSetting);

    MonitorWarningSetting SelectByProjectId(Long projectId);

    List<MonitorWarningSetting> selectWaitWarningSetting();
}




