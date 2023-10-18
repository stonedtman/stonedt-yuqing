package com.stonedt.intelligence.vo;

import lombok.Data;

/**
 * description: 绑定参数 <br>
 *
 * @author 文轩
 */
@Data
public class BindParamsVo {

    /**
     * 用户id
     */
    private int userId;

    /**
     * 认证id
     */
    private String secretId;

    /**
     * 认证key
     */
    private String secretKey;

}
