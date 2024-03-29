package ru.on8off.redis.configuration;

import io.lettuce.core.ClientOptions;
import io.lettuce.core.SocketOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.serializer.RedisSerializer;
import ru.on8off.redis.client.dto.Event;

import java.time.Duration;

@Configuration
public class RedisConfiguration {
    @Value("${redis.host}")
    private String redisHost;

    private @Value("${redis.port}")
    int redisPort;

    private @Value("${redis.timeout}")
    Duration redisCommandTimeout;

    private @Value("${redis.socket.timeout}")
    Duration socketTimeout;

    @Bean
    public ChannelTopic topic() {
        return new ChannelTopic("messageQueue");
    }

    @Bean
    public RedisTemplate<String, Event> publishRedisTemplate(){
        var template = new RedisTemplate<String, Event>();
        template.setConnectionFactory(lettuceConnectionFactory());
        template.setKeySerializer(RedisSerializer.string());
        template.setValueSerializer(RedisSerializer.json());
        return template;
    }

    @Bean
    public RedisMessageListenerContainer redisContainer() {
        var container = new RedisMessageListenerContainer();
        container.setConnectionFactory(lettuceConnectionFactory());
        return container;
    }

    @Bean
    public LettuceConnectionFactory lettuceConnectionFactory() {
        var socketOptions = SocketOptions.builder().connectTimeout(socketTimeout).build();
        var clientOptions = ClientOptions.builder().socketOptions(socketOptions).build();
        var clientConfig = LettuceClientConfiguration.builder()
                                                    .commandTimeout(redisCommandTimeout)
                                                    .clientOptions(clientOptions).build();
        var serverConfig = new RedisStandaloneConfiguration(redisHost, redisPort);
        var lettuceConnectionFactory = new LettuceConnectionFactory(serverConfig, clientConfig);
        lettuceConnectionFactory.setValidateConnection(true);
        return new LettuceConnectionFactory(serverConfig, clientConfig);

    }
}
