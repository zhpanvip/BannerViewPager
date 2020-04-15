package com.example.zhpan.circleviewpager.adapter

import android.content.Context
import android.content.res.Resources
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.viewpager2.widget.ViewPager2

import com.example.zhpan.circleviewpager.R
import com.example.zhpan.circleviewpager.bean.ArticleWrapper
import com.example.zhpan.circleviewpager.bean.CustomBean
import com.example.zhpan.circleviewpager.viewholder.CustomPageViewHolder
import com.example.zhpan.circleviewpager.viewholder.ImageResourceViewHolder
import com.zhpan.bannerview.BannerViewPager
import com.zhpan.indicator.enums.IndicatorStyle

import java.util.ArrayList

class ArticleAdapter(context: Context, data: List<ArticleWrapper.Article>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val mList = ArrayList<ArticleWrapper.Article>()
    private val inflater: LayoutInflater

    init {
        this.mList.addAll(data)
        this.inflater = LayoutInflater.from(context)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, itemType: Int): RecyclerView.ViewHolder {
        if (itemType == 1001) {
            return BannerItemViewHolder(inflater.inflate(R.layout.item_home_banner, viewGroup, false))
        }
        return ArticleViewHolder(inflater.inflate(R.layout.item_article, viewGroup, false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, i: Int) {
        val article = mList[i]
        if (article.type == 1001 && holder is BannerItemViewHolder) {
            holder.bannerViewPager.setCanLoop(false)
                    .setIndicatorStyle(IndicatorStyle.ROUND_RECT)
                    .setIndicatorSliderGap(holder.resources.getDimensionPixelOffset(R.dimen.dp_4))
                    .setIndicatorSliderWidth(holder.resources.getDimensionPixelOffset(R.dimen.dp_4), holder.resources.getDimensionPixelOffset(R.dimen.dp_10))
                    .setIndicatorSliderColor(holder.resources.getColor(R.color.red_normal_color), holder.resources.getColor(R.color.red_checked_color))
                    .setOrientation(ViewPager2.ORIENTATION_VERTICAL)
                    .setInterval(2000)
                    .setAdapter(ImageResourceAdapter(0)).create(article.pagers);
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
        var bannerViewPager: BannerViewPager<Int, ImageResourceViewHolder> = itemView.findViewById(R.id.banner_view3)
        var resources: Resources = itemView.context.resources

    }

}
