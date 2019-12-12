package com.example.zhpan.circleviewpager.activity


import android.os.Bundle

import com.example.zhpan.circleviewpager.R
import com.example.zhpan.circleviewpager.viewholder.PhotoViewHolder
import com.zhpan.bannerview.BannerViewPager
import com.zhpan.bannerview.constants.IndicatorSlideMode
import com.zhpan.bannerview.holder.HolderCreator

class PhotoViewActivity : BaseDataActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_banner_photo_view)
        setTitle(R.string.wrapper_photo_view)
        initViewPager()
    }

    private fun initViewPager() {
        val bannerViewPager = findViewById<BannerViewPager<Int, PhotoViewHolder>>(R.id.viewpager)
        bannerViewPager.setAutoPlay(false)
                .setCanLoop(false)
                .setHolderCreator { PhotoViewHolder() }
                .create(mDrawableList)
        bannerViewPager.currentItem = 1
    }
}
