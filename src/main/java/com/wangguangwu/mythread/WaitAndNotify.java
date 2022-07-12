package com.wangguangwu.mythread;

import lombok.AllArgsConstructor;


import java.util.concurrent.CountDownLatch;


/**
 * 线程的同步使用 Object 类自带的 wait 和 notifyAll 方法实现
 *
 * @author wangguangwu
 */
@AllArgsConstructor
public class WaitAndNotify implements Runnable {

    private boolean runNow;
    private final Object lock;
    private int num;
    private CountDownLatch countDownLatch;

    @Override
    public void run() {
        synchronized (lock) {
            int size = 100;
            while (num <= size) {
                if (runNow) {
                    runNow = false;
                } else {
                    try {
                        // 使当前线程等待
                        lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        Thread.currentThread().interrupt();
                    }
                }
                System.out.println(Thread.currentThread().getName() + " - " + runNow);
                System.out.println(Thread.currentThread().getName() + " - " + num);
                num += 2;

                // 通知其他线程运行
                lock.notifyAll();
            }
            countDownLatch.countDown();
        }
    }

}
