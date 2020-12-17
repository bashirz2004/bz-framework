package com.bzamani.framework.common.config;

import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

@Configuration
public class ResourceBundleConfiguration {

  @Bean(name = "bzFrmMessages")
  public PropertiesFactoryBean bzFrameworkMessages(){
    PropertiesFactoryBean bean = new PropertiesFactoryBean();
    bean.setLocation(new ClassPathResource("bz-framework-messages.properties"));
    bean.setFileEncoding("UTF-8");
    return bean;
  }
}
