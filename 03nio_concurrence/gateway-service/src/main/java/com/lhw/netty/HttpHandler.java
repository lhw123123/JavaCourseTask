package com.lhw.netty;

import com.lhw.netty.filter.IHttpRequestFilter;
import com.lhw.netty.filter.IHttpResponseFilter;
import com.lhw.netty.httpClient.HttpClientUtil;
import com.lhw.netty.route.IHttpRoute;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpUtil;
import io.netty.util.ReferenceCountUtil;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

import static io.netty.handler.codec.http.HttpHeaderNames.CONNECTION;
import static io.netty.handler.codec.http.HttpHeaderValues.KEEP_ALIVE;
import static io.netty.handler.codec.http.HttpResponseStatus.NO_CONTENT;
import static io.netty.handler.codec.http.HttpResponseStatus.OK;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;

/**
 * @author lhw
 * @title
 * @description
 * @created 7/8/21 4:14 PM
 * @changeRecord
 */
@Component
public class HttpHandler extends ChannelInboundHandlerAdapter {

    private static List<String> proxyServers = Arrays.asList("https://www.baidu.com", "https://www.json.cn");

    @Resource
    private IHttpRoute httpRoute;

    @Resource
    private IHttpRequestFilter requestFilter;

    @Resource
    private IHttpResponseFilter responseFilter;

    public static IHttpRoute httpRouteInit;

    public static IHttpRequestFilter requestFilterInit;

    public static IHttpResponseFilter responseFilterInit;

    @PostConstruct
    public void init() {
        httpRouteInit = httpRoute;
        requestFilterInit = requestFilter;
        responseFilterInit = responseFilter;
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        ctx.flush();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        try {
            FullHttpRequest fullRequest = (FullHttpRequest) msg;
            String uri = fullRequest.uri();
            System.out.println("接收到的请求url:" + uri);
            this.handler(fullRequest, ctx);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ReferenceCountUtil.release(msg);
        }
    }

    private void handler(FullHttpRequest fullRequest, ChannelHandlerContext ctx) {

        System.out.println("proxyServers:" + proxyServers);
        String url = httpRouteInit.route(proxyServers);
        System.out.println("url:" + url);
        requestFilterInit.filter(fullRequest, ctx);
        System.out.println("header requestFilter name:" + fullRequest.headers().get("name"));
        FullHttpResponse response = null;
        try {
            String value = HttpClientUtil.get(url);
            response = new DefaultFullHttpResponse(HTTP_1_1, OK, Unpooled.wrappedBuffer(value.getBytes("UTF-8")));
            response.headers().set("Content-Type", "application/json");
            response.headers().setInt("Content-Length", response.content().readableBytes());
            responseFilterInit.filter(response);
            System.out.println("header responseFilter age:" + response.headers().get("age"));
        } catch (Exception e) {
            System.out.println("处理出错:" + e.getMessage());
            response = new DefaultFullHttpResponse(HTTP_1_1, NO_CONTENT);
        } finally {
            if (fullRequest != null) {
                if (!HttpUtil.isKeepAlive(fullRequest)) {
                    ctx.write(response).addListener(ChannelFutureListener.CLOSE);
                } else {
                    response.headers().set(CONNECTION, KEEP_ALIVE);
                    ctx.write(response);
                }
                ctx.flush();
            }
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }

}
