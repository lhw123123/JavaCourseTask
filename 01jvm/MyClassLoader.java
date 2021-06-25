package com.lhw.week01;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;

/**
 * @author lhw
 * @title 自定义类加载器
 * @description
 * @created 6/24/21 5:35 PM
 * @changeRecord
 */
public class MyClassLoader extends ClassLoader {

    /**
     * 覆盖了父类的findClass，实现自定义的classloader
     *
     * @param name
     * @return
     */
    @Override
    public Class<?> findClass(String name) {

        byte[] bt = this.loadClassData(name);
        for (int i = 0; i < bt.length; i++) {

            bt[i] = (byte) (~bt[i]);
            //bt[i] = (byte) (255 - bt[i]);
        }
        return defineClass("Hello", bt, 0, bt.length);
    }

    private byte[] loadClassData(String className) {
        InputStream is = getClass().getClassLoader().getResourceAsStream(className);
        ByteArrayOutputStream byteSt = new ByteArrayOutputStream();
        int len;
        try {
            while ((len = is.read()) != -1) {
                byteSt.write(len);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return byteSt.toByteArray();
    }

    public static void main(String[] args) {
        MyClassLoader loader = new MyClassLoader();
        Class<?> c;
        try {
            c = loader.loadClass("Hello.xlass");
            Object o = c.newInstance();
            Method helloM = c.getDeclaredMethod("hello");
            helloM.invoke(o);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
