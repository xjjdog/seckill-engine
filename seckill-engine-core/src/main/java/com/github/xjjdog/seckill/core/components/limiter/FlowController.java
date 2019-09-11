package com.github.xjjdog.seckill.core.components.limiter;

import com.google.common.util.concurrent.RateLimiter;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 原理在这里，我专门写了一篇文章介绍guava的使用 <br/>
 * 高并发之限流，到底限的什么鬼 （精品长文）： https://mp.weixin.qq.com/s/UEy272RDQ2-Ygxn3W4x7IA
 */
class FlowController {
    private final RateLimiter rateLimiter;

    private int maxPermits;

    /**
     * 等待获取permits的请求个数，原则上可以通过maxPermits推算
     */
    private int maxWaitingRequests;

    private AtomicInteger waitingRequests = new AtomicInteger(0);

    public FlowController(int maxPermits, int maxWaitingRequests) {
        this.maxPermits = maxPermits;
        this.maxWaitingRequests = maxWaitingRequests;
        this.rateLimiter = RateLimiter.create(maxPermits);
    }

    public FlowController(int permits, long warmUpPeriodAsSecond, int maxWaitingRequests) {
        this.maxPermits = maxPermits;
        this.maxWaitingRequests = maxWaitingRequests;
        this.rateLimiter = RateLimiter.create(permits, warmUpPeriodAsSecond, TimeUnit.SECONDS);
    }

    public boolean acquire() {
        return acquire(1);
    }

    public boolean acquire(int permits) {
        boolean success = rateLimiter.tryAcquire(permits);
        if (success) {
            rateLimiter.acquire(permits);
            return true;
        }
        if (waitingRequests.get() > maxWaitingRequests) {
            return false;
        }
        waitingRequests.getAndAdd(permits);
        rateLimiter.acquire(permits);

        waitingRequests.getAndAdd(0 - permits);
        return true;
    }
}
