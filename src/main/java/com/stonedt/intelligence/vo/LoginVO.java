package com.stonedt.intelligence.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author 文轩
 * description: 登录VO <br>
 */
@Data
@Schema(name = "LoginVO", description = "登录VO")
public class LoginVO {

    /**
     * 用户名
     */
    @Schema(name = "username", description = "用户名,即手机号")
    private String username;

    /**
     * 密码
     */
    @Schema(name = "password", description = "密码")
    private String password;

    /**
     * 去除空格
     */
    public void trim() {
        this.username = this.username.trim();
        this.password = this.password.trim();
    }

}
