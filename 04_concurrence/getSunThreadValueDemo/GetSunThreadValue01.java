package com.lhw.week04;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * @author lhw
 * @title
 * @description
 * @created 7/15/21 7:57 PM
 * @changeRecord
 */
public class GetSunThreadValue01 {

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        FutureTask futureTask = new FutureTask(new MyCallable(1));
        Thread thread = new Thread(futureTask);
        thread.start();
        if (!futureTask.isDone()) {
            System.out.println("task has not finished!");
        }
        System.out.println(Thread.currentThread().getName() + "线程拿到子线程返回值：" + futureTask.get());
    }

}
