package com.example.demo.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * com.example.demo.config
 *
 * @author ypl
 * @create 2020-11-25 13:11
 */

/**
 * 在启动类启动 SpringApplication.run(DemoApplication.class, args);
 * 后执行
 * 可以进行初始化操作，比如存放redis数据等等
 */

@Component
public class InitialExec implements CommandLineRunner {
    @Override
    public void run(String... args) throws Exception {

        System.out.println("--------业务逻辑-------");
    }
}

/**
 或者使用

 @PostConstruct 注解在方法上
 */