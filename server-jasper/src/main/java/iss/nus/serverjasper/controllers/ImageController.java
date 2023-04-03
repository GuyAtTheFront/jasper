package iss.nus.serverjasper.controllers;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.apache.http.protocol.ResponseServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.amazonaws.Response;
import com.google.gson.Gson;

import iss.nus.serverjasper.models.FormData;
import iss.nus.serverjasper.models.S3Result;
import iss.nus.serverjasper.repositories.S3Repository;
import iss.nus.serverjasper.repositories.SqlRepository;
import iss.nus.serverjasper.services.MongoService;
import iss.nus.serverjasper.services.RedisService;
import jakarta.json.Json;

@RestController
@RequestMapping(path="/api")
public class ImageController {

    @Autowired
    RedisService redisService;

    @Autowired
    MongoService mongoService;

    @Autowired
    S3Repository s3Repo;

    @Autowired
    SqlRepository sqlRepo;

    @PostMapping(path="/image", consumes=MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> uploadImage(@RequestPart MultipartFile imageFile,
                                            @RequestPart String username, 
                                            @RequestPart String comment,
                                             @RequestPart String description) throws IOException{

        FormData formData = new FormData();
        formData.setId(UUID.randomUUID().toString().substring(0, 8));
        formData.setUsername(username);
        formData.setComment(comment);
        formData.setDescription(description);
        formData.setFile(imageFile);


        // // Redis
        // String redisPayload = redisService.insertData(formData);
        // return ResponseEntity.ok().body(redisPayload);

        // // Mongo
        // String mongoPayload = mongoService.insetData(formData);
        // return ResponseEntity.ok().body(mongoPayload);

        
        // // S3
        Map<String, String> userData = new HashMap<>();
        userData.put("username", username);
        userData.put("comment", comment);
        userData.put("description", description);

        String url = s3Repo.putObject(imageFile, "myApp", userData);
        
        return ResponseEntity.ok().body("{'url': %s}".formatted(url));

        // SQL
        // sqlRepo.insertFormData(formData);

        // return ResponseEntity.ok().body(formData.getId());
    }

    @GetMapping(path="/image")
    public ResponseEntity<byte[]> getData(@RequestParam String key) {

        Optional<S3Result> opt = s3Repo.getObject(key);

        if (opt.isEmpty()){
            return ResponseEntity.notFound().build();
        }

        S3Result result = opt.get();

        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.parseMediaType(result.getContentType()))
                .contentLength(result.getContentLength())
                .header("X-name", result.getUserData().get("username"))
                .header("X-comment", result.getUserData().get("comment"))
                .header("X-description", result.getUserData().get("description"))
                .body(result.getBlob());
    }
}
