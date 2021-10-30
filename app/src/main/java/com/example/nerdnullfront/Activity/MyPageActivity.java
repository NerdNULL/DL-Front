package com.example.nerdnullfront.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.nerdnullfront.R;

public class MyPageActivity extends AppCompatActivity {
    private String nickName;
    private String email;
    private String profileImageURI;
    private ImageView profileImage;
    private TextView name_textView,email_textView;
    private Button withdrawUserBtn;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mypage);
        setID();
        setEvents();
        setMyPageInfo(getIntent());

        setProfileImage(profileImageURI);
        setNickName(nickName);
        setEmail(email);
    }
    public void setID(){
        name_textView=findViewById(R.id.nickname_MyPageActivity);
        email_textView=findViewById(R.id.email_MyPageActivity);
        profileImage=findViewById(R.id.profileImage_MyPageActivity);
        withdrawUserBtn=findViewById(R.id.withdrawUser);
    }
    public void setEvents(){
        withdrawUserBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //회원 탈퇴 로직
            }
        });
    }
    public void setMyPageInfo(Intent intent){
        nickName=intent.getStringExtra("name");
        profileImageURI=intent.getStringExtra("profileImage");
        email=intent.getStringExtra("email");
    }

    public void setProfileImage(String uri) {
        if(uri!=null){
            Glide.with(this).load(uri).into(profileImage);
        }
    }
    public void setNickName(String nickName){
        name_textView.setText(nickName);
    }
    public void setEmail(String email) {
        email_textView.setText(email);
    }
}
