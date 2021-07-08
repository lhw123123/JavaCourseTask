package com.lhw.netty.filter;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;
import org.springframework.stereotype.Component;

/**
 * @author lhw
 * @title
 * @description
 * @created 7/8/21 6:35 PM
 * @changeRecord
 */
@Component
public class HttpRequestFilterImpl implements IHttpRequestFilter {

    @Override
    public void filter(FullHttpRequest fullRequest, ChannelHandlerContext ctx) {
        fullRequest.headers().set("name", "lhw");
    }
}
