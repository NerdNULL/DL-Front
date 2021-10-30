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

import com.bumptech.glide.Glide;
import com.example.nerdnullfront.Fragment.AllScheduleFragment;
import com.example.nerdnullfront.Fragment.CalendarFragment;
import com.example.nerdnullfront.R;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.LogoutResponseCallback;

public class MainActivity extends AppCompatActivity{
    //홈화면으로 사용
    private TextView userNameText;
    private ImageButton openSideMenuBtn;
    private DrawerLayout parentLayout;
    private RelativeLayout drawerMenu;
    private ImageView profileImage;
    private ListView menuList;
    private CalendarFragment calendarFragment; //캘린더 프래그먼트
    private AllScheduleFragment allScheduleFragment; //전체 일정 프래그먼트
    private FragmentManager fragmentManager=null;
    //계정정보
    private String nickName=null;
    private String profileImageURI=null;
    private String email=null; //마이페이지에서 쓰여질것.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setID();
        setEvents();

        setMyAccountInfo(getIntent()); //카카오계정 정보를 받아옴
        setUserNickName(nickName); //닉네임 설정
        setUserProfileImage(profileImageURI); //프로필 사진 설정

        calendarFragment=new CalendarFragment();
        fragmentManager=getSupportFragmentManager();
        //초기화면은 달력 프래그먼트이다.
        fragmentManager.beginTransaction().replace(R.id.frameLayout_MainActivity,calendarFragment).commit();

    }
    public void setID(){
        userNameText=findViewById(R.id.userNameText_MainActivity);
        openSideMenuBtn=findViewById(R.id.openSideMenuButton_MainActivity);
        profileImage=findViewById(R.id.profileImage_MainActivity);
        parentLayout=findViewById(R.id.parentLayout_MainActivity);
        drawerMenu=findViewById(R.id.drawerMenu_MainActivity);
        menuList=findViewById(R.id.menuList_MainActivity);

        //사이드 메뉴의 리스트
        ArrayAdapter adapter=new ArrayAdapter(MainActivity.this, android.R.layout.simple_expandable_list_item_1,
                new String[]{"마이페이지","히스토리","로그아웃","전체 일정"});
        menuList.setAdapter(adapter);
    }
    public void setMyAccountInfo(Intent intent){
        nickName=intent.getStringExtra("name");
        profileImageURI=intent.getStringExtra("profileImage");
        email=intent.getStringExtra("email");
    }
    public void setUserNickName(String name){
        if(name!=null)
            userNameText.setText(name);
    }
    public void setUserProfileImage(String uri){
        if(uri!=null){
            Glide.with(this).load(uri).into(profileImage);
        }
    }
    public void setEvents(){
        openSideMenuBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                parentLayout.openDrawer(drawerMenu); //사이드메뉴 오픈
            }
        });
        menuList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch(position){
                    case 0:{
                        //마이페이지 이동
                        Toast.makeText(MainActivity.this,"My Page",Toast.LENGTH_SHORT).show();
                        Intent intent=new Intent(MainActivity.this,MyPageActivity.class);
                        intent.putExtra("name",nickName); //닉네임
                        intent.putExtra("profileImage",profileImageURI); //프로필 이미지
                        intent.putExtra("email",email); //프로필 이미지
                        startActivity(intent);
                        if(drawerMenu.isShown())
                            parentLayout.closeDrawer(drawerMenu);
                        break;
                    }
                    case 1:{
                        Toast.makeText(MainActivity.this,"History Page",Toast.LENGTH_SHORT).show();
                        Intent intent =new Intent(MainActivity.this,HistoryActivity.class);
                        startActivity(intent);
                        if(drawerMenu.isShown())
                            parentLayout.closeDrawer(drawerMenu);
                        //히스토리(거래내역) 화면 이동
                        break;
                    }
                    case 2:{
                        //로그아웃
                        Toast.makeText(MainActivity.this,"로그아웃 되었습니다.",Toast.LENGTH_SHORT).show();
                        if(drawerMenu.isShown())
                            parentLayout.closeDrawer(drawerMenu);
                        UserManagement.getInstance().requestLogout(new LogoutResponseCallback() {
                            @Override
                            public void onCompleteLogout() {
                                Intent intent=new Intent(MainActivity.this,LoginActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        });
                        break;
                    }
                    case 3:{
                        //'전체일정' 이동
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