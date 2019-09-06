package com.github.xjjdog.sf.controller;

import com.github.xjjdog.sf.common.model.ResponseData;
import com.github.xjjdog.sf.common.util.ResponseUtil;
import com.github.xjjdog.sf.service.GoodsServiceI;
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
