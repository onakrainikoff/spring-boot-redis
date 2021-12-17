package ru.on8off.redis.configuration;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.redisson.config.SingleServerConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.util.StringUtils;

import java.time.Duration;

@Configuration
public class RedissonConfiguration {
    private @Value("${redisson.lock.timeout}")
    Duration lockTimeout;

    @Bean
    public RedissonClient redissonClient(LettuceConnectionFactory lettuceConnectionFactory){
        Config config = new Config();
        config.setLockWatchdogTimeout(lockTimeout.toMillis());
        SingleServerConfig singleServerConfig = config.useSingleServer();
        String schema = lettuceConnectionFactory.isUseSsl() ? "rediss://" : "redis://";
        singleServerConfig.setAddress(schema + lettuceConnectionFactory.getHostName() + ":" + lettuceConnectionFactory.getPort());
        singleServerConfig.setDatabase(lettuceConnectionFactory.getDatabase());
        if (!StringUtils.isEmpty(lettuceConnectionFactory.getPassword())) {
            singleServerConfig.setPassword(lettuceConnectionFactory.getPassword());
        }
        return Redisson.create(config);
    }
}
