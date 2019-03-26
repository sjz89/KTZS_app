package me.daylight.ktzs.entity;

import java.util.Map;

public class UploadFile {
    private Long id;

    private Map<String,Object> uploader;

    private String fileName;

    private String UUIDName;

    private String uploadTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Map<String, Object> getUploader() {
        return uploader;
    }

    public void setUploader(Map<String, Object> uploader) {
        this.uploader = uploader;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getUUIDName() {
        return UUIDName;
    }

    public void setUUIDName(String UUIDName) {
        this.UUIDName = UUIDName;
    }

    public String getUploadTime() {
        return uploadTime;
    }

    public void setUploadTime(String uploadTime) {
        this.uploadTime = uploadTime;
    }
}
