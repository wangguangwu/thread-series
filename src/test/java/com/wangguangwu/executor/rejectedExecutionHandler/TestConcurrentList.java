package com.wangguangwu.executor.rejectedExecutionHandler;

import com.wangguangwu.cpuntdownlatchdemo.MyArrayList;
import com.wangguangwu.cpuntdownlatchdemo.MyArrayList4;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;

import static org.junit.Assert.assertEquals;

/**
 * @author wangguangwu
 */
public class TestConcurrentList {

    @Test
    public void testArrayList() throws InterruptedException {

        List<Integer> list = new ArrayList<>();

        CountDownLatch countDownLatch = new CountDownLatch(2);

        // 开启两个线程，每个线程插入 1000 次
        Runnable runnable = () -> {
            for (int i = 0; i < 1000; i++) {
                list.add(i);
            }
            countDownLatch.countDown();
        };

        for (int i = 0; i < 2; i++) {
            new Thread(runnable).start();
        }

        countDownLatch.await();
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

        MyArrayList4<Integer> list = new MyArrayList4<>();
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
    public void
    testVector() throws InterruptedException {
        // 使用 Vector 存放数据
        Vector<Integer> vector = new Vector<>();

        CountDownLatch countDownLatch = new CountDownLatch(2);

        // 开启两个线程，各执行 10000 次插入
        Runnable runnable = (() -> {
            for (int i = 0; i < 10000; i++) {
                vector.add(i);
            }
            countDownLatch.countDown();
        });

        for (int i = 0; i < 2; i++) {
            new Thread(runnable).start();
        }

        countDownLatch.await();
        System.out.println("size: " + vector.size());
    }

    @Test
    public void testSynchronizedList() throws InterruptedException {
        List<Object> list = Collections.synchronizedList(new ArrayList<>());

        CountDownLatch countDownLatch = new CountDownLatch(2);

        // 开启两个线程，各执行 10000 次插入操作
        Runnable runnable = (() -> {
            for (int i = 0; i < 10000; i++) {
                list.add(i);
            }
            countDownLatch.countDown();
        });

        for (int i = 0; i < 2; i++) {
            new Thread(runnable).start();
        }

        countDownLatch.await();
        assertEquals(20000, list.size());
        System.out.println("size: " + list.size());
    }

    @Test
    public void testCopyOnWriteArrayList() throws InterruptedException {
        CopyOnWriteArrayList<Integer> list = new CopyOnWriteArrayList<>();

        CountDownLatch countDownLatch = new CountDownLatch(2);

        // 开启两个线程，各执行 10000 次插入操作
        Runnable runnable = (() -> {
            for (int i = 0; i < 10000; i++) {
                list.add(i);
            }
            countDownLatch.countDown();
        });

        for (int i = 0; i < 2; i++) {
            new Thread(runnable).start();
        }

        countDownLatch.await();
        assertEquals(20000, list.size());
        System.out.println("size: " + list.size());
    }

    @Test
    public void testConcurrentList() {
        for (int j = 0; j < 5; j++) {

            int size = 100000 << j;

            // Vector
            Vector<Integer> vector = new Vector<>();
            vector.add(0);
            long vectorBegin = System.currentTimeMillis();
            for (int i = 0; i < size; i++) {
                vector.get(0);
            }
            long vectorEnd = System.currentTimeMillis();
            System.out.println("vector: " + (vectorEnd - vectorBegin));

            // SynchronizedList
            List<Integer> synchronizedList = Collections.synchronizedList(new ArrayList<>());
            synchronizedList.add(0);
            long synchronizedListBegin = System.currentTimeMillis();
            for (int i = 0; i < size; i++) {
                synchronizedList.get(0);
            }
            long synchronizedListEnd = System.currentTimeMillis();
            System.out.println("synchronizedList: " + (synchronizedListEnd - synchronizedListBegin));

            // CopyOnWriteArrayList
            List<Integer> copyOnWriteArrayList = new CopyOnWriteArrayList<>();
            copyOnWriteArrayList.add(0);
            long copyOnWriteArrayListBegin = System.currentTimeMillis();
            for (int i = 0; i < size; i++) {
                copyOnWriteArrayList.get(0);
            }
            long copyOnWriteArrayListEnd = System.currentTimeMillis();
            System.out.println("copyOnWriteArrayList: " + (copyOnWriteArrayListEnd - copyOnWriteArrayListBegin));
            System.out.println("================================================");
        }
    }

}
