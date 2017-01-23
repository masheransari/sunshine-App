package com.example.asheransari.recyclerview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by asher.ansari on 1/24/2017.
 */

public class GreenAdapter extends RecyclerView.Adapter<GreenAdapter.NumberViewHolder>{

    private static final String TAG = GreenAdapter.class.getSimpleName();

    private int mNumberItems;

    public GreenAdapter(int numberOfItems)
    {
        mNumberItems = numberOfItems;
    }

    @Override
    public NumberViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForListItem = R.layout.number_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;
        View view = inflater.inflate(layoutIdForListItem,parent,shouldAttachToParentImmediately);
        NumberViewHolder numberViewHolder = new NumberViewHolder(view);

        return numberViewHolder;
    }

    @Override
    public void onBindViewHolder(NumberViewHolder holder, int position) {
        Log.e(TAG,"#"+position);
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return mNumberItems;
    }


    class NumberViewHolder extends RecyclerView.ViewHolder
    {
        TextView listItemNumberVIew;

        public NumberViewHolder(View itemView) {
            super(itemView);
            listItemNumberVIew = (TextView)itemView.findViewById(R.id.tv_item_number);
        }

        void bind(int listIndex)
        {
            listItemNumberVIew.setText(String.valueOf(listIndex));
        }
    }
}
