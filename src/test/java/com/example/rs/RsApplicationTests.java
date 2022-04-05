package com.example.rs;
import com.example.rs.service.QQSenderMailService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;


@SpringBootTest
class RsApplicationTests {
    @Autowired
    RedisTemplate<String, Object> redisTemplate;
    @Autowired
    QQSenderMailService qqSenderMailService;

    @Test
    void contextLoads() {
        qqSenderMailService.sendTextMail("tersedream@hotmail.com", "123", "321");
        redisTemplate.opsForValue().set("s", 1);
        redisTemplate.opsForValue().increment("s");
        System.out.println(redisTemplate.opsForValue().get("s"));


    }

}
