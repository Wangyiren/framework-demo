package com.szss.demo;

import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

/**
 * @author 鼠笑天
 * @date 2019/2/11
 */
@Slf4j
@EnableDubbo(scanBasePackages = "com.szss.demo.dubbo")
@SpringBootApplication
@EnableDiscoveryClient
public class ServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServerApplication.class, args);
    }
}

@Component
class SampleRunner implements ApplicationRunner {

    @Value("${user.name}")
    String userName;

    @Value("${user.age}")
    int userAge;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        System.out.println(userName);
        System.out.println(userAge);
    }
}

@RestController
@RefreshScope
class SampleController {

    @Value("${user.name}")
    String userName;

    @Value("${user.age}")
    int age;

    @RequestMapping("/user")
    public String simple() {
        return "Hello Nacos Config!" + "Hello " + userName + " " + age + "!";
    }
}