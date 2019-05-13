package com.zhouyu.streamRocketmqDemo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.log4j.Log4j2;

/**
 * @Description:
 */
@Controller
@Log4j2
public class HelloController {


  @RequestMapping("/hello")
  public String sayHai(){
    log.info("yes,hai");
    return "zhouyu";
  }
}
