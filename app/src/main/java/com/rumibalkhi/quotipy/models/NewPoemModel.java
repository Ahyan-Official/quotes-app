package com.rumibalkhi.quotipy.models;

public class NewPoemModel {

    String text, title;


    public NewPoemModel() {
    }

    public NewPoemModel(String text, String title) {
        this.text = text;
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
