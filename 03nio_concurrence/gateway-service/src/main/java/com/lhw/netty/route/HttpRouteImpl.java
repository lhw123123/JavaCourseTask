package com.lhw.netty.route;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

/**
 * @author lhw
 * @title
 * @description
 * @created 7/8/21 5:18 PM
 * @changeRecord
 */
@Service
public class HttpRouteImpl implements IHttpRoute {

    @Override
    public String route(List<String> urls) {
        int size = urls.size();
        Random random = new Random(System.currentTimeMillis());
        return urls.get(random.nextInt(size));
    }
}
