package com.lhw.week04;

/**
 * @author lhw
 * @title
 * @description
 * @created 7/16/21 4:46 PM
 * @changeRecord
 */
public class GetSunThreadValue04 {

    public static void main(String[] args) throws InterruptedException {

        AgeAndName ageAndName = new AgeAndName();
        MyThread myThread = new MyThread(ageAndName);
        myThread.start();
        // 获取子线程的返回值：Thread的join方法来阻塞主线程，直到子线程返回
        myThread.join();
        System.out.println(Thread.currentThread().getName() + "线程拿到子线程返回值：ageAndName" + ageAndName.toString());
    }
}
