package com.example.zhpan.circleviewpager.activity


import android.os.Bundle
import android.view.View

import com.example.zhpan.circleviewpager.R
import com.example.zhpan.circleviewpager.viewholder.PhotoViewHolder
import com.zhpan.bannerview.BannerViewPager
import com.zhpan.bannerview.BaseBannerAdapter

class PhotoViewActivity : BaseDataActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_banner_photo_view)
        setTitle(R.string.wrapper_photo_view)
        initViewPager()
    }

    private fun initViewPager() {
        val bannerViewPager = findViewById<BannerViewPager<Int, PhotoViewHolder>>(R.id.viewpager)
        bannerViewPager.apply {
            adapter = object : BaseBannerAdapter<Int, PhotoViewHolder>() {
                override fun onBind(holder: PhotoViewHolder?, data: Int?, position: Int, pageSize: Int) {
                    holder?.bindData(data, position, pageSize);
                }

                override fun createViewHolder(itemView: View?, viewType: Int): PhotoViewHolder {
                    return PhotoViewHolder(itemView!!)
                }

                override fun getLayoutId(viewType: Int): Int {
                    return R.layout.item_slide_mode;
                }

            }
            setCanLoop(false)
        }.create(mDrawableList)

        bannerViewPager.setCurrentItem(1, false)
    }
}
