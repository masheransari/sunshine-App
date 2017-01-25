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

    private int viewHolderCount;

    final private listItemClickListner mOnClickListner;

    public interface listItemClickListner{
        void onListItemClick(int clickedItemIndex);

    }
    public GreenAdapter(int numberOfItems, listItemClickListner listner)
    {
        mNumberItems = numberOfItems;
        mOnClickListner = listner;
        viewHolderCount = 0;
    }

    @Override
    public NumberViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForListItem = R.layout.number_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;
        View view = inflater.inflate(layoutIdForListItem,parent,shouldAttachToParentImmediately);
        NumberViewHolder viewHolder = new NumberViewHolder(view);
        viewHolder.viewHolderIndex.setText("ViewHolder index: "+viewHolderCount);
        int backgroundColorForVIewHolder = ColorUtils.getViewHolderBackgroundColorFromInstance(context,viewHolderCount);
        viewHolder.itemView.setBackgroundColor(backgroundColorForVIewHolder);

        viewHolderCount++;

        return viewHolder;
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


    class NumberViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
        {
            TextView listItemNumberVIew;
            TextView viewHolderIndex;

        public NumberViewHolder(View itemView) {
            super(itemView);
            listItemNumberVIew = (TextView)itemView.findViewById(R.id.tv_item_number);
            viewHolderIndex = (TextView)itemView.findViewById(R.id.tv_view_holder);
            itemView.setOnClickListener(this);
        }

        void bind(int listIndex)
        {
            listItemNumberVIew.setText(String.valueOf(listIndex));
        }

        @Override
        public void onClick(View view) {
            int clickPosition = getAdapterPosition();
            mOnClickListner.onListItemClick(clickPosition);
        }
    }
}
