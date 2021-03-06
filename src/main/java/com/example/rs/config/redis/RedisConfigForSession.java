package com.example.rs.config.redis;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.RedisConfiguration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettucePoolingClientConfiguration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import java.time.Duration;

@Configuration
public class RedisConfigForSession {
    @Value("${spring.redis.session.host}")
    private String host;

    @Value("${spring.redis.session.port}")
    private int port;

    @Value("${spring.redis.session.password}")
    private String password;

    @Value("${spring.redis.session.maxTotal}")
    private Integer maxTotal;

    @Value("${spring.redis.session.maxIdle}")
    private Integer maxIdle;

    @Value("0")
    private Integer minIdle;

    @Value("${spring.redis.session.maxWaitMillis}")
    private Integer maxWaitMillis;

    @Value("${spring.redis.session.timeout}")
    private int timeout;

    @Value("${spring.redis.session.database}")
    private int database;

    @Value("${spring.redis.session.shutdown}")
    private int shutdownTimeOut;


    @Bean(name = "redisConnectionFactoryForSession")
    public RedisConnectionFactory redisConnectionFactoryForSession() {
        RedisConfiguration redisConfiguration = new
                RedisStandaloneConfiguration(host,port);
        ((RedisStandaloneConfiguration) redisConfiguration).setDatabase(database);
        ((RedisStandaloneConfiguration) redisConfiguration).setPassword(password);

        GenericObjectPoolConfig genericObjectPoolConfig =
                new GenericObjectPoolConfig();
        genericObjectPoolConfig.setMaxIdle(maxIdle);
        genericObjectPoolConfig.setMinIdle(minIdle);
        genericObjectPoolConfig.setMaxTotal(maxTotal);
        genericObjectPoolConfig.setMaxWaitMillis(maxWaitMillis);

        LettucePoolingClientConfiguration.LettucePoolingClientConfigurationBuilder
                builder =  LettucePoolingClientConfiguration.builder().
                commandTimeout(Duration.ofMillis(timeout));
        builder.shutdownTimeout(Duration.ofMillis(shutdownTimeOut));
        builder.poolConfig(genericObjectPoolConfig);
        LettuceClientConfiguration lettuceClientConfiguration = builder.build();

        //??????????????????????????????????????????
        LettuceConnectionFactory lettuceConnectionFactory = new
                LettuceConnectionFactory(redisConfiguration,lettuceClientConfiguration);
        lettuceConnectionFactory .afterPropertiesSet();
        return lettuceConnectionFactory;
    }

    @Bean(name = "redisTemplateForSession")
    public RedisTemplate<String, Object> redisTemplate(
            @Autowired
            @Qualifier("redisConnectionFactoryForSession")
                    RedisConnectionFactory factory) {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(factory);
        GenericJackson2JsonRedisSerializer ser = new GenericJackson2JsonRedisSerializer();
        redisTemplate.setDefaultSerializer(ser);
        return redisTemplate;
    }
    @Bean(name = "stringRedisTemplateForSession")
    public StringRedisTemplate stringRedisTemplate(
            @Autowired
            @Qualifier("redisConnectionFactoryForSession")
                    RedisConnectionFactory factory) {
        StringRedisTemplate template = new StringRedisTemplate(factory);
        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
        ObjectMapper om = new ObjectMapper();
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        jackson2JsonRedisSerializer.setObjectMapper(om);
        template.setValueSerializer(jackson2JsonRedisSerializer);
        template.afterPropertiesSet();
        return template;
    }
}
