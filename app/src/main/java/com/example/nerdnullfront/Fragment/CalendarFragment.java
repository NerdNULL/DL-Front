package com.example.nerdnullfront.Fragment;

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

import com.example.nerdnullfront.Adapter.ScheduleAdapter;
import com.example.nerdnullfront.Adapter.UpComingScheduleAdapter;
import com.example.nerdnullfront.Data.ScheduleData;
import com.example.nerdnullfront.Data.UpComingScheduleData;
import com.example.nerdnullfront.R;
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
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.calendar_fragment,container,false);

        setID(view);
        setEvents();

        Calendar calendar = Calendar.getInstance();
        Date curDate = new Date(calendarView.getDate());
        calendar.setTime(curDate);
        String thisDay = (calendar.get(Calendar.YEAR))+ "." +
                (calendar.get(Calendar.MONTH)+1) + ". " +(calendar.get(Calendar.DATE));
        dayText.setText(thisDay);

        return view;
    }
    public void setID(View view){
        dayText=view.findViewById(R.id.dayText_MainActivity);
        calendarView=view.findViewById(R.id.Calendar_MainActivity);
        scheduleListView=view.findViewById(R.id.scheduleListView_MainActivity);
        upComingScheduleListView=view.findViewById(R.id.upcomingSchedule_MainActivity);
        slider=view.findViewById(R.id.slider);
        slideArrowImage=view.findViewById(R.id.slideArrow_ImageView);

        //해당 날짜의 모든 일정 슬라이딩 업 레이아웃 리스트
        setScheduleListView();

        //다가오는 스케줄 리스트 2개
        setUpComingScheduleListView();

    }
    public void setEvents(){
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                String thisDay=""+year+"."+(month+1)+"."+dayOfMonth;
                Toast.makeText(getContext(),""+year+"."+(month+1)+"."+dayOfMonth,Toast.LENGTH_SHORT).show();
                dayText.setText(thisDay);
            }
        });
        slider.addPanelSlideListener(new SlidingUpPanelLayout.PanelSlideListener() {
            @Override
            public void onPanelSlide(View panel, float slideOffset) { }

            @Override
            public void onPanelStateChanged(View panel, SlidingUpPanelLayout.PanelState previousState, SlidingUpPanelLayout.PanelState newState) {
                if(newState==SlidingUpPanelLayout.PanelState.COLLAPSED){
                    slideArrowImage.setImageResource(R.drawable.ic_baseline_keyboard_arrow_up_24);
                }
                else if(newState==SlidingUpPanelLayout.PanelState.EXPANDED){
                    slideArrowImage.setImageResource(R.drawable.ic_baseline_keyboard_arrow_down_24);
                }
            }
        });
    }
    void setScheduleListView(){
        ArrayList<ScheduleData> arrayList=new ArrayList(); //각 일정들의 정보를 담아야함!!
        arrayList.add(new ScheduleData("술약속","명하,선민,정훈,영웅","2021.09.12","부천"));
        arrayList.add(new ScheduleData("회의","명하,선민,정훈,영웅","2021.09.13","부천"));
        ScheduleAdapter scheduleAdapter=new ScheduleAdapter(arrayList,this);
        scheduleListView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
        scheduleListView.setAdapter(scheduleAdapter);
    }
    void setUpComingScheduleListView(){
        ArrayList<UpComingScheduleData> upComingScheduleDataArrayList=new ArrayList();
        upComingScheduleDataArrayList.add(new UpComingScheduleData("upComing1","Whenever1"));
        upComingScheduleDataArrayList.add(new UpComingScheduleData("upComing2","Whenever2"));
        UpComingScheduleAdapter upComingScheduleAdapter=new UpComingScheduleAdapter(upComingScheduleDataArrayList,this);
        upComingScheduleListView.setLayoutManager(new GridLayoutManager(getContext(),2));
        upComingScheduleListView.setAdapter(upComingScheduleAdapter);
    }
    @Override
    public void onScheduleTouchEventing(int p) {
        Toast.makeText(getContext(),"position : "+p,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onUpComingScheduleTouchEventing(int p) {
        Toast.makeText(getContext(),"Up Coming position : "+p,Toast.LENGTH_SHORT).show();
    }
}
