package com.example.graduation;

import java.util.Date;
import java.util.HashMap;

public class question {
    public String title;
    public String content;
    public String user;
    public String date;
    public String email;
    public String key;



    public question(){

    }
    public question(String title, String content, String user, String date,String email, String key) {
        this.title = title;
        this.content = content;
        this.user = user;
        this.date = date;
        this.email= email;
        this.key= key;
    }


    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
