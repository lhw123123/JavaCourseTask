package com.lhw.week04;

import java.util.concurrent.*;

/**
 * @author lhw
 * @title
 * @description
 * @created 7/16/21 4:11 PM
 * @changeRecord
 */
public class GetSunThreadValue02 {

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Future future = executorService.submit(new MyCallable(1));
        if (!future.isDone()) {
            System.out.println("task has not finished!");
        }
        System.out.println(Thread.currentThread().getName() + "线程拿到子线程返回值：" + future.get());
        executorService.shutdownNow();
    }
}
