package com.example.nerdnullfront.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nerdnullfront.Data.ScheduleData;
import com.example.nerdnullfront.R;

import java.util.ArrayList;

//전체 일정리스트뷰 어뎁터
public class AllScheduleAdapter extends RecyclerView.Adapter<AllScheduleAdapter.ViewHolder> {

    ArrayList<ScheduleData> items = new ArrayList<ScheduleData>();

    public void addItem(ScheduleData item) {
        items.add(item);
    }

    public void setItems(ArrayList<ScheduleData> items) {
        this.items = items;
    }

    public ScheduleData getItem(int position) {
        return items.get(position);
    }

    public void setItem(int position, ScheduleData item) {
        items.set(position, item);
    }

    //주목 - 클릭이벤트 인터페이스 생성했는데
    @NonNull
    private IAllScheduleClickable iClickable;
    public interface IAllScheduleClickable{
        void onAllScheduleTouchEventing(int p);
    }


    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View itemView = inflater.inflate(R.layout.item_schedule,viewGroup, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ScheduleData item = items.get(position);
        holder.SetItem(item);//세팅
        //주목 - 클릭이벤트 인터페이스 생성했는데
        holder.itemView.setOnClickListener(v -> {
            iClickable.onAllScheduleTouchEventing(position);
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView promiseName;
        TextView participants;
        TextView time;
        TextView place;
        public ViewHolder(View itemView) {
            super(itemView);

            promiseName = itemView.findViewById(R.id.scheduleTitle_TextView);
            participants = itemView.findViewById(R.id.countOfParty_TextView);
            time = itemView.findViewById(R.id.time_TextView);
            place = itemView.findViewById(R.id.place_TextView);
        }
        public void SetItem(ScheduleData item){ //각 리스트뷰에 데이터클래스의 속성 대입
            promiseName.setText(item.getPromise_name());
            participants.setText(item.getParticipants());
            time.setText(item.getTime());
            place.setText(item.getPlace());
        }

    }

}
