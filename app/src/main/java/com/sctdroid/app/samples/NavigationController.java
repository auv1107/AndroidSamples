package com.sctdroid.app.samples;

import android.support.annotation.IdRes;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.sctdroid.app.samples.modules.BaseFragment;
import com.sctdroid.app.samples.modules.gallery.GalleryFragment;
import com.sctdroid.app.samples.modules.layoutAnimation.LayoutAnimationFragment;
import com.sctdroid.app.samples.modules.sectionlist.SectionListFragment;


public class NavigationController {
    private final int containerId;
    private final FragmentManager fragmentManager;

    public NavigationController(AppCompatActivity activity, @IdRes int containerId) {
        this.containerId = containerId;
        this.fragmentManager = activity.getSupportFragmentManager();
    }

    public BaseFragment navigateToLayoutAnimation() {
        LayoutAnimationFragment fragment = new LayoutAnimationFragment();
        fragmentManager.beginTransaction()
                .replace(containerId, fragment)
                .commitAllowingStateLoss();

        return fragment;
    }

    public BaseFragment navigateToGallery() {
        GalleryFragment fragment = new GalleryFragment();
        fragmentManager.beginTransaction()
                .replace(containerId, fragment)
                .commitAllowingStateLoss();

        return fragment;
    }
    public BaseFragment navigateToSectionList() {
        SectionListFragment fragment = new SectionListFragment();
        fragmentManager.beginTransaction()
                .replace(containerId, fragment)
                .commitAllowingStateLoss();

        return fragment;
    }

}