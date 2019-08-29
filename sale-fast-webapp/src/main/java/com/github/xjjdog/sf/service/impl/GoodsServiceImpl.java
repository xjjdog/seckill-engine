package com.github.xjjdog.sf.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.xjjdog.sf.entty.GoodsDO;
import com.github.xjjdog.sf.mapper.GoodsMapper;
import com.github.xjjdog.sf.service.GoodsServiceI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GoodsServiceImpl extends ServiceImpl<GoodsMapper, GoodsDO> implements GoodsServiceI {

    @Autowired
    private GoodsMapper goodsMapper;



}
