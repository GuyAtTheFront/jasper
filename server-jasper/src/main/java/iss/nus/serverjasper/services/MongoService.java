package iss.nus.serverjasper.services;

import java.util.UUID;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import iss.nus.serverjasper.models.FormData;
import iss.nus.serverjasper.repositories.MongoRepository;
import iss.nus.serverjasper.utils.Utils;
import jakarta.json.JsonObject;

@Service
public class MongoService {
    
    @Autowired
    MongoRepository mongoRepo;

    public String insetData(FormData data) {

        String key = UUID.randomUUID().toString().substring(0, 8);

        JsonObject json = Utils.toJson(data);

        Document doc = Document.parse(json.toString()).append("key", key);

        mongoRepo.insertData(doc);

        return doc.toJson();
    }
}
