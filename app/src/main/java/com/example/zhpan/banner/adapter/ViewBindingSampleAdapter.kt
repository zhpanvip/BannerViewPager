package com.example.zhpan.banner.adapter

import android.view.View
import android.view.ViewGroup
import com.example.zhpan.banner.R.layout
import com.example.zhpan.banner.databinding.ItemSlideModeBinding
import com.zhpan.bannerview.BaseBannerAdapter
import com.zhpan.bannerview.BaseViewHolder

/**
 * @author DBoy
 * @date 2020/12/11
 * Class 描述 : 使用ViewBinding示例
 */
class ViewBindingSampleAdapter(private val mRoundCorner: Int) : BaseBannerAdapter<Int>() {

  override fun createViewHolder(
    parent: ViewGroup,
    itemView: View,
    viewType: Int
  ): BaseViewHolder<Int> {
    return ViewBindingViewHolder(ItemSlideModeBinding.bind(itemView))
  }

  override fun bindData(holder: BaseViewHolder<Int>, data: Int, position: Int, pageSize: Int) {
    if (holder is ViewBindingViewHolder) {
      holder.viewBinding.bannerImage.setRoundCorner(mRoundCorner)
      holder.viewBinding.bannerImage.setImageResource(data)
    }
  }

  override fun getLayoutId(viewType: Int): Int {
    return layout.item_slide_mode
  }
}

internal class ViewBindingViewHolder(var viewBinding: ItemSlideModeBinding) :
  BaseViewHolder<Int>(viewBinding.root)

