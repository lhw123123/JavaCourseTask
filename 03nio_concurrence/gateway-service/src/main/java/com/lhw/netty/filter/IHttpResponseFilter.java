package com.lhw.netty.filter;

import io.netty.handler.codec.http.FullHttpResponse;

/**
 * @author lhw
 * @title
 * @description
 * @created 7/8/21 6:51 PM
 * @changeRecord
 */
public interface IHttpResponseFilter {

    /**
     * 过滤器
     *
     * @param response
     */
    void filter(FullHttpResponse response);
}
