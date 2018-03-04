package com.sctdroid.app.samples.modules.gallery;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.sctdroid.app.samples.NavigationController;
import com.sctdroid.app.samples.R;
import com.sctdroid.app.samples.modules.BaseFragment;

/**
 * Created by lixindong on 2018/3/4.
 */

public class GalleryActivity extends AppCompatActivity {

    private NavigationController mNavigationController;
    private BaseFragment mFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_gallery);

        mNavigationController = new NavigationController(this, R.id.container);
        mFragment = mNavigationController.navigateToGallery();

    }

    public static void start(Context context) {
        Intent intent = new Intent(context, GalleryActivity.class);
        context.startActivity(intent);
    }
}
