package com.wangguangwu.countdownlatchdemo;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author wangguangwu
 */
public class MyArrayList<E> {

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
        size.incrementAndGet();
        return true;
    }

    public int size() {
        return size.get();
    }

}
