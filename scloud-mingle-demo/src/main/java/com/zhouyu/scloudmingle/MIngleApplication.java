package com.zhouyu.scloudmingle;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @Description:
 */
@SpringBootApplication
@EnableDiscoveryClient
public class MIngleApplication {
  public static void main(String[] args) {
    SpringApplication.run(MIngleApplication.class, args);
  }
}
