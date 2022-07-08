package com.wangguangwu.executor.rejectedExecutionHandler;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.util.StopWatch;

import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author wangguangwu
 */
public class TestCountdownLatch {

    private final StopWatch stopwatch = new StopWatch();

    @Before
    public void before() {
        stopwatch.start();
    }

    @After
    public void after() {
        stopwatch.stop();
        System.out.println("task execute time: " + stopwatch.getTotalTimeMillis());
    }


    private final ExecutorService threadPool = Executors.newFixedThreadPool(10);

    @Test
    public void testExecution() {
        int task  = 200;
        CountDownLatch countDownLatch = new CountDownLatch(1);
        // 可推送案件数
        AtomicInteger wait = new AtomicInteger(100);
        // 已经推送案件数
        AtomicInteger used = new AtomicInteger(0);
        for (int i = 0; i < task; i++) {
            threadPool.execute(() -> {
                if (wait.get() > 0) {
                    if (test()) {
                        wait.decrementAndGet();
                    }
                    used.incrementAndGet();
                } else {
                    countDownLatch.countDown();
                }
                // 运行数量等于需要推送的数目
                if (used.get() == task) {
                    countDownLatch.countDown();
                }
            });
        }
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            // Ignore
        }
        System.out.println("wait: " + wait);
        System.out.println("used: " + used);
        System.out.println("execute end");
    }

    private boolean test() {
        Random random = new Random();
        return random.nextBoolean();
    }

}
