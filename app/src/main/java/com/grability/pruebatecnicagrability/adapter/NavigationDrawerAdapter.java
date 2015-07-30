package com.grability.pruebatecnicagrability.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.grability.pruebatecnicagrability.R;

/**
 * Created by luiscarvajal on 7/29/15.
 */
public class NavigationDrawerAdapter extends
        RecyclerView.Adapter<NavigationDrawerAdapter.ViewHolder> {
    private String[] items;

    public NavigationDrawerAdapter(Context context, String[] items) {
        this.items = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int arg1) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(
                R.layout.nav_drawer_option, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int pos) {
        viewHolder.option.setText(items[pos]);

    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView option;
        public View viewNavigation;

        public ViewHolder(View itemView) {
            super(itemView);
            option = (TextView) itemView.findViewById(R.id.text_option);
        }
    }

    @Override
    public int getItemCount() {
        return items != null ? items.length : 0;
    }

}