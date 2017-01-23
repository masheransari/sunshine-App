package com.example.asheransari.recyclerview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

public class MainActivity extends AppCompatActivity {

    private GreenAdapter adapter;
    private RecyclerView mNumberList;
    private static final int NUM_LIST_ITEMS = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mNumberList = (RecyclerView)findViewById(R.id.rv_numbers);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mNumberList.setLayoutManager(layoutManager);


        mNumberList.setHasFixedSize(true);

        adapter = new GreenAdapter(NUM_LIST_ITEMS);

        mNumberList.setAdapter(adapter);
    }
}
