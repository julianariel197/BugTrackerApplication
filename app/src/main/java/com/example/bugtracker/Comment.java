package com.example.bugtracker;

public class Comment {

private int bugId;
private String userName;
private String text;
private String date;

    public Comment(int bugId, String userName, String text, String date) {
        this.bugId = bugId;
        this.userName = userName;
        this.text = text;
        this.date = date;
    }

    public int getBugId() {
        return bugId;
    }

    public void setBugId(int bugId) {
        this.bugId = bugId;
    }


    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "bugId=" + bugId +
                ", text='" + text + '\'' +
                ", date='" + date + '\'' +
                '}';
    }
}
