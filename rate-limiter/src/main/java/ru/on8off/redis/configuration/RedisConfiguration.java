package ru.on8off.redis.configuration;

import io.lettuce.core.ClientOptions;
import io.lettuce.core.SocketOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;

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
