package com.example.nerdnullfront.Activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.nerdnullfront.R;

public class MainActivity extends AppCompatActivity {
    //홈화면으로 사용
    private TextView userNameText;
    private ImageButton openSideMenuBtn;
    private CalendarView calendarView;
    private TextView preShowSchedule;
    private Button checkScheduleBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setID();
        setEvents();
    }
    public void setID(){
        userNameText=findViewById(R.id.userNameText_MainActivity);
        openSideMenuBtn=findViewById(R.id.openSideMenuButton_MainActivity);
        calendarView=findViewById(R.id.Calendar_MainActivity);
        preShowSchedule=findViewById(R.id.preShowScheduleText_MainActivity);
        checkScheduleBtn=findViewById(R.id.checkScheduleButton_MainActivity);
    }
    public void setEvents(){
        openSideMenuBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //func
            }
        });
        checkScheduleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //func
            }
        });
    }
}