/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2019 All Rights Reserved.
 */
package Lock;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

/**
 *
 * @author wb-wj449816
 * @version $Id: ThreadPoolExecutorTest.java, v 0.1 2019年08月09日 9:43 wb-wj449816 Exp $
 */
public class ThreadPoolExecutorTest {

    public static void main(String[] args) {

        ThreadFactory threadFactory = new MyThreadFactory();

        // 固定数量的线程池
        ExecutorService service = Executors.newFixedThreadPool(3, threadFactory);

        //        // 单个线程的线程池
        //        ExecutorService service = Executors.newSingleThreadExecutor(threadFactory);
        //
        //        // 缓存线程池
        //        ExecutorService service = Executors.newCachedThreadPool(threadFactory);

        for (int i = 0; i < 6; i++) {
            service.execute(new Run(i));
        }
        // 还是会执行完已经添加的任务
        service.shutdown();
    }

}

class MyThreadFactory implements ThreadFactory {
    private int sequenceNumber = 0;

    @Override
    public Thread newThread(Runnable r) {
        return new Thread(r, "线程" + (++sequenceNumber));
    }
}

class Run implements Runnable {

    private int index;

    public Run(int index) {
        this.index = index + 1;
    }

    @Override
    public void run() {
        System.out.println("--" + Thread.currentThread().getName() + "开始运行 任务" + index);
        try {
            int waitTime = 100 + index * 10;
            Thread.sleep(waitTime);
        } catch (InterruptedException e) {
            System.out.println(Thread.currentThread().getName() + " 发生中断异常  exception==" + e.getMessage());
        }
        System.out.println("=======" + Thread.currentThread().getName() + "结束 任务" + index);
    }
}