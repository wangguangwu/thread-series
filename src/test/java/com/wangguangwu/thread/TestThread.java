package com.wangguangwu.thread;

import com.wangguangwu.mythread.WaitAndNotify;
import org.junit.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 使用两个线程交替打出 1 ～ 100 间的奇数和偶数
 *
 * @author wangguangwu
 */
public class TestThread {

    // test printNUm
    @Test
    public void testWaitAndNotify() throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(2);
        Object lock = new Object();
        Thread thread1 = new Thread(new WaitAndNotify(true, lock, 1, countDownLatch));
        Thread thread2 = new Thread(new WaitAndNotify(false, lock, 2, countDownLatch));
        // t2 先运行，先进入等待状态
        thread2.start();
        // t1 直接运行，唤醒 t2 后进入等待
        thread1.start();
        // junit 单元测试不支持多线程
        countDownLatch.await();
        System.out.println("执行结束");
    }

    //===============================================================

    private final AtomicInteger threadNo = new AtomicInteger(1);

    @Test
    public void testAlternatelyPrint() {
        new Thread(() -> {
            for (int i = 0; i < 100; i += 2) {
                while (threadNo.get() != 1) {

                }
                System.out.println("偶数：" + i);
                threadNo.set(2);
            }
        }).start();

        new Thread(() -> {
            for (int i = 1; i < 100; i += 2) {
                while (threadNo.get() != 2) {

                }
                System.out.println("奇数：" + i);
                threadNo.set(1);
            }
        }).start();
    }

}
