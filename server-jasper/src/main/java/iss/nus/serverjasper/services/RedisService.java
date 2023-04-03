package iss.nus.serverjasper.services;

import java.util.UUID;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import iss.nus.serverjasper.models.FormData;
import iss.nus.serverjasper.repositories.RedisRepository;
import iss.nus.serverjasper.utils.Utils;
import jakarta.json.JsonObject;

@Service
public class RedisService {

    @Autowired
    RedisRepository redisRepo;
    
    public String insertData(FormData data) {

        String key = UUID.randomUUID().toString().substring(0, 8);

        JsonObject json = Utils.toJson(data);

        String enhancedData = Document.parse(json.toString()).append("key", key).toJson();

        redisRepo.insertData(key, enhancedData);

        return enhancedData;

    }

}
