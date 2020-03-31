package com.zhpan.bannerview.adapter;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.zhpan.bannerview.holder.HolderCreator2;
import com.zhpan.bannerview.utils.BannerUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * <pre>
 *   Created by zhangpan on 2020/3/31.
 *   Description:
 * </pre>
 */
public class BaseBannerAdapter<T, VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH> {
    private List<T> mList = new ArrayList<>();
    private HolderCreator2<VH> mHolderCreator;
    private boolean isCanLoop;
    public static final int MAX_VALUE = 500;
    private BannerPagerAdapter.PageClickListener mPageClickListener;

    public BaseBannerAdapter(List<T> list, HolderCreator2<VH> holderCreator) {
        mList.addAll(list);
        this.mHolderCreator = holderCreator;
    }


    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return mHolderCreator.createViewHolder();
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, final int position) {

        mHolderCreator.bindViewHolder(holder, BannerUtils.getRealPosition(isCanLoop, position, mList.size()));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mPageClickListener != null) {
                    mPageClickListener.onPageClick(BannerUtils.getRealPosition(isCanLoop, position, mList.size()));
                }
            }
        });
    }


    @Override
    public int getItemCount() {
        if (isCanLoop && mList.size() > 1) {
            return MAX_VALUE;
        } else {
            return mList.size();
        }
    }

    public List<T> getList() {
        return mList;
    }

    public void setList(List<T> list) {
        mList = list;
    }

    public void setCanLoop(boolean canLoop) {
        isCanLoop = canLoop;
    }

    public void setPageClickListener(BannerPagerAdapter.PageClickListener pageClickListener) {
        mPageClickListener = pageClickListener;
    }

    public int getListSize() {
        return mList.size();
    }
}
