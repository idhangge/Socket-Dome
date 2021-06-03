package com.i2f.paycore.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.atomic.AtomicInteger;

@EnableAsync
@Configuration
public class PayCoreConfig {
    private static final AtomicInteger THREAD_NUM = new AtomicInteger(0);

    @Bean
    public TaskExecutor taskExecutor() {
            ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
            threadPoolTaskExecutor.setCorePoolSize(10);
            return threadPoolTaskExecutor;
    }
}
