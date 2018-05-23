package com.example.dsm2016.mytravelhelper;

/**
 * Created by dsm2016 on 2018-05-06.
 */

public class ShareFreightData {
    private String freigt;
    private String username;

    public ShareFreightData(){}

    public ShareFreightData(String username, String freight){
        this.username = username;
        this.freigt = freight;
    }

    public String getUsername(){
        return username;
    }
    public String getFreigt(){
        return freigt;
    }
    public void setUsername(String username){
        this.username = username;
    }
    public void setFreigt(String freigt){
        this.freigt = freigt;
    }


}
