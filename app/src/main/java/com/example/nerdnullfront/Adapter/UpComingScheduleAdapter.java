package com.example.nerdnullfront.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nerdnullfront.Data.UpComingScheduleData;
import com.example.nerdnullfront.R;

import java.util.List;

//다가오는 일정리스트뷰의 어뎁터
public class UpComingScheduleAdapter extends RecyclerView.Adapter<UpComingScheduleAdapter.ScheduleHolder> {
    private List<UpComingScheduleData> list;
    private IUpComingScheduleClickable iClickable;
    public interface IUpComingScheduleClickable{
        void onUpComingScheduleTouchEventing(int p);
    }
    public UpComingScheduleAdapter(List<UpComingScheduleData> list, IUpComingScheduleClickable ic){
        this.list=list;
        iClickable=ic;
    }
    @NonNull
    @Override
    public ScheduleHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        return new ScheduleHolder(inflater.inflate(R.layout.item_upcoming_schedule,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ScheduleHolder holder, int position) {
        holder.scheduleTitle.setText(list.get(position).getTitle());
        holder.scheduleDate.setText(list.get(position).getDate());
        holder.itemView.setOnClickListener(v -> { //뷰 터치시,
            iClickable.onUpComingScheduleTouchEventing(position);
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ScheduleHolder extends RecyclerView.ViewHolder {
        private TextView scheduleTitle;
        private TextView scheduleDate;
        public ScheduleHolder(View v){
            super(v);
            scheduleTitle=v.findViewById(R.id.upcomingTitleText);
            scheduleDate=v.findViewById(R.id.upcomingDateText);
        }
    }
}
