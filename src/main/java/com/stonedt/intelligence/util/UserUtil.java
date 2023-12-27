package com.stonedt.intelligence.util;

import com.alibaba.fastjson.JSON;
import com.stonedt.intelligence.dto.UserDTO;
import com.stonedt.intelligence.entity.User;
import com.stonedt.intelligence.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * description 用户操作工具 <br>
 * date: 2020/4/14 15:01 <br>
 * author: huajiancheng <br>
 * version: 1.0 <br>
 */
@Component
public class UserUtil {

    private final UserService userService;

    public UserUtil(UserService userService) {
        this.userService = userService;
    }

    public User getuser(HttpServletRequest request) {
        // 从 http 请求头中取出 token
        Cookie[] cookies = request.getCookies();
        String token = null;
        //匹配名为token的cookie
        for (Cookie cookie : cookies) {
            if ("token".equals(cookie.getName())) {
                token = cookie.getValue();
            }
        }

        // 如果存在,则从token中获取，否则从session中获取
        if (token != null && !token.isEmpty()) {
            try {
                return JWTUtils.getEntity(token, User.class);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        //抛出异常
        throw new RuntimeException("token不存在！");
    }
    
    public long getUserId(HttpServletRequest request) {
        // 从 http 请求头中取出 token
        Cookie[] cookies = request.getCookies();
        String token = null;
        //匹配名为token的cookie
        for (Cookie cookie : cookies) {
            if ("token".equals(cookie.getName())) {
                token = cookie.getValue();
            }
        }
        // 如果存在,则从token中获取，否则从session中获取
        if (token != null && !token.isEmpty()) {
            try {
                return JWTUtils.getEntity(token, User.class).getUser_id();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        //抛出异常
        throw new RuntimeException("token不存在！");
	}


    public void setUser(HttpServletRequest request,
                        HttpServletResponse response,
                        User user) throws Exception {
        // 从 http 请求头中取出 token
        // 从 http 请求头中取出 token
        Cookie[] cookies = request.getCookies();
        String token = null;
        //匹配名为token的cookie
        for (Cookie cookie : cookies) {
            if ("token".equals(cookie.getName())) {
                token = cookie.getValue();
            }
        }

        UserDTO userDTO = JWTUtils.getEntity(token, UserDTO.class);
        BeanUtils.copyProperties(user,userDTO);
        // 生成token
        String newToken = userService.getToken(userDTO);
        // 将token放在响应头中
        response.setHeader("token", newToken);
    }
}
