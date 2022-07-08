package com.wangguangwu.executor.rejectedExecutionHandler;

import lombok.extern.slf4j.Slf4j;
import org.junit.After;
import org.junit.Test;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author wangguangwu
 */
@Slf4j
public class TestRejectedExecutionHandler {

    private static ThreadPoolExecutor executor;

    private static final AtomicInteger times = new AtomicInteger(0);

    @After
    public void afterHandle() {
        // 关闭线程池
        executor.shutdown();
        log.info("执行线程数: {}", times);
    }

    @Test
    public void testAbortPolicy() {
        // 丢弃任务，并抛出拒绝执行 RejectedExecutionException 异常信息
        // 线程池的默认策略
        RejectedExecutionHandler abortPolicy = new ThreadPoolExecutor.AbortPolicy();
        // 获取线程池
        executor = getThreadPoolExecutor(abortPolicy);
        for (int i = 0; i < 100; i++) {
            try {
                executor.execute(new Thread(() -> {
                    times.incrementAndGet();
                    log.info("Thread【{}】is running", Thread.currentThread().getName());
                }));
            } catch (RejectedExecutionException e) {
                // 如果不处理抛出的异常，则会打断当前的执行流程，影响后续的任务执行
                log.error("error: {}", e.getMessage());
            }
        }
    }

    @Test
    public void testCallerRunsPolicy() {
        // 当触发拒绝策略时，只要线程池没有关闭，则使用调用线程直接运行任务
        RejectedExecutionHandler callerRunsPolicy = new ThreadPoolExecutor.CallerRunsPolicy();
        // 获取线程池
        executor = getThreadPoolExecutor(callerRunsPolicy);
        for (int i = 0; i < 100; i++) {
            try {
                executor.execute(new Thread(() -> {
                    times.incrementAndGet();
                    log.info("Thread【{}】is running", Thread.currentThread().getName());
                }));
            } catch (Exception e) {
                log.error("error: {}", e.getMessage());
            }
        }
    }

    @Test
    public void testDiscardPolicy() {
        // 直接丢弃
        RejectedExecutionHandler discardPolicy = new ThreadPoolExecutor.CallerRunsPolicy();
        // 获取线程池
        executor = getThreadPoolExecutor(discardPolicy);
        for (int i = 0; i < 100; i++) {
            try {
                executor.execute(new Thread(() -> {
                    times.incrementAndGet();
                    log.info("Thread【{}】is running", Thread.currentThread().getName());
                }));
            } catch (Exception e) {
                log.error("error: {}", e.getMessage());
            }
        }
    }

    @Test
    public void testDiscardOldestPolicy() {
        // 当触发拒绝策略时，只要线程池没有关闭，丢弃阻塞队列 workQueue 中最老的一个任务，并将新任务加入
        RejectedExecutionHandler discardOldestPolicy = new ThreadPoolExecutor.DiscardOldestPolicy();
        // 获取线程池
        executor = getThreadPoolExecutor(discardOldestPolicy);
        for (int i = 0; i < 100; i++) {
            try {
                executor.execute(new Thread(() -> {
                    times.incrementAndGet();
                    log.info("Thread【{}】is running", Thread.currentThread().getName());
                }));
            } catch (Exception e) {
                log.error("error: {}", e.getMessage());
            }
        }
    }


    /**
     * 获取线程池
     *
     * @param handler 拒绝策略
     * @return ThreadPoolExecutor
     */
    private ThreadPoolExecutor getThreadPoolExecutor(RejectedExecutionHandler handler) {
        // 核心线程数
        int corePoolSize = 5;
        // 最大线程数
        int maximumPoolSize = 10;
        // 存活时间
        long keepAlive = 5L;
        // 阻塞队列
        BlockingDeque<Runnable> workQueue = new LinkedBlockingDeque<>(10);
        return new ThreadPoolExecutor(corePoolSize,
                maximumPoolSize,
                keepAlive, TimeUnit.SECONDS,
                workQueue,
                handler);
    }

}
