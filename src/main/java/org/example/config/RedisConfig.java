package org.example.config;

import org.example.util.RedisPool;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author 李志豪
 * @Date 2024/9/17 21:09
 */
@Configuration
public class RedisConfig {
    @Value("${spring.redis.host}")
    private String host;

    @Value("${spring.redis.port:0}")
    private Integer port;

    @Value("${spring.redis.database:0}")
    private Integer database;
    @Value("${spring.redis.password}")
    private String password;

    @Bean
    public RedisPool getRedisPool() {
        if(host.equals("disabled")) {
            return null;
        }
        RedisPool redisPool =new RedisPool();
        redisPool.initPool(host, port, database, password);
        return redisPool;
    }
}
