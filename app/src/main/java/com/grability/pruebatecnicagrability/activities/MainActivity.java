package com.grability.pruebatecnicagrability.activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.grability.pruebatecnicagrability.R;
import com.grability.pruebatecnicagrability.adapter.NavigationDrawerAdapter;
import com.grability.pruebatecnicagrability.adapter.RecyclerItemClickListener;
import com.grability.pruebatecnicagrability.fragments.CategoryListFragment;
import com.grability.pruebatecnicagrability.services.SincService;

import java.util.List;

public class MainActivity extends AppCompatActivity {


    private DataUpdateReceiver dataUpdateReceiver;

    protected DrawerLayout mDrawerLayout;
    protected ActionBarDrawerToggle mDrawerToggle;
    protected RecyclerView mLeftDrawerList;
    public NavigationDrawerAdapter mNavigationDrawerAdapter;
    private String[] mLeftSliderData;
    public android.support.v7.app.ActionBar mActionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        findViewById(R.id.splash).setVisibility(View.VISIBLE);
        Intent serviceIntent = new Intent(this, SincService.class);
        startService(serviceIntent);

        mLeftSliderData = new String[]{"Category"};

        nitView();
        initDrawer();
    }

    private void initDrawer() {
        if (mDrawerLayout != null) {
            mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow,
                    GravityCompat.START);

            mActionBar.setDisplayHomeAsUpEnabled(true);
            mActionBar.setHomeButtonEnabled(true);
            mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                    R.string.drawer_open, R.string.drawer_open) {

                @Override
                public void onDrawerOpened(View drawerView) {
                    super.onDrawerOpened(drawerView);

                }
            };
            mDrawerLayout.setDrawerListener(mDrawerToggle);
        } else {
            mActionBar.setDisplayHomeAsUpEnabled(false);
            mActionBar.setHomeButtonEnabled(false);
        }
        mLeftDrawerList.setLayoutManager(new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false));
        mLeftDrawerList.setItemAnimator(new DefaultItemAnimator());
    }

    private void nitView() {
        mActionBar = getSupportActionBar();
        mLeftDrawerList = (RecyclerView) findViewById(R.id.left_drawer);
        // mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        mNavigationDrawerAdapter = new NavigationDrawerAdapter(this,
                mLeftSliderData);
        mLeftDrawerList.setAdapter(mNavigationDrawerAdapter);

        mLeftDrawerList.addOnItemTouchListener(new RecyclerItemClickListener(
                this, mLeftDrawerList,
                new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        selectItem(position + 1);
                    }

                    @Override
                    public void onItemLongClick(View view, int position) {
                        selectItem(position + 1);
                    }
                }));
    }


    @Override
    protected void onResume() {
        super.onResume();
        if (dataUpdateReceiver == null) dataUpdateReceiver = new DataUpdateReceiver();
        IntentFilter intentFilter = new IntentFilter(SincService.REFRESH_DATA_INTENT);
        registerReceiver(dataUpdateReceiver, intentFilter);
    }
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        if (mDrawerToggle != null)
            mDrawerToggle.syncState();
    }



    /**
     * Funcion que permite
     * */
    public void openDrawer(int start) {
        if (mDrawerLayout != null)
            mDrawerLayout.openDrawer(start);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (mDrawerToggle != null)
            mDrawerToggle.onConfigurationChanged(newConfig);
    }

    public void dismissDrawer(int position) {

        if (mDrawerLayout != null)
            mDrawerLayout.closeDrawers();
    }

    public void selectItem(int position) {
        dismissDrawer(position);
        changeFragment(new CategoryListFragment());
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (dataUpdateReceiver != null) unregisterReceiver(dataUpdateReceiver);
    }

    public void changeFragment(Fragment fragment) {
        getSupportActionBar().show();
        findViewById(R.id.splash).setVisibility(View.GONE);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.content, fragment);
        fragmentTransaction.addToBackStack("categoryList");
        fragmentTransaction.commit();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            try {
                FragmentManager fm = getSupportFragmentManager();
                List<Fragment> lista = fm.getFragments();
                if (fm.getBackStackEntryCount() > 1) {
                    fm.popBackStack();
                } else
                    finish();

            } catch (Exception e) {
                finish();
            }
        }

        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private class DataUpdateReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(SincService.REFRESH_DATA_INTENT)) {
                changeFragment(new CategoryListFragment());
            }
        }
    }
}
