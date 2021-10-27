package com.example.nerdnullfront.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.nerdnullfront.R;
//초대 수락거절 화면
public class InvitedActivity extends AppCompatActivity {
    Button button_ok = (Button) findViewById(R.id.button_ok); //수락버튼
    Button button_reject = (Button) findViewById(R.id.button_reject); //거절버튼

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invited);
        button_ok.setOnClickListener(new View.OnClickListener() { //수락시,
            @Override
            public void onClick(View v) {
                Toast.makeText(InvitedActivity.this,"수락되었습니다.",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
            }
        });
        button_reject.setOnClickListener(new View.OnClickListener() { //거절시,
            @Override
            public void onClick(View v) {
                Toast.makeText(InvitedActivity.this,"거절되었습니다.",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
