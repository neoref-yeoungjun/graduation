package com.example.graduation;

public class Comment {
    private String email;
    private String date;
    private String content;
    private String key;
    private String mykey;
    private String myid;

    public Comment(){

    }

    public Comment(String email, String date, String content, String key, String mykey, String myid) {
        this.email = email;
        this.date = date;
        this.content = content;
        this.key = key;
        this.mykey= mykey;
        this.myid= myid;
    }

    public String getMyid() {
        return myid;
    }

    public void setMyid(String myid) {
        this.myid = myid;
    }

    public String getMykey() {
        return mykey;
    }

    public void setMykey(String mykey) {
        this.mykey = mykey;
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
