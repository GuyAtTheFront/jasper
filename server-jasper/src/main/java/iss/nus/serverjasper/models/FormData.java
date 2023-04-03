package iss.nus.serverjasper.models;

import org.springframework.web.multipart.MultipartFile;

public class FormData {
    private String id;
    private String username;
    private String comment;
    private String description;
    private MultipartFile file;
    
    @Override
    public String toString() {
        return "FormData [id=" + id + ", username=" + username + ", comment=" + comment + ", description=" + description
                + ", file=" + file + "]";
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getComment() {
        return comment;
    }
    public void setComment(String comment) {
        this.comment = comment;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public MultipartFile getFile() {
        return file;
    }
    public void setFile(MultipartFile file) {
        this.file = file;
    }


    public String getId() {
        return id;
    }


    public void setId(String id) {
        this.id = id;
    }

    
}
