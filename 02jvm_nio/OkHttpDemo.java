package com.lhw.week02.okhttp;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

/**
 * @author lhw
 * @title
 * @description
 * @created 7/2/21 10:37 AM
 * @changeRecord
 */
public class OkHttpDemo {

    public static void main(String[] args) {
        String url = "http://localhost:8801";
        OkHttpClient okHttpClient = new OkHttpClient();
        final Request request = new Request.Builder()
                .url(url)
                .build();
        final Call call = okHttpClient.newCall(request);
        new Thread(() -> {
            try {
                Response response = call.execute();
                System.out.println(response.body().string());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();

    }

}
