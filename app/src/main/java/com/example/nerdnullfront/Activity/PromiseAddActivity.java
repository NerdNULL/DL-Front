package com.example.nerdnullfront.Activity;

import android.app.Activity;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.example.nerdnullfront.R;

public class PromiseAddActivity extends AppCompatActivity {
    private Button addPlaceBtn,addScheduleBtn;
    private ActivityResultLauncher<Intent> activityStarter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_promise_add);
        setID();
        setEvents();

    }
    public void setID(){
        addPlaceBtn=findViewById(R.id.app_map_button);
        addScheduleBtn=findViewById(R.id.checked_button);
        activityStarter=registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if(result.getResultCode()== Activity.RESULT_OK){
                            //장소 반환
                            Intent intent=result.getData();
                        }
                    }
                });
    }
    public void setEvents(){
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
                //스케줄 추가
            }
        });
    }
    public boolean checkLocationServicesStatus() {
        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
                || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }
}