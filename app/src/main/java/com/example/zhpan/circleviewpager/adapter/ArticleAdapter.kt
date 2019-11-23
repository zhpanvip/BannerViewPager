package com.example.zhpan.circleviewpager.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

import com.example.zhpan.circleviewpager.R
import com.example.zhpan.circleviewpager.bean.ArticleWrapper

import java.util.ArrayList

class ArticleAdapter(context: Context, data: List<ArticleWrapper.Article>) : RecyclerView.Adapter<ArticleAdapter.ArticleViewHolder>() {
    private val mList = ArrayList<ArticleWrapper.Article>()
    private val inflater: LayoutInflater

    init {
        this.mList.addAll(data)
        this.inflater = LayoutInflater.from(context)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ArticleViewHolder {
        return ArticleViewHolder(inflater.inflate(R.layout.item_article, viewGroup, false))
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, i: Int) {
        val article = mList[i]
        holder.tvAuthor.text = article.author
        holder.tvTitle.text = article.title
    }

    fun setData(list: List<ArticleWrapper.Article>) {
        mList.clear()
        mList.addAll(list)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    inner class ArticleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        internal var tvTitle: TextView = itemView.findViewById(R.id.tv_title)
        internal var tvAuthor: TextView = itemView.findViewById(R.id.tv_auther)

    }

}
