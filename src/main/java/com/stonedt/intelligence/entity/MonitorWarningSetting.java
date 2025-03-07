package com.stonedt.intelligence.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.sql.Time;
import java.util.Date;
import java.util.Set;

/**
 * 
 * @author 文轩
 */
@Data
public class MonitorWarningSetting implements Serializable {
    /**
     * 
     */
    private Integer id;

    /**
     *  项目组id 序列化为string 反序列化为long
     */
    @JsonSerialize(using = ToStringSerializer.class, as = Long.class)
    private Long groupId;

    /**
     * 
     */
    @JsonSerialize(using = ToStringSerializer.class, as = Long.class)
    private Long projectId;

    /**
     * 收件人列表
     */
    private Set<String> toList;

    /**
     * 推送时间
     */
    @JsonFormat(pattern = "HH:mm:ss", timezone = "GMT+8",shape = JsonFormat.Shape.STRING)
    @DateTimeFormat(pattern = "HH:mm:ss")
    private Time popUpTime;

    /**
     * 是否开启推送
     */
    private boolean enable;

    /**
     * 实时推送（0：否 1：是）
     */
    private Integer realTimePush;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 修改时间
     */
    private Date updateTime;

    private static final long serialVersionUID = 1L;

}