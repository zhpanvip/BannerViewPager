package com.example.zhpan.circleviewpager.recyclerview.listener;

/**
 * 2018.11.4
 * 当使用默认刷新是调用该接口
 * 该接口只用于回调刷新和加载数据
 * 优先级小于{@link ICustomScrollListener}
 */
public interface IScrollListener {

    /**
     * 在此方法内写刷新的逻辑
     */
    void refresh();

    /**
     * 在此方法内写加载的逻辑
     */
    void loadMore();
}
