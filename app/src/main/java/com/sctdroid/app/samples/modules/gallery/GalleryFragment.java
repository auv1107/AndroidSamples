package com.sctdroid.app.samples.modules.gallery;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.sctdroid.app.samples.R;
import com.sctdroid.app.samples.business.AppDatabase;
import com.sctdroid.app.samples.common.ExecutorThread;
import com.sctdroid.app.samples.data.entity.Gif;
import com.sctdroid.app.samples.modules.BaseFragment;
import com.sctdroid.app.samples.modules.layoutAnimation.LayoutAnimationFragment;
import com.sctdroid.app.samples.widget.ShapedImageView;
import com.sctdroid.app.samples.widget.TabLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by lixindong on 2018/3/4.
 */

public class GalleryFragment extends BaseFragment {

    @BindView(R.id.tabLayout)
    TabLayout mTabLayout;

    @BindView(R.id.viewPager)
    ViewPager mViewPager;

    private TabPagerAdapter mTabPagerAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_gallery;
    }

    @Override
    protected void onViewBound() {
        mTabPagerAdapter = new TabPagerAdapter();
        mViewPager.setAdapter(mTabPagerAdapter);

        mTabLayout.setupWithViewPager(mViewPager);
    }

    class TabPagerAdapter extends PagerAdapter {
        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View view;
            if (position == 0) {
                view = LayoutInflater.from(getContext()).inflate(R.layout.gallery_tab_all, container, false);
                initTabAll(view);
            } else {
                view = LayoutInflater.from(getContext()).inflate(R.layout.gallery_tab_group, container, false);
            }
            container.addView(view);
            return view;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return getResources().getString(position == 0 ? R.string.tab_all : R.string.tab_group);
        }
    }

    private RecyclerView mRecyclerView;
    private ContentAdapter mAdapter;

    private void initTabAll(View view) {
        mRecyclerView = ButterKnife.findById(view, R.id.recyclerView);
        mAdapter = new ContentAdapter();
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        loadAllGifs();
    }

    private void loadAllGifs() {
        ExecutorThread.getInstance().execute(new Runnable() {
            @Override
            public void run() {
                final List<Gif> list = AppDatabase.getAllGifs();
                notifyGifUpdated(list);
            }
        });
    }

    private void notifyGifUpdated(final List<Gif> gifs) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mAdapter.updateData(gifs);
            }
        });
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        private ShapedImageView mImageView;

        public ViewHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.item_picture, parent, false));
            ButterKnife.bind(itemView);

            mImageView = ButterKnife.findById(itemView, R.id.imageView);
        }

        public void bind(Gif gif) {
            Glide.with(itemView.getContext())
                    .load(gif.url)
                    .into(mImageView);
        }
    }

    static class ContentAdapter extends RecyclerView.Adapter<ViewHolder> {

        private final List<Gif> mData = new ArrayList<>();

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(parent.getContext()), parent);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            holder.bind(getItem(position));
        }

        @Override
        public int getItemCount() {
            return mData.size();
        }

        public Gif getItem(int position) {
            return mData.get(position);
        }

        public void updateData(List<Gif> data) {
            mData.clear();
            if (data != null) {
                mData.addAll(data);
            }
            notifyDataSetChanged();
        }
    }
}
