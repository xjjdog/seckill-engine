package com.github.xjjdog.seckill.core.components.queue;

import com.github.xjjdog.seckill.core.entity.ActionSell;
import com.github.xjjdog.seckill.core.target.Target;

public class Entry {
    Target target;
    ActionSell sell;

    Entry(Target target, ActionSell sell) {
        this.target = target;
        this.sell = sell;
    }

}
