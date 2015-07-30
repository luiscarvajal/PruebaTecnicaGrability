package com.grability.pruebatecnicagrability.fragments;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.grability.pruebatecnicagrability.R;
import com.grability.pruebatecnicagrability.Sqlite.Helper;
import com.grability.pruebatecnicagrability.activities.MainActivity;
import com.grability.pruebatecnicagrability.adapter.CategoryRecyclerViewAdapter;
import com.grability.pruebatecnicagrability.entities.Category;
import com.grability.pruebatecnicagrability.services.SincService;

import java.util.ArrayList;

public class CategoryListFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    private RecyclerView mRecyclerView;
    private CategoryRecyclerViewAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private SwipeRefreshLayout mSwipeRefreshLayout;

    public CategoryListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ((MainActivity) getActivity()).getSupportActionBar().setTitle(getString(R.string.categorias));

        Helper db = new Helper(this.getActivity());

        final ArrayList<Category> listaCategory = db.getAllCategory();
        mRecyclerView = (RecyclerView) view.findViewById(R.id.my_recycler_view);
        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = getLayoutManager();
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new CategoryRecyclerViewAdapter(listaCategory);
        mAdapter.setOnItemClickListener(new CategoryRecyclerViewAdapter.MyClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                SubCategoryListFragment fragment = new SubCategoryListFragment();
                fragment.setCategory(listaCategory.get(position));
                ((MainActivity) CategoryListFragment.this.getActivity()).changeFragment(fragment);
            }
        });
        mRecyclerView.setAdapter(mAdapter);

        mSwipeRefreshLayout = (SwipeRefreshLayout) view
                .findViewById(R.id.category_swipe);
        mSwipeRefreshLayout.setOnRefreshListener(this);

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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_category_list, container, false);
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
    public void onRefresh() {
        Intent serviceIntent = new Intent(this.getActivity(), SincService.class);
        getActivity().startService(serviceIntent);
    }
}
