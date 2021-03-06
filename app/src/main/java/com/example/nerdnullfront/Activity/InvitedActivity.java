package com.example.nerdnullfront.Activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.nerdnullfront.R;
//초대 수락거절 화면
public class InvitedActivity extends AppCompatActivity {
    private Button button_ok;
    private Button button_reject;
    private String inviter,sNumber;
    private TextView inviterName;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invited);
        inviter=getIntent().getStringExtra("scheduleMaker");
        sNumber=getIntent().getStringExtra("scheduleNumber"); //스케줄의 고유번호
        setID();
        setEvents();

        setInviter(inviter);
        Toast.makeText(this, sNumber,Toast.LENGTH_SHORT).show();
        getInvitedScheduleData(sNumber); //서버로부터 데이터 받아옴
    }
    public void setID(){
        button_ok = findViewById(R.id.check_button); //수락버튼
        button_reject = findViewById(R.id.delete_button); //거절버튼
        inviterName=findViewById(R.id.InviterName);
    }
    public void setEvents(){
        button_ok.setOnClickListener(new View.OnClickListener() { //수락시,
            @Override
            public void onClick(View v) {
                Toast.makeText(InvitedActivity.this,"수락되었습니다.",Toast.LENGTH_SHORT).show();
                //update Participants
                finish();
            }
        });
        button_reject.setOnClickListener(new View.OnClickListener() { //거절시,
            @Override
            public void onClick(View v) {
                Toast.makeText(InvitedActivity.this,"거절되었습니다.",Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }
    public void setInviter(String inviter){
        this.inviterName.setText(inviter);
    }
    public void getInvitedScheduleData(String sNumber){
        //고유번호로 데이터 닫아와서 뷰에 뿌릴것임.

    }
}
