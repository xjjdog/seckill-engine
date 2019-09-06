package com.github.xjjdog.seckill.demo;


import com.spring4all.swagger.EnableSwagger2Doc;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@EnableSwagger2Doc
@SpringBootApplication
public class App {

    public static void main(String[] args) throws Exception {
//        RedisServer redisServer = new RedisServer(6379);
//        redisServer.start();

        SpringApplication.run(App.class, args);
        log.info("=================App Started=====================");
//        redisServer.stop();
    }
}
