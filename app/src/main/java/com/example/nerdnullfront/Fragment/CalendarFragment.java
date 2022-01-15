package com.example.nerdnullfront.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nerdnullfront.Activity.PromiseAddActivity;
import com.example.nerdnullfront.Activity.PromiseDetailActivity;
import com.example.nerdnullfront.Adapter.ScheduleAdapter;
import com.example.nerdnullfront.Adapter.UpComingScheduleAdapter;
import com.example.nerdnullfront.Data.ScheduleData;
import com.example.nerdnullfront.Data.UpComingScheduleData;
import com.example.nerdnullfront.R;
import com.example.nerdnullfront.ServerInterface.CreateScheduleServerRequestAPI;
import com.example.nerdnullfront.ServerInterface.ReadScheduleServerRequestAPI;
import com.example.nerdnullfront.ServerResponseDataSet.CreateScheduleResponseData;
import com.example.nerdnullfront.ServerResponseDataSet.ReadScheduleResponseData;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CalendarFragment extends Fragment implements ScheduleAdapter.IScheduleClickable,
        UpComingScheduleAdapter.IUpComingScheduleClickable {
    private TextView dayText;
    private ImageView slideArrowImage;
    private CalendarView calendarView;
    private RecyclerView scheduleListView,upComingScheduleListView;
    private SlidingUpPanelLayout slider;
    private FloatingActionButton floatingActionButton;
    private String nickName=null;
    private Button testBtn;
    private Retrofit retrofit;
    ArrayList<ScheduleData> arrayList=new ArrayList();
    public CalendarFragment(String nickName){
        this.nickName=nickName;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.calendar_fragment,container,false);

        setID(view);
        setEvents();

        //초기화면 설정
        Calendar calendar = Calendar.getInstance();
        Date curDate = new Date(calendarView.getDate());
        calendar.setTime(curDate);
        String thisDay = (calendar.get(Calendar.YEAR))+ "." +
                (calendar.get(Calendar.MONTH)+1) + "." +(calendar.get(Calendar.DATE));
        dayText.setText(thisDay);

        setScheduleListView(thisDay); //현재 날짜의 일정 받기
        setUpComingScheduleListView(); //다가오는 스케줄 리스트 2개
        return view;
    }
    public void setID(View view){
        dayText=view.findViewById(R.id.dayText_MainActivity);
        calendarView=view.findViewById(R.id.Calendar_MainActivity);
        scheduleListView=view.findViewById(R.id.scheduleListView_MainActivity);
        upComingScheduleListView=view.findViewById(R.id.upcomingSchedule_MainActivity);
        slider=view.findViewById(R.id.slider);
        slideArrowImage=view.findViewById(R.id.slideArrow_ImageView);
        floatingActionButton=view.findViewById(R.id.addSchedule);
        testBtn=view.findViewById(R.id.tester);
        retrofit = new Retrofit.Builder() // Retrofit 구성
                .baseUrl("http://3.35.234.34/") //요청 URL
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
    public void setEvents(){
        testBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent intent=new Intent(getActivity(), RouletteActivity.class);
                //startActivity(intent);
                CreateScheduleServerRequestAPI api = retrofit.create(CreateScheduleServerRequestAPI.class);   // 통신 인터페이스를 객체로 생성
                Call<CreateScheduleResponseData> call = api.getSearchKeyword("101");  // 검색 조건 입력
                // API 서버에 요청
                call.enqueue(new Callback<CreateScheduleResponseData>() {
                    @Override
                    public void onResponse(Call<CreateScheduleResponseData> call, Response<CreateScheduleResponseData> response) {
                        //reponse.body().객체
                        //sendLink(); //초대메세지 링크보내기
                        if(response.isSuccessful())
                            Log.e("TESTING","성공"+response.body().status);
                        else
                            Log.e("TESTING","성공..?");
                    }
                    @Override
                    public void onFailure(Call<CreateScheduleResponseData> call, Throwable t) {
                        Log.e("TESTING","실패"+t.getMessage());
                    }
                });
            }
        });
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() { //캘린더의 날짜변경에 따른 이벤트
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                String thisDay=""+year+"."+(month+1)+"."+dayOfMonth;
                //setScheduleListView(thisDay);
                dayText.setText(thisDay);
            }
        });
        slider.addPanelSlideListener(new SlidingUpPanelLayout.PanelSlideListener() { //패널 상태 리스너
            @Override
            public void onPanelSlide(View panel, float slideOffset) { }

            @Override
            public void onPanelStateChanged(View panel, SlidingUpPanelLayout.PanelState previousState, SlidingUpPanelLayout.PanelState newState) {
                if(newState==SlidingUpPanelLayout.PanelState.COLLAPSED){ //펼쳐지기 전,
                    slideArrowImage.setImageResource(R.drawable.ic_baseline_keyboard_arrow_up_24);
                }
                else if(newState==SlidingUpPanelLayout.PanelState.EXPANDED){ //펼쳐진 후,
                    slideArrowImage.setImageResource(R.drawable.ic_baseline_keyboard_arrow_down_24);
                }
            }
        });
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(), PromiseAddActivity.class);
                if(nickName!=null)
                    intent.putExtra("nickName",nickName);
                startActivity(intent);
                //약속 추가화면
            }
        });
    }
    void setScheduleListView(String thisDay){ //각 날짜마다의 일정정보를 업데이트
        //ArrayList<ScheduleData> arrayList=new ArrayList(); //각 일정들의 정보를 담아야함!!
        //
        //requestSchedulesResponseData(1,thisDay,arrayList); //각 날짜마다 업데이트
        //
        arrayList.add(new ScheduleData("회식","김강산, 이선민, 황명하","2021-12-10","12:00",
                "역곡","2000","역곡역 앞 시나브로"));
        arrayList.add(new ScheduleData("토익 스터디","김정훈, 김영웅","2021-12-10","13:00",
                "홍대","1500","스터디 카페"));
        ScheduleAdapter scheduleAdapter=new ScheduleAdapter(arrayList,this);
        scheduleListView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
        scheduleListView.setAdapter(scheduleAdapter);
    }
    void setUpComingScheduleListView(){ //다가오는 일정 2개를 미리 보여줌.
        ArrayList<UpComingScheduleData> upComingScheduleDataArrayList=new ArrayList();
        //
        //requestSchedulesResponseData(2,upComingScheduleDataArrayList); //최신 2개만
        //
        upComingScheduleDataArrayList.add(new UpComingScheduleData("회식","2021-11-29"));
        upComingScheduleDataArrayList.add(new UpComingScheduleData("토익 스터디","2021-12-03"));
        UpComingScheduleAdapter upComingScheduleAdapter=new UpComingScheduleAdapter(upComingScheduleDataArrayList,this);
        upComingScheduleListView.setLayoutManager(new GridLayoutManager(getContext(),2));
        upComingScheduleListView.setAdapter(upComingScheduleAdapter);
    }
    @Override
    public void onScheduleTouchEventing(ScheduleData sd) { //날짜마다의 일정 터치 이벤트 함수
        Intent intent=new Intent(getActivity(), PromiseDetailActivity.class);
        if(nickName!=null)
            intent.putExtra("nickName",nickName);
        intent.putExtra("scData",sd);
        startActivity(intent);
    }

    @Override
    public void onUpComingScheduleTouchEventing(int p) { //다가오는 일정 터치시 이벤트 함수
        Intent intent=new Intent(getActivity(), PromiseDetailActivity.class);
        if(nickName!=null)
            intent.putExtra("nickName",nickName);
        startActivity(intent);
    }
    public void requestSchedulesResponseData(int reqType, String thisDay, List<ScheduleData> scheduleDataList){ //각 일자에 해당하는 일정 받기
        ReadScheduleServerRequestAPI api = retrofit.create(ReadScheduleServerRequestAPI.class);   // 통신 인터페이스를 객체로 생성
        Call<ReadScheduleResponseData> call = api.getSearchKeyword("", "");  // 검색 조건 입력
        // API 서버에 요청
        call.enqueue(new Callback<ReadScheduleResponseData>() {
            @Override
            public void onResponse(Call<ReadScheduleResponseData> call, Response<ReadScheduleResponseData> response) {
                //reponse.body().객체
            }
            @Override
            public void onFailure(Call<ReadScheduleResponseData> call, Throwable t) {

            }
        });
    }
    public void requestSchedulesResponseData(int reqType, List<UpComingScheduleData> upComingScheduleDataList){ //upComming 일정받기
        ReadScheduleServerRequestAPI api = retrofit.create(ReadScheduleServerRequestAPI.class);   // 통신 인터페이스를 객체로 생성
        Call<ReadScheduleResponseData> call = api.getSearchKeyword("", "");  // 검색 조건 입력
        // API 서버에 요청
        call.enqueue(new Callback<ReadScheduleResponseData>() {
            @Override
            public void onResponse(Call<ReadScheduleResponseData> call, Response<ReadScheduleResponseData> response) {
                //reponse.body().객체
            }
            @Override
            public void onFailure(Call<ReadScheduleResponseData> call, Throwable t) {

            }
        });
    }
    public void addSchedule(ScheduleData data){
        arrayList.add(data);
        ScheduleAdapter scheduleAdapter=new ScheduleAdapter(arrayList,this);
        scheduleListView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
        scheduleListView.setAdapter(scheduleAdapter);
    }
}
