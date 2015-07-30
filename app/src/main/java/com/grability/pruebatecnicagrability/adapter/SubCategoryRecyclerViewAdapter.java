package com.grability.pruebatecnicagrability.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.grability.pruebatecnicagrability.R;
import com.grability.pruebatecnicagrability.entities.SubCategory;

import java.util.ArrayList;

/**
 * Created by luiscarvajal on 7/29/15.
 */
public class SubCategoryRecyclerViewAdapter extends RecyclerView
        .Adapter<SubCategoryRecyclerViewAdapter
        .SubCategoryHolder> {
    private static String LOG_TAG = "MyRecyclerViewAdapter";

    private ArrayList<SubCategory> mDataset;

    public SubCategoryRecyclerViewAdapter(ArrayList<SubCategory> myDataset) {
        mDataset = myDataset;
    }

    private static MyClickListener myClickListener;


    @Override
    public SubCategoryHolder onCreateViewHolder(ViewGroup parent,
                                               int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.sub_category_recycler_view_item, parent, false);

        SubCategoryHolder dataObjectHolder = new SubCategoryHolder(view);
        return dataObjectHolder;
    }

    public void setOnItemClickListener(MyClickListener myClickListener) {
        this.myClickListener = myClickListener;
    }

    @Override
    public void onBindViewHolder(SubCategoryHolder holder, int position) {
        holder.label.setText(mDataset.get(position).get_nombre());
        holder.dateTime.setText(mDataset.get(position).get_descripcion());
    }

    public void addItem(SubCategory dataObj, int index) {
        mDataset.add(dataObj);
        notifyItemInserted(index);
    }

    public void deleteItem(int index) {
        mDataset.remove(index);
        notifyItemRemoved(index);
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public interface MyClickListener {
        public void onItemClick(int position, View v);
    }

    public static class SubCategoryHolder extends RecyclerView.ViewHolder
            implements View
            .OnClickListener {
        TextView label;
        TextView dateTime;

        public SubCategoryHolder(View itemView) {
            super(itemView);
            label = (TextView) itemView.findViewById(R.id.textView);
            dateTime = (TextView) itemView.findViewById(R.id.textView2);
            Log.i(LOG_TAG, "Adding Listener");
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            myClickListener.onItemClick(getPosition(), v);
        }
    }
}
