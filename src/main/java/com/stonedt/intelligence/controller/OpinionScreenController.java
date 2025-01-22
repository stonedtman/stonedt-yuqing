package com.stonedt.intelligence.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@RequestMapping(value = "/opinion_screen")
@Controller
public class OpinionScreenController {

    @GetMapping
    public ModelAndView opinion_screen(ModelAndView mv) {
        mv.setViewName("opinion_screen/index");
        return mv;
    }
}

