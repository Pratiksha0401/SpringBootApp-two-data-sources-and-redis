package com.employee.employeedetails.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

@Configuration
public class RedisConfiguration {

    @Value("${redis.host}")
    String redisHost;
    @Value("${redis.port}")
    int redisPort;
    @Value("${redis.password}")
    String redisPassword;
    @Bean
    JedisConnectionFactory jedisConnectionFactory() {
        JedisConnectionFactory jf = new JedisConnectionFactory();
        jf.setHostName(redisHost);
        jf.setPort(redisPort);
        jf.setPassword(redisPassword);
        return jf;
    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate() {
        RedisTemplate<String, Object> template = new RedisTemplate();
        template.setConnectionFactory(jedisConnectionFactory());
        return template;

    }
}
