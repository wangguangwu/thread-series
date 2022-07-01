package com.wangguangwu.countdownlatchdemo;

import java.util.concurrent.atomic.LongAdder;

/**
 * @author wangguangwu
 */
public class MyArrayList3<E> {

    /**
     * 维护一个数组
     */
    Object[] elementData;

    /**
     * 使用线程安全的变量进行计算
     */
    private final LongAdder size;

    public MyArrayList3() {
        // 直接给 1000000 的容量，免去扩容机制
        this.elementData = new Object[1000000];
        size = new LongAdder();
    }

    public boolean add(E e) {
        long i = size.sum();
        elementData[Math.toIntExact(i)] = e;
        size.increment();
        return true;
    }

    public long size() {
        return size.sum();
    }

}

