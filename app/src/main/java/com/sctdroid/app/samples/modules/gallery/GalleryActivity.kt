package com.sctdroid.app.samples.modules.gallery

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity

import com.sctdroid.app.samples.NavigationController
import com.sctdroid.app.samples.R
import com.sctdroid.app.samples.modules.BaseFragment

/**
 * Created by lixindong on 2018/3/4.
 */

class GalleryActivity : AppCompatActivity() {

    private var mNavigationController: NavigationController? = null
    private var mFragment: BaseFragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_gallery)

        mNavigationController = NavigationController(this, R.id.container)
        mFragment = mNavigationController!!.navigateToGallery()

    }

    companion object {

        fun start(context: Context) {
            val intent = Intent(context, GalleryActivity::class.java)
            context.startActivity(intent)
        }
    }
}
