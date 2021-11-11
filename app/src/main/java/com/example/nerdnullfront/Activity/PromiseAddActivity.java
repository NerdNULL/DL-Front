package com.example.nerdnullfront.Activity;

import android.app.Activity;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
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

import java.util.Arrays;
import java.util.HashMap;

import kotlin.Unit;
import kotlin.jvm.functions.Function2;

public class PromiseAddActivity extends AppCompatActivity {
    private Button addPlaceBtn,addScheduleBtn;
    private ActivityResultLauncher<Intent> activityStarter;
    private EditText myDetailPlace,myDetailSubject,myDetailDate,myDetailTime,myDetailMoney,myMemo;
    private String nickName=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_promise_add);
        setID();
        setEvents();
        nickName=getIntent().getStringExtra("nickName");

    }
    public void setID(){
        addPlaceBtn=findViewById(R.id.app_map_button);
        addScheduleBtn=findViewById(R.id.check_button);
        myDetailPlace=findViewById(R.id.myDetailPlace);
        myDetailSubject=findViewById(R.id.myDetailSubject);
        myDetailDate=findViewById(R.id.myDetailDate);
        myDetailTime=findViewById(R.id.editTextTime);
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
                    Intent intent = new Intent(PromiseAddActivity.this, PlacePickUpActivity.class);
                    activityStarter.launch(intent);
                }
                else
                    Toast.makeText(PromiseAddActivity.this, "GPS를 켜주세요.", Toast.LENGTH_SHORT).show();
            }
        });
        addScheduleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //스케줄 추가한 후, 인원을 정하는게 좋을듯 함. -> 약속 고유 번호로 다시 인원을 update해야 하기때문.
                sendLink();
            }
        });
    }
    public void sendLink(){
        FeedTemplate feedTemplate = new FeedTemplate(new Content(
                nickName+" 님이 귀하를 약속에 초대하였습니다!",
                "http://mud-kage.kakao.co.kr/dn/Q2iNx/btqgeRgV54P/VLdBs9cvyn8BJXB3o7N8UK/kakaolink40_original.png",
                new Link(null, null)), null, null,
                Arrays.asList(
                        new com.kakao.sdk.template.model.Button("초대 확인하기",new Link(
                                R.string.kakao_scheme+"://"+R.string.kakaolink_host,R.string.kakao_scheme+"://"+R.string.kakaolink_host,
                                new HashMap<String,String>(){{
                                    put("scheduleMaker",nickName); //약속을 만드는 사람의 닉네임
                                    put("scheduleNumber",String.valueOf(3)); //스케줄의 고유번호를 전달
                                }}
                        )))
                );
        LinkClient.getInstance().defaultTemplate(this, feedTemplate, new Function2<LinkResult, Throwable, Unit>() {
            @Override
            public Unit invoke(LinkResult linkResult, Throwable throwable) {
                startActivity(linkResult.getIntent());
                Log.w("MSGAPIERROR", "Warning Msg: "+linkResult.getWarningMsg());
                Log.w("MSGAPIERROR", "Argument Msg: "+linkResult.getArgumentMsg());
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