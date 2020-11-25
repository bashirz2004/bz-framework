package com.bzamani.framework;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@org.springframework.boot.autoconfigure.SpringBootApplication
public class SpringBootApplication extends SpringBootServletInitializer {

  public static void main(String[] args) {
    SpringApplication.run(SpringBootApplication.class, args);
  }

}
