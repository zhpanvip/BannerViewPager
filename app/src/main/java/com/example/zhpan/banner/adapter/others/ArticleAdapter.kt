package com.example.zhpan.banner.adapter.others

import android.content.Context
import android.content.res.Resources
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.viewpager2.widget.ViewPager2

import com.example.zhpan.banner.R
import com.example.zhpan.banner.adapter.DataBindingSampleAdapter
import com.example.zhpan.banner.bean.ArticleWrapper
import com.example.zhpan.banner.net.BannerData
import com.zhpan.bannerview.BannerViewPager
import com.zhpan.bannerview.BaseViewHolder
import com.zhpan.bannerview.constants.IndicatorGravity
import com.zhpan.indicator.enums.IndicatorStyle

import java.util.ArrayList

/**
 * HomeFragment RecyclerView Adapter
 */
class ArticleAdapter(
  val context: Context,
  data: List<ArticleWrapper.Article>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
  private val mList = ArrayList<ArticleWrapper.Article>()
  private val inflater: LayoutInflater

  init {
    this.mList.addAll(data)
    this.inflater = LayoutInflater.from(context)
  }

  override fun onCreateViewHolder(
    viewGroup: ViewGroup,
    itemType: Int
  ): RecyclerView.ViewHolder {
    if (itemType == 1001) {
      return BannerItemViewHolder(inflater.inflate(R.layout.item_home_banner, viewGroup, false))
    }
    return ArticleViewHolder(inflater.inflate(R.layout.item_article, viewGroup, false))
  }

  override fun onBindViewHolder(
    holder: RecyclerView.ViewHolder,
    i: Int
  ) {
    val article = mList[i]
    if (article.type == 1001 && holder is BannerItemViewHolder) {
      holder.bannerViewPager
        .refreshData(article.bannerData)
    } else if (holder is ArticleViewHolder) {
      holder.tvAuthor.text = article.author
      holder.tvTitle.text = article.title
    }
  }

  fun setData(list: List<ArticleWrapper.Article>) {
    mList.clear()
    mList.addAll(list)
    notifyDataSetChanged()
  }

  override fun getItemViewType(position: Int): Int {
    return mList[position].type
  }

  override fun getItemCount(): Int {
    return mList.size
  }

  inner class ArticleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    internal var tvTitle: TextView = itemView.findViewById(R.id.tv_title)
    internal var tvAuthor: TextView = itemView.findViewById(R.id.tv_auther)
  }

  inner class BannerItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var bannerViewPager: BannerViewPager<BannerData> = itemView.findViewById(R.id.banner_view3)
    var resources: Resources = itemView.context.resources

    init {
      if (context is AppCompatActivity)
        bannerViewPager.setCanLoop(false)
          .setOrientation(ViewPager2.ORIENTATION_VERTICAL)
          .setIndicatorGravity(IndicatorGravity.END)
          .setInterval(2000)
          .setAdapter(DataBindingSampleAdapter())
          .registerLifecycleObserver(context.lifecycle)
          .create()
    }
  }
}
