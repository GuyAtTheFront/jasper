package iss.nus.serverjasper.repositories;

import java.io.IOException;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;

import iss.nus.serverjasper.models.S3Result;

@Repository
public class S3Repository {

    @Value("${DO_STORAGE_BUCKET_NAME}")
    private String bucketName;

    @Value("${DO_STORAGE_ENDPOINT_REGION}")
    private String endpointRegion;

    @Autowired
    private AmazonS3 s3;
    
    public String putObject(MultipartFile file, String dirRoot, Map<String, String> userData) throws IOException {

        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType(file.getContentType());
        metadata.setContentLength(file.getSize());
        metadata.setUserMetadata(userData);

        final String key = "%s/%s".formatted(dirRoot, file.getOriginalFilename());

        PutObjectRequest putReq = new PutObjectRequest(bucketName, 
                                        key, 
                                        file.getInputStream(), 
                                        metadata);
        putReq = putReq.withCannedAcl(CannedAccessControlList.PublicRead);

        s3.putObject(putReq);

        return "https://%s.%s.digitaloceanspaces.com/%s".formatted(bucketName, endpointRegion, key);

    }

    public Optional<S3Result> getObject(String key) {

        try {
        
        GetObjectRequest getReq = new GetObjectRequest(bucketName, key);
        S3Object result = s3.getObject(getReq);
        ObjectMetadata metadata = result.getObjectMetadata();
        
        S3Result res = new S3Result();
        res.setContentLength(metadata.getContentLength());
        res.setContentType(metadata.getContentType());
        res.setUserData(metadata.getUserMetadata());
        res.setBlob(result.getObjectContent().readAllBytes());

        return Optional.of(res);

        } catch (AmazonS3Exception e) {
            // key not found
            e.printStackTrace();
            return Optional.empty();
        
        } catch (Exception e) {
            // input stream error
            e.printStackTrace();
            return Optional.empty();
        }
    }
}