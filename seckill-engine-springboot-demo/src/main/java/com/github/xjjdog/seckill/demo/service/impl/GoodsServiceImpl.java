package com.github.xjjdog.seckill.demo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.xjjdog.seckill.demo.entty.GoodsDO;
import com.github.xjjdog.seckill.demo.mapper.GoodsMapper;
import com.github.xjjdog.seckill.demo.service.GoodsServiceI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GoodsServiceImpl extends ServiceImpl<GoodsMapper, GoodsDO> implements GoodsServiceI {

    @Autowired
    private GoodsMapper goodsMapper;



}
