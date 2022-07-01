package com.wangguangwu.countdownlatchdemo;

import java.util.concurrent.CountDownLatch;

/**
 * @author wangguangwu
 */
public class Demo {
    public static void main(String[] args) throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(1);

        Thread 大乔 = new Thread(() -> waitToFight(countDownLatch));
        Thread 兰陵王 = new Thread(() -> waitToFight(countDownLatch));
        Thread 安其拉 = new Thread(() -> waitToFight(countDownLatch));
        Thread 哪吒 = new Thread(() -> waitToFight(countDownLatch));
        Thread 铠 = new Thread(() -> waitToFight(countDownLatch));

        大乔.start();
        兰陵王.start();
        安其拉.start();
        哪吒.start();
        铠.start();
        Thread.sleep(1000);
        System.out.println("敌方还有5秒达到战场，全军出击！");
        countDownLatch.countDown();
    }

    private static void waitToFight(CountDownLatch countDownLatch) {
        try {
            countDownLatch.await(); // 在此等待信号再继续
            System.out.println("收到，发起进攻！");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


}
