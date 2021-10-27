package com.example.nerdnullfront.Data;
//금액의 사용 내역 데이터클래스
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
