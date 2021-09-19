package com.example.nerdnullfront.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nerdnullfront.Data.PromiseData;
import com.example.nerdnullfront.R;

import java.util.ArrayList;

public class PromiseAdapter extends RecyclerView.Adapter<PromiseAdapter.ViewHolder> {

    ArrayList<PromiseData> items = new ArrayList<PromiseData>();

    public void addItem(PromiseData item) {
        items.add(item);
    }

    public void setItems(ArrayList<PromiseData> items) {
        this.items = items;
    }

    public PromiseData getItem(int position) {
        return items.get(position);
    }

    public void setItem(int position, PromiseData item) {
        items.set(position, item);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View itemView = inflater.inflate(R.layout.item_promise,viewGroup, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        PromiseData item = items.get(position);
        holder.SetItem(item);
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

            promiseName = itemView.findViewById(R.id.textView);
            participants = itemView.findViewById(R.id.textView2);
            time = itemView.findViewById(R.id.textView3);
            place = itemView.findViewById(R.id.textView4);
        }

        public void SetItem(PromiseData item){
            promiseName.setText(item.getPromise_name());
            participants.setText(item.getParticipants());
            time.setText(item.getTime());
            place.setText(item.getPlace());
        }

    }

}
