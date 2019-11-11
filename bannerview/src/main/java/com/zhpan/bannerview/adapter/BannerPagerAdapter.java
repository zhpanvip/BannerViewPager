package com.zhpan.bannerview.adapter;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import android.view.View;
import android.view.ViewGroup;

import com.zhpan.bannerview.annotation.APageStyle;
import com.zhpan.bannerview.constants.PageStyle;
import com.zhpan.bannerview.holder.HolderCreator;
import com.zhpan.bannerview.holder.ViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhpan on 2017/3/28.
 */

public class BannerPagerAdapter<T, VH extends ViewHolder> extends PagerAdapter {

    private List<T> list;

    private HolderCreator holderCreator;

    private boolean isCanLoop;

    private PageClickListener mPageClickListener;

    private List<View> mViewList = new ArrayList<>();

    private int mPageStyle;

    public BannerPagerAdapter(List<T> list, HolderCreator<VH> holderCreator) {
        this.list = list;
        this.holderCreator = holderCreator;
    }


    @Override
    public int getCount() {
        if (isCanLoop && list.size() > 1) {
            if (mPageStyle == PageStyle.NORMAL) {
                return list.size() + 2;
            } else {
                return list.size() + 4;
            }
        } else {
            return list.size();
        }
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @Override
    public @NonNull
    Object instantiateItem(@NonNull final ViewGroup container, final int position) {
        View itemView = findViewByPosition(container, position);
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
            throw new RuntimeException("can not return a null holder");
        }
        return createView(holder, position, container);
    }

    private View createView(ViewHolder<T> holder, int position, ViewGroup container) {
        View view = null;
        if (list != null && list.size() > 0) {
            if (isCanLoop && list.size() > 1) {
                int size = list.size();
                if (mPageStyle == PageStyle.NORMAL) {
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
                    if (position == 0) {
                        view = holder.createView(container, container.getContext(), list.size() - 2);
                        holder.onBind(container.getContext(), list.get(list.size() - 2), list.size() - 2, size);
                    } else if (position == 1) {
                        view = holder.createView(container, container.getContext(), list.size() - 1);
                        holder.onBind(container.getContext(), list.get(list.size() - 1), list.size() - 1, size);
                    } else if (position == size + 2) {
                        view = holder.createView(container, container.getContext(), 0);
                        holder.onBind(container.getContext(), list.get(0), 0, size);
                    } else if (position == size + 3) {
                        view = holder.createView(container, container.getContext(), 1);
                        holder.onBind(container.getContext(), list.get(1), 1, size);
                    } else {
                        view = holder.createView(container, container.getContext(), position - 2);
                        holder.onBind(container.getContext(), list.get(position - 2), position - 2, size);
                    }
                }

            } else {
                view = holder.createView(container, container.getContext(), position);
                holder.onBind(container.getContext(), list.get(position), position, list.size());
            }
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

    public void setPageStyle(@APageStyle int pageStyle) {
        mPageStyle = pageStyle;
    }

    public interface PageClickListener {
        void onPageClick(int position);
    }
}
