package iss.nus.serverjasper.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class RedisRepository {
    
    @Autowired
    RedisTemplate<String, Object> redisTemplate;

    public void insertData(String key, String data) {
        redisTemplate.opsForValue().set(key, data);
    }

}
