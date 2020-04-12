package com.zhpan.bannerview;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.zhpan.bannerview.utils.BannerUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhpan on 2017/3/28.
 */
public abstract class BaseBannerAdapter<T, VH extends BaseViewHolder<T>> extends RecyclerView.Adapter<VH> {
    protected List<T> mList = new ArrayList<>();
    private boolean isCanLoop;
    static final int MAX_VALUE = 500;
    private BannerViewPager.OnPageClickListener mPageClickListener;

    @NonNull
    @Override
    public final VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(getLayoutId(viewType), parent, false);
        return createViewHolder(inflate, viewType);
    }

    @Override
    public final void onBindViewHolder(@NonNull VH holder, final int position) {
        int realPosition = BannerUtils.getRealPosition(isCanLoop, position, mList.size());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mPageClickListener != null) {
                    mPageClickListener.onPageClick(BannerUtils.getRealPosition(isCanLoop, position, mList.size()));
                }
            }
        });
        onBind(holder, mList.get(realPosition), realPosition, mList.size());
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

    void setData(List<T> list) {
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

    protected abstract void onBind(VH holder, T data, int position, int pageSize);

    public abstract VH createViewHolder(View itemView, int viewType);

    public abstract int getLayoutId(int viewType);
}
