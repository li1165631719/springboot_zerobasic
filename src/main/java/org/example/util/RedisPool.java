package org.example.util;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * @Author 李志豪
 * @Date 2024/9/17 20:17
 */
public class RedisPool {

    private JedisPool jedisPool;

    public void initPool(String host, Integer port, Integer database, String password){
        JedisPoolConfig poolConfig = new JedisPoolConfig();
        poolConfig.setMaxTotal(200);
        poolConfig.setMaxIdle(30);
        poolConfig.setBlockWhenExhausted(true);
        poolConfig.setMaxWaitMillis(10*1000);
        poolConfig.setTestOnBorrow(true);
        jedisPool=new JedisPool(poolConfig, host, port,20*1000, password);
    }

    public Jedis getJedis(){
        return jedisPool.getResource();
    }

}
