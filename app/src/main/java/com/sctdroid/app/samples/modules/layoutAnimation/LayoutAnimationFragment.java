package com.sctdroid.app.samples.modules.layoutAnimation;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.sctdroid.app.samples.R;
import com.sctdroid.app.samples.annotations.FragmentMenuItem;
import com.sctdroid.app.samples.modules.BaseFragment;

import butterknife.BindView;

/**
 * Created by lixindong on 2018/2/26.
 */

@FragmentMenuItem(title = "布局动画", iconRes = R.drawable.ic_layers_black_24dp, checked = true, id = 1, groupId = 0)
public class LayoutAnimationFragment extends BaseFragment {

    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;

    ContentAdapter mAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_layout_animation;
    }

    @Override
    protected void onViewBound() {
        mAdapter = new ContentAdapter();
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.item_block, parent, false));
        }
    }

    static class ContentAdapter extends RecyclerView.Adapter<ViewHolder> {
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(parent.getContext()), parent);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {

        }

        @Override
        public int getItemCount() {
            return 40;
        }
    }
}
