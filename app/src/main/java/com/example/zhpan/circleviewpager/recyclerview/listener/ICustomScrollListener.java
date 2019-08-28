package com.example.zhpan.circleviewpager.recyclerview.listener;

/**
 * 2018.11.4
 * 使用了自己的刷新加载view时，需要实现改接口，不然接受不到数据
 * 如果不需要使用自定义的刷新View，可使用{@link IScrollListener}
 */
public interface ICustomScrollListener {
    /**
     * 下拉刷新时候的回调
     * @param state
     */
    void scrollRefreshState(int state);

    /**
     * 上拉加载时候的回调
     * @param state
     */
    void scrollLoadMoreState(int state);

    /**
     * 在此方法内写刷新的逻辑
     */
    void refresh();
    /**
     * 在此方法内写加载的逻辑
     */
    void loadMore();
}
