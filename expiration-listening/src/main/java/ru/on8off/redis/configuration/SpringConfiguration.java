package ru.on8off.redis.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.LinkedBlockingDeque;

@Configuration
public class SpringConfiguration {

    @Bean
    public LinkedBlockingDeque<String> eventQueue(){
        return new LinkedBlockingDeque<>();
    }
}
