package com.example.zhpan.circleviewpager.adapter;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.zhpan.circleviewpager.R;
import com.example.zhpan.circleviewpager.bean.ArticleWrapper;

import java.util.ArrayList;
import java.util.List;

public class ArticleAdapter extends RecyclerView.Adapter<ArticleAdapter.ArticleViewHolder> {
    private List<ArticleWrapper.Article> mList = new ArrayList<>();
    private LayoutInflater inflater;

    public ArticleAdapter(Context context, List<ArticleWrapper.Article> data) {
        this.mList.addAll(data);
        this.inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ArticleViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ArticleViewHolder(inflater.inflate(R.layout.item_article, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ArticleViewHolder holder, int i) {
        ArticleWrapper.Article article = mList.get(i);
        holder.tvAuthor.setText(article.getAuthor());
        holder.tvTitle.setText(article.getTitle());
    }

    public void setData(List<ArticleWrapper.Article> list) {
        mList.clear();
        mList.addAll(list);
        notifyDataSetChanged();
    }

    public void addData(List<ArticleWrapper.Article> list) {
        mList.addAll(list);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class ArticleViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle;
        TextView tvAuthor;

        public ArticleViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tv_title);
            tvAuthor = itemView.findViewById(R.id.tv_auther);
        }
    }

}
