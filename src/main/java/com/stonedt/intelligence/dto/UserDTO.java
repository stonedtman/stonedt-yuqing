package com.stonedt.intelligence.dto;

import com.stonedt.intelligence.entity.User;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.Date;

/**
 * @author 文轩
 * description: 用户信息实体 <br>
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class UserDTO extends User {

    /**
     * token签发时间
     */
    private Long tokenIssueTime;
}
