package com.sctdroid.app.samples;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import com.sctdroid.app.samples.common.ExecutorThread;
import com.sctdroid.app.samples.common.FileUtils;
import com.sctdroid.app.samples.common.Result;
import com.sctdroid.app.samples.common.ViewRecorder;
import com.sctdroid.app.samples.modules.BaseFragment;
import com.sctdroid.app.samples.thirdParty.GifUtils;
import com.sctdroid.app.samples.thirdParty.GifflenClient;

import java.io.File;

import butterknife.ButterKnife;
import cn.dxjia.ffmpeg.library.FFmpegNativeHelper;

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

    private boolean mIsRecording = false;
    private ViewRecorder mRecorder;

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

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Adding menu icon to Toolbar
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.setHomeAsUpIndicator(R.drawable.ic_menu_black_24dp);
            supportActionBar.setDisplayHomeAsUpEnabled(true);
        }

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mDrawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerLayout.setDrawerListener(toggle);
        toggle.syncState();

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
            //noinspection SimplifiableIfStatement
            case R.id.action_settings: {
                break;
            }
            case R.id.action_record: {
                if (mIsRecording) {
                    mIsRecording = false;
                    item.setIcon(R.drawable.ic_play_arrow_black_24dp);
                    stopRecord();
                } else {
                    mIsRecording = true;
                    item.setIcon(R.drawable.ic_stop_black_24dp);
                    startRecord();
                }
            }
        }
        return super.onOptionsItemSelected(item);
    }

    private void stopRecord() {
        /*String input = Environment.getExternalStorageDirectory() + "/j%d.jpg";
        String output = Environment.getExternalStorageDirectory() + "/video.gif";
        GifUtils.GifByFFmpge(input, output, new Result() {
            @Override
            public void onResult(RESULT result) {
                Snackbar.make(mContainer, "" + result, Snackbar.LENGTH_LONG).show();
            }
        });*/
        mRecorder.stopRecord();
    }

    private void startRecord() {
        mRecorder = new ViewRecorder(mContainer, new GifflenClient(), 1, Environment.getExternalStorageDirectory() + "/record.gif", new ViewRecorder.Listener() {
            @Override
            public void onFrame(final Bitmap bitmap, final int currentFrame) {
            }

            @Override
            public void onMergeStart() {
                showLoading("正在合成");
            }

            @Override
            public void onMergeFinish(String path) {
                hideLoading();
                final Snackbar snackbar = Snackbar.make(mContainer, "已保存至: " + path, Snackbar.LENGTH_LONG);
                snackbar.setAction("OK", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        snackbar.dismiss();
                    }
                }).show();
            }
        });
        mRecorder.startRecord();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    public void showLoading(String msg) {
        if (mProgressDialog == null) {
            mProgressDialog = ProgressDialog.show(this, "", msg, true, false);
        } else if (!mProgressDialog.isShowing()){
            mProgressDialog.show();
        }
    }

    public void hideLoading() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.hide();
        }
    }
}
