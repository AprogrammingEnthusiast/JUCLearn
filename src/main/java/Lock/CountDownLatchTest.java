/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2019 All Rights Reserved.
 */
package Lock;

import java.util.concurrent.CountDownLatch;

/**
 * CountDownLatch类也是一个共享锁，它有个变量count，当获取共享锁时，只有count等于0，才能获取成功，否则都是获取失败，当前线程阻塞等待。
 * ps:
 * 我们经常碰到这样一个需求，想让当前线程等待另一个线程完成之后在操作，这个很简单，在当前线程中调用另一个线程的join方法
 * 那么如果让多个线程等待多个线程完成之后在操作，该怎么办呢？
 * @author wb-wj449816
 * @version $Id: CountDownLatchTest.java, v 0.1 2019年07月31日 14:30 wb-wj449816 Exp $
 */
public class CountDownLatchTest {

    public static void newThread(CountDownLatch latch, String name, int time) {

        Thread t = new Thread(() -> {
            System.out.println("线程" + Thread.currentThread().getName() + " 开始");
            try {
                Thread.sleep(time);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //尝试释放CountDownLatch锁
            latch.countDown();
            System.out.println("线程" + Thread.currentThread().getName() + " 结束");
        }, name);
        t.start();
    }

    public static void main(String[] args) throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(4);
        newThread(latch,"t1", 100);
        newThread(latch,"t2", 100);
        newThread(latch,"t3", 200);
        newThread(latch,"t4", 500);

        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("线程"+Thread.currentThread().getName()+" 开始");
                try {
                    latch.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("线程"+Thread.currentThread().getName()+" 结束");
            }
        }, "thread000000000").start();
        latch.await();

        System.out.println("主线程结束");
    }

}