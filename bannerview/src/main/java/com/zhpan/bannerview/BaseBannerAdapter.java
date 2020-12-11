package com.zhpan.bannerview;

import android.content.Context;
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

    public static final int MAX_VALUE = 500;

    protected List<T> mList = new ArrayList<>();

    private boolean isCanLoop;
    /**
     * 页面点击回调
     */
    private BannerViewPager.OnPageClickListener mPageClickListener;
    /**
     * 上下问
     */
    protected Context mContext;

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        mContext = recyclerView.getContext();
    }

    @NonNull
    @Override
    public final VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate;
        int layoutId = getLayoutId(viewType);
        if (layoutId <= 0) {
            inflate = createBindingView(getContext(), parent, viewType);
        } else {
            inflate = LayoutInflater.from(parent.getContext()).inflate(layoutId, parent, false);
        }
        return createViewHolder(parent, inflate, viewType);
    }

    /**
     * 创建ViewHolder中的根View,重写此方法创建View;使用此方法后{@link #getLayoutId(int)}失效
     */
    protected View createBindingView(Context context, ViewGroup parent, int viewType) {
        return null;
    }

    public Context getContext() {
        return mContext;
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

    protected abstract void onBind(VH holder, T data, int position, int pageSize);

    public abstract VH createViewHolder(@NonNull ViewGroup parent, View itemView, int viewType);

    public abstract int getLayoutId(int viewType);
}
