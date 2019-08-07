/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2019 All Rights Reserved.
 */
package Lock;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

/**
 * Callable表示一个任务，当任务执行完成后会返回结果值。但是结果值并不是立即返回的，
 * 那么其他线程怎么获取这个结果值呢？那么就需要Future对象。
 *
 * Future就是监控Callable任务完成情况的，通过组合的方式，即Future对象实例一般有个Callable实例的成员变量。
 * 当Callable任务还没有完成时，调用Future的get方法，就会让当前线程等待，直到Callable任务完成。
 * @author wb-wj449816
 * @version $Id: FutureTaskTest.java, v 0.1 2019年08月07日 16:22 wb-wj449816 Exp $
 */
public class FutureTaskTest {

    public static void newThread(String name, Future<Integer> future) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("线程" + Thread.currentThread().getName() + "开始运行");
                try {
                    int result = future.get();
                    System.out.println("线程" + Thread.currentThread().getName() + "获取结果  result==" + result);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            }
        }, name).start();
    }

    public static void main(String[] args) {
        FutureTask<Integer> future = new FutureTask<Integer>(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                System.out.println("线程" + Thread.currentThread().getName() + "运行任务");
                Thread.sleep(1000);
                System.out.println("线程" + Thread.currentThread().getName() + "任务运行完成");
                return 100;
            }
        });
        new Thread(new Runnable() {
            @Override
            public void run() {
                future.run();
            }
        }, "t10").start();

        newThread("t1", future);
        newThread("t2", future);
        newThread("t3", future);
        newThread("t4", future);
    }

}