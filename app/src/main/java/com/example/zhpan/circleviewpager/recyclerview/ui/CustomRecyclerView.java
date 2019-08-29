package com.example.zhpan.circleviewpager.recyclerview.ui;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;


import com.example.zhpan.circleviewpager.R;
import com.example.zhpan.circleviewpager.recyclerview.listener.ICustomClickListener;
import com.example.zhpan.circleviewpager.recyclerview.module.ViewConfig;

import java.util.ArrayList;

/**
 * 2018.11.4
 * 自定义RecyclerView，主要用于添加不同类型的Head和Foot
 */
public class CustomRecyclerView extends RecyclerView {
    private ArrayList<ViewConfig> mHeadCouListInfo; //保存头部的view
    private ArrayList<ViewConfig> mFootCouListInfo; //保存尾部的view
    private int headCount;  //记录head的个数
    private int footCount;  //记录foot的个数
    private Adapter mAdapter; //adapter，可能是customadapter， 可能是自定义adapter
    private Context mContext;
    private ICustomClickListener customClickListener;

    public CustomRecyclerView(@NonNull Context context) {
        this(context, null);
    }

    public CustomRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    private void init(Context context) {
        mHeadCouListInfo = new ArrayList<>();
        mFootCouListInfo = new ArrayList<>();
        mContext = context;
    }

    public ArrayList<ViewConfig> getmHeadCouListInfo() {
        return mHeadCouListInfo;
    }

    public ArrayList<ViewConfig> getmFootCouListInfo() {
        return mFootCouListInfo;
    }

    /**
     * 添加HeadView的方法
     *
     * @param view
     */

    public void addHeadView(View view) {
        addHeadView(view, false);
    }

    public void addHeadView(View view, boolean isCache) {
        headCount++;
        setHeadViewConfig(view, ViewConfig.HEADVIEW, headCount, 100000, isCache);
        if (mAdapter != null) {
            if (!(mAdapter instanceof CustomAdapter)) {
                wrapHeadAdapter();
            }
        }
    }

    public void addFootView(View view) {
        footCount++;
        setFootViewConfig(view, ViewConfig.FOOTVIEW, footCount, 100000);
        if (mAdapter != null) {
            if (!(mAdapter instanceof CustomAdapter)) {
                wrapHeadAdapter();
            }
        }
    }

    /**
     * 将adapter构建为customadapter用来填充头部尾部布局
     */
    private void wrapHeadAdapter() {
        mAdapter = new CustomAdapter(mHeadCouListInfo, mFootCouListInfo, mAdapter, mContext, this);
    }

    @Override
    public void setAdapter(@Nullable Adapter adapter) {
        if (mHeadCouListInfo.size() > 0 || mFootCouListInfo.size() > 0) {
            mAdapter = new CustomAdapter(mHeadCouListInfo, mFootCouListInfo, adapter, mContext, this);
        } else {
            mAdapter = adapter;
        }
        /**
         * 设置头尾的两个缓存为size  变相解决复用问题
         */
        getRecycledViewPool().setMaxRecycledViews(ViewConfig.FOOTVIEW_TYPE, mFootCouListInfo.size() + 1);
        getRecycledViewPool().setMaxRecycledViews(ViewConfig.HEADVIEW_TYPE, mHeadCouListInfo.size() + 1);
        //现在交给scroolwrap处理
//        /**
//         * 计算高度
//         */
//        if (mRefreshView != null) {
//            ViewGroup.MarginLayoutParams layoutParams = (MarginLayoutParams) getLayoutParams();
//            layoutParams.topMargin = getRefreshHeight();
//            setLayoutParams(layoutParams);
//        }
        super.setAdapter(mAdapter);
    }

    /**
     * 配置头部view的信息
     *
     * @param view
     * @param type
     * @param count
     * @param headCount
     * @param isCache
     */
    private void setHeadViewConfig(View view, String type, int count, int headCount, boolean isCache) {
        ViewConfig viewConfig = new ViewConfig();
        viewConfig.setTag(view.getClass() + type + count);
        viewConfig.setType(headCount);
        viewConfig.setView(R.layout.item_head_foot_parent);
        viewConfig.setCache(isCache);
        ViewGroup mHeadParent = (ViewGroup) view.getParent();
        if (mHeadParent != null) {
            mHeadParent.removeView(view);
        }
        viewConfig.setContentView(view);
        mHeadCouListInfo.add(viewConfig);
    }

    /**
     * 配置尾部view的信息
     *
     * @param view
     * @param type
     * @param count
     * @param headCount
     */
    private void setFootViewConfig(View view, String type, int count, int headCount) {
        ViewConfig viewConfig = new ViewConfig();
        viewConfig.setTag(view.getClass() + type + count);
        viewConfig.setType(headCount);
        viewConfig.setView(R.layout.item_head_foot_parent);
        ViewGroup mFootParent = (ViewGroup) view.getParent();
        if (mFootParent != null) {
            mFootParent.removeView(view);
        }
        viewConfig.setContentView(view);
        mFootCouListInfo.add(viewConfig);
    }

    public CustomAdapter getHeadAndFootAdapter() {
        return (CustomAdapter) mAdapter;
    }


    public void setCustomClickListener(ICustomClickListener customClickListener) {
        this.customClickListener = customClickListener;
        getHeadAndFootAdapter().setCustomClickListener(customClickListener);
    }

    /**
     * 移除最后一个View， 就是加载更多的哪一个
     */
    public void removeLastFootView(int foorIndex) {
        this.mFootCouListInfo.remove(foorIndex);
        footCount--;
    }

    public void removeFirstHeadView() {
        this.mHeadCouListInfo.remove(0);
        headCount--;
    }
}
