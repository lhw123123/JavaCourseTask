package com.lhw.netty.httpClient;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

/**
 * @author liuhongwei
 * @title
 * @description
 * @company 好未来-学而思思维
 * @mobile 13311560290
 * @created 7/8/21 5:48 PM
 * @changeRecord
 */
public class HttpClientUtil {

    public static String get(String url) throws IOException {

        //创建httpclient对象
        CloseableHttpClient httpClient = HttpClients.createDefault();
        //创建Http get请求
        HttpGet httpGet = new HttpGet(url);
        //接收返回值
        CloseableHttpResponse response = null;
        try {
            //请求执行
            response = httpClient.execute(httpGet);
            if (response.getStatusLine().getStatusCode() == 200) {
                String content = EntityUtils.toString(response.getEntity(), "utf-8");
                System.out.println(content);
                return content;
            }
        } finally {
            if (response != null) {
                response.close();
            }
            httpClient.close();
        }
        return null;
    }
}
