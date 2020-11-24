package com.example.zhpan.banner.viewholder

import android.view.View
import android.widget.Toast

import com.example.zhpan.banner.R
import com.zhpan.bannerview.BaseViewHolder

/**
 * Created by zhpan on 2017/10/30.
 * Description:
 */

class PhotoViewHolder(itemView: View) : BaseViewHolder<Int>(itemView) {

    override fun bindData(data: Int?, position: Int, size: Int) {
        setImageResource(R.id.banner_image, data!!)
        setOnClickListener(R.id.banner_image) {
            Toast.makeText(itemView.context, "$adapterPosition  页面数$size", Toast.LENGTH_SHORT).show()
        }
    }
}
