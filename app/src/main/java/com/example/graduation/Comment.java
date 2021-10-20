package com.example.graduation;

public class Comment {
    private String email;
    private String date;
    private String content;
    private String key;

    public Comment(){

    }

    public Comment(String email, String date, String content, String key) {
        this.email = email;
        this.date = date;
        this.content = content;
        this.key = key;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
