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

import com.example.nerdnullfront.Adapter.ScheduleAdapter;
import com.example.nerdnullfront.Data.ScheduleData;
import com.example.nerdnullfront.R;
import com.example.nerdnullfront.ServerInterface.CreateScheduleServerRequestAPI;
import com.example.nerdnullfront.ServerResponseDataSet.CreateScheduleResponseData;
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

public class PromiseAddActivity extends AppCompatActivity implements ScheduleAdapter.IScheduleClickable{
    private Button addPlaceBtn,addScheduleBtn,payBtn;
    private ActivityResultLauncher<Intent> activityStarter;
    private EditText myDetailPlace,myDetailSubject,myDetailDate,myDetailTime,myDetailMoney,myMemo;
    private String nickName=null;
    private String sNumber="-1";
    private Retrofit retrofit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_promise_add);
        setID();
        setEvents();
        nickName=getIntent().getStringExtra("nickName");
        retrofit = new Retrofit.Builder() // Retrofit 구성
                .baseUrl("http://3.35.234.34/") //요청 URL(IP)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
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

        payBtn = findViewById(R.id.pay_button);

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
        payBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // EditText에 입력한 상품 정보를 가져온다.
                //String name = myDetailMgitoney.getText().toString();
                String price = myDetailMoney.getText().toString();
                if(price.equals("")){
                    Toast.makeText(PromiseAddActivity.this,"먼저 금액을 입력해주세요.",Toast.LENGTH_SHORT).show();
                    return;
                }

                // 결제가 이루어지는 PayActivity를 생성한다.
                // - 생성자를 이용하여 상품 정보를 입력한다.
                PayActivity payActivity = new PayActivity(price);

                // Intent로 새로운 Activity를 실행한다.
                Intent intent = new Intent(PromiseAddActivity.this, payActivity.getClass());
                startActivity(intent);
            }
        });

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
                //sendLink();
                //requestCreateScheduleResponse(); //약속 생성요청
                //스케줄 추가한 후, 인원을 정하는게 좋을듯 함. -> 약속 고유 번호로 다시 인원을 update해야 하기때문.
                ScheduleData data=new ScheduleData(myDetailSubject.getText().toString(),
                        "이선민,황명하",
                        myDetailDate.getText().toString(),
                        myDetailTime.getText().toString(),
                        myDetailPlace.getText().toString(),
                        myDetailMoney.getText().toString(),
                        myMemo.getText().toString());
                MainActivity.calendarFragment.addSchedule(data);
                finish();
            }
        });
    }
    public void sendLink(){
        FeedTemplate feedTemplate = new FeedTemplate(new Content(
                nickName+" 님이 귀하를 약속에 초대하였습니다!",
                "https://drive.google.com/file/d/1nFX83bl7FBcb3RSdGK-y3DLZAZ_HNjgc/view?usp=sharing/",
                new Link(null, null)), null, null,
                Arrays.asList(
                        new com.kakao.sdk.template.model.Button("초대 확인하기",new Link(
                                R.string.kakao_scheme+"://"+R.string.kakaolink_host,R.string.kakao_scheme+"://"+R.string.kakaolink_host,
                                new HashMap<String,String>(){{
                                    put("scheduleMaker",nickName); //약속을 만드는 사람의 닉네임
                                    put("sNumber",sNumber); //스케줄의 고유번호를 전달
                                    put("myDetailSubject", myDetailSubject.getText().toString());
                                    put("myDetailParticipants","이선민,황명하");
                                    put("myDetailDate",myDetailDate.getText().toString());
                                    put("myDetailTime",myDetailTime.getText().toString());
                                    put("myDetailPlace",myDetailPlace.getText().toString());
                                    put("myDetailMoney",myDetailMoney.getText().toString());
                                    put("myMemo",myMemo.getText().toString());
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
    public void requestCreateScheduleResponse(){
        String title,date,time,place,payment,description;
        CreateScheduleServerRequestAPI api = retrofit.create(CreateScheduleServerRequestAPI.class);   // 통신 인터페이스를 객체로 생성
        Call<CreateScheduleResponseData> call = api.getSearchKeyword("101");  // 검색 조건 입력
        // API 서버에 요청
        call.enqueue(new Callback<CreateScheduleResponseData>() {
            @Override
            public void onResponse(Call<CreateScheduleResponseData> call, Response<CreateScheduleResponseData> response) {
                //reponse.body().객체
                //sendLink(); //초대메세지 링크보내기
                Toast.makeText(PromiseAddActivity.this,"생성완료 : "+response.body().status,Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onFailure(Call<CreateScheduleResponseData> call, Throwable t) {

            }
        });
    }

    @Override
    public void onScheduleTouchEventing(ScheduleData sd) {

    }
}