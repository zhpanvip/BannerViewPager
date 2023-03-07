package com.example.zhpan.banner.adapter

import android.view.View
import android.view.ViewGroup
import com.zhpan.bannerview.BaseBannerAdapter
import com.example.zhpan.banner.net.BannerData
import androidx.databinding.DataBindingUtil
import com.example.zhpan.banner.R.layout
import com.example.zhpan.banner.databinding.ItemSlideModelDataBindingBinding
import com.zhpan.bannerview.BaseViewHolder
import java.lang.NullPointerException

/**
 * <pre>
 * Created by zhpan on 2020/4/5.
 * Description:使用DataBinding示例
</pre> *
 */
class DataBindingSampleAdapter : BaseBannerAdapter<BannerData?>() {

  override fun createViewHolder(
    parent: ViewGroup, itemView: View,
    viewType: Int
  ): BaseViewHolder<BannerData?> {
    val binding = DataBindingUtil.bind<ItemSlideModelDataBindingBinding>(itemView)
      ?: throw NullPointerException("binding is Null")
    return DataBindingViewHolder(binding)
  }

  override fun bindData(
    holder: BaseViewHolder<BannerData?>?,
    data: BannerData?,
    position: Int,
    pageSize: Int
  ) {
    if (holder is DataBindingViewHolder) {
      holder.binding.bannerData = data
    }
  }

  override fun getLayoutId(viewType: Int): Int {
    return layout.item_slide_model_data_binding
  }
}

internal class DataBindingViewHolder(var binding: ItemSlideModelDataBindingBinding) : BaseViewHolder<BannerData?>(binding.root)