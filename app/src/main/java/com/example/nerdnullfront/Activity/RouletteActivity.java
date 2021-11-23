package com.example.nerdnullfront.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.nerdnullfront.R;

public class RouletteActivity extends AppCompatActivity {
    private TextView promiseName, moneyText, peopleCount, PeopleNames;
    private Button completeBtn;
    private RadioGroup radioGroup;
    int selectedRadioNum = 0; //라디오버튼 클릭시 해당 번호로 바뀌게 설정
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_roulette);
        setID();
        setEvents();
    }

    public void setID() {
        promiseName = findViewById(R.id.promiseName); //약속명 받아오기
        moneyText = findViewById(R.id.moneyReminder); //금액 받아오기
        peopleCount = findViewById(R.id.personCount); //약속인원수 받아오기
        PeopleNames = findViewById(R.id.peopleText); //약속 모든 인원 이름 받아오기
        completeBtn=findViewById(R.id.completeButton);
        radioGroup = findViewById(R.id.radioGroup);

    }

    public void setEvents() {

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId == R.id.radioButton1) { //(차후) 1번 옵션 선택 시
                    selectedRadioNum = 1;
                }
                else if(checkedId == R.id.radioButton2) { //(차후) 2번 옵션 선택 시
                    selectedRadioNum = 2;
                }
                else if(checkedId == R.id.radioButton3) { //(차후) 3번 옵션 선택 시
                    selectedRadioNum = 3;
                }
            }
        });

        completeBtn.setOnClickListener(new View.OnClickListener() {
            //라디오 버튼이 눌려진 뒤에야 버튼 후 정산이 가능해져야 함
            @Override
            public void onClick(View v) {
                if (selectedRadioNum == 1) { //1번 옵션 선택 시

                } else if (selectedRadioNum == 2) { //2번 옵션 선택 시

                } else if (selectedRadioNum == 3) { //3번 옵션 선택 시

                } else { //옵션 선택 안할 시 토스트 메시지 띄우기
                    Toast.makeText(RouletteActivity.this, "먼저 옵션을 선택해주세요.", Toast.LENGTH_SHORT).show();
                }
                Intent intent=new Intent(RouletteActivity.this,DividedActivity.class);
                startActivity(intent);
                finish();
            }
        });


    }



}
