package com.example.plogging.sqlite_database;

public class MyPloggingData {
    private String date;
    private String location;

    public MyPloggingData(String date, String location) {
        this.date = date;
        this.location = location;
    }

    public String getDate() {
        return date;
    }

    public String getLocation() {
        return location;
    }
}
