package com.example.asheransari.recyclerviewlatest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private GreenAdapter mGreenAdapter;
    private RecyclerView mRecyclerView;
    private static final int NUMBER_LIST_ITEMS = 100;
    private Toast mToast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = (RecyclerView)findViewById(R.id.rv_number);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(linearLayoutManager);

//        mRecyclerView.setHasFixedSize(true);
        mGreenAdapter = new GreenAdapter(NUMBER_LIST_ITEMS,this);
        mRecyclerView.setAdapter(mGreenAdapter);
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
            case R.id.action_reset:

                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onListItemClick(int clickedItemIndex)
    {
        if (mToast != null)
        {
            mToast.cancel();
        }
        String toastMessage = "Item #"+clickedItemIndex+" Clicked.";
        mToast = Toast.makeText(this,toastMessage,Toast.LENGTH_SHORT);
        mToast.show();
    }
}
