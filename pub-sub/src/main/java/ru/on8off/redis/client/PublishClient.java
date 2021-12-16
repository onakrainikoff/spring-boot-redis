package ru.on8off.redis.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Component;
import ru.on8off.redis.client.dto.Event;

@Component
public class PublishClient {
    @Autowired
    private RedisTemplate<String, Event> publishRedisTemplate;
    @Autowired
    private ChannelTopic topic;

    public void publish(Event event){
        publishRedisTemplate.convertAndSend(topic.getTopic(), event);
    }
}
