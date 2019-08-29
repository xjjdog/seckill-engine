package com.github.xjjdog.sf.common.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public abstract class IDAutoEntity <T extends Model<?>> extends Model<T> {

    @TableId(value = "ID", type = IdType.AUTO)
    Long id;

    @Override
    protected Serializable pkVal() { return this.id; }

}
