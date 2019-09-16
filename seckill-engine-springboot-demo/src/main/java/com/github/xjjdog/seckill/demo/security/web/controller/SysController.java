package com.github.xjjdog.seckill.demo.security.web.controller;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping({"/"})
public class SysController {


    @RequestMapping(value = "/login", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView loginPage(HttpServletRequest request, ModelAndView mv) {
        String shiroLoginFailureMsg = (String) request.getAttribute("shiroLoginFailure");
        if (StringUtils.isNotBlank(shiroLoginFailureMsg)) {
            mv.addObject("msg", shiroLoginFailureMsg);
        }
        Subject subject = SecurityUtils.getSubject();
        if ((subject != null) && (subject.isAuthenticated())) {
            subject.logout();
        }
        mv.setViewName("/sys/login");
        return mv;
    }

    @RequestMapping(value = {"/logout"}, method = {RequestMethod.GET, RequestMethod.POST})
    public String logoutForm(HttpServletRequest request) {
        Subject subject = SecurityUtils.getSubject();
        if ((subject != null) && (subject.isAuthenticated())) {
            subject.logout();
        }
        return "/sys/login";
    }

    @GetMapping({"/center"})
    public String adminPage() {
        return "/sys/index";
    }

    @GetMapping({"/recently"})
    public String recentlyPage() {
        return "/sys/recently";
    }





}
