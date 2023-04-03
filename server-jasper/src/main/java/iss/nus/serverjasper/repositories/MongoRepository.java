package iss.nus.serverjasper.repositories;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class MongoRepository {
    
    @Autowired
    MongoTemplate mongoTemplate;

    public void insertData(Document doc) {
        mongoTemplate.insert(doc, "data");
        return;
    }

}
