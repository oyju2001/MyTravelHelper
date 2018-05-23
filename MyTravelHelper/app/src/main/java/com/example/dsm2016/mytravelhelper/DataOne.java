package com.example.dsm2016.mytravelhelper;

/**
 * Created by dsm2016 on 2018-05-22.
 */

public class DataOne {
    private String name;
    private String place;
    private String startDate;
    private String endDate;
    private String code = null;

    public DataOne(){}

    public DataOne(String name, String place, String startDate, String endDate){
        this.name = name;
        this.place = place;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public String getPlace() {
        return place;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getEndDate() {
        return endDate;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
}
