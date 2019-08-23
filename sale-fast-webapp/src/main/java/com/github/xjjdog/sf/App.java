package com.github.xjjdog.sf;


import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import redis.embedded.RedisServer;

@SpringBootApplication
@EnableAutoConfiguration
@ComponentScan(basePackageClasses = {})
//@MapperScan(basePackages = "")
@Slf4j
public class App {
    public static void main(String[] args) throws Exception {
        RedisServer redisServer = new RedisServer(6379);
        redisServer.start();

        SpringApplication.run(App.class, args);
        log.info("=================App Started=====================");

//        redisServer.stop();
    }
}
