package com.lhw.netty.filter;

import io.netty.handler.codec.http.FullHttpResponse;
import org.springframework.stereotype.Component;

/**
 * @author lhw
 * @title
 * @description
 * @created 7/8/21 6:52 PM
 * @changeRecord
 */
@Component
public class HttpResponseFilterImpl implements IHttpResponseFilter {
    @Override
    public void filter(FullHttpResponse response) {
        response.headers().set("age", "18");
    }
}
