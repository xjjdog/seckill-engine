package com.github.xjjdog.seckill.core.components.limiter;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Slf4j
public class FlowControllerTest {
    @Test
    public void testAcquire1() throws Exception {
        FlowController limiter = new FlowController(5, 0);
        boolean rs = limiter.acquire(1);
        assertEquals(rs, true);

        Thread.sleep(1000L/5);
        rs = limiter.acquire(1);
        assertEquals(rs, true);

        rs = limiter.acquire(1);
        assertEquals(rs, false);
    }

    @Test
    public void testAcquire2() throws Exception {
        FlowController limiter = new FlowController(5, 0);
        boolean rs = limiter.acquire(10);
        assertEquals(rs, true);
        rs = limiter.acquire(1);
        assertEquals(rs, false);
    }
}
