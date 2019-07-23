/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2019 All Rights Reserved.
 */
package Lock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 可重入锁测试
 * @author wb-wj449816
 * @version $Id: ReentrantLockTest.java, v 0.1 2019年07月23日 18:25 wb-wj449816 Exp $
 */
public class ReentrantLockTest {

    public static void newThread(Lock lock, String name, int time) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("线程" + Thread.currentThread().getName() + " 开始运行，准备获取锁");
                lock.lock();
                try {
                    System.out.println("====线程" + Thread.currentThread().getName() + " 在run方法中获取了锁");
                    lockAgain();
                    try {
                        Thread.sleep(time);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } finally {
                    System.out.println("----线程" + Thread.currentThread().getName() + " 在run方法中释放了锁");
                    lock.unlock();
                }
            }

            private void lockAgain() {
                lock.lock();
                try {
                    System.out.println("====线程" + Thread.currentThread().getName() + "  在lockAgain方法中再次获取了锁");
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } finally {

                    System.out.println("----线程" + Thread.currentThread().getName() + " 在lockAgain方法中释放了锁");
                    lock.unlock();
                }
            }
        }, name).start();
    }

    public static void main(String[] args) {
        Lock lock = new ReentrantLock();
        newThread(lock, "t1111", 1000);
        newThread(lock, "t2222", 1000);
        newThread(lock, "t3333", 1000);
    }

}