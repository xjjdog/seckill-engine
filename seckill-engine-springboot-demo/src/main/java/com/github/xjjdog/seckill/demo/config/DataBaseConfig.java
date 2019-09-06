package com.github.xjjdog.seckill.demo.config;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@MapperScan(basePackages = "com.github.xjjdog.**.mapper")
public class DataBaseConfig {



    @Bean
    public PaginationInterceptor paginationInterceptor() { return new PaginationInterceptor(); }



}
