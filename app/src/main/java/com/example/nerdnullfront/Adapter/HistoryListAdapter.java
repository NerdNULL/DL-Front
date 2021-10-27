package com.example.nerdnullfront.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nerdnullfront.Data.HistoryData;
import com.example.nerdnullfront.R;

import java.util.List;

//금액 사용내역 리스트뷰의 어뎁터
public class HistoryListAdapter extends RecyclerView.Adapter<HistoryListAdapter.HistoryHolder> {
    private List<HistoryData> list;
    public HistoryListAdapter(List<HistoryData> list){
        this.list=list;
    } //리스트를 건네주는 생성자
    public HistoryListAdapter(){ }
    public void setList(List<HistoryData> list){
        this.list=list;
    } //동기적인 리스트 업데이트시 사용
    @NonNull
    @Override
    public HistoryHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        return new HistoryHolder(inflater.inflate(R.layout.item_history,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryHolder holder, int position) {
        holder.cost.setText(list.get(position).getCost()+" 원");
        holder.date.setText(list.get(position).getDate());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class HistoryHolder extends RecyclerView.ViewHolder {
        private TextView cost;
        private TextView date;
        public HistoryHolder(View v){
            super(v);
            cost=v.findViewById(R.id.costText_HistoryActivity);
            date=v.findViewById(R.id.payDate_HistoryActivity);
        }
    }
}
