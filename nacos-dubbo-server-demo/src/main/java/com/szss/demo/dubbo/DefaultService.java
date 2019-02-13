package com.szss.demo.dubbo;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.dubbo.rpc.RpcContext;

import lombok.extern.slf4j.Slf4j;

/**
 * @author 鼠笑天
 * @date 2019/2/11
 */
@Slf4j
@Service(version = "1.0.0")
public class DefaultService implements DemoService {

    public String sayName(String name) {
        log.info("invoke demoService.sayName({})", name);
        RpcContext rpcContext = RpcContext.getContext();
        return String.format("Service [name :%s , port : %d] %s(\"%s\") : Hello,%s", "demoService",
            rpcContext.getLocalPort(), rpcContext.getMethodName(), name, name);
    }
}