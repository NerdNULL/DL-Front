package com.example.nerdnullfront.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nerdnullfront.Activity.PromiseDetailActivity;
import com.example.nerdnullfront.Adapter.AllScheduleAdapter;
import com.example.nerdnullfront.Data.ScheduleData;
import com.example.nerdnullfront.R;
import com.example.nerdnullfront.ServerInterface.ReadScheduleServerRequestAPI;
import com.example.nerdnullfront.ServerResponseDataSet.ReadScheduleResponseData;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AllScheduleFragment extends Fragment implements AllScheduleAdapter.IAllScheduleClickable{
    private Retrofit retrofit;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.all_schedule_list_fragment,container,false);
        retrofit = new Retrofit.Builder() // Retrofit 구성
                .baseUrl("") //요청 URL
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RecyclerView recyclerView = view.findViewById(R.id.PromiseRecycler);

        LinearLayoutManager layoutManager =
                new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);
        //iClickable 참조를 위해 this을 넘겨줌
        AllScheduleAdapter adapter = new AllScheduleAdapter(this);

        ArrayList<ScheduleData> arrayList=new ArrayList<>();
        requestAllSchedulesResponseData(arrayList); //전체일정리스트 받아오기
        adapter.setList(arrayList);

        recyclerView.setAdapter(adapter);
        return view;
    }

    @Override
    public void onAllScheduleTouchEventing(int p) {
        Intent intent=new Intent(getActivity(), PromiseDetailActivity.class);
        startActivity(intent);
    }
    public void requestAllSchedulesResponseData(ArrayList<ScheduleData> arrayList){ //전체일정받기
        ReadScheduleServerRequestAPI api = retrofit.create(ReadScheduleServerRequestAPI.class);   // 통신 인터페이스를 객체로 생성
        Call<ReadScheduleResponseData> call = api.getSearchKeyword("", "");  // 검색 조건 입력
        // API 서버에 요청
        call.enqueue(new Callback<ReadScheduleResponseData>() {
            @Override
            public void onResponse(Call<ReadScheduleResponseData> call, Response<ReadScheduleResponseData> response) {
                //reponse.body().객체
                //arrayList에 삽입해야함.
            }
            @Override
            public void onFailure(Call<ReadScheduleResponseData> call, Throwable t) {

            }
        });
    }
}
