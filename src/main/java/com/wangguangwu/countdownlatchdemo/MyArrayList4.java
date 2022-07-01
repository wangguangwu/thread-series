package com.wangguangwu.countdownlatchdemo;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author wangguangwu
 */
public class MyArrayList4<E> {

    Object[] elementData;

    private int size;

    private final Lock lock = new ReentrantLock();

    public MyArrayList4() {
        // 直接给 10000 的容量，免去扩容机制
        this.elementData = new Object[1000000];
    }

    public boolean add(E e) {
        // 加一把锁
        lock.lock();
        try {
            elementData[size++] = e;
            return true;
        } catch (Exception exception) {
            // Ignore
        } finally {
            lock.unlock();
        }
        return false;
    }

    public int size() {
        return size;
    }

}
