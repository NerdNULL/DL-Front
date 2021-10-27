package com.example.nerdnullfront.Data;
//각 리스트의 일정 아이템뷰에서 사용될 데이터클래스
public class ScheduleData {
    String promiseName;
    String participants;
    String time;
    String place;

    public ScheduleData(String promiseName, String participants, String time, String place) {
        this.promiseName = promiseName;
        this.participants = participants;
        this.time = time;
        this.place = place;
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
}
