package com.lhw;

import com.lhw.netty.NettyHttpServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class GatewayServiceApplication {

    public static void main(String[] args) throws InterruptedException {

        SpringApplication.run(GatewayServiceApplication.class, args);
        NettyHttpServer.run();
    }

}
