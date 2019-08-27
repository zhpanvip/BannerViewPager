package com.example.zhpan.circleviewpager.recyclerview.module;


import android.view.View;

public class ViewConfig {
    public static final String HEADVIEW = "_head_";
    public static final String FOOTVIEW = "_foot_";
    public static final int HEADVIEW_TYPE = 100000;
    public static final int FOOTVIEW_TYPE = 100001;
    private int type; // 100000 代表头部 100001 代表尾部
    private int view;
    private String tag;
    private View contentView;
    private boolean isCache;    //是否需要缓存

    public boolean isCache() {
        return isCache;
    }

    public void setCache(boolean cache) {
        isCache = cache;
    }

    public View getContentView() {
        return contentView;
    }

    public void setContentView(View contentView) {
        this.contentView = contentView;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getView() {
        return view;
    }

    public void setView(int view) {
        this.view = view;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }
}
