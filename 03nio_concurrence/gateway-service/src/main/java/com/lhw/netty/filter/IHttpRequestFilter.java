package com.lhw.netty.filter;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;

/**
 * @author lhw
 * @title
 * @description
 * @created 7/8/21 6:34 PM
 * @changeRecord
 */
public interface IHttpRequestFilter {

    /**
     * 过滤器
     *
     * @param fullRequest
     * @param ctx
     */
    void filter(FullHttpRequest fullRequest, ChannelHandlerContext ctx);
}
