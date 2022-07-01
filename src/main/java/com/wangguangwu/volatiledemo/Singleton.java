package com.wangguangwu.volatiledemo;


/**
 * 线程安全的单例模式
 *
 * @author wangguangwu
 */
public class Singleton {

    private Singleton() {
    }

    /**
     * 使用 volatile 禁止指令重排序
     */
    private static volatile Singleton instance = null;

    /**
     * 给私有变量加 volatile 是为了防止步骤 2 执行时，进行重排序
     * <p>
     * instance = new Singleton(); 会创建一个对象
     * <p>
     * 2.1. 创建内存空间
     * 2.2 在内存空间中初始化对象 Singleton
     * 2.3. 将 Singleton 的内存地址赋值给 instance 变量（此时，instance 变量就不为 null 了）
     *
     * 2.2 和 2.3 之间不存在依赖关系，谁先执行对于最后的执行结果没有改变，因此两者之间是允许重排优化的。
     * <p>
     * 假设此时有两个线程，线程 A 和线程 B，如果没有通过添加 volatile 关键字禁止重排序
     * 线程 A 在执行步骤 1 的时候执行了指令重排序，将步骤 2 的 2.1、2.2、2.3 重排为了 2.1、2.3、2.2
     * 当线程 A 执行到 2.2 时，此时 instance 变量已经不为 null，但并未实例化结束。
     * 此时线程 B 执行步骤 1 时，发现 instance 变量不为 null，直接返回一个未实例化的对象，从而导致程序执行出错。
     */
    public static Singleton getInstance() {
        // 双端检锁
        if (// 步骤 1
                instance == null) {
            synchronized (Singleton.class) {
                if (instance == null) {
                    // 步骤 2
                    instance = new Singleton();
                }
            }
        }
        return instance;
    }

}
