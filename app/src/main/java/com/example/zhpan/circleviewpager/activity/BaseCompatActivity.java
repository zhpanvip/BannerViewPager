package com.example.zhpan.circleviewpager.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.zhpan.circleviewpager.R;
import com.scwang.smartrefresh.header.MaterialHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

public class BaseCompatActivity extends AppCompatActivity implements OnRefreshListener {
    protected SmartRefreshLayout mRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    // 设置下拉刷新
    protected void setRefreshLayout(boolean isAutoRefresh) {
        mRefreshLayout = findViewById(R.id.swipe_refresh_layout);
        if (mRefreshLayout == null) return;
        if (needRefreshHeader())
            mRefreshLayout.setRefreshHeader(getRefreshHeader());
        mRefreshLayout.setEnableLoadMore(needLoadMore());
        if (isAutoRefresh) {
            mRefreshLayout.post(new Runnable() {
                @Override
                public void run() {
                    mRefreshLayout.autoRefresh();
                }
            });
        }
        mRefreshLayout.setOnRefreshListener(this);
    }

    //  是否需要上拉加载
    protected boolean needLoadMore() {
        return false;
    }

    //  是否需要添加刷新头部，如不需要可在子类中添加刷新头
    protected boolean needRefreshHeader() {
        return true;
    }

    //  获取刷新头
    protected MaterialHeader getRefreshHeader() {
        MaterialHeader materialHeader = new MaterialHeader(this);
        materialHeader.setColorSchemeResources(R.color.red);
        return materialHeader;
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {

    }
}
