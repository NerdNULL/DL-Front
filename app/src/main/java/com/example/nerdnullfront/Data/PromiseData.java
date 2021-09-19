package com.example.nerdnullfront.Data;

public class PromiseData {

    String promiseName;
    String participants;
    String time;
    String place;

    public PromiseData(String promiseName, String participants, String time, String place) {
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
