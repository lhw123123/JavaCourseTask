package com.lhw.netty.route;

import java.util.List;

/**
 * @author lhw
 * @title
 * @description
 * @created 7/8/21 5:16 PM
 * @changeRecord
 */
public interface HttpRoute {

    /**
     * 随机路由
     *
     * @param urls
     * @return
     */
    String route(List<String> urls);
}
