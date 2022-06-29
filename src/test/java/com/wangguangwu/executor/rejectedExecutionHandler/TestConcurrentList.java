package com.wangguangwu.executor.rejectedExecutionHandler;

import com.wangguangwu.cpuntdownlatchdemo.MyArrayList;
import com.wangguangwu.cpuntdownlatchdemo.MyArrayList2;
import com.wangguangwu.cpuntdownlatchdemo.MyArrayList3;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * @author wangguangwu
 */
public class TestConcurrentList {

    @Test
    public void testArrayList() throws InterruptedException {

        List<Integer> list = new ArrayList<>();

        // 开启两个线程，每个线程插入 1000 次
        Runnable runnable = () -> {
            for (int i = 0; i < 1000; i++) {
                list.add(i);
            }
        };

        for (int i = 0; i < 2; i++) {
            new Thread(runnable).start();
        }

        Thread.sleep(500);
        System.out.println("size: " + list.size());
    }

    @Test
    public void testMyArrayList() throws InterruptedException {

        MyArrayList<Integer> list = new MyArrayList<>();
        CountDownLatch countDownLatch = new CountDownLatch(2);

        // 开启两个线程，各插入 1000 条数据
        Runnable runnable = (() -> {
            for (int i = 0; i < 100000; i++) {
                list.add(i);
            }
            countDownLatch.countDown();
        });

        for (int i = 0; i < 2; i++) {
            new Thread(runnable).start();
        }

        countDownLatch.await();
        System.out.println("size: " + list.size());
    }

    @Test
    public void testMyArrayList2() throws InterruptedException {

        CountDownLatch countDownLatch = new CountDownLatch(2);

        MyArrayList3<Integer> list = new MyArrayList3<>();
        // 开启两个线程，各插入 1000 条数据
        Runnable runnable = (() -> {
            for (int i = 0; i < 100000; i++) {
                list.add(i);
            }
            countDownLatch.countDown();
        });

        for (int i = 0; i < 2; i++) {
            new Thread(runnable).start();
        }

        countDownLatch.await();
        System.out.println("size: " + list.size());
    }

}
