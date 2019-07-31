/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2019 All Rights Reserved.
 */
package Lock;

import java.util.concurrent.Semaphore;

/**
 * Semaphore可设置现线程等待个数的锁（用共享锁实现）
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