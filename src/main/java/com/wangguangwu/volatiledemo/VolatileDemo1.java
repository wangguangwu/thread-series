package com.wangguangwu.volatiledemo;

/**
 * @author wangguangwu
 */
public class VolatileDemo1 {

    private static boolean flag = false;

    public static void main(String[] args) {
        new Thread(() -> {
            while (!flag) {
                // 如果说我在循环内部调用了 flag，又可以看到 flag 的值发生了变化
                // 说明线程 1 感知到了 flag 变量的变化
//                System.out.println(flag);
            }
            System.out.println("execute end");
        }).start();
        new Thread(() -> {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("set flag as true");
            flag = true;
        }).start();
    }

}
