package com.example.nerdnullfront.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nerdnullfront.ServerResponseDataSet.AddressPlace;
import com.example.nerdnullfront.R;

import java.util.List;

//금액 사용내역 리스트뷰의 어뎁터
public class PlaceListAdapter extends RecyclerView.Adapter<PlaceListAdapter.HistoryHolder> {
    private List<AddressPlace> list;
    private IPickable iPickable;
    public PlaceListAdapter(List<AddressPlace> list,IPickable iPickable){
        this.list=list;
        this.iPickable=iPickable;
    } //리스트를 건네주는 생성자
    public PlaceListAdapter(){ }
    public void setList(List<AddressPlace> list){
        this.list=list;
    } //동기적인 리스트 업데이트시 사용
    public interface IPickable{
        void pickPlace(AddressPlace addressPlace);
    }
    @NonNull
    @Override
    public HistoryHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        return new HistoryHolder(inflater.inflate(R.layout.item_place,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryHolder holder, int position) {
        holder.placeName.setText(list.get(position).place_name);
        holder.address.setText(list.get(position).road_address_name);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iPickable.pickPlace(list.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class HistoryHolder extends RecyclerView.ViewHolder {
        private TextView placeName;
        private TextView address;
        public HistoryHolder(View v){
            super(v);
            placeName=v.findViewById(R.id.placeName);
            address=v.findViewById(R.id.address);
        }
    }
}
