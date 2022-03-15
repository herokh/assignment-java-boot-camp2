package com.javabootcamp.assessment2.configurations;

import com.javabootcamp.assessment2.ApplicationContext;
import com.javabootcamp.assessment2.SeederWrapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;

@Configuration
public class AppConfiguration {

    @Bean
    @Scope(value = "request",  proxyMode = ScopedProxyMode.TARGET_CLASS)
    public ApplicationContext myApplicationContext() {
        return new ApplicationContext();
    }

    @Bean
    @Scope(value = "singleton", proxyMode = ScopedProxyMode.TARGET_CLASS)
    public SeederWrapper seederWrapper() {return new SeederWrapper();}

}
