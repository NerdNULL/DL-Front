package com.example.nerdnullfront.Data;

public class HistoryData {
    String cost;
    String date;
    public HistoryData(String cost, String date) {
        this.cost = cost;
        this.date = date;
    }

    public String getCost() {
        return cost;
    }

    public String getDate() {
        return date;
    }
}
