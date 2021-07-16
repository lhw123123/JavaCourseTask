package com.lhw.week04;

/**
 * @author lhw
 * @title
 * @description
 * @created 7/16/21 4:28 PM
 * @changeRecord
 */
public class MyRunnable implements Runnable {

    private AgeAndName ageAndName;

    public MyRunnable(AgeAndName ageAndName) {
        this.ageAndName = ageAndName;
    }

    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName() + "：开始执行run方法");
        try {
            ageAndName.setAge(1);
            ageAndName.setName("张三");
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName() + "：执行run方法结束");
    }
}
