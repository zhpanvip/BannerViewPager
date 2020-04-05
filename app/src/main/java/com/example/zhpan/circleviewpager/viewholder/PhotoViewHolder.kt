package com.example.zhpan.circleviewpager.viewholder

import android.view.View
import android.widget.Toast

import com.example.zhpan.circleviewpager.R
import com.github.chrisbanes.photoview.PhotoView
import com.zhpan.bannerview.holder.BaseViewHolder

/**
 * Created by zhpan on 2017/10/30.
 * Description:
 */

class PhotoViewHolder(itemView: View) : BaseViewHolder<Int>(itemView) {
    override fun bind(data: Int?, position: Int, size: Int) {
        val imageView = itemView.findViewById<PhotoView>(R.id.banner_image)
        imageView.setImageResource(data!!)
        imageView.setOnClickListener { v ->
            Toast.makeText(itemView.context, "$adapterPosition  页面数$size", Toast.LENGTH_SHORT).show()
        }
    }
}
