package com.zhouyu.scloudmingle.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

/**
 * @Description:
 */
@Controller
public class TestController {

  @Autowired
  LoadBalancerClient loadBalancerClient;

  @RequestMapping("/test")
  public String test() {
    // 通过spring cloud common中的负载均衡接口选取服务提供节点实现接口调用
    ServiceInstance serviceInstance = loadBalancerClient.choose("alibaba-nacos-discovery-server");
    String url = serviceInstance.getUri() + "/hello";
    RestTemplate restTemplate = new RestTemplate();
    String result = restTemplate.getForObject(url, String.class);
    return "Invoke : " + url + ", return : " + result;
  }

}
