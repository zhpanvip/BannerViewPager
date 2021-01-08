package com.zhpan.bannerview;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.zhpan.bannerview.utils.BannerUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhpan on 2017/3/28.
 */
public abstract class BaseBannerAdapter<T> extends RecyclerView.Adapter<BaseViewHolder<T>> {
    protected List<T> mList = new ArrayList<>();
    private boolean isCanLoop;
    public static final int MAX_VALUE = 1000;
    private BannerViewPager.OnPageClickListener mPageClickListener;

    @NonNull
    @Override
    public final BaseViewHolder<T> onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(getLayoutId(viewType), parent, false);
        return createViewHolder(parent, itemView, viewType);
    }

    @Override
    public final void onBindViewHolder(@NonNull BaseViewHolder<T> holder, final int position) {
        int realPosition = BannerUtils.getRealPosition(position, getListSize());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View clickedView) {
                if (mPageClickListener != null) {
                    mPageClickListener.onPageClick(clickedView, BannerUtils.getRealPosition(position, getListSize()));
                }
            }
        });
        bindData(holder, mList.get(realPosition), realPosition, getListSize());
    }

    @Override
    public final int getItemViewType(int position) {
        int realPosition = BannerUtils.getRealPosition(position, getListSize());
        return getViewType(realPosition);
    }

    @Override
    public final int getItemCount() {
        if (isCanLoop && getListSize() > 1) {
            return MAX_VALUE;
        } else {
            return getListSize();
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

    public boolean isCanLoop() {
        return isCanLoop;
    }

    /**
     * Don't need override this method in subclass.
     * This method calls {@link #onCreateViewHolder(ViewGroup, int)} to create a new{@link BaseViewHolder}
     *
     * @param parent   The ViewGroup into which the new View will be added after it is bound to
     *                 an adapter position.
     * @param itemView Item View.
     * @param viewType The view type of the new View.
     * @return ViewHolder extends {@link BaseViewHolder}.
     */
    public BaseViewHolder<T> createViewHolder(@NonNull ViewGroup parent, View itemView, int viewType) {
        return new BaseViewHolder<>(itemView);
    }

    /**
     * @param holder   The ViewHolder which should be updated to represent the contents of the
     *                 item at the given position in the data set.
     * @param data     Current item data.
     * @param position Current item position.
     * @param pageSize Page size of BVP,equals {@link BaseBannerAdapter#getListSize()}.
     */
    protected abstract void bindData(BaseViewHolder<T> holder, T data, int position, int pageSize);

    /**
     * @param viewType The view type of the new View.
     * @return The item view layout.
     */
    public abstract @LayoutRes
    int getLayoutId(int viewType);
}
