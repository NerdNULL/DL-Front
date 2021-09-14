package com.example.nerdnullfront.Activity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CalendarView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nerdnullfront.Adapter.ScheduleAdapter;
import com.example.nerdnullfront.R;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements ScheduleAdapter.IClickable{
    //홈화면으로 사용
    private TextView dayText;
    private TextView userNameText;
    private ImageButton openSideMenuBtn;
    private ImageView slideArrowImage;
    private CalendarView calendarView;
    private DrawerLayout parentLayout;
    private RelativeLayout drawerMenu;
    private ImageView profileImage;
    private ListView menuList;
    private RecyclerView scheduleListView;
    private SlidingUpPanelLayout slider;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setID();
        setEvents();

    }
    public void setID(){
        dayText=findViewById(R.id.dayText_MainActivity);
        userNameText=findViewById(R.id.userNameText_MainActivity);
        openSideMenuBtn=findViewById(R.id.openSideMenuButton_MainActivity);
        calendarView=findViewById(R.id.Calendar_MainActivity);
        profileImage=findViewById(R.id.profileImage_MainActivity);
        parentLayout=findViewById(R.id.parentLayout_MainActivity);
        drawerMenu=findViewById(R.id.drawerMenu_MainActivity);
        menuList=findViewById(R.id.menuList_MainActivity);
        scheduleListView=findViewById(R.id.scheduleListView_MainActivity);
        slider=findViewById(R.id.slider);
        slideArrowImage=findViewById(R.id.slideArrow_ImageView);

        ArrayAdapter adapter=new ArrayAdapter(MainActivity.this, android.R.layout.simple_expandable_list_item_1,
                new String[]{"마이페이지","히스토리","로그아웃"});
        menuList.setAdapter(adapter);

        ArrayList<String> arrayList=new ArrayList<String>(); //각 일정들의 정보를 담아야함!!
        arrayList.add("0"); arrayList.add("1"); arrayList.add("2"); //sample
        arrayList.add("3"); arrayList.add("4"); arrayList.add("5"); //sample
        ScheduleAdapter scheduleAdapter=new ScheduleAdapter(arrayList,this);
        scheduleListView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        scheduleListView.setAdapter(scheduleAdapter);

    }
    public void setEvents(){
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                String thisDay=""+year+"."+(month+1)+"."+dayOfMonth;
                Toast.makeText(MainActivity.this,""+year+"."+(month+1)+"."+dayOfMonth,Toast.LENGTH_SHORT).show();
                dayText.setText(thisDay);
            }
        });
        openSideMenuBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                parentLayout.openDrawer(drawerMenu); //사이드메뉴 오픈
            }
        });
        profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this,"profile Image",Toast.LENGTH_SHORT).show();
                //프로필 이미지 터치시, 변경 이벤트
            }
        });
        menuList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch(position){
                    case 0:{
                        Toast.makeText(MainActivity.this,"My Page",Toast.LENGTH_SHORT).show();
                        //마이페이지
                        break;
                    }
                    case 1:{
                        Toast.makeText(MainActivity.this,"History Page",Toast.LENGTH_SHORT).show();
                        //히스토리(거래내역) 화면 이동
                        break;
                    }
                    case 2:{
                        Toast.makeText(MainActivity.this,"Logout",Toast.LENGTH_SHORT).show();
                        if(drawerMenu.isShown())
                            parentLayout.closeDrawer(drawerMenu);
                        //로그아웃 이벤트
                        break;
                    }
                }
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

    @Override
    public void Eventing(int p) {
        Toast.makeText(MainActivity.this,"position : "+p,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBackPressed() {
        if(drawerMenu.isShown())
            parentLayout.closeDrawer(drawerMenu);
        if(slider.getPanelState()== SlidingUpPanelLayout.PanelState.EXPANDED)
            slider.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
        else
            super.onBackPressed();
    }
}