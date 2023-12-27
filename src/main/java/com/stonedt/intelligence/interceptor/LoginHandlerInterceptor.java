package com.stonedt.intelligence.interceptor;

import com.stonedt.intelligence.dto.UserDTO;
import com.stonedt.intelligence.entity.User;
import com.stonedt.intelligence.util.JWTUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
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
        if (token == null || token.isEmpty()) {
            token = request.getParameter("token");
        }

        if (!JWTUtils.decode(token, privateKey)) {
            sendRedirect(request, response);
            return false;
        }

        UserDTO userDTO = JWTUtils.getEntity(token, UserDTO.class);

        if (userDTO == null || userDTO.getTokenIssueTime() + expireTime * 1000L < System.currentTimeMillis()) {
            sendRedirect(request, response);
            return false;
        }


        return true;

    }

    /**
     * 重定向方法
     */
    public void sendRedirect(HttpServletRequest request, HttpServletResponse response) throws Exception {
        if("XMLHttpRequest".equals(request.getHeader("X-Requested-With"))){
//                //告诉ajax我是重定向
            response.setHeader("REDIRECT", "REDIRECT");
//                //告诉ajax我重定向的路径
            response.setHeader("CONTENTPATH", "/login");
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        }else{
            response.sendRedirect(request.getContextPath() + "/login");
        }
    }



    /**
     *
     * @param request current HTTP request
     * @param response current HTTP response
     * @param handler handler (or {@link HandlerMethod}) that started asynchronous
     * execution, for type and/or instance examination
     * @param modelAndView the {@code ModelAndView} that the handler returned
     * (can also be {@code null})
     * @throws Exception
     */
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
