package com.storyfactory;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableRetry
@EnableAsync
@SpringBootApplication
public class StoryFactoryApplication {

    public static void main(String[] args) {
        SpringApplication.run(StoryFactoryApplication.class, args);
    }

}
