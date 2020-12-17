package com.example.zhpan.banner.adapter

import android.animation.ObjectAnimator
import android.view.View
import android.widget.ImageView
import com.example.zhpan.banner.R
import com.example.zhpan.banner.bean.CustomBean
import com.zhpan.bannerview.BaseBannerAdapter
import com.zhpan.bannerview.BaseViewHolder

/**
 * <pre>
 * Created by zhpan on 2020/4/5.
 * Description:
 * </pre>
 */
class SimpleAdapter : BaseBannerAdapter<CustomBean>() {

    var mOnSubViewClickListener: OnSubViewClickListener? = null

    override fun bindData(holder: BaseViewHolder<CustomBean>, data: CustomBean?, position: Int, pageSize: Int) {
        val imageStart: ImageView = holder.findViewById(R.id.iv_logo)
        holder.setImageResource(R.id.banner_image, data!!.imageRes)
        holder.setOnClickListener(R.id.iv_logo) { view: View? ->
            if (null != mOnSubViewClickListener) mOnSubViewClickListener!!.onViewClick(view, holder.adapterPosition)
        }
        val alphaAnimator = ObjectAnimator.ofFloat(imageStart, "alpha", 0f, 1f)
        alphaAnimator.duration = 1500
        alphaAnimator.start()
    }

    override fun getLayoutId(viewType: Int): Int {
        return R.layout.item_custom_view;
    }

    interface OnSubViewClickListener {
        fun onViewClick(view: View?, position: Int)
    }
}
