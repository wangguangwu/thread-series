package com.wangguangwu.executor.rejectedExecutionHandler;

import com.wangguangwu.countdownlatchdemo.MyArrayList4;
import org.junit.Test;
import org.slf4j.helpers.MessageFormatter;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

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
    public void testNull() throws InterruptedException {
        MyArrayList<Integer> list = new MyArrayList<>();

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

//        list.forEach(System.out::println);

        AtomicInteger nullCount = new AtomicInteger();
        Set<Integer> set = new HashSet<>();


        list.forEach(a -> {
            if (a == null) {
                nullCount.getAndIncrement();
            }
            if (!set.contains(a) && a != null) {
                set.add(a);
            }
        });
        System.out.println(nullCount);
        System.out.println(set.size());
    }

    /**
     * @author wangguangwu
     */
    static class MyArrayList<E> {

        protected transient int modCount = 0;

        /**
         * 维护一个数组
         */
        Object[] elementData;

        /**
         * 使用线程安全的变量进行计算
         */
        private final AtomicInteger size;

        public MyArrayList() {
            // 直接给 1000000 的容量，免去扩容机制
            this.elementData = new Object[1000000];
            size = new AtomicInteger(0);
        }

        public boolean add(E e) {
            int i = size.get();
            elementData[i] = e;
            modCount++;
            size.incrementAndGet();
            return true;
        }

        public int size() {
            return size.get();
        }

        public void forEach(Consumer<? super E> action) {
            Objects.requireNonNull(action);
            final int expectedModCount = modCount;
            @SuppressWarnings("unchecked") final E[] elementData = (E[]) this.elementData;
            final int size = this.size.get();
            for (int i = 0; modCount == expectedModCount && i < size; i++) {
                action.accept(elementData[i]);
            }
            if (modCount != expectedModCount) {
                throw new ConcurrentModificationException();
            }
        }
    }

    @Test
    public void testMyArrayList() throws InterruptedException {

        MyArrayList<Integer> list = new MyArrayList<>();

        int num = 10;
        // 开启两个线程，各插入 1000 条数据
        Runnable runnable = () -> {
            for (int i = 0; i < num; i++) {
                list.add(i);
            }
        };

        ArrayList<Thread> threads = new ArrayList<>();

        int threadNum = 2;
        for (int i = 0; i < threadNum; i++) {
            Thread thread = new Thread(runnable);
            threads.add(thread);
            thread.start();
        }

        for (Thread thread : threads) {
            thread.join();
        }

        String message = MessageFormatter.format("expect: [{}}, real: [{}]", threadNum * num, list.size()).getMessage();
        System.out.println(message);
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
