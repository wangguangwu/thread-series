package com.wangguangwu.cpuntdownlatchdemo;


/**
 * @author wangguangwu
 */
public class MyArrayList2<E> {

    Object[] elementData;

    private int size;

    public MyArrayList2() {
        // 直接给 10000 的容量，免去扩容机制
        this.elementData = new Object[1000000];
    }

    public boolean add(E e) {
        // 加一把锁
        synchronized (this) {
            elementData[size++] = e;
            return true;
        }
    }

    public int size() {
        return size;
    }

}
