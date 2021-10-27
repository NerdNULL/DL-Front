package com.example.nerdnullfront.Data;
//다가오는 일정리스트의 아이템에 담을 데이터클래스
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
