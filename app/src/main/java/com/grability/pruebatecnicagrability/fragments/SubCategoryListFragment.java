package com.grability.pruebatecnicagrability.fragments;

import android.app.Activity;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.grability.pruebatecnicagrability.R;
import com.grability.pruebatecnicagrability.activities.MainActivity;
import com.grability.pruebatecnicagrability.adapter.SubCategoryRecyclerViewAdapter;
import com.grability.pruebatecnicagrability.entities.Category;

public class SubCategoryListFragment extends Fragment {

    private Category category;
    private RecyclerView mRecyclerView;
    private SubCategoryRecyclerViewAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;


    public SubCategoryListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ((MainActivity) getActivity()).getSupportActionBar().setTitle(category != null ? category.get_nombre() : "error");
        ((TextView) view.findViewById(R.id.descripcion_categoria)).setText(category != null ? category.get_descripcion() : "error");

        mRecyclerView = (RecyclerView) view.findViewById(R.id.sub_category_list);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = getLayoutManager();
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new SubCategoryRecyclerViewAdapter(category.get_list_sub_category());
        mAdapter.setOnItemClickListener(new SubCategoryRecyclerViewAdapter.MyClickListener() {
            @Override
            public void onItemClick(int position, View v) {

            }
        });
        mRecyclerView.setAdapter(mAdapter);
    }

    private RecyclerView.LayoutManager getLayoutManager() {

        int screenSize = getResources().getConfiguration().screenLayout &
                Configuration.SCREENLAYOUT_SIZE_MASK;

        switch (screenSize) {
            case Configuration.SCREENLAYOUT_SIZE_LARGE:
                return new GridLayoutManager(this.getActivity(), 4);

            case Configuration.SCREENLAYOUT_SIZE_NORMAL:
                if (getActivity().getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)
                    return new GridLayoutManager(this.getActivity(), 2);
                else
                    return new GridLayoutManager(this.getActivity(), 1);

            case Configuration.SCREENLAYOUT_SIZE_SMALL:
                return new LinearLayoutManager(this.getActivity());
        }
        return new LinearLayoutManager(this.getActivity());
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null)
            category = (Category) savedInstanceState.getSerializable("category");
        Log.d("Con", "");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sub_category_list, container, false);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

    }

    @Override
    public void onDetach() {
        super.onDetach();

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putSerializable("category", category);
        super.onSaveInstanceState(outState);
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}
