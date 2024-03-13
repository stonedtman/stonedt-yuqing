package com.stonedt.intelligence.controller;

import com.alibaba.fastjson.JSONObject;
import com.google.code.kaptcha.Constants;
import com.stonedt.intelligence.aop.SystemControllerLog;
import com.stonedt.intelligence.entity.User;
import com.stonedt.intelligence.service.UserService;
import com.stonedt.intelligence.util.*;

import com.stonedt.intelligence.vo.LoginVO;
import com.stonedt.intelligence.vo.ResultVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 登陆控制器
 * @author wangyi
 *
 */
@Controller
@RequestMapping(value = "/")
public class LoginController {

    @Autowired
    UserService userService;

    @Autowired
    UserUtil userUtil;

    /**
     * 登录页面跳转
     * @param mv
     * @return
     */
  //  @SystemControllerLog(module = "用户登录",submodule="用户登录", type = "查询",operation = "login")
    @GetMapping(value = "/login")
    public ModelAndView login(ModelAndView mv,@RequestParam(required = false) String reference) throws UnsupportedEncodingException {
        if (reference == null || "".equals(reference)) {
            mv.addObject("reference", "");
        }else {
            mv.addObject("reference", URLDecoder.decode(reference, "UTF-8"));
        }
        mv.setViewName("user/login");
        return mv;
    }

    @GetMapping(value = "/loginbak")
    public ModelAndView loginbak(ModelAndView mv) {
        mv.setViewName("user/loginbak");
        return mv;
    }

    /**
     * description: 退出 <br>
     * version: 1.0 <br>
     * date: 2020/4/13 11:08 <br>
     * author: objcat <br>
     *
     * @return ModelAndView
     */
    @SystemControllerLog(module = "用户登出", submodule = "用户登出", type = "登出", operation = "logout")
    @GetMapping(value = "/logout")
    public void logout(HttpServletResponse response, HttpServletRequest request) {
        try {
            try {
                User user = userUtil.getuser(request);
                try {
                    //如果Vector中有用户==》移除==》记录==>这样如果切换到别的浏览器同一账号登录且之前账号没有退出就不准确了
                    //如果Vector中没用户==》不记录
                        userService.updateEndLoginTime(user.getUser_id());
                        userUtil.removeUser(request, response);
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

            response.sendRedirect(request.getContextPath() + "login");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * description: 登录处理<br>
     * version: 1.0 <br>
     * date: 2020/4/13 11:09 <br>
     * author: objcat <br>
     *
     * @param telephone(登录手机号)、password(密码)
     * @return ModelAndView
     */
    @SystemControllerLog(module = "用户登录", submodule = "用户登录", type = "登录", operation = "login")
    @PostMapping(value = "/login")
    @ResponseBody
    public JSONObject login(@RequestParam(value = "telephone") String telephone,
                            @RequestParam(value = "password") String password,
                            @RequestParam(value = "graph_code") String graph_code,
                            HttpSession session,
                            HttpServletRequest request,
                            HttpServletResponse httpServletResponse) {
        JSONObject response = new JSONObject();
        String string = null;//图形验证码
        if(null!=session.getAttribute(Constants.KAPTCHA_SESSION_KEY)) {
            string = session.getAttribute(Constants.KAPTCHA_SESSION_KEY).toString();
        }
        User user = userService.selectUserByTelephone(telephone);
        if (null != user) {
            if (user.getTerm_of_validity() != null) {
                if (user.getTerm_of_validity().before(new Date())) {
                    response.put("code", Integer.valueOf(500));
                    response.put("msg", "账号已过期！");
                    return response;
                }
            }
            if (user.getStatus() == 0) {
                response.put("code", 3);
                response.put("msg", "用户禁止登录");
            } else {
                if (MD5Util.getMD5(password).equals(user.getPassword())) {
                    Integer status = user.getStatus();
                    if (status == 2) {
                        response.put("code", 4);
                        response.put("msg", "账户已被注销");
                    } 
                    if(null==string||!graph_code.equals(string)) {
                        response.put("code", 6);
                        response.put("msg", "图形验证码不正确");
                        return response;
                    }
                    else {

                        Integer login_count = user.getLogin_count() + 1;
                        String end_login_time = DateUtil.getNowTime();
                        Map<String, Object> paramMap = new HashMap<String, Object>();
                        paramMap.put("telephone", telephone);
                        paramMap.put("end_login_time", end_login_time);
                        paramMap.put("login_count", login_count);
                        userService.updateUserLoginCountByPhone(paramMap);
                        try {
                            userUtil.setUser(request, httpServletResponse, user);
                            response.put("code", 1);
                            response.put("msg", "用户登录成功");
                        } catch (Exception e) {
                            e.printStackTrace();
                            response.put("code", -1);
                            response.put("msg", "用户登录失败");
                        }

                    }
                } else {
                    response.put("code", 2);
                    response.put("msg", "登录密码错误");
                }
            }
        } else {
            response.put("code", -1);
            response.put("msg", "用户不存在");
        }
        return response;
    }


    /**
     * description: 跳转忘记密码页面 <br>
     * version: 1.0 <br>
     * date: 2020/4/13 11:08 <br>
     * author: objcat <br>
     *
     * @return ModelAndView
     */
    @SystemControllerLog(module = "用户登录", submodule = "用户登录-忘记密码", type = "忘记密码", operation = "forgotpwd")
    @GetMapping(value = "/forgotpwd")
    public ModelAndView forgotpwd(ModelAndView mv) {
        mv.setViewName("user/forgotPassword");
        return mv;
    }

    @SystemControllerLog(module = "用户登录", submodule = "用户登录", type = "查询", operation = "jumpLogin")
    @GetMapping(value = "/jumpLogin")
    public String login(String b64, HttpServletResponse response,HttpServletRequest request) throws Exception {
        System.err.println("=====b64-encode:" + b64 + "====================================================");
        b64 = Base64.decode(b64);
        System.err.println("=====b64-decode:" + b64 + "====================================================");
        b64 = b64.substring(4, 15);
        System.err.println("=====phone:" + b64 + "========================================================");
        User user = userService.selectUserByTelephone(b64);
        if (null == user) {
            return "user/login";
        }
        Integer status = user.getStatus();
        if (status == 0 || status == 2) {
            return "user/login";
        }
        userUtil.setUser(request,response, user);
        Integer login_count = user.getLogin_count() + 1;
        String end_login_time = DateUtil.getNowTime();
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("telephone", b64);
        paramMap.put("end_login_time", end_login_time);
        paramMap.put("login_count", login_count);
        userService.updateUserLoginCountByPhone(paramMap);
        return "redirect:/monitor";
    }

    /**
     * 微信跳转登录
     * @param args
     */
    @GetMapping(value = "/wechatJumpLogin")
    public String wechatJumpLogin(String sha,
                                  String userId,
                                  HttpServletRequest request,HttpServletResponse response) throws Exception {
        if (userId==null|| userId.isEmpty() ||sha==null|| sha.isEmpty()){
            return "user/login";
        }
        User user = userService.selectUserByUserId(Long.valueOf(userId));
        if (null == user) {
            return "user/login";
        }
//        String password = user.getPassword();
//        if(password ==null|| password.isEmpty()) {
//            return "user/login";
//        }
        //password与传入的password进行比较
        String decode = ShaUtil.getSHA256(userId,false);
        if(!decode.equals(sha)) {
            return "user/login";
        }
        if (user.getStatus() == 0) {
            return "user/login";
        }
        if (user.getStatus() == 2) {
            return "user/login";
        }

        userUtil.setUser(request,response,user);
        userService.updateEndLoginTime(user.getUser_id());

        return "redirect:/monitor";
    }


    public static void main(String[] args) {
        System.err.println(Base64.encode("$$$#13813866138===1553241639885#$$$"));
    }

    /**
     * description: 登录处理<br>
     * version: 1.0 <br>
     * date: 2020/4/13 11:09 <br>
     * author: objcat <br>
     *
     * @param
     * @return ModelAndView
     */
//    @SystemControllerLog(module = "统计用户在线数量", submodule = "用户在线数量统计", type = "查询", operation = "onlinestatistical")
    @PostMapping(value = "/onlinestatistical")
    @ResponseBody
    public JSONObject onlinestatistical(HttpServletRequest request) {
        JSONObject response = new JSONObject();
        try {
            User user = userUtil.getuser(request);
            Map<String, Object>result = userService.onlinestatistical(user);
            response.put("onlinedata", result);
            response.put("code",1);
            return response;
        } catch (Exception e) {
            // TODO: handle exception
            response.put("code",-1 );
            response.put("msg","查询失败");
            return response;
        }
    }

    /**
     * 登录获取token
     */
    @Operation(summary = "登录获取token",
            description = "登录获取token。成功调用时，token在data中返回" +
                    "该token用于后续接口调用,请在请求头中以token为key，token值为value传递",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(content = @Content(schema = @Schema(implementation = LoginVO.class))),
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "登录成功"),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "登录失败")
            }
    )
    @PostMapping(value = "/user/getToken")
    @ResponseBody
    public ResultVO<String> getToken(@RequestBody LoginVO loginVO, HttpServletRequest request) {
        loginVO.trim();
        if (loginVO.getUsername() == null || loginVO.getPassword() == null
                || loginVO.getUsername().isEmpty() || loginVO.getPassword().isEmpty()) {
            return ResultVO.error("用户名或密码不能为空");
        }
        User user = null;
        try {
            user = userService.selectUserByTelephone(loginVO.getUsername());
        } catch (Exception e) {
            ResultVO.error("登录失败");
        }
        if (user == null) {
            return ResultVO.error("用户不存在");
        }
        if (!MD5Util.getMD5(loginVO.getPassword()).equals(user.getPassword())) {
            return ResultVO.error("密码错误");
        }
        if (user.getStatus() == 0) {
            return ResultVO.error("用户禁止登录");
        }
        if (user.getStatus() == 2) {
            return ResultVO.error("账户已被注销");
        }
        if (user.getTerm_of_validity().before(new Date())) {
            return ResultVO.error("账号已过期");
        }

        String token;
        try {
            token = userService.getToken(user);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultVO.error("登录失败");
        }
        return ResultVO.success(token);

    }


}
