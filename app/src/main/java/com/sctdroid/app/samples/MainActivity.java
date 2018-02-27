package com.sctdroid.app.samples;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.sctdroid.app.samples.modules.BaseFragment;

import butterknife.ButterKnife;

/**
 * Created by lixindong on 2018/2/26.
 */

public class MainActivity extends AppCompatActivity {


    private NavigationController mNavigationController;
    private BaseFragment mFragment;

    private DrawerLayout mDrawerLayout;
    private NavigationView mNavigationView;

    private ProgressDialog mProgressDialog;

    private FrameLayout mContainer;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mNavigationController = new NavigationController(this);
        mFragment = mNavigationController.navigateToLayoutAnimation();
        mContainer = ButterKnife.findById(this, R.id.container);

        initDrawerLayout();
    }


    private void initDrawerLayout() {
        // Create Navigation drawer and inflate layout
        mNavigationView = ButterKnife.findById(this, R.id.nav_view);
        mDrawerLayout = ButterKnife.findById(this, R.id.drawer_layout);

        // Adding menu icon to Toolbar
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.setHomeAsUpIndicator(R.drawable.ic_menu_black_24dp);
            supportActionBar.setDisplayHomeAsUpEnabled(true);
        }

        // Set behavior of Navigation drawer
        mNavigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    // This method will trigger on item Click of navigation menu
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        // Set item in checked state
                        menuItem.setChecked(true);
                        // Closing drawer on item click
                        mDrawerLayout.closeDrawers();
                        return onOptionsItemSelected(menuItem);
                    }
                });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.d("MainActivity", "onOptionsItemSelected: " + item.getTitle());

        switch (item.getItemId()) {
            case R.id.menu_layoutAnimation:
                mNavigationController.navigateToLayoutAnimation();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}
