package com.example.asheransari.recyclerview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements GreenAdapter.listItemClickListner{

    private GreenAdapter adapter;
    private RecyclerView mNumberList;
    private static final int NUM_LIST_ITEMS = 100;
    private Toast mToast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mNumberList = (RecyclerView)findViewById(R.id.rv_numbers);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mNumberList.setLayoutManager(layoutManager);


        mNumberList.setHasFixedSize(true);

        adapter = new GreenAdapter(NUM_LIST_ITEMS, this);

        mNumberList.setAdapter(adapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int itemId = item.getItemId();

        switch (itemId)
        {
            case R.id.action_refresh:
                adapter = new GreenAdapter(NUM_LIST_ITEMS,this);
                mNumberList.setAdapter(adapter);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onListItemClick(int clickedItemIndex) {
        if(mToast != null)
        {
            mToast.cancel();
        }
        String toastMessage = "Item # "+clickedItemIndex + " clicked.";
        mToast = Toast.makeText(this,toastMessage,Toast.LENGTH_SHORT);
        mToast.show();

    }
}
