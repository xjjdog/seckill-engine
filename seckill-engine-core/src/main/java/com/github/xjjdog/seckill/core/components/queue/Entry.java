package com.github.xjjdog.seckill.core.components.queue;

import com.github.xjjdog.seckill.core.Holder;
import com.github.xjjdog.seckill.core.entity.ActionSell;
import com.github.xjjdog.seckill.core.target.Target;

public class Entry {
    Target target;
    ActionSell sell;

    Entry(Target target, ActionSell sell) {
        this.target = target;
        this.sell = sell;
    }

    public String toCacheString() {
        return target.getId() + "|" + sell.getCount() + "|" + sell.getCreateTime();
    }

    public static Entry fromString(String str) throws Exception {
        String[] split = str.split("\\|");
        String targetID = split[0];
        int count = Integer.valueOf(split[1]);
        long createTime = Long.parseLong(split[2]);
        ActionSell sell = new ActionSell();
        sell.setCount(count);
        sell.setCreateTime(createTime);

        Target target = Holder.getInstance().getTargetService().getTarget(targetID);
        Entry e = new Entry(target, sell);
        return e;
    }
}
