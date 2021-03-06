package com.springboot.controller;

import com.springboot.common.shiro.SessionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/index")
public class IndexController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @RequestMapping(value = "/goIndex", method = RequestMethod.GET)
    public String goIndex(Model model) {
        logger.info("=================进入首页============================");
        model.addAttribute("localUser", SessionUtil.getAttribute("threadLocal"));
        //return "/pages/index";
        return "/index";
    }
}
