package com.lhw.week02.httpclient;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

/**
 * @author lhw
 * @title
 * @description
 * @created 7/2/21 10:18 AM
 * @changeRecord
 */
public class HttpClientDemo {

    public static void main(String[] args) throws Exception {
        //创建httpclient对象
        CloseableHttpClient httpClient = HttpClients.createDefault();
        //创建Http get请求
        HttpGet httpGet = new HttpGet("http://localhost:8801");
        //接收返回值
        CloseableHttpResponse response = null;
        try {
            //请求执行
            response = httpClient.execute(httpGet);
            if (response.getStatusLine().getStatusCode() == 200) {
                String content = EntityUtils.toString(response.getEntity(), "utf-8");
                System.out.println(content);
            }
        } finally {
            if (response != null) {
                response.close();
            }
            httpClient.close();
        }
    }
}
