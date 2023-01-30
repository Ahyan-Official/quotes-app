package com.rumibalkhi.ahyan2.models;

public class NewPhotoModel {


    int img;
    String name;


    public NewPhotoModel() {
    }

    public NewPhotoModel(int img, String name) {
        this.img = img;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }



    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }
}
