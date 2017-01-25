package com.example.asheransari.recyclerviewlatest;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by asher.ansari on 1/25/2017.
 */

public class GreenAdapter extends RecyclerView.Adapter<GreenAdapter.NumberViewHolder> {

    private static final String TAG = GreenAdapter.class.getSimpleName();

    private int mNameItems;
    private int viewHolderCount;
    final private listItemClickListner mListItemClickListner;

    @Override
    public NumberViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        return null;
        Context context = parent.getContext();
        int layoutInflater = R.layout.number_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttackToParentImmeditely = false;

        View view = inflater.inflate(layoutInflater, parent, shouldAttackToParentImmeditely);
        NumberViewHolder viewHolder = new NumberViewHolder(view);

        viewHolder.viewHolderindex.setText("ViewHolder index: " + viewHolderCount);
        int backgroundColor = color.getViewHolderBackgroundColorFromInstance(context, viewHolderCount);

        viewHolder.itemView.setBackgroundColor(backgroundColor);

        viewHolderCount++;

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(NumberViewHolder holder, int position) {
        Log.e(TAG, "#" + position);
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return mNameItems;
    }

    public interface listItemClickListner {
        void onListItemClick(int clickedItemIndex);
    }

    public GreenAdapter(int numberOfItems, listItemClickListner listner) {
        mNameItems = numberOfItems;
        mListItemClickListner = listner;
        viewHolderCount = 0;
    }


    class NumberViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView listItemNumberView;
        TextView viewHolderindex;

        public NumberViewHolder(View itemView) {
            super(itemView);
            listItemNumberView = (TextView) itemView.findViewById(R.id.tv_item_number);
            viewHolderindex = (TextView) itemView.findViewById(R.id.tv_view_holder);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int clickPosition = getAdapterPosition();
            mListItemClickListner.onListItemClick(clickPosition);
        }

        void bind(int listIndex) {
            listItemNumberView.setText(String.valueOf(listIndex));
        }
    }

}
