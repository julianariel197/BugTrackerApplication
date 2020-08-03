package com.example.bugtracker;

public class Option {

    private String optionName;
    private String imgUrl;
    private int id;

    public Option(String optionName) {
        this.id=Utils.getOptionId();
        this.optionName = optionName;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOptionName() {
        return optionName;
    }

    public void setOptionName(String optionName) {
        this.optionName = optionName;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
}
