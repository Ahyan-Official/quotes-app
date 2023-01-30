package com.rumibalkhi.ahyan2;

public class Note2 {

    private long id;
    private String name;
    byte[] image;
    private String isimage;

    Note2(){
        // empty constructor
    }

    public Note2(long id, String name, byte[] image, String isimage) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.isimage = isimage;
    }

    public Note2(String name, byte[] image, String isimage) {
        this.name = name;
        this.image = image;
        this.isimage = isimage;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getIsimage() {
        return isimage;
    }

    public void setIsimage(String isimage) {
        this.isimage = isimage;
    }
}
