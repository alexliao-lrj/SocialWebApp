package com.socialweb.dev.entity;

public class Dashboard {
    //total posts
    private Integer total;
    //posts today
    private Integer day;
    //posts this month
    private Integer month;
    //posts this year
    private Integer year;

    public Dashboard(int total, int day, int month, int year){
        this.total = total;
        this.day = day;
        this.month = month;
        this.year = year;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Integer getDay() {
        return day;
    }

    public void setDay(Integer day) {
        this.day = day;
    }

    public Integer getMonth() {
        return month;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }
}
