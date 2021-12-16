package ru.on8off.redis.client;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.stereotype.Component;
import ru.on8off.redis.client.dto.Event;

import java.util.concurrent.LinkedBlockingDeque;

@Component
public class SubscribeClient extends MessageListenerAdapter {
    @Autowired
    private LinkedBlockingDeque<Event> eventQueue;

    public SubscribeClient(RedisMessageListenerContainer redisContainer, ChannelTopic topic) {
        redisContainer.addMessageListener(this, topic);
    }

    @SneakyThrows
    @Override
    public void onMessage(Message message, byte[] pattern) {
        eventQueue.put((Event)RedisSerializer.json().deserialize(message.getBody()));
    }
}
