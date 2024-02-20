package com.stonedt.intelligence.interceptor;

import com.stonedt.intelligence.dto.UserDTO;
import com.stonedt.intelligence.entity.User;
import com.stonedt.intelligence.util.JWTUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
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
@Slf4j
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

        log.info("进入登录拦截器");

        //从查询参数中获取token
        String queryToken = request.getParameter("token");
        if (queryToken != null && !queryToken.isEmpty() && JWTUtils.decode(queryToken, privateKey)) {

            UserDTO userDTO = JWTUtils.getEntity(queryToken, UserDTO.class);

            if (userDTO == null || userDTO.getTokenIssueTime() + expireTime * 1000L < System.currentTimeMillis()) {
                String url = request.getRequestURI();
                sendRedirect(request, response,url);
                return false;
            }
            //并且将token放入cookie中
            Cookie cookie = new Cookie("token", queryToken);
            // 将token放在响应头中
            //配置域名，如果不配置，那么只能在当前项目下访问
            cookie.setPath("/");
            cookie.setMaxAge(expireTime.intValue());
            response.addCookie(cookie);
            response.setHeader("token", queryToken);
            return true;
        }



        //获取cookie
        Cookie[] cookies = request.getCookies();
        String token = null;
        //匹配名为token的cookie
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("token".equals(cookie.getName())) {
                    token = cookie.getValue();
                }
            }
        }

        //取出请求的url
        String url = request.getRequestURI();
        String queryString = request.getQueryString();
        if (queryString != null) {
            url += "?" + queryString;
        }


        if (token == null || token.isEmpty()) {
            sendRedirect(request, response,url);
            return false;
        }

        if (!JWTUtils.decode(token, privateKey)) {
            sendRedirect(request, response,url);
            return false;
        }

        UserDTO userDTO = JWTUtils.getEntity(token, UserDTO.class);

        if (userDTO == null || userDTO.getTokenIssueTime() + expireTime * 1000L < System.currentTimeMillis()) {
            sendRedirect(request, response,url);
            return false;
        }


        return true;

    }

    /**
     * 重定向方法
     */
    public void sendRedirect(HttpServletRequest request, HttpServletResponse response,String reference) throws Exception {
        if("XMLHttpRequest".equals(request.getHeader("X-Requested-With"))){
//                //告诉ajax我是重定向
            response.setHeader("REDIRECT", "REDIRECT");
//                //告诉ajax我重定向的路径
            response.setHeader("CONTENTPATH", "/login?reference="+java.net.URLEncoder.encode(reference, "UTF-8"));
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        }else{
            //referer是请求的来源地址,需要进行url编码
            response.sendRedirect(request.getContextPath() + "/login?reference="+java.net.URLEncoder.encode(reference, "UTF-8"));
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
