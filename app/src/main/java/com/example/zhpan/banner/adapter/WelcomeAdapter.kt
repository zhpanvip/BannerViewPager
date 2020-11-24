package com.example.zhpan.banner.adapter

import android.view.View
import android.view.ViewGroup
import com.example.zhpan.banner.R

import com.example.zhpan.banner.bean.CustomBean
import com.example.zhpan.banner.viewholder.CustomPageViewHolder
import com.zhpan.bannerview.BaseBannerAdapter

/**
 * <pre>
 * Created by zhpan on 2020/4/5.
 * Description:
</pre> *
 */
class WelcomeAdapter : BaseBannerAdapter<CustomBean, CustomPageViewHolder>() {

    var mOnSubViewClickListener: CustomPageViewHolder.OnSubViewClickListener? = null

    override fun onBind(holder: CustomPageViewHolder, data: CustomBean, position: Int, pageSize: Int) {
        holder.bindData(data, position, pageSize)
    }

    override fun createViewHolder(parent: ViewGroup, itemView: View, viewType: Int): CustomPageViewHolder? {
        val customPageViewHolder = CustomPageViewHolder(itemView)
        customPageViewHolder.setOnSubViewClickListener(mOnSubViewClickListener)
        return customPageViewHolder
    }

    override fun getLayoutId(viewType: Int): Int {
        return R.layout.item_custom_view
    }
}
