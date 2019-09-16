package com.github.xjjdog.seckill.demo.security.web.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ErrorPageController implements ErrorController {

    private static final String ERROR_PATH = "/error";

    @RequestMapping(value = ERROR_PATH)
    public String handleError() {
        return "err/404";
    }

    @RequestMapping(value = ERROR_PATH+"/401")
    public String unauthorizedError() {
        return "err/403";
    }

    @Override
    public String getErrorPath() {
        return ERROR_PATH;
    }

}
