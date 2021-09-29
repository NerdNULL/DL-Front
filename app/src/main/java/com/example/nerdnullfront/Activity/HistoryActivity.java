package com.example.nerdnullfront.Activity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nerdnullfront.Adapter.HistoryListAdapter;
import com.example.nerdnullfront.Data.HistoryData;
import com.example.nerdnullfront.R;

import java.util.ArrayList;

public class HistoryActivity extends AppCompatActivity {
    private TextView usernameTextView,sumPaymentText;
    private RecyclerView historyList;
    private Spinner dateRange_S,order_S;
    private HistoryListAdapter historyListAdapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        setID();
        setEvents();

        ArrayList<HistoryData> list=new ArrayList<>();
        list.add(new HistoryData("3000","2021.09.28"));
        list.add(new HistoryData("10000","2021.09.28"));
        list.add(new HistoryData("200","2021.09.28"));
        historyListAdapter.setList(list);
        historyList.setLayoutManager(new LinearLayoutManager(HistoryActivity.this,LinearLayoutManager.VERTICAL,false));
        historyList.setAdapter(historyListAdapter);
    }
    public void setID(){
        usernameTextView=findViewById(R.id.userNameText_HistoryActivity);
        sumPaymentText=findViewById(R.id.PaymentText_HistoryActivity);
        historyList=findViewById(R.id.historyRecyclerview_HistoryActivity);
        dateRange_S=findViewById(R.id.yearSpinner);
        order_S=findViewById(R.id.orderSpinner);
        historyListAdapter=new HistoryListAdapter();
    }
    public void setEvents(){
        dateRange_S.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String RANGE=parent.getItemAtPosition(position).toString();
                Toast.makeText(HistoryActivity.this,RANGE,Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        });
        order_S.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String ORDER=parent.getItemAtPosition(position).toString();
                Toast.makeText(HistoryActivity.this,ORDER,Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        });
    }
    public void updateHistoryList(){

    }
}
