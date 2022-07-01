package com.wangguangwu.volatiledemo;


/**
 * 线程安全的单例模式
 *
 * @author wangguangwu
 */
public class Singleton {

    /**
     * 1. 私有化构造方法，避免从外部 new 对象破坏单例模式
     */
    private Singleton() {
    }

    /**
     * 2. 通过私有变量保存单例对象，并使用 volatile 关键字进行修饰
     */
    private static volatile Singleton instance = null;

    /**
     * 提供公共获取单例对象的方法
     */
    public static Singleton getInstance() {
        // 双端检锁
        if (instance == null) {
            synchronized (Singleton.class) {
                if (instance == null) {
                  instance = new Singleton();
                }
            }
        }
        return instance;
    }

}
