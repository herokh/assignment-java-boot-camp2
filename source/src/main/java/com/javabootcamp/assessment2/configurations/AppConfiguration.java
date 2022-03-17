package com.javabootcamp.assessment2.configurations;

import com.javabootcamp.assessment2.ApplicationContext;
import com.javabootcamp.assessment2.SeederWrapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@Configuration
public class AppConfiguration {

    @Value("${async.corePoolSize}")
    private Integer corePoolSize;

    @Value("${async.maxPoolSize}")
    private Integer maxPoolSize;

    @Value("${async.queueCapacity}")
    private Integer queueCapacity;

    @Bean
    @Scope(value = "request",  proxyMode = ScopedProxyMode.TARGET_CLASS)
    public ApplicationContext myApplicationContext() {
        return new ApplicationContext();
    }

    @Bean
    @Scope(value = "singleton", proxyMode = ScopedProxyMode.TARGET_CLASS)
    public SeederWrapper seederWrapper() {return new SeederWrapper();}

    @Bean
    public Executor asyncExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(this.corePoolSize);
        executor.setMaxPoolSize(this.maxPoolSize);
        executor.setQueueCapacity(this.queueCapacity);
        executor.setThreadNamePrefix("Async-");
        executor.initialize();
        return executor;
    }

}
