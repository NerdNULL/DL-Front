package com.example.nerdnullfront.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.example.nerdnullfront.ServerInterface.ReadScheduleServerRequestAPI;
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
        UpComingScheduleAdapter.IUpComingScheduleClickable{
    private TextView dayText;
    private ImageView slideArrowImage;
    private CalendarView calendarView;
    private RecyclerView scheduleListView,upComingScheduleListView;
    private SlidingUpPanelLayout slider;
    private FloatingActionButton floatingActionButton;
    private String nickName=null;
    private Retrofit retrofit;
    public CalendarFragment(String nickName){
        this.nickName=nickName;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.calendar_fragment,container,false);

        setID(view);
        setEvents();

        //???????????? ??????
        Calendar calendar = Calendar.getInstance();
        Date curDate = new Date(calendarView.getDate());
        calendar.setTime(curDate);
        String thisDay = (calendar.get(Calendar.YEAR))+ "." +
                (calendar.get(Calendar.MONTH)+1) + "." +(calendar.get(Calendar.DATE));
        dayText.setText(thisDay);

        setScheduleListView(thisDay); //?????? ????????? ?????? ??????
        setUpComingScheduleListView(); //???????????? ????????? ????????? 2???
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
        retrofit = new Retrofit.Builder() // Retrofit ??????
                .baseUrl("http://jsonplaceholder.typicode.com") //?????? URL
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
    public void setEvents(){
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() { //???????????? ??????????????? ?????? ?????????
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                String thisDay=""+year+"."+(month+1)+"."+dayOfMonth;
                setScheduleListView(thisDay);
                dayText.setText(thisDay);
            }
        });
        slider.addPanelSlideListener(new SlidingUpPanelLayout.PanelSlideListener() { //?????? ?????? ?????????
            @Override
            public void onPanelSlide(View panel, float slideOffset) { }

            @Override
            public void onPanelStateChanged(View panel, SlidingUpPanelLayout.PanelState previousState, SlidingUpPanelLayout.PanelState newState) {
                if(newState==SlidingUpPanelLayout.PanelState.COLLAPSED){ //???????????? ???,
                    slideArrowImage.setImageResource(R.drawable.ic_baseline_keyboard_arrow_up_24);
                }
                else if(newState==SlidingUpPanelLayout.PanelState.EXPANDED){ //????????? ???,
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
                //?????? ????????????
            }
        });
    }
    void setScheduleListView(String thisDay){ //??? ??????????????? ??????????????? ????????????
        ArrayList<ScheduleData> arrayList=new ArrayList(); //??? ???????????? ????????? ????????????!!
        //
        requestSchedulesResponseData(1,thisDay,arrayList); //??? ???????????? ????????????
        //
        ScheduleAdapter scheduleAdapter=new ScheduleAdapter(arrayList,this);
        scheduleListView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
        scheduleListView.setAdapter(scheduleAdapter);
    }
    void setUpComingScheduleListView(){ //???????????? ?????? 2?????? ?????? ?????????.
        ArrayList<UpComingScheduleData> upComingScheduleDataArrayList=new ArrayList();
        //
        requestSchedulesResponseData(2,upComingScheduleDataArrayList); //?????? 2??????
        //
        UpComingScheduleAdapter upComingScheduleAdapter=new UpComingScheduleAdapter(upComingScheduleDataArrayList,this);
        upComingScheduleListView.setLayoutManager(new GridLayoutManager(getContext(),2));
        upComingScheduleListView.setAdapter(upComingScheduleAdapter);
    }
    @Override
    public void onScheduleTouchEventing(int p) { //??????????????? ?????? ?????? ????????? ??????
        Intent intent=new Intent(getActivity(), PromiseDetailActivity.class);
        if(nickName!=null)
            intent.putExtra("nickName",nickName);
        startActivity(intent);
    }

    @Override
    public void onUpComingScheduleTouchEventing(int p) { //???????????? ?????? ????????? ????????? ??????
        Intent intent=new Intent(getActivity(), PromiseDetailActivity.class);
        if(nickName!=null)
            intent.putExtra("nickName",nickName);
        startActivity(intent);
    }
    public void requestSchedulesResponseData(int reqType, String thisDay, List<ScheduleData> scheduleDataList){ //??? ????????? ???????????? ?????? ??????
        ReadScheduleServerRequestAPI api = retrofit.create(ReadScheduleServerRequestAPI.class);   // ?????? ?????????????????? ????????? ??????
        Call<ReadScheduleResponseData> call = api.getSearchKeyword("", "");  // ?????? ?????? ??????
        // API ????????? ??????
        call.enqueue(new Callback<ReadScheduleResponseData>() {
            @Override
            public void onResponse(Call<ReadScheduleResponseData> call, Response<ReadScheduleResponseData> response) {
                //reponse.body().??????
            }
            @Override
            public void onFailure(Call<ReadScheduleResponseData> call, Throwable t) {

            }
        });
    }
    public void requestSchedulesResponseData(int reqType, List<UpComingScheduleData> upComingScheduleDataList){ //upComming ????????????
        ReadScheduleServerRequestAPI api = retrofit.create(ReadScheduleServerRequestAPI.class);   // ?????? ?????????????????? ????????? ??????
        Call<ReadScheduleResponseData> call = api.getSearchKeyword("", "");  // ?????? ?????? ??????
        // API ????????? ??????
        call.enqueue(new Callback<ReadScheduleResponseData>() {
            @Override
            public void onResponse(Call<ReadScheduleResponseData> call, Response<ReadScheduleResponseData> response) {
                //reponse.body().??????
            }
            @Override
            public void onFailure(Call<ReadScheduleResponseData> call, Throwable t) {

            }
        });
    }
}
