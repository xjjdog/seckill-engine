package com.github.xjjdog.sf.core.target;

import lombok.Data;

@Data
public final class Post {
    /**
     * 数据回写周期，如果sink为空，则不做任何运算
     */
    long sinkPeriod;
}
