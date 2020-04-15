package com.example.zhpan.circleviewpager.recyclerview.ui;

import android.content.Context;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;


import com.example.zhpan.circleviewpager.R;
import com.example.zhpan.circleviewpager.recyclerview.listener.ICustomClickListener;
import com.example.zhpan.circleviewpager.recyclerview.module.ViewConfig;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public final class CustomAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<ViewConfig> headConfig;
    private List<ViewConfig> footConfig;
    private ArrayList<ViewConfig> EMPTY_LIST = new ArrayList<>();
    private LayoutInflater inflater;
    private RecyclerView.Adapter mAdapter;
    private int headcount = 0;
    private int footcount = 0;
    private Context mContext;
    private ICustomClickListener customClickListener;
    private ConcurrentHashMap<String, View> mCache = new ConcurrentHashMap<>();
    private RecyclerView mRecyclerView;
    private RecyclerView.RecycledViewPool mPool;
    private RecyclerView.LayoutManager mLayoutManager;

    public CustomAdapter(List<ViewConfig> headConfig, List<ViewConfig> footConfig, RecyclerView.Adapter mAdapter, Context mContext, RecyclerView mRecyclerView) {
        this.mAdapter = mAdapter;
        this.inflater = LayoutInflater.from(mContext);
        this.mContext = mContext;
        if (headConfig == null) {
            this.headConfig = EMPTY_LIST;
        } else {
            this.headConfig = headConfig;
        }
        if (footConfig == null) {
            this.footConfig = EMPTY_LIST;
        } else {
            this.footConfig = footConfig;
        }
        init(mRecyclerView);
    }

    private void init(RecyclerView mRecyclerView) {
        this.mRecyclerView = mRecyclerView;
        this.mPool = mRecyclerView.getRecycledViewPool();
        this.mLayoutManager = mRecyclerView.getLayoutManager();
    }

    /**
     * 设置监听事件
     *
     * @param customClickListener
     */
    public void setCustomClickListener(ICustomClickListener customClickListener) {
        this.customClickListener = customClickListener;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int index) {
        if (index == ViewConfig.HEADVIEW_TYPE) {
//            mLayoutManager.set
            FrameLayout contentView = (FrameLayout) inflater.inflate(R.layout.item_head_foot_parent, viewGroup, false);
//            if (null == contentView.getTag()) {
            contentView.setTag(contentView.getClass() + "_head_" + headcount);
            View cView = headConfig.get(headcount).getContentView();
            ViewGroup vg = (ViewGroup) cView.getParent();
            if (vg != null) {
                vg.removeView(cView);
            }
            contentView.addView(cView);
            mCache.put((String) contentView.getTag(), contentView);
            CustomViewHolder customViewHolder = new CustomViewHolder(contentView);
            customViewHolder.setIsRecyclable(headConfig.get(headcount).isCache());
            headcount += 1;
            if (headcount > headConfig.size() - 1) {
                headcount = 0;
            }
//                customViewHolder.setIsRecyclable(false);
//            Log.log("CustomAdapter", "onCreateViewHolder#HEADVIEW_TYPE");
            return customViewHolder;
//            }else{
//                return new CustomViewHolder(mCache.get(contentView.getTag()));
//            }
        } else if (index == ViewConfig.FOOTVIEW_TYPE) {
            FrameLayout contentView = (FrameLayout) inflater.inflate(R.layout.item_head_foot_parent, viewGroup, false);
//            if (null == contentView.getTag()) {
            contentView.setTag(contentView.getClass() + "_foot_" + footcount);
            View cView = footConfig.get(footcount).getContentView();
            ViewGroup vg = (ViewGroup) cView.getParent();
            if (vg != null) {
                vg.removeView(cView);
            }
            contentView.addView(cView);
            mCache.put((String) contentView.getTag(), contentView);
            CustomViewHolder customViewHolder = new CustomViewHolder(contentView);
            customViewHolder.setIsRecyclable(footConfig.get(footcount).isCache());
            footcount += 1;
            if (footcount > footConfig.size() - 1) {
                footcount = 0;
            }
            return customViewHolder;
//            }else{
//                return new CustomViewHolder(mCache.get(contentView.getTag()));
//            }
        } else {
            return mAdapter.onCreateViewHolder(viewGroup, index);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int position) {
        CustomViewHolder customViewHolder;
        if (viewHolder instanceof CustomViewHolder) {
            customViewHolder = (CustomViewHolder) viewHolder;
            customViewHolder.setIsRecyclable(true);
            View mParent = customViewHolder.mParent;
            //设置view的点击事件
            if (mParent instanceof ViewGroup) {
                ViewGroup parentGroup = (ViewGroup) mParent;
                int childCount = parentGroup.getChildCount();
                for (int i = 0; i < childCount; i++) {
                    parentGroup.getChildAt(i).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (customClickListener != null) {
                                if (position < getHeadSize()) {
                                    customClickListener.onClick(v, position, 0);
                                } else {
                                    customClickListener.onClick(v, position - getHeadSize() - mAdapter.getItemCount(), 1);
                                }
                            }
                        }
                    });

                }
            }
            mParent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (position < getHeadSize()) {
                        customClickListener.onClick(v, position, 0);
                    } else {
                        customClickListener.onClick(v, position - getHeadSize() - mAdapter.getItemCount(), 1);
                    }
                }
            });
//            Log.log("CustomAdapter", "onBindViewHolder" + position + "------->" + customViewHolder);
        } else {
            mAdapter.onBindViewHolder(viewHolder, position - getHeadSize());
        }
    }

    @Override
    public int getItemCount() {
        return mAdapter != null
                ? headConfig.size() + mAdapter.getItemCount() + footConfig.size()
                : headConfig.size() + footConfig.size();
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {
        View mParent;

        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            mParent = itemView;
        }
    }

    @Override
    public int getItemViewType(int position) {
        int headSize = getHeadSize();
        int adapterCount = getItemCount();//获取实际的个数
        if (position < headSize) {
            return ViewConfig.HEADVIEW_TYPE;
        } else if (position >= headSize + mAdapter.getItemCount() && position < adapterCount) {
            return ViewConfig.FOOTVIEW_TYPE;
        }
        return mAdapter.getItemViewType(position-headSize);
    }

    public int getHeadSize() {
        return headConfig.size();
    }

    public View getIndexHeadView(int index) {
        return headConfig.get(index).getContentView();
    }

    public View getIndexFootView(int index) {
        return footConfig.get(index).getContentView();
    }

    public int getFootSize() {
        return footConfig.size();
    }

    /**
     * 适配GridLayoutManager
     *
     * @param recyclerView
     */
    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            final GridLayoutManager gridLayoutManager = (GridLayoutManager) layoutManager;
            final GridLayoutManager.SpanSizeLookup spanSizeLookup = gridLayoutManager.getSpanSizeLookup();
            gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    if (getHeadSize() > position) {
                        return gridLayoutManager.getSpanCount();
                    } else if (position >= getHeadSize() + mAdapter.getItemCount()) {
                        return gridLayoutManager.getSpanCount();
                    }
                    if (spanSizeLookup != null) return spanSizeLookup.getSpanSize(position);
                    return 1;
                }
            });
            gridLayoutManager.setSpanCount(gridLayoutManager.getSpanCount());
        }

    }

    /**
     * 适配StaggeredGridLayoutManager
     *
     * @param holder
     */
    @Override
    public void onViewAttachedToWindow(@NonNull RecyclerView.ViewHolder holder) {
        super.onViewAttachedToWindow(holder);
        int position = holder.getLayoutPosition();
        if (getHeadSize() > position || position >= getHeadSize() + mAdapter.getItemCount()) {
            ViewGroup.LayoutParams lp = holder.itemView.getLayoutParams();
            if (lp != null && lp instanceof StaggeredGridLayoutManager.LayoutParams) {
                StaggeredGridLayoutManager.LayoutParams p = (StaggeredGridLayoutManager.LayoutParams) lp;
                p.setFullSpan(true);
            }
        }
    }
}
