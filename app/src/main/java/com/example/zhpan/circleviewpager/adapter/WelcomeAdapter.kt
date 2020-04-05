package com.example.zhpan.circleviewpager.adapter

import android.view.View
import com.example.zhpan.circleviewpager.R

import com.example.zhpan.circleviewpager.bean.CustomBean
import com.example.zhpan.circleviewpager.viewholder.CustomPageViewHolder
import com.zhpan.bannerview.base.BaseBannerAdapter

/**
 * <pre>
 * Created by zhpan on 2020/4/5.
 * Description:
</pre> *
 */
class WelcomeAdapter : BaseBannerAdapter<CustomBean, CustomPageViewHolder>() {

    override fun onBind(holder: CustomPageViewHolder, data: CustomBean, position: Int, pageSize: Int) {
        holder.onBind(data, position, pageSize)
    }

    override fun createViewHolder(itemView: View, viewType: Int): CustomPageViewHolder? {
        return CustomPageViewHolder(itemView)
    }

    override fun getLayoutId(viewType: Int): Int {
        return R.layout.item_custom_view
    }
}
