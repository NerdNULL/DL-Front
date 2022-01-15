package com.example.nerdnullfront.Data;

import java.io.Serializable;

//각 리스트의 일정 아이템뷰에서 사용될 데이터클래스
public class ScheduleData implements Serializable {
    String promiseName;
    String participants;
    String time;
    String date;
    String place;
    String money;
    String memo;

    public ScheduleData(String promiseName, String participants, String date,String time ,String place,String money,String memo) {
        this.promiseName = promiseName;
        this.participants = participants;
        this.date=date;
        this.time = time;
        this.place = place;
        this.money=money;
        this.memo=memo;
    }

    public String getPromise_name() {
        return promiseName;
    }

    public void setPromise_name(String promise_name) {
        this.promiseName = promise_name;
    }

    public String getParticipants() {
        return participants;
    }

    public void setParticipants(String participants) {
        this.participants = participants;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }
    public String getDate() {
        return date;
    }
    public String getMemo() {
        return memo;
    }
    public String getMoney() {
        return money;
    }
}