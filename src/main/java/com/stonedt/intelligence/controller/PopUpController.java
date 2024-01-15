package com.stonedt.intelligence.controller;

import com.stonedt.intelligence.entity.User;
import com.stonedt.intelligence.service.PopUpService;
import com.stonedt.intelligence.util.UserUtil;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author 文轩
 */
@RestController
@RequestMapping("/popUp")
public class PopUpController {


    private final PopUpService popUpService;

    private final UserUtil userUtil;

    public PopUpController(PopUpService popUpService,
                           UserUtil userUtil) {
        this.popUpService = popUpService;
        this.userUtil = userUtil;
    }


    /**
     * 是否弹窗
     */
    @GetMapping("/needPopUp")
    public boolean needPopUp(HttpServletRequest request) {
        User user = userUtil.getuser(request);
        return popUpService.needPopUp(user);
    }

    /**
     * 弹窗次数+1
     */
    @PostMapping("/close")
    public void close(HttpServletRequest request) {
        User user = userUtil.getuser(request);
        popUpService.close(user);
    }

}
