package com.github.xjjdog.seckill.demo.controller;

import com.github.xjjdog.seckill.demo.common.model.ResponseData;
import com.github.xjjdog.seckill.demo.common.util.ResponseUtil;
import com.github.xjjdog.seckill.demo.service.GoodsServiceI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/salefast")
public class SaleFastController {

    @Autowired
    private GoodsServiceI goodsService;

    @GetMapping({"/goods"})
    public ResponseData goods() {
        return ResponseUtil.success(goodsService.listMaps());
    }

}
