package com.example.nerdnullfront.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nerdnullfront.Adapter.AllScheduleAdapter;
import com.example.nerdnullfront.Adapter.ScheduleAdapter;
import com.example.nerdnullfront.Adapter.UpComingScheduleAdapter;
import com.example.nerdnullfront.Data.ScheduleData;
import com.example.nerdnullfront.R;

public class AllScheduleFragment extends Fragment implements AllScheduleAdapter.IAllScheduleClickable{
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.all_schedule_list_fragment,container,false);

        RecyclerView recyclerView = view.findViewById(R.id.PromiseRecycler);

        LinearLayoutManager layoutManager =
                new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);
        AllScheduleAdapter adapter = new AllScheduleAdapter();

        adapter.addItem(new ScheduleData("점심","명하,선민","2021.09.12","역곡"));
        adapter.addItem(new ScheduleData("아침","명하,선민,정훈,영웅","2021.09.12","역곡"));
        adapter.addItem(new ScheduleData("저녁","명하,선민","2021.09.12","역곡"));

        recyclerView.setAdapter(adapter);


        //수정
        view.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {

                if(event.getAction() == MotionEvent.ACTION_MOVE){
                    Toast.makeText(getContext(),"dfadfda",Toast.LENGTH_SHORT).show();
                }
                return true;
            }
        });
        //수정끝
        return view;
    }

    @Override
    public void onAllScheduleTouchEventing(int p) {
        Toast.makeText(getContext(),"position 131231: "+p,Toast.LENGTH_SHORT).show();
    }
}
