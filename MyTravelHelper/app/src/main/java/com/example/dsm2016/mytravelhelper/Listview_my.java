package com.example.dsm2016.mytravelhelper;

import java.util.List;

/**
 * Created by dsm2016 on 2018-05-21.
 */

public class Listview_my {
    private String text;
    private String isCheckd;

    public Listview_my(){}
    public Listview_my(String text, String isCheckd){
        this.text = text;
        this.isCheckd = isCheckd;
    }

    public void setText(String text){
        this.text = text;
    }

    public void setIsCheckd(String isCheckd){
        this.isCheckd = isCheckd;
    }

    public String getText(){
        return text;
    }

    public String getIsCheckd(){
        return isCheckd;
    }

}
