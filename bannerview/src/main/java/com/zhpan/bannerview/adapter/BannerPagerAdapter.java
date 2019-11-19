package com.zhpan.bannerview.adapter;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import android.view.View;
import android.view.ViewGroup;

import com.zhpan.bannerview.holder.HolderCreator;
import com.zhpan.bannerview.holder.ViewHolder;
import com.zhpan.bannerview.utils.PositionUtils;

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

    private List<View> mViewList = new ArrayList<>();

    public static final int MAX_VALUE = Integer.MAX_VALUE;

    public BannerPagerAdapter(List<T> list, HolderCreator<VH> holderCreator) {
        this.mList = list;
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
        View itemView = findViewByPosition(container, PositionUtils.getRealPosition(isCanLoop, position, mList.size()));
        container.addView(itemView);
        return itemView;
    }

    private View findViewByPosition(ViewGroup container, int position) {
        for (View view : mViewList) {
            if (((int) view.getTag()) == position && view.getParent() == null) {
                return view;
            }
        }
        View view = getView(position, container);
        view.setTag(position);
        mViewList.add(view);
        return view;
    }

    @SuppressWarnings("unchecked")
    private View getView(final int position, ViewGroup container) {
        ViewHolder<T> holder = holderCreator.createViewHolder();
        if (holder == null) {
            throw new NullPointerException("can not return a null holder");
        }
        return createView(holder, position, container);
    }

    private View createView(ViewHolder<T> holder, int position, ViewGroup container) {
        View view = null;
        if (mList != null && mList.size() > 0) {
            view = holder.createView(container, container.getContext(), position);
            holder.onBind(container.getContext(), mList.get(position), position, mList.size());
            setViewListener(view, position);
        }
        return view;
    }

    private void setViewListener(View view, int position) {
        if (view != null)
            view.setOnClickListener(v -> {
                if (null != mPageClickListener)
                    mPageClickListener.onPageClick(position);
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
}
