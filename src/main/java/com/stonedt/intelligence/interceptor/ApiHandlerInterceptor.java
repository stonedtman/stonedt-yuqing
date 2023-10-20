package com.stonedt.intelligence.interceptor;

import com.stonedt.intelligence.dto.UserDTO;
import com.stonedt.intelligence.util.JWTUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * api接口调用拦截器
 *
 * @author 文轩
 */
@Component
public class ApiHandlerInterceptor implements HandlerInterceptor {

    @Value("${token.expire-time}")
    private Long expireTime;

    @Value("${token.private-key}")
    private String privateKey;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 从 http 请求头中取出 token
        String token = request.getHeader("token");
        // 如果不存在,则从session中获取
        if (token == null && token.isEmpty()) {
            return false;
        }
        // 验证 token
        if (JWTUtils.decode(token, privateKey)) {
            UserDTO userDTO = JWTUtils.getEntity(token, UserDTO.class);
            // 判断 token 是否过期
            long currentTimeMillis = System.currentTimeMillis();
            if (userDTO.getTokenIssueTime() + expireTime * 1000 > currentTimeMillis) {
                return true;
            }else {
                // 返回utf-8 json格式的错误信息
                response.setCharacterEncoding("UTF-8");
                response.setContentType("application/json; charset=utf-8");
                response.getWriter().write("{\"code\":401,\"msg\":\"token过期\"}");
                return false;
            }
        }else {
            // 返回utf-8 json格式的错误信息
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json; charset=utf-8");
            response.getWriter().write("{\"code\":403,\"msg\":\"token错误\"}");
            return false;
        }

    }
}
