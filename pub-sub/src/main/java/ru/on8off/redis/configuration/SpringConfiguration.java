package ru.on8off.redis.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.on8off.redis.client.dto.Event;

import java.util.concurrent.LinkedBlockingDeque;

@Configuration
public class SpringConfiguration {

    @Bean
    public LinkedBlockingDeque<Event> eventQueue(){
        return new LinkedBlockingDeque<>();
    }
}
