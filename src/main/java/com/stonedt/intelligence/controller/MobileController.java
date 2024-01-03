package com.stonedt.intelligence.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("/mobile")
public class MobileController {

    @RequestMapping("/monitor")
    public ModelAndView monitor(ModelAndView modelAndView) {
        modelAndView.setViewName("/mobile/monitor");
        return modelAndView;
    }

    @RequestMapping("/monitor/detail")
    public ModelAndView monitorDetail(ModelAndView modelAndView) {
        modelAndView.setViewName("/mobile/detail");
        return modelAndView;
    }

    @RequestMapping("/warning")
    public ModelAndView monitorWarning(ModelAndView modelAndView) {
        modelAndView.setViewName("/mobile/warning");
        return modelAndView;
    }
}
