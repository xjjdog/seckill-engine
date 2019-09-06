package com.github.xjjdog.sf.core.target;

import lombok.Data;

import java.util.concurrent.TimeUnit;

@Data
public final class Pre {
    /**
     * 是否是懒加载方式
     */
    boolean lazy;

    /**
     * 如果启用了lazy模式。在秒杀前多少秒，如果有请求进来，则进行数据加载
     * 默认1小时
     */
    int beforehandSecond = (int) TimeUnit.HOURS.toSeconds(1);

    /**
     * 数据预加载时间，如果lazy=false，则此参数无效
     */
    long onLoadTime;
}
