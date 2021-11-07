package com.example.nerdnullfront.Activity;

import android.app.Activity;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.example.nerdnullfront.R;
import com.kakao.sdk.link.LinkClient;
import com.kakao.sdk.link.model.LinkResult;
import com.kakao.sdk.template.model.Content;
import com.kakao.sdk.template.model.FeedTemplate;
import com.kakao.sdk.template.model.Link;

import kotlin.Unit;
import kotlin.jvm.functions.Function2;

public class PromiseDetailActivity extends AppCompatActivity {
    private Button addPlaceBtn,saveBtn,deleteBtn;
    private ActivityResultLauncher<Intent> activityStarter;
    private EditText myDetailPlace,myDetailSubject,myDetailDate,myDetailTime,
            myDetailParticipants,myDetailMoney,myMemo;
    private Button addParticipants;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_promise_detail);
        setID();
        setEvents();
    }
    public void setID(){
        addParticipants=findViewById(R.id.button3);
        addPlaceBtn=findViewById(R.id.app_map_button);
        saveBtn=findViewById(R.id.checked_button);
        deleteBtn=findViewById(R.id.delete_button);
        myDetailPlace=findViewById(R.id.myDetailPlace);
        myDetailSubject=findViewById(R.id.myDetailSubject);
        myDetailDate=findViewById(R.id.myDetailDate);
        myDetailTime=findViewById(R.id.editTextTime);
        myDetailParticipants=findViewById(R.id.myDetailParticipants);
        myDetailMoney=findViewById(R.id.myDetailMoney);
        myMemo=findViewById(R.id.myMemo);
        activityStarter=registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if(result.getResultCode()== Activity.RESULT_OK){
                            //장소 반환
                            Intent intent=result.getData();
                            myDetailPlace.setText(intent.getStringExtra("pickedPlaceName"));
                        }
                    }
                });
    }
    public void setEvents(){
        addPlaceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkLocationServicesStatus()) {
                    Intent intent = new Intent(PromiseDetailActivity.this, PlacePickUpActivity.class);
                    activityStarter.launch(intent);
                }
                else
                    Toast.makeText(PromiseDetailActivity.this, "GPS를 켜주세요.", Toast.LENGTH_SHORT).show();
            }
        });
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //스케줄 업데이트 - 백엔드와 테스트 필요
            }
        });
        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //스케줄 삭제 - 백엔드와 테스트 필요
            }
        });
        addParticipants.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendLink();
            }
        });
    }
    public void sendLink(){
        FeedTemplate feedTemplate = new FeedTemplate(new Content(
                "초대되었습니다!",
                "http://mud-kage.kakao.co.kr/dn/Q2iNx/btqgeRgV54P/VLdBs9cvyn8BJXB3o7N8UK/kakaolink40_original.png",
                new Link("https://www.naver.com")));
        LinkClient.getInstance().defaultTemplate(this, feedTemplate, new Function2<LinkResult, Throwable, Unit>() {
            @Override
            public Unit invoke(LinkResult linkResult, Throwable throwable) {
                startActivity(linkResult.getIntent());
                return null;
            }
        });
    }
    public boolean checkLocationServicesStatus() {
        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
                || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }
}