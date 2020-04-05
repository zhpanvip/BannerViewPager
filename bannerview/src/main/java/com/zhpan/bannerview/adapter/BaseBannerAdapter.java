package com.zhpan.bannerview.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.zhpan.bannerview.BannerViewPager;
import com.zhpan.bannerview.holder.BaseViewHolder;
import com.zhpan.bannerview.utils.BannerUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhpan on 2017/3/28.
 */
public abstract class BaseBannerAdapter<T, VH extends BaseViewHolder> extends RecyclerView.Adapter<VH> {
    private List<T> mList = new ArrayList<>();
    private boolean isCanLoop;
    public static final int MAX_VALUE = 500;
    private BannerViewPager.OnItemClickListener mPageClickListener;

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(getLayoutId(), parent, false);
        return createViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, final int position) {
        int realPosition = BannerUtils.getRealPosition(isCanLoop, position, mList.size());
        onBind(holder, mList.get(realPosition), realPosition, mList.size());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mPageClickListener != null) {
                    mPageClickListener.onPageClick(BannerUtils.getRealPosition(isCanLoop, position, mList.size()));
                }
            }
        });
    }

    protected abstract void onBind(VH holder, T data, int position, int pageSize);

    public abstract VH createViewHolder(View itemView);

    public abstract int getLayoutId();

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
        if (null != list) {
            mList.clear();
            mList.addAll(list);
        }
    }

    public void setCanLoop(boolean canLoop) {
        isCanLoop = canLoop;
    }

    public void setPageClickListener(BannerViewPager.OnItemClickListener pageClickListener) {
        mPageClickListener = pageClickListener;
    }

    public int getListSize() {
        return mList.size();
    }
}
