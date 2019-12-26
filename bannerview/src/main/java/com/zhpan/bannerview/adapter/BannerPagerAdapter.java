package com.zhpan.bannerview.adapter;


import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zhpan.bannerview.holder.HolderCreator;
import com.zhpan.bannerview.holder.ViewHolder;
import com.zhpan.bannerview.utils.BannerUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhpan on 2017/3/28.
 */

public class BannerPagerAdapter<T, VH extends ViewHolder> extends PagerAdapter {

    private List<T> mList;

    private HolderCreator holderCreator;

    private boolean isCanLoop;

    private PageClickListener mPageClickListener;

//    private List<View> mViewList = new ArrayList<>();

    public static final int MAX_VALUE = 10000;

    public BannerPagerAdapter(List<T> list, HolderCreator<VH> holderCreator) {
        mList = new ArrayList<>();
        mList.addAll(list);
        this.holderCreator = holderCreator;
    }

    @Override
    public int getCount() {
        if (isCanLoop && mList.size() > 1) {
            return MAX_VALUE;
        } else {
            return mList.size();
        }
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @Override
    public @NonNull
    Object instantiateItem(@NonNull final ViewGroup container, final int position) {
        View itemView = getView(container, BannerUtils.getRealPosition(isCanLoop, position, mList.size()));
        container.addView(itemView);
        return itemView;
    }

//    @Override
//    public int getItemPosition(@NonNull Object object) {
//        return POSITION_NONE;
//    }
    //  没必要缓存
//    private View findViewByPosition(ViewGroup container, int position) {
//        for (View view : mViewList) {
//            if (((int) view.getTag()) == position && view.getParent() == null) {
//                return view;
//            }
//        }
//        View view = getView(container, position);
//        view.setTag(position);
//        mViewList.add(view);
//        return view;
//    }

    @SuppressWarnings("unchecked")
    private View getView(ViewGroup container, final int position) {
        ViewHolder<T> holder = holderCreator.createViewHolder();
        if (holder == null) {
            throw new NullPointerException("Can not return a null holder");
        }
        return createView(holder, position, container);
    }

    private View createView(ViewHolder<T> holder, int position, ViewGroup container) {
        View itemView = LayoutInflater.from(container.getContext()).inflate(holder.getLayoutId(), container, false);
        if (mList != null && mList.size() > 0) {
//            holder.createView(itemView, position);
            holder.onBind(itemView, mList.get(position), position, mList.size());
            setViewListener(itemView, position);
        }
        return itemView;
    }

    private void setViewListener(View view, final int position) {
        if (view != null)
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (null != mPageClickListener)
                        mPageClickListener.onPageClick(position);
                }
            });
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    @Override
    public void finishUpdate(@NonNull ViewGroup container) {
        super.finishUpdate(container);
    }

    public void setPageClickListener(PageClickListener pageClickListener) {
        mPageClickListener = pageClickListener;
    }

    public void setCanLoop(boolean canLoop) {
        isCanLoop = canLoop;
    }


    public interface PageClickListener {
        void onPageClick(int position);
    }

//    public void setList(List<T> list) {
//        mList.clear();
//        notifyDataSetChanged();
//        mList.addAll(list);
//        notifyDataSetChanged();
//        mViewList.clear();
//    }

    public List<T> getList() {
        return mList;
    }

    public int getListSize() {
        return mList.size();
    }
}
