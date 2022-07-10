package com.wangguangwu.thread;

import org.junit.Test;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 使用两个线程交替打出 1 ～ 100 间的奇数和偶数
 *
 * @author wangguangwu
 */
public class TestThread {





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
