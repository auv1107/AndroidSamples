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
import com.sctdroid.app.samples.common.LinkedSection
import com.sctdroid.app.samples.common.LinkedSection.SectionIncludeEntrySet
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
        val spanCount = 4
        val glm = GridLayoutManager(context, spanCount)
        glm.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                if (recyclerView.adapter is ContentAdapter) {
                    val entry = (recyclerView.adapter as ContentAdapter).getItem(position)
                    if (entry.isHeader) return spanCount
                }
                return 1
            }
        }
        recyclerView.layoutManager = glm
        recyclerView.itemAnimator = DefaultItemAnimator()
        recyclerView.addItemDecoration(object :GridDecoration(context, 8) {
            override fun getItemSidesIsHaveOffsets(itemPosition: Int): BooleanArray {
                val booleans = booleanArrayOf(true, true, true, true)
                val positionWithoutHeader = itemPosition - (itemPosition / 6 + 1)
                if (itemPosition % 6 != 0) {
                    when {
                        positionWithoutHeader % 4 == 0 -> booleans[0] = false
                        positionWithoutHeader < 4 -> booleans[1] = false
                        positionWithoutHeader % 4 == 3 -> booleans[2] = false
                    }
                }
                return booleans
            }
        })
        loadAllGifs()
    }

    private fun loadAllGifs() {
        ExecutorThread.getInstance().execute {
            val list = AppDatabase.getAllGifs()
            notifyGifUpdated(list)
        }
    }

    private fun notifyGifUpdated(gifs: List<Gif>) {
        activity.runOnUiThread { mAdapter!!.updateData(buildLinkedSection(gifs)) }
    }

    private fun buildLinkedSection(gifs: List<Gif>): LinkedSection<String, Gif> {
        val table = LinkedSection<String, Gif>()
        for (gif in gifs) {
            table.add("Section1", gif)
        }
        for (gif in gifs) {
            table.add("Section2", gif)
        }
        for (gif in gifs) {
            table.add("Section3", gif)
        }
        for (gif in gifs) {
            table.add("Section4", gif)
        }
        return table
    }

    open class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
    }

    internal class ItemViewHolder(inflater: LayoutInflater, parent: ViewGroup) : ViewHolder(inflater.inflate(R.layout.item_picture, parent, false)) {

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
    internal class HeaderViewHolder(inflater: LayoutInflater, parent: ViewGroup) : ViewHolder(inflater.inflate(R.layout.item_header, parent, false)) {

        init {
        }
    }

    internal class ContentAdapter : RecyclerView.Adapter<ViewHolder>() {

        private var mData = LinkedSection<String, Gif>()
        val VIEW_TYPE_HEADER = 0
        val VIEW_TYPE_ITEM = 1

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            when (viewType) {
                VIEW_TYPE_ITEM -> return ItemViewHolder(LayoutInflater.from(parent.context), parent)
                VIEW_TYPE_HEADER -> return HeaderViewHolder(LayoutInflater.from(parent.context), parent)
            }
            return ItemViewHolder(LayoutInflater.from(parent.context), parent)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            if (holder is ItemViewHolder) {
                holder.bind(getItem(position).getValue())
            }
        }

        override fun getItemViewType(position: Int): Int {
            return if (getItem(position).isHeader) VIEW_TYPE_HEADER
            else VIEW_TYPE_ITEM
        }

        override fun getItemCount(): Int {
            return mData.entrySet().size
        }

        fun getItem(position: Int): LinkedSection.LinkedSectionEntry {
            return mData.entrySet().get(position)
        }

        fun updateData(data: LinkedSection<String, Gif>) {
            mData = data
            notifyDataSetChanged()
        }
    }
}
