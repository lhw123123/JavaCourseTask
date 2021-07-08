package com.lhw.netty.route;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

/**
 * @author liuhongwei
 * @title
 * @description
 * @company 好未来-学而思思维
 * @mobile 13311560290
 * @created 7/8/21 5:18 PM
 * @changeRecord
 */
@Service
public class HttpRouteImpl implements HttpRoute {

    @Override
    public String route(List<String> urls) {
        int size = urls.size();
        Random random = new Random(System.currentTimeMillis());
        return urls.get(random.nextInt(size));
    }
}
