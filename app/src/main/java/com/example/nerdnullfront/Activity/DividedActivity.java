package com.example.nerdnullfront.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import com.example.nerdnullfront.Fragment.CalendarFragment;
import com.example.nerdnullfront.R;

public class DividedActivity extends AppCompatActivity {
    private TextView promiseName, moneyText, peopleCount, PeopleNames;
    private Button toHomeBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_divided);
        setID();
        setEvents();
    }

    public void setID() {
        promiseName = findViewById(R.id.promiseName2); // (차후) 약속명 받아오기
        moneyText = findViewById(R.id.diviedMoney); // (차후) 금액 받아오기
        peopleCount = findViewById(R.id.diviedPersonCount); // (차후) 약속인원수 받아오기
        PeopleNames = findViewById(R.id.diviedPeopleText); // (차후) 약속 모든 인원 이름 받아오기
        toHomeBtn = findViewById(R.id.toHomeButton);
    }

    public void setEvents() {

        toHomeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { //홈으로 이동 버튼 누르면 메인화면으로 이동

            }
        });

    }



}
