package com.example.nerdnullfront.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nerdnullfront.R;

import java.util.List;

public class ScheduleAdapter extends RecyclerView.Adapter<ScheduleAdapter.ScheduleHolder> {
    private List<String> list;
    private IClickable iClickable;
    public interface IClickable{
        void Eventing(int p);
    }
    public ScheduleAdapter(List<String> list,IClickable ic){
        this.list=list;
        iClickable=ic;
    }
    @NonNull
    @Override
    public ScheduleHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        return new ScheduleHolder(inflater.inflate(R.layout.item_schedule,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ScheduleHolder holder, int position) {
        holder.scheduleTitle.setText(list.get(position));
        holder.itemView.setOnClickListener(v -> {
            iClickable.Eventing(position);
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ScheduleHolder extends RecyclerView.ViewHolder {
        private TextView scheduleTitle;
        public ScheduleHolder(View v){
            super(v);
            scheduleTitle=v.findViewById(R.id.scheduleTitle_TextView);
        }
    }
}
