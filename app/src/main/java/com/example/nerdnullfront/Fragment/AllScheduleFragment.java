package com.example.nerdnullfront.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nerdnullfront.Adapter.AllScheduleAdapter;
import com.example.nerdnullfront.Data.ScheduleData;
import com.example.nerdnullfront.R;

public class AllScheduleFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.all_schedule_list_fragment,container,false);

        RecyclerView recyclerView = view.findViewById(R.id.PromiseRecycler);

        LinearLayoutManager layoutManager =
                new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);
        AllScheduleAdapter adapter = new AllScheduleAdapter();

        //adapter.addItem(new PromiseData("저녁","명하,선민","2021.09.12","역곡"));
        adapter.addItem(new ScheduleData("아침","명하,선민,정훈,영웅","2021.09.12","역곡"));
        adapter.addItem(new ScheduleData("저녁","명하,선민","2021.09.12","역곡"));

        recyclerView.setAdapter(adapter);

        return view;
    }



}
