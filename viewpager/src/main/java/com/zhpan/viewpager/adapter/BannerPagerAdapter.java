package com.zhpan.viewpager.adapter;

import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import com.zhpan.viewpager.holder.HolderCreator;
import com.zhpan.viewpager.holder.ViewHolder;
import com.zhpan.viewpager.view.BannerViewPager;

import java.util.List;

/**
 * Created by zhpan on 2017/3/28.
 */

public class BannerPagerAdapter<T, VH extends ViewHolder> extends PagerAdapter {
    private List<T> list;
    private BannerViewPager viewPager;
    private HolderCreator holderCreator;
    private boolean isCanLoop;

    public void setCanLoop(boolean canLoop) {
        isCanLoop = canLoop;
    }

    public BannerPagerAdapter(List<T> list, BannerViewPager viewPager, HolderCreator<VH> holderCreator) {
        this.list = list;
        this.viewPager = viewPager;
        this.holderCreator = holderCreator;
    }

    @Override
    public int getCount() {
        if (isCanLoop && list.size() > 1) {
            return list.size() + 2;
        } else {
            return list.size();
        }

    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(@NonNull final ViewGroup container, final int position) {
        View view = getView(position, container);
        container.addView(view);
        return view;
    }


    //  根据图片URL创建对应的ImageView并添加到集合
    private View getView(final int position, ViewGroup container) {
        ViewHolder<T> holder = holderCreator.createViewHolder();
        if (holder == null) {
            throw new RuntimeException("can not return a null holder");
        }
        View view = null;
        if (list != null && list.size() > 0) {
            if (isCanLoop && list.size() > 1) {
                int size = list.size();
                if (position == 0) {
                    view = holder.createView(container, container.getContext(), list.size() - 1);
                    holder.onBind(container.getContext(), list.get(list.size() - 1), list.size() - 1, size);
                } else if (position == list.size() + 1) {
                    view = holder.createView(container, container.getContext(), 0);
                    holder.onBind(container.getContext(), list.get(0), 0, size);
                } else {
                    view = holder.createView(container, container.getContext(), position - 1);
                    holder.onBind(container.getContext(), list.get(position - 1), position - 1, size);
                }
            } else {
                view = holder.createView(container, container.getContext(), position);
                holder.onBind(container.getContext(), list.get(position), position, list.size());
            }
        }

        if (view != null)
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    viewPager.imageClick(position);
                }
            });
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public void finishUpdate(@NonNull ViewGroup container) {
        super.finishUpdate(container);

    }
}
