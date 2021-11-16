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
import com.example.nerdnullfront.ServerInterface.RemoveScheduleServerRequestAPI;
import com.example.nerdnullfront.ServerInterface.UpdateScheduleServerRequestAPI;
import com.example.nerdnullfront.ServerResponseDataSet.RemoveScheduleResponseData;
import com.example.nerdnullfront.ServerResponseDataSet.UpdateScheduleResponseData;
import com.kakao.sdk.link.LinkClient;
import com.kakao.sdk.link.model.LinkResult;
import com.kakao.sdk.template.model.Content;
import com.kakao.sdk.template.model.FeedTemplate;
import com.kakao.sdk.template.model.Link;

import java.util.Arrays;
import java.util.HashMap;

import kotlin.Unit;
import kotlin.jvm.functions.Function2;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PromiseDetailActivity extends AppCompatActivity {
    private Button addPlaceBtn,saveBtn,deleteBtn;
    private ActivityResultLauncher<Intent> activityStarter;
    private EditText myDetailPlace,myDetailSubject,myDetailDate,myDetailTime,
            myDetailParticipants,myDetailMoney,myMemo;
    private Button addParticipants;
    private String nickName=null;
    private Retrofit retrofit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_promise_detail);
        setID();
        setEvents();
        nickName=getIntent().getStringExtra("nickName");
    }
    public void setID(){
        addParticipants=findViewById(R.id.button_add);
        addPlaceBtn=findViewById(R.id.app_map_button);
        saveBtn=findViewById(R.id.edit_button);
        deleteBtn=findViewById(R.id.delete_button);
        myDetailPlace=findViewById(R.id.myDetailPlace);
        myDetailSubject=findViewById(R.id.myDetailSubject);
        myDetailDate=findViewById(R.id.myDetailDate);
        myDetailTime=findViewById(R.id.editTextTime);
        myDetailParticipants=findViewById(R.id.myDetailParticipants);
        myDetailMoney=findViewById(R.id.myDetailMoney);
        myMemo=findViewById(R.id.myMemo);
        retrofit = new Retrofit.Builder() // Retrofit 구성
                .baseUrl("http://jsonplaceholder.typicode.com") //요청 URL
                .addConverterFactory(GsonConverterFactory.create())
                .build();
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
                requestUpdateScheduleResponse();
            }
        });
        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //스케줄 삭제 - 백엔드와 테스트 필요
                requestRemoveScheduleResponse();
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
                nickName+" 님이 귀하를 약속에 초대하였습니다!",
                "http://mud-kage.kakao.co.kr/dn/Q2iNx/btqgeRgV54P/VLdBs9cvyn8BJXB3o7N8UK/kakaolink40_original.png",
                new Link(null, null)), null, null,
                Arrays.asList(
                        new com.kakao.sdk.template.model.Button("초대 확인하기",new Link(
                                R.string.kakao_scheme+"://"+R.string.kakaolink_host,R.string.kakao_scheme+"://"+R.string.kakaolink_host,
                                new HashMap<String,String>(){{
                                    put("scheduleMaker",nickName); //약속을 만드는 사람의 닉네임
                                    put("scheduleNumber",String.valueOf(3));// 스케줄 고유번호
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
    public void requestUpdateScheduleResponse(){
        UpdateScheduleServerRequestAPI api = retrofit.create(UpdateScheduleServerRequestAPI.class);   // 통신 인터페이스를 객체로 생성
        Call<UpdateScheduleResponseData> call = api.getSearchKeyword("", "");  // 검색 조건 입력
        // API 서버에 요청
        call.enqueue(new Callback<UpdateScheduleResponseData>() {
            @Override
            public void onResponse(Call<UpdateScheduleResponseData> call, Response<UpdateScheduleResponseData> response) {
                //reponse.body().객체
            }
            @Override
            public void onFailure(Call<UpdateScheduleResponseData> call, Throwable t) {

            }
        });
    }
    public void requestRemoveScheduleResponse(){
        RemoveScheduleServerRequestAPI api = retrofit.create(RemoveScheduleServerRequestAPI.class);   // 통신 인터페이스를 객체로 생성
        Call<RemoveScheduleResponseData> call = api.getSearchKeyword("", "");  // 검색 조건 입력
        // API 서버에 요청
        call.enqueue(new Callback<RemoveScheduleResponseData>() {
            @Override
            public void onResponse(Call<RemoveScheduleResponseData> call, Response<RemoveScheduleResponseData> response) {
                //reponse.body().객체
            }
            @Override
            public void onFailure(Call<RemoveScheduleResponseData> call, Throwable t) {

            }
        });
    }
}