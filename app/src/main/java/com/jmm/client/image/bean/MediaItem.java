package com.jmm.client.image.bean;


public class MediaItem {

    private String path;
    private MediaType type;


   public enum MediaType {
        image, video
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public MediaType getType() {
        return type;
    }

    public void setType(MediaType type) {
        this.type = type;
    }
}
