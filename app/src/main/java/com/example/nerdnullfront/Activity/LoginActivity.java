package com.example.nerdnullfront.Activity;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.nerdnullfront.R;
import com.kakao.auth.ISessionCallback;
import com.kakao.auth.Session;
import com.kakao.network.ErrorResult;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.MeV2ResponseCallback;
import com.kakao.usermgmt.response.MeV2Response;
import com.kakao.util.exception.KakaoException;
import com.kakao.util.helper.log.Logger;

import java.security.MessageDigest;

public class LoginActivity extends AppCompatActivity {
    private ImageView logoImage;
    private ISessionCallback sessionCallback;
    private String action=null;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setID();
        //getAppKeyHash();
        action=getIntent().getAction(); //능동적으로 킨건지, 초대링크를 탄건지 파악
    }
    public void setID(){
        logoImage=findViewById(R.id.logoImage_LoginActivity);
        sessionCallback=new ISessionCallback() { //로그인 세션에 따라 콜백이벤트 설정
            @Override
            public void onSessionOpened() {
                //로그인을 요청
                UserManagement.getInstance().me(new MeV2ResponseCallback() {
                    @Override
                    public void onFailure(ErrorResult errorResult) {
                        //로그인이 실패했을 때,
                        Toast.makeText(LoginActivity.this,"로그인이 실패하였습니다.",Toast.LENGTH_SHORT).show();;
                    }

                    @Override
                    public void onSessionClosed(ErrorResult errorResult) {
                        //로그인을 위한 세션이 닫혔을 때,
                        Toast.makeText(LoginActivity.this,"세션이 닫혔습니다. 다시 시도해주십시오.",Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onSuccess(MeV2Response result) {
                        //로그인이 성공하였을 때,
                        Intent intent=new Intent(LoginActivity.this,MainActivity.class);
                        intent.putExtra("name",result.getKakaoAccount().getProfile().getNickname()); //닉네임
                        intent.putExtra("profileImage",result.getKakaoAccount().getProfile().getProfileImageUrl()); //프로필 이미지
                        intent.putExtra("email",result.getKakaoAccount().getEmail()); //프로필 이미지
                        if(Intent.ACTION_VIEW.equalsIgnoreCase(action)){
                            //딥링크를 타고옴
                            intent.putExtra("invited",true);
                            Intent linkIntent = getIntent();
                            //AndroidExecutionParams에서 전달했던 키값
                            String maker = linkIntent.getData().getQueryParameter("scheduleMaker"); //스케줄 메이커
                            String snumber = linkIntent.getData().getQueryParameter("scheduleNumber"); //스케줄 고유 번호
                            intent.putExtra("scheduleMaker",maker);
                            intent.putExtra("scheduleNumber",snumber);
                        }
                        startActivity(intent);
                        Toast.makeText(LoginActivity.this,"로그인에 성공하였습니다.",Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });
            }

            @Override
            public void onSessionOpenFailed(KakaoException exception) {
                //로그인을 위한 세션을 여는데 실패.
                Logger.e(exception);
                Toast.makeText(LoginActivity.this,"세션을 열 수 없습니다. 다시 시도해주십시오.",Toast.LENGTH_SHORT).show();
            }
        };
        Session.getCurrentSession().addCallback(sessionCallback); //콜백을 등록
        Session.getCurrentSession().checkAndImplicitOpen(); //로그인 상태라면 로그아웃을 하지 않는이상, 자동으로 로그인
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(Session.getCurrentSession().handleActivityResult(requestCode,resultCode,data)) {
            //동의를 받고 로그인을 시작할 때, 해당 로그인 Activity에서 동의 결과를 받고 MainActivity를 수행하는 로직.
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    protected void onDestroy() { //앱이 죽거나 꺼지면 세션콜백을 유지 하지 않아도 된다.
        super.onDestroy();
        Session.getCurrentSession().removeCallback(sessionCallback);
    }

    /**
     * 카카오 API 사용을 위한 HashKey를 반환 - 개발시 한 번만 활용될것.
     */
    private void getAppKeyHash(){
        try{
            PackageInfo info=getPackageManager().getPackageInfo(getPackageName(), PackageManager.GET_SIGNATURES);
            for(Signature signature:info.signatures){
                MessageDigest md;
                md=MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                String key=new String(Base64.encode(md.digest(),0));
                Log.e("Hash Key",key);
            }
        }catch(Exception e){
            Log.e("not Found",e.toString());
        }
    }

}
