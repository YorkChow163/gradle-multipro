package com.zhouyu.securitydemo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @Description:
 * @Author:zhouyu
 * @Time:2019/5/13 22:13
 */
@Controller("/menu")
public class SysmenuController {

    @RequestMapping(value = "/nav", method = RequestMethod.GET)
    public void getNavMenu() {

    }
}
