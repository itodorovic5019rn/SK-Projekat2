package com.raf.notificationservice.configuration;

import com.raf.notificationservice.exception.NotFoundException;
import io.github.resilience4j.retry.Retry;
import io.github.resilience4j.retry.RetryConfig;
import io.github.resilience4j.retry.RetryRegistry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Configuration
public class RetryConfiguration {

    @Bean
    public Retry userServiceRetry(){

        RetryConfig retryConfig = RetryConfig.custom()
                .ignoreExceptions(NotFoundException.class)
                .maxAttempts(10)
                .waitDuration(Duration.ofSeconds(5))
                .build();

        RetryRegistry registry = RetryRegistry.of(retryConfig);

        return registry.retry("userServiceRetry");

    }
}
