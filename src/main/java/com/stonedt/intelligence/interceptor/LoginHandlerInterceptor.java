package com.stonedt.intelligence.interceptor;

import com.stonedt.intelligence.dto.UserDTO;
import com.stonedt.intelligence.entity.User;
import com.stonedt.intelligence.util.JWTUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * 拦截器，登录检查
 *
 * @author Mapeng
 * <p>
 * Date: 2019年10月11日
 */
@Component
public class LoginHandlerInterceptor implements HandlerInterceptor {

    @Value("${token.expire-time}")
    private Long expireTime;

    @Value("${token.private-key}")
    private String privateKey;

    // 目标方法执行之前
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
//        Object user = request.getSession().getAttribute("User");
//		// 如果获取的request的session中的loginUser参数为空（未登录），就返回登录页，否则放行访问
//        if (user == null) {
//            // 未登录，给出错误信息，
//            request.setAttribute("msg","无权限请先登录");
//            // 获取request返回页面到登录页
//            response.sendRedirect(request.getContextPath() + "/login");
//            return false;
//        } else {
//            // 已登录，放行
//            return true;
//        }
        // 从 http 请求头中取出 token
        String token = request.getHeader("token");
        // 如果不存在,则从session中获取
        if (token != null && !token.isEmpty()) {
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

        Object attribute = request.getSession().getAttribute("User");
        System.out.println(attribute);
        if (attribute == null) {
            if("XMLHttpRequest".equals(request.getHeader("X-Requested-With"))){
//                //告诉ajax我是重定向
                response.setHeader("REDIRECT", "REDIRECT");
//                //告诉ajax我重定向的路径
                response.setHeader("CONTENTPATH", "/login");
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            }else{
                response.sendRedirect(request.getContextPath() + "/login");
            }
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {
        // TODO Auto-generated method stub
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
        // TODO Auto-generated method stub
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }
}
