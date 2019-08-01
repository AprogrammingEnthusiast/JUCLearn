/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2019 All Rights Reserved.
 */
package Lock;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * CyclicBarrier表示循环屏障，它内部有个屏障数count，当调用await方法就会减少一个屏障，
 * 并让当前线程等待。当屏障数count减到0的时候，表示条件已满足，所有等待的线程应该被唤醒，
 * 并且重置屏障数count，这样就可以继续使用了。
 * ps:
 * 想想一下这样一个场景，有多个人需要过河，河上有一条船，船要等待满10个人才过河，过完河后每个人又各自行动。
 * @author wb-wj449816
 * @version $Id: CyclicBarrierTest.java, v 0.1 2019年08月01日 11:18 wb-wj449816 Exp $
 */
public class CyclicBarrierTest {

    public static void newThread(String name, CyclicBarrier barrier) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    System.out.println("线程" + Thread.currentThread().getName() + "等待船满过河");
                    barrier.await();
                    System.out.println("线程" + Thread.currentThread().getName() + "过完河, 各自行动");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
            }
        }, name).start();
    }

    public static void main(String[] args) {
        CyclicBarrier barrier = new CyclicBarrier(10, new Runnable() {
            @Override
            public void run() {
                System.out.println("\n在线程" + Thread.currentThread().getName() + "中 船过河了\n");
            }
        });

        for (int i = 1; i <= 10; i++) {
            newThread("t" + i, barrier);
        }
    }

}