package com.example.nerdnullfront.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.nerdnullfront.Fragment.AllScheduleFragment;
import com.example.nerdnullfront.Fragment.CalendarFragment;
import com.example.nerdnullfront.R;

public class MainActivity extends AppCompatActivity{
    //홈화면으로 사용
    private TextView userNameText;
    private ImageButton openSideMenuBtn;
    private DrawerLayout parentLayout;
    private RelativeLayout drawerMenu;
    private ImageView profileImage;
    private ListView menuList;
    private CalendarFragment calendarFragment;
    private AllScheduleFragment allScheduleFragment;
    private FragmentManager fragmentManager=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setID();
        setEvents();

        calendarFragment=new CalendarFragment();
        fragmentManager=getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.frameLayout_MainActivity,calendarFragment).commit();

    }
    public void setID(){
        userNameText=findViewById(R.id.userNameText_MainActivity);
        openSideMenuBtn=findViewById(R.id.openSideMenuButton_MainActivity);
        profileImage=findViewById(R.id.profileImage_MainActivity);
        parentLayout=findViewById(R.id.parentLayout_MainActivity);
        drawerMenu=findViewById(R.id.drawerMenu_MainActivity);
        menuList=findViewById(R.id.menuList_MainActivity);

        ArrayAdapter adapter=new ArrayAdapter(MainActivity.this, android.R.layout.simple_expandable_list_item_1,
                new String[]{"마이페이지","히스토리","로그아웃","전체 일정"});
        menuList.setAdapter(adapter);
    }
    public void setEvents(){
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
                        Intent intent=new Intent(MainActivity.this,LoginActivity.class);
                        startActivity(intent);
                        finish();
                        break;
                    }
                    case 3:{
                        if(allScheduleFragment!=null){
                            if(drawerMenu.isShown())
                                parentLayout.closeDrawer(drawerMenu);
                            break;
                        }
                        allScheduleFragment=new AllScheduleFragment();
                        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.frameLayout_MainActivity,allScheduleFragment);
                        fragmentTransaction.addToBackStack(null); //현재 프래그먼트를 백스택으로 넣어줌.
                        fragmentTransaction.commit();
                        if(drawerMenu.isShown())
                            parentLayout.closeDrawer(drawerMenu);
                        break;
                        //전체 일정리스트
                    }
                }
            }
        });
    }
    @Override
    public void onBackPressed() {
        if(allScheduleFragment!=null)
            allScheduleFragment=null;
        if(drawerMenu.isShown())
            parentLayout.closeDrawer(drawerMenu);
        else
            super.onBackPressed();
    }
}