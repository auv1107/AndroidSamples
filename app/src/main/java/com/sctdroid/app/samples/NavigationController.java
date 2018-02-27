package com.sctdroid.app.samples;

import android.support.v4.app.FragmentManager;

import com.sctdroid.app.samples.modules.BaseFragment;
import com.sctdroid.app.samples.modules.layoutAnimation.LayoutAnimationFragment;


public class NavigationController {
    private final int containerId;
    private final FragmentManager fragmentManager;

    public NavigationController(MainActivity mainActivity) {
        this.containerId = R.id.container;
        this.fragmentManager = mainActivity.getSupportFragmentManager();
    }

    public BaseFragment navigateToLayoutAnimation() {
        LayoutAnimationFragment fragment = new LayoutAnimationFragment();
        fragmentManager.beginTransaction()
                .replace(containerId, fragment)
                .commitAllowingStateLoss();

        return fragment;
    }

}