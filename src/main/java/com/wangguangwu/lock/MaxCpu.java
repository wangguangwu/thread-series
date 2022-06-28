package com.wangguangwu.lock;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * jstack 命令分析 cpu 过高
 *
 * @author wangguangwu
 */
@SuppressWarnings("all")
public class MaxCpu {

    private static ExecutorService executor = Executors.newFixedThreadPool(5);

    public static void main(String[] args) {
        Task task1 = new Task();
        Task task2 = new Task();
        executor.execute(task1);
        executor.execute(task2);
    }

    public static Object lock = new Object();

    static class Task implements Runnable {

        @Override
        public void run() {
            synchronized (lock) {
                long sum = 0L;
                while (true) {
                    sum += 1;
                    System.out.println(sum);
                }
            }
        }
    }
}
