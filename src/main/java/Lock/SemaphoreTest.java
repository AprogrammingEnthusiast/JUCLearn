/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2019 All Rights Reserved.
 */
package Lock;

import java.util.concurrent.Semaphore;

/**
 * Semaphore类是一个共享锁，所以允许多个线程获取共享锁，它有一个permits许可数变量，
 * 只有当permits许可数大于0时，线程才可以获取共享锁，否则线程就要阻塞等待。
 * ps:
 * 假如有这样一个需求，现在有许多线程要执行，我们要控制同时执行的线程数量，
 * 当达到这个数量时，其他线程必须等待，只有当有线程执行完毕时，才会唤醒等待线程，让它执行。
 *
 * 形象地解释就是，有5个厕所，但是有10个人准备上厕所。所以只有前面5个人才能上厕所，后面的人必须等待。
 * @author wb-wj449816
 * @version $Id: SemaphoreTest.java, v 0.1 2019年07月31日 14:22 wb-wj449816 Exp $
 */
public class SemaphoreTest {

    public static void newThread(Semaphore semaphore, String name, int time) {
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("线程"+Thread.currentThread().getName()+" 开始");
                try {
                    semaphore.acquire();
                    System.out.println("线程"+Thread.currentThread().getName()+" 获取了许可");
                    Thread.sleep(time);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                semaphore.release();
                System.out.println("线程"+Thread.currentThread().getName()+" 结束");
            }
        }, name);
        t.start();
    }

    public static void main(String[] args) throws InterruptedException {
        Semaphore semaphore = new Semaphore(3);
        for (int i = 1; i <= 6; i++) {
            newThread(semaphore,"t"+i, 100);
        }
    }

}