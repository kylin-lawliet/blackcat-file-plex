package com.blackcat.nas;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class AppApplication extends SpringBootServletInitializer {

     public static void main(String[] args) {
         SpringApplication.run(AppApplication.class);
         System.out.println("启动成功");
     }
 }
