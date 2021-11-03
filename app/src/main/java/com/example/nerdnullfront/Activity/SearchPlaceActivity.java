package com.example.nerdnullfront.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nerdnullfront.Adapter.PlaceListAdapter;
import com.example.nerdnullfront.Data.AddressPlace;
import com.example.nerdnullfront.Data.AddressResponseData;
import com.example.nerdnullfront.KakaoMapHandler.KakaoRequestAPI;
import com.example.nerdnullfront.R;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SearchPlaceActivity extends AppCompatActivity implements PlaceListAdapter.IPickable{
    static String BASE_URL = "https://dapi.kakao.com/";
    static String API_KEY = "KakaoAK ed7d8312a8e083b9ff3652b86b39eb72"; // REST API 키
    private Retrofit retrofit;
    private EditText editText;
    private RecyclerView placeListView;
    private PlaceListAdapter placeListAdapter=new PlaceListAdapter(new ArrayList<AddressPlace>(),this);
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_place);
        setID();
        setEvents();
    }
    public void setID(){
        editText=findViewById(R.id.searchPlace_editText);
        placeListView=findViewById(R.id.placeListView);
        placeListView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        placeListView.setAdapter(placeListAdapter);
        retrofit = new Retrofit.Builder() // Retrofit 구성
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
    public void setEvents(){
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String keyword=editText.getText().toString();
                if(keyword.equals("")) {
                    placeListAdapter.setList(new ArrayList<>());
                    placeListAdapter.notifyDataSetChanged();
                    return;
                }
                KakaoRequestAPI api = retrofit.create(KakaoRequestAPI.class);   // 통신 인터페이스를 객체로 생성
                Call<AddressResponseData> call = api.getSearchKeyword(API_KEY, keyword);  // 검색 조건 입력
                // API 서버에 요청
                call.enqueue(new Callback<AddressResponseData>() {
                    @Override
                    public void onResponse(Call<AddressResponseData> call, Response<AddressResponseData> response) {
                        placeListAdapter.setList(response.body().documents);
                        placeListAdapter.notifyDataSetChanged();
                    }
                    @Override
                    public void onFailure(Call<AddressResponseData> call, Throwable t) {

                    }
                });
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    public void pickPlace(AddressPlace addressPlace) {
        Intent intent=new Intent();
        intent.putExtra("latitude",Double.parseDouble(addressPlace.y));
        intent.putExtra("longitude",Double.parseDouble(addressPlace.x));
        intent.putExtra("placeName",addressPlace.place_name);
        setResult(Activity.RESULT_OK,intent);
        finish();
    }
}
