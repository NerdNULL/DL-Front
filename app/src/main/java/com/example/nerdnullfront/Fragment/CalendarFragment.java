package com.example.nerdnullfront.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class CalendarFragment extends Fragment implements ScheduleAdapter.IScheduleClickable,
        UpComingScheduleAdapter.IUpComingScheduleClickable{
    private TextView dayText;
    private ImageView slideArrowImage;
    private CalendarView calendarView;
    private RecyclerView scheduleListView,upComingScheduleListView;
    private SlidingUpPanelLayout slider;
    private FloatingActionButton floatingActionButton;
    private String nickName=null;
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
        //수정
        /*
        view.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {

                if(event.getAction() == MotionEvent.ACTION_MOVE){
                    Toast.makeText(getContext(),"dfadfda",Toast.LENGTH_SHORT).show();
                }
                return true;
            }
        });
        */
        //수정끝

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

        //해당 날짜의 모든 일정 슬라이딩 업 레이아웃 리스트
        setScheduleListView();

        //다가오는 스케줄 리스트 2개
        setUpComingScheduleListView();

    }
    public void setEvents(){
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() { //캘린더의 날짜변경에 따른 이벤트
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                String thisDay=""+year+"."+(month+1)+"."+dayOfMonth;
                Toast.makeText(getContext(),""+year+"."+(month+1)+"."+dayOfMonth,Toast.LENGTH_SHORT).show();
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
    void setScheduleListView(){ //각 날짜마다의 일정정보를 업데이트
        ArrayList<ScheduleData> arrayList=new ArrayList(); //각 일정들의 정보를 담아야함!!
        arrayList.add(new ScheduleData("술약속","명하,선민,정훈,영웅","2021.09.12","부천"));
        arrayList.add(new ScheduleData("회의","명하,선민,정훈,영웅","2021.09.13","부천"));
        arrayList.add(new ScheduleData("술약속","명하,선민,정훈,영웅","2021.09.12","부천"));
        arrayList.add(new ScheduleData("회의","명하,선민,정훈,영웅","2021.09.13","부천"));
        arrayList.add(new ScheduleData("술약속","명하,선민,정훈,영웅","2021.09.12","부천"));
        arrayList.add(new ScheduleData("회의","명하,선민,정훈,영웅","2021.09.13","부천"));
        ScheduleAdapter scheduleAdapter=new ScheduleAdapter(arrayList,this);
        scheduleListView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
        scheduleListView.setAdapter(scheduleAdapter);
    }
    void setUpComingScheduleListView(){ //다가오는 일정 2개를 미리 보여줌.
        ArrayList<UpComingScheduleData> upComingScheduleDataArrayList=new ArrayList();
        upComingScheduleDataArrayList.add(new UpComingScheduleData("upComing1","Whenever1"));
        upComingScheduleDataArrayList.add(new UpComingScheduleData("upComing2","Whenever2"));
        UpComingScheduleAdapter upComingScheduleAdapter=new UpComingScheduleAdapter(upComingScheduleDataArrayList,this);
        upComingScheduleListView.setLayoutManager(new GridLayoutManager(getContext(),2));
        upComingScheduleListView.setAdapter(upComingScheduleAdapter);
    }
    @Override
    public void onScheduleTouchEventing(int p) { //날짜마다의 일정 터치 이벤트 함수
        Intent intent=new Intent(getActivity(), PromiseDetailActivity.class);
        if(nickName!=null)
            intent.putExtra("nickName",nickName);
        startActivity(intent);
    }

    @Override
    public void onUpComingScheduleTouchEventing(int p) { //다가오는 일정 터치시 이벤트 함수
        Intent intent=new Intent(getActivity(), PromiseDetailActivity.class);
        if(nickName!=null)
            intent.putExtra("nickName",nickName);
        startActivity(intent);
    }
}
