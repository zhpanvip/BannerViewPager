package com.zhpan.bannerview;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.zhpan.bannerview.utils.BannerUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhpan on 2017/3/28.
 * Base Adapter of BVP，Multiple view types can extends this class.
 * For single view type,Please use {@link BaseSimpleAdapter<T>}
 */
public abstract class BaseBannerAdapter<T, VH extends BaseViewHolder<T>> extends RecyclerView.Adapter<VH> {

    public static final int MAX_VALUE = 500;

    protected List<T> mList = new ArrayList<>();

    private boolean isCanLoop;

    private BannerViewPager.OnPageClickListener mPageClickListener;

    @NonNull
    @Override
    public final VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return createViewHolder(parent, createItemView(parent, viewType), viewType);
    }

    @Override
    public final void onBindViewHolder(@NonNull VH holder, final int position) {
        int realPosition = BannerUtils.getRealPosition(isCanLoop, position, mList.size());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View clickedView) {
                if (mPageClickListener != null) {
                    mPageClickListener.onPageClick(clickedView, BannerUtils.getRealPosition(isCanLoop, position, mList.size()));
                }
            }
        });
        bindData(holder, mList.get(realPosition), realPosition, mList.size());
    }

    @Override
    public final int getItemViewType(int position) {
        int realPosition = BannerUtils.getRealPosition(isCanLoop, position, mList.size());
        return getViewType(realPosition);
    }

    @Override
    public final int getItemCount() {
        if (isCanLoop && mList.size() > 1) {
            return MAX_VALUE;
        } else {
            return mList.size();
        }
    }

    List<T> getData() {
        return mList;
    }

    void setData(List<? extends T> list) {
        if (null != list) {
            mList.clear();
            mList.addAll(list);
        }
    }

    void setCanLoop(boolean canLoop) {
        isCanLoop = canLoop;
    }

    void setPageClickListener(BannerViewPager.OnPageClickListener pageClickListener) {
        mPageClickListener = pageClickListener;
    }

    int getListSize() {
        return mList.size();
    }

    protected int getViewType(int position) {
        return 0;
    }

    protected abstract void bindData(VH holder, T data, int position, int pageSize);

    protected abstract View createItemView(ViewGroup parent, int viewType);

    public abstract VH createViewHolder(@NonNull ViewGroup parent, View itemView, int viewType);

}
