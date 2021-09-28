package com.example.nerdnullfront.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.nerdnullfront.R;

public class LoginActivity extends AppCompatActivity {
    private ImageView logoImage;
    private Button loginButton;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setID();
        setEvents();
    }
    public void setID(){
        logoImage=findViewById(R.id.logoImage_LoginActivity);
        loginButton=findViewById(R.id.loginButton_LoginActivity);
    }
    public void setEvents(){
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(LoginActivity.this,MainActivity.class);
                startActivity(intent); //바로이동
                finish();
            }
        });
    }
}
