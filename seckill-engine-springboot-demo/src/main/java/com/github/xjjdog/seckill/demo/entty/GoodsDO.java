package com.github.xjjdog.seckill.demo.entty;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.github.xjjdog.seckill.demo.common.entity.IDAutoEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@TableName("sf_goods")
public class GoodsDO extends IDAutoEntity<GoodsDO> {

    @TableField("name")
    private String name;
    @TableField("saleType")
    private String saleType;
    @TableField("stock")
    private Integer stock;

}
