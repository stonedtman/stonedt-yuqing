package com.stonedt.intelligence.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Set;

/**
 * 邮件配置实体
 * @author 文轩
 */
@Data
public class MailConfig implements Serializable {

    /**
     * host
     */
    private String host;

    /**
     * port
     */
    private String port;

    /**
     * username
     */
    private String username;

    /**
     * password
     */
    private String password;

    /**
     * 收件人邮箱
     */
    private String to;

    /**
     * 抄送人邮箱
     */
    private String[] cc;

    /**
     * 收件人列表
     */
    private Set<String> toList;
}
