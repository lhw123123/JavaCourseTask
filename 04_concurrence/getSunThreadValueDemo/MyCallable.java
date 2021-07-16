package com.lhw.week04;

import java.util.concurrent.Callable;

/**
 * @author lhw
 * @title
 * @description
 * @created 7/16/21 4:16 PM
 * @changeRecord
 */
public class MyCallable implements Callable<Integer> {

    private Integer a;

    public MyCallable(Integer a) {
        this.a = a;
    }

    @Override
    public Integer call() throws InterruptedException {
        System.out.println(Thread.currentThread().getName() + "：开始执行call方法");
        Thread.sleep(3000);
        System.out.println(Thread.currentThread().getName() + "：执行call方法结束");
        return a + 1;
    }
}
