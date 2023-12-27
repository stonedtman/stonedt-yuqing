package com.stonedt.intelligence.util;

import com.alibaba.fastjson.JSON;
import com.stonedt.intelligence.dto.UserDTO;
import com.stonedt.intelligence.entity.User;
import com.stonedt.intelligence.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

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
        String token = request.getHeader("token");
        if (token == null || token.isEmpty()) {
            token = request.getParameter("token");
        }

        // 如果存在,则从token中获取，否则从session中获取
        if (token != null && !token.isEmpty()) {
            try {
                return JWTUtils.getEntity(token, User.class);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("User");
        return user;
    }
    
    public long getUserId(HttpServletRequest request) {
        // 从 http 请求头中取出 token
        String token = request.getHeader("token");
        if (token == null || token.isEmpty()) {
            token = request.getParameter("token");
        }
        // 如果存在,则从token中获取，否则从session中获取
        if (token != null && !token.isEmpty()) {
            try {
                return JWTUtils.getEntity(token, User.class).getUser_id();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        HttpSession session = request.getSession();
    	User user = (User) session.getAttribute("User");
    	return user.getUser_id();
	}


    public void setUser(HttpServletRequest request,
                        HttpServletResponse response,
                        User user) throws Exception {
        // 从 http 请求头中取出 token
        String token = request.getHeader("token");
        if (token == null || token.isEmpty()) {
            token = request.getParameter("token");
        }

        UserDTO userDTO = JWTUtils.getEntity(token, UserDTO.class);
        BeanUtils.copyProperties(user,userDTO);
        // 生成token
        String newToken = userService.getToken(userDTO);
        // 将token放在响应头中
        response.setHeader("token", newToken);
    }
}
