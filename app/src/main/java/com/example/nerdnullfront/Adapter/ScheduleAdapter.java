package com.example.nerdnullfront.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nerdnullfront.Data.ScheduleData;
import com.example.nerdnullfront.R;

import java.util.List;

public class ScheduleAdapter extends RecyclerView.Adapter<ScheduleAdapter.ScheduleHolder> {
    private List<ScheduleData> list;
    private IScheduleClickable iClickable;
    public interface IScheduleClickable{
        void onScheduleTouchEventing(int p);
    }
    public ScheduleAdapter(List<ScheduleData> list, IScheduleClickable ic){
        this.list=list;
        iClickable=ic;
    }
    @NonNull
    @Override
    public ScheduleHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        return new ScheduleHolder(inflater.inflate(R.layout.item_promise,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ScheduleHolder holder, int position) {
        holder.promiseName.setText(list.get(position).getPromise_name());
        holder.participants.setText(list.get(position).getParticipants());
        holder.place.setText(list.get(position).getPlace());
        holder.time.setText(list.get(position).getTime());
        holder.itemView.setOnClickListener(v -> {
            iClickable.onScheduleTouchEventing(position);
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ScheduleHolder extends RecyclerView.ViewHolder {
        TextView promiseName;
        TextView participants;
        TextView time;
        TextView place;
        public ScheduleHolder(View v){
            super(v);
            promiseName = itemView.findViewById(R.id.scheduleTitle_TextView);
            participants = itemView.findViewById(R.id.countOfParty_TextView);
            time = itemView.findViewById(R.id.time_TextView);
            place = itemView.findViewById(R.id.place_TextView);
        }
    }
}
