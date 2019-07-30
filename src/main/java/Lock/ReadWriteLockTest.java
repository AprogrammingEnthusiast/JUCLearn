/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2019 All Rights Reserved.
 */
package Lock;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 读写锁测试
 * @author wb-wj449816
 * @version $Id: ReadWriteLockTest.java, v 0.1 2019年07月29日 19:22 wb-wj449816 Exp $
 */
class Helper  {

    private ReadWriteLock lock;

    public Helper(ReadWriteLock lock) {
        this.lock = lock;
    }

    public void read() {
        lock.readLock().lock();
        try {
            System.out.println("====线程"+Thread.currentThread().getName()+"  获取了读锁");
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } finally {
            System.out.println("----线程"+Thread.currentThread().getName()+" 释放了读锁");
            lock.readLock().unlock();
        }
    }

    public void write() {
        lock.writeLock().lock();
        try {
            System.out.println("====线程"+Thread.currentThread().getName()+"  获取了写锁");
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } finally {
            System.out.println("----线程"+Thread.currentThread().getName()+" 释放了写锁");
            lock.writeLock().unlock();
        }
    }

}

public class ReadWriteLockTest {

    public static void newThread(Helper helper, String name, boolean isRead) {
        new Thread(new Runnable() {

            @Override
            public void run() {
                System.out.println("线程"+Thread.currentThread().getName()+" 运行");
                if (isRead) helper.read();
                else helper.write();
            }
        }, name).start();
    }
    public static void main(String[] args) {
        ReadWriteLock lock = new ReentrantReadWriteLock();
        Helper helper = new Helper(lock);
        newThread(helper, "t1111", true);
        newThread(helper, "t2222", true);
        newThread(helper, "t3333", false);
        newThread(helper, "t444", true);
    }
}