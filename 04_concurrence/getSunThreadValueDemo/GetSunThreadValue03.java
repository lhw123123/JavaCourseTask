package com.lhw.week04;

/**
 * @author lhw
 * @title
 * @description
 * @created 7/16/21 4:26 PM
 * @changeRecord
 */
public class GetSunThreadValue03 {

    public static void main(String[] args) throws InterruptedException {

        AgeAndName ageAndName = new AgeAndName();
        Thread thread = new Thread(new MyRunnable(ageAndName));
        thread.start();
        // 获取子线程的返回值：Thread的join方法来阻塞主线程，直到子线程返回
        thread.join();
        System.out.println(Thread.currentThread().getName() + "线程拿到子线程返回值：ageAndName" + ageAndName.toString());
    }
}
