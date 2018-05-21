package com.sctdroid.app.samples.business;

import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.view.Menu;
import android.view.MenuItem;

import com.sctdroid.app.samples.annotations.FragmentMenuItem;
import com.sctdroid.app.samples.annotations.Menus;

/**
 * Created by lixindong2 on 5/21/18.
 */

public class NavigationMenuHandler {
    private NavigationView mNavigationView;
    private Class mClazz;
    private OnItemClickListener mMenuItemClickListener;

    public NavigationMenuHandler(NavigationView navigationView, Class clz, OnItemClickListener listener) {
        mNavigationView = navigationView;
        mClazz = clz;
        mMenuItemClickListener = listener;
    }

    public void handle() {
        Menus annotation = (Menus) mClazz.getAnnotation(Menus.class);
        Class[] fragments = annotation.fragments();
        Menu menu = mNavigationView.getMenu();
        for (int i = 0; i < fragments.length; i++) {
            Class cls = fragments[i];
            FragmentMenuItem fragmentMenuItem = (FragmentMenuItem) cls.getAnnotation(FragmentMenuItem.class);
            int id = fragmentMenuItem.id();
            menu.add(fragmentMenuItem.groupId(), id, Menu.NONE, fragmentMenuItem.title());
            menu.setGroupCheckable(fragmentMenuItem.groupId(), true, true);
            menu.findItem(id).setShowAsAction(MenuItem.SHOW_AS_ACTION_NEVER);
            menu.findItem(id).setIcon(fragmentMenuItem.iconRes());
            menu.findItem(id).setChecked(fragmentMenuItem.checked());
            menu.findItem(id).setOnMenuItemClickListener(item -> {
                try {
                    mMenuItemClickListener.onItemClicked(item, (Fragment) cls.newInstance());
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
                return true;
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClicked(MenuItem item, Fragment fragment);
    }
}
