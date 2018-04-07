package com.sctdroid.app.samples.modules.gallery

import android.os.Bundle
import android.support.v4.view.PagerAdapter
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import butterknife.ButterKnife
import com.bumptech.glide.Glide
import com.sctdroid.app.samples.R
import com.sctdroid.app.samples.business.AppDatabase
import com.sctdroid.app.samples.common.ExecutorThread
import com.sctdroid.app.samples.data.entity.Gif
import com.sctdroid.app.samples.modules.BaseFragment
import com.sctdroid.app.samples.widget.ShapedImageView
import kotlinx.android.synthetic.main.fragment_gallery.*
import kotlinx.android.synthetic.main.gallery_tab_all.view.*
import java.util.*

/**
 * Created by lixindong on 2018/3/4.
 */

class GalleryFragment : BaseFragment() {

    private var mTabPagerAdapter: TabPagerAdapter? = null

    private var mAdapter: ContentAdapter? = null

    override fun getLayoutId(): Int {
        return R.layout.fragment_gallery
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(layoutId, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onViewBound()
    }

    override fun onViewBound() {
        mTabPagerAdapter = TabPagerAdapter()
        viewPager.adapter = mTabPagerAdapter

        tabLayout.setupWithViewPager(viewPager)
    }

    internal inner class TabPagerAdapter : PagerAdapter() {
        override fun getCount(): Int {
            return 2
        }

        override fun isViewFromObject(view: View, `object`: Any): Boolean {
            return view === `object`
        }

        override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
            container.removeView(`object` as View)
        }

        override fun instantiateItem(container: ViewGroup, position: Int): Any {
            val view: View
            if (position == 0) {
                view = LayoutInflater.from(context).inflate(R.layout.gallery_tab_all, container, false)
                initTabAll(view)
            } else {
                view = LayoutInflater.from(context).inflate(R.layout.gallery_tab_group, container, false)
            }
            container.addView(view)
            return view
        }

        override fun getPageTitle(position: Int): CharSequence {
            return resources.getString(if (position == 0) R.string.tab_all else R.string.tab_group)
        }
    }

    private fun initTabAll(view: View) {
        val recyclerView = view.recyclerView
        mAdapter = ContentAdapter()
        recyclerView.adapter = mAdapter
        recyclerView.layoutManager = GridLayoutManager(context, 2)
        recyclerView.itemAnimator = DefaultItemAnimator()
        loadAllGifs()
    }

    private fun loadAllGifs() {
        ExecutorThread.getInstance().execute {
            val list = AppDatabase.getAllGifs()
            notifyGifUpdated(list)
        }
    }

    private fun notifyGifUpdated(gifs: List<Gif>) {
        activity.runOnUiThread { mAdapter!!.updateData(gifs) }
    }

    internal class ViewHolder(inflater: LayoutInflater, parent: ViewGroup) : RecyclerView.ViewHolder(inflater.inflate(R.layout.item_picture, parent, false)) {

        private val mImageView: ShapedImageView

        init {
            ButterKnife.bind(itemView)

            mImageView = ButterKnife.findById(itemView, R.id.imageView)
        }

        fun bind(gif: Gif) {
            Glide.with(itemView.context)
                    .load(gif.url)
                    .into(mImageView)
        }
    }

    internal class ContentAdapter : RecyclerView.Adapter<ViewHolder>() {

        private val mData = ArrayList<Gif>()

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            return ViewHolder(LayoutInflater.from(parent.context), parent)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.bind(getItem(position))
        }

        override fun getItemCount(): Int {
            return mData.size
        }

        fun getItem(position: Int): Gif {
            return mData[position]
        }

        fun updateData(data: List<Gif>?) {
            mData.clear()
            if (data != null) {
                mData.addAll(data)
            }
            notifyDataSetChanged()
        }
    }
}
