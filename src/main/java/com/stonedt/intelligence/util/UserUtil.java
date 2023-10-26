package com.stonedt.intelligence.util;

import com.stonedt.intelligence.entity.User;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * description 用户操作工具 <br>
 * date: 2020/4/14 15:01 <br>
 * author: huajiancheng <br>
 * version: 1.0 <br>
 */
@Component
public class UserUtil {
	
    public User getuser(HttpServletRequest request) {
        // 从 http 请求头中取出 token
        String token = request.getHeader("token");
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
}
