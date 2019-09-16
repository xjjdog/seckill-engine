package com.github.xjjdog.seckill.core.target.optr;

import com.github.xjjdog.seckill.core.entity.RuntimeUnit;
import com.github.xjjdog.seckill.core.target.Target;

public interface Source {
    void load(Target target, RuntimeUnit runtimeUnit);
}
