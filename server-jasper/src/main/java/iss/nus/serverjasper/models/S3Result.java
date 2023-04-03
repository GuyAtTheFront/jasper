package iss.nus.serverjasper.models;

import java.util.Map;

public class S3Result {

    private Long contentLength;
    private String contentType;
    private Map<String, String> userData;
    private byte[] blob;

    public Long getContentLength() {
        return contentLength;
    }
    public void setContentLength(Long contentLength) {
        this.contentLength = contentLength;
    }
    public String getContentType() {
        return contentType;
    }
    public void setContentType(String contentType) {
        this.contentType = contentType;
    }
    public Map<String, String> getUserData() {
        return userData;
    }
    public void setUserData(Map<String, String> userData) {
        this.userData = userData;
    }
    public byte[] getBlob() {
        return blob;
    }
    public void setBlob(byte[] blob) {
        this.blob = blob;
    }
}