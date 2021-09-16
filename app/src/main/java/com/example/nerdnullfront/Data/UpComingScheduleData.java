package com.example.nerdnullfront.Data;

public class UpComingScheduleData {
    private String title;
    private String date;
    public UpComingScheduleData(String t, String d){
        title=t;
        date=d;
    }
    public String getTitle(){return title;}
    public String getDate(){return date;}
}
