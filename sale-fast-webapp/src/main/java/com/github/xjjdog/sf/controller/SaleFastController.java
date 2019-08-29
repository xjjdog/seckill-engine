package com.github.xjjdog.sf.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/salefast")
public class SaleFastController {

    @GetMapping({"/test"})
    public String deleteArticleOriginal() {
        return "11111";
    }

}
