package com.szss.demo;

import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;
import com.szss.demo.dubbo.DemoService;

import java.util.Collections;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;

import lombok.extern.slf4j.Slf4j;

/**
 * @author 鼠笑天
 * @date 2019/2/11
 */
@Slf4j
@EnableDubbo(scanBasePackages = "com.szss.demo.dubbo")
@SpringBootApplication
@EnableDiscoveryClient
public class ClientApplication {

    public static void main(String[] args) {
        //sentinel
        FlowRule flowRule = new FlowRule();
        flowRule.setResource(
            "com.szss.demo.dubbo.DemoService:sayName(java.lang.String)");
        flowRule.setCount(2);
        flowRule.setGrade(RuleConstant.FLOW_GRADE_QPS);
        flowRule.setLimitApp("default");
        FlowRuleManager.loadRules(Collections.singletonList(flowRule));

        //run app
        SpringApplication.run(ClientApplication.class, args);
    }
}

@RestController
@RefreshScope
class SampleController {

    @Reference(version = "1.0.0")
    private DemoService demoService;

    @RequestMapping("/demo")
    public String simple() {
        return demoService.sayName("dubbo");
    }
}