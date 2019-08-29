package com.example.zhpan.circleviewpager.recyclerview.ui;

import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.content.Context;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.zhpan.circleviewpager.R;
import com.example.zhpan.circleviewpager.recyclerview.listener.ICustomClickListener;
import com.example.zhpan.circleviewpager.recyclerview.listener.ICustomScrollListener;
import com.example.zhpan.circleviewpager.recyclerview.listener.IScrollListener;


public class ScrollWrapRecycler extends LinearLayout {
    /**
     * 没有滑动
     */
    public static final int SCROLL_RL_NOTSLIPPING = 0;
    /**
     * 正在滑动 但是还没有到可以刷新的指定距离
     */
    public static final int SCROLL_RL_NOTMET = 1;
    /**
     * 松开后刷新
     */
    public static final int SCROLL_RL_REFRESH = 2;
    /**
     * 正在刷新
     */
    public static final int SCROLL_RL_LOADING = 3;
    /**
     * 刷新成功
     */
    public static final int SCROLL_RL_SUCCESS = 4;
    /**
     * 刷新失败
     */
    public static final int SCROLL_RL_FAILD = 5;
    /**
     * 刷新状态
     */
    private int refreshScrollStatus;
    private int loadMoreScrollStatus;

    private float start_X, start_Y = 0;

    private boolean isUseSelfRefresh = false;
    private boolean isUseSelfLoadMore = false;
    //是否正在刷新或者加载
    private boolean isRefreshing = false;
    private boolean isLoadMored = false;
    //记录当前的刷新滑动状态  如果isRefreshStatus==true，那就是刷新， 如果isLoadMoreStatus==true,那就是加载
    private boolean isRefreshStatus = false;
    private boolean isLoadMoreStatus = false;
    //刷新加载的标志  为true的时候才添加  默认都添加
    private boolean isRefresh = true;
    private boolean isLoadMore = true;

    private View mRefreshView;
    private View mLoadMoreView;
    private TextView mRefreshHint;
    private TextView mLoadMoreHint;
    private CustomRecyclerView mRecyclerView;
    private ICustomScrollListener mCustomScrollListener;
    private IScrollListener mIScrollListener;

    private ValueAnimator refreshAnimator;
    private ValueAnimator loadmoreAnimator;

    private Context mContext;


    public ScrollWrapRecycler(Context context) {
        this(context, null);
    }

    public ScrollWrapRecycler(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ScrollWrapRecycler(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    /**
     * 设置是否允许刷新
     *
     * @param refresh
     */
    public void setRefresh(boolean refresh) {
        this.isRefresh = refresh;
    }

    /**
     * 设置是否允许加载
     *
     * @param loadMore
     */
    public void setLoadMore(boolean loadMore) {
        this.isLoadMore = loadMore;
    }

    /**
     * 同时设置
     *
     * @param isRefALoad
     */
    public void setRefreshAndLoadMore(boolean isRefALoad) {
        this.isRefresh = isRefALoad;
        this.isLoadMore = isRefALoad;
    }

    /**
     * 初始化一些信息，比如动画之类的
     *
     * @param context
     */
    private void init(Context context) {
        setOrientation(VERTICAL);
        this.mRecyclerView = new CustomRecyclerView(context);
        this.mRecyclerView.setLayoutParams(new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        this.mContext = context.getApplicationContext();
        refreshAnimator = new ValueAnimator();
        loadmoreAnimator = new ValueAnimator();
        /**
         * 刷新成功/失败后回调
         */
        refreshAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float animatedValue = (float) animation.getAnimatedValue();
                if (animatedValue < -mRefreshView.getMeasuredHeight()) {
                    animatedValue = mRefreshView.getMeasuredHeight();
                }
                getMarginParams(mRefreshView).topMargin = (int) animatedValue;
                mRefreshView.setLayoutParams(getMarginParams(mRefreshView));
            }
        });
        /**
         * 加载成功/失败后加载
         */
        loadmoreAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float animatedValue = (float) animation.getAnimatedValue();
                if (animatedValue < -mLoadMoreView.getMeasuredHeight()) {
                    animatedValue = mLoadMoreView.getMeasuredHeight();
                }
                setPadding(0, 0, 0, (int) animatedValue);
            }
        });
    }

    /**
     * 设置用户自定义接口
     *
     * @param mCustomScrollListener
     */
    public void setmCustomScrollListener(ICustomScrollListener mCustomScrollListener) {
        if (mIScrollListener != null) {
            mIScrollListener = null;
        }
        this.mCustomScrollListener = mCustomScrollListener;
    }

    /**
     * 设置默认实现接口,如果采用了自定义的View做刷新加载，则不允许设置该接口,使用一下接口{@see}替代
     *
     * @param mIScrollListener
     * @see #setmCustomScrollListener(ICustomScrollListener)
     */
    public void setmIScrollListener(IScrollListener mIScrollListener) {
        if (isUseSelfRefresh || isUseSelfLoadMore) {
            return;
        }
        if (mCustomScrollListener != null) {
            mCustomScrollListener = null;
        }
        this.mIScrollListener = mIScrollListener;
    }

    /**
     * 添加HeadView的方法
     *
     * @param view
     */
    public void addHeadView(View view) {
        this.mRecyclerView.addHeadView(view);
    }

    /**
     * 添加HeadView的方法 并添加是否添加缓存的标识
     *
     * @param view
     * @param isCache
     */
    public void addHeadView(View view, boolean isCache) {
        this.mRecyclerView.addHeadView(view, isCache);
    }

    /**
     * 添加FootView的方法 并添加是否添加缓存的标识
     *
     * @param view
     */
    public void addFootView(View view) {
        this.mRecyclerView.addFootView(view);
    }

    /**
     * 设置Adapter
     *
     * @param adapter
     */
    public void setAdapter(RecyclerView.Adapter adapter) {
        View refreshView;
        View loadmoreView;
        //初始化的时候根据是否需要刷新加载给一个默认
        if (mRefreshView == null && isRefresh) {
            refreshView = LayoutInflater.from(mContext).inflate(R.layout.item_defalut_refresh_view, null);
            mRefreshHint = (TextView) refreshView.findViewById(R.id.m_refresh_hint);
            addRefreshView(refreshView);
            addView(mRefreshView);
            isUseSelfRefresh = false;
        } else if(mRefreshView != null){
            addView(mRefreshView);
        }
        ViewGroup parent = (ViewGroup) mRecyclerView.getParent();
        if (parent==null) {
            addView(mRecyclerView, new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1));
        }
        if (mLoadMoreView == null && isLoadMore) {
            loadmoreView = LayoutInflater.from(mContext).inflate(R.layout.item_defalut_loadmore_view, null);
            mLoadMoreHint = (TextView) loadmoreView.findViewById(R.id.m_loadmore_hint);
            addLoadMoreView(loadmoreView);
            addView(mLoadMoreView);
            isUseSelfLoadMore = false;
        } else if(mLoadMoreView != null){
            addView(mLoadMoreView);
        }
        this.mRecyclerView.setAdapter(adapter);
        viewLayout();
    }

    /**
     * 改变View的位置
     */
    private void viewLayout() {
        if (mRefreshView != null) {
            mRefreshView.measure(MeasureSpec.UNSPECIFIED, MeasureSpec.UNSPECIFIED);
            getMarginParams(mRefreshView).topMargin = -mRefreshView.getMeasuredHeight();
        }
        if (mLoadMoreView != null) {
            mLoadMoreView.measure(MeasureSpec.UNSPECIFIED, MeasureSpec.UNSPECIFIED);
            setPadding(0, 0, 0, -mLoadMoreView.getMeasuredHeight());
        }
    }

    /**
     * @return
     */
    public CustomAdapter getAdapter() {
        return this.mRecyclerView.getHeadAndFootAdapter();
    }

    /**
     * 返回指定的Head
     *
     * @param index
     * @return
     */
    public View getIndexHeadView(int index) {
        if (mRefreshView != null)
            return getAdapter().getIndexHeadView(index + 1);
        return getAdapter().getIndexHeadView(index);
    }

    /**
     * 返回指定的Foot
     *
     * @param index
     * @return
     */
    public View getIndexFootView(int index) {
        if (mLoadMoreView != null && index >= getAdapter().getFootSize())
            return getAdapter().getIndexFootView(index - 1);
        return getAdapter().getIndexFootView(index);
    }

    /**
     * 设置点击事件
     *
     * @param customClickListener
     */
    public void setCustomClickListener(ICustomClickListener customClickListener) {
        this.mRecyclerView.setCustomClickListener(customClickListener);
    }

    /**
     * 设置LayoutManager
     *
     * @param manager
     */
    public void setLayoutManager(RecyclerView.LayoutManager manager) {
        this.mRecyclerView.setLayoutManager(manager);
    }

    /**
     * 设置LayoutParmas
     *
     * @param pramas
     */
    public void setRLayoutPramas(ViewGroup.LayoutParams pramas) {
        this.mRecyclerView.setLayoutParams(pramas);
    }

    /**
     * 添加刷新的View
     *
     * @param mRefreshView
     */
    public void addRefreshView(View mRefreshView) {
        this.isUseSelfRefresh = true;
        this.mIScrollListener = null;
        this.mRefreshView = mRefreshView;
    }

    /**
     * 添加加载的View
     *
     * @param mLoadMoreView
     */
    public void addLoadMoreView(View mLoadMoreView) {
        this.isUseSelfLoadMore = true;
        this.mIScrollListener = null;
        this.mLoadMoreView = mLoadMoreView;
    }

    /**
     * 用来回调是否刷新成功
     *
     * @param status
     */
    public void setRefreshStatus(int status) {
        this.refreshScrollStatus = status;
        if (isUseSelfRefresh) {
            mCustomScrollListener.scrollRefreshState(status);
        } else {
            scrollRefreshState(status);
        }
        postDelayed(new Runnable() {
            @Override
            public void run() {
                setRefreshScrollAnimation(-mRefreshView.getMeasuredHeight());
                isRefreshing = false;
            }
        }, 500);
    }

    /**
     * 用来回调是否刷新成功
     *
     * @param status
     */
    public void setLoadMoreStatus(int status) {
        this.loadMoreScrollStatus = status;
        if (isUseSelfLoadMore) {
            mCustomScrollListener.scrollLoadMoreState(status);
        } else {
            scrollLoadMoreState(status);
        }
        notifyDataSetChanged();
        setLoadMoreScrollAnimation(-mLoadMoreView.getMeasuredHeight());
        isLoadMored = false;
    }

    public void scrollToPosition(int position) {
        mRecyclerView.scrollToPosition(position);
    }

    /**
     * 重写手势监听事件
     *
     * @param ev
     * @return
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (!isRefreshing && !isLoadMored && this.isRefresh && this.isLoadMore) {
            switch (ev.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    start_X = (int) ev.getX();
                    start_Y = (int) ev.getY();
                    break;
                case MotionEvent.ACTION_MOVE:
                    float move_X = ev.getX();
                    float move_Y = ev.getY();
                    /*
                     * 如果没有刷新的View  或者刷新的View <= view的高度负值  证明刷新的view已经还原  这时候执行recycler的滑动
                     */
//                    View childAt = layoutManager.getChildAt(1);
                    /*
                     * 每次进来时先全部置为false  且isRefreshStatus与isLoadMoreStatus为互斥状态  isLoadMoreStatus为true，则且isRefreshStatus为false，反之一样
                     */
                    LinearLayoutManager layoutManager = (LinearLayoutManager) mRecyclerView.getLayoutManager();
                    /*
                     * 下拉刷新时候的判断
                     */
                    if ((mRefreshView != null && layoutManager.findFirstCompletelyVisibleItemPosition() == 0 && move_Y - start_Y > 0)
                            || (getMarginParams(mRefreshView).topMargin > -mRefreshView.getMeasuredHeight()/* && move_Y - start_Y < 0*/)) {
                        float phaseDiff = move_Y - start_Y;
                        updateHead((float) (phaseDiff / 1.5));
                        start_Y = move_Y;
                        return true;
                        /*
                         * 上拉加载时候的判断
                         */
                    } else if ((mLoadMoreView != null && layoutManager.findLastCompletelyVisibleItemPosition() == getAdapter().getItemCount() - 1 && move_Y - start_Y < 0)
                            || getBottomPadding(this) > -mLoadMoreView.getMeasuredHeight() /*&& move_Y - start_Y > 0*/) {
                        float phaseDiff = move_Y - start_Y;
                        updateFoot((float) (phaseDiff / 1.5));
                        start_Y = move_Y;
                        return true;
                        /*
                         * 正常滑动 直接分发该事件 不拦截
                         */
                    } else {
                        isRefreshStatus = false;
                        isLoadMoreStatus = false;
                        start_Y = move_Y;
                        return super.dispatchTouchEvent(ev);
                    }
                case MotionEvent.ACTION_CANCEL:
                case MotionEvent.ACTION_UP:
                    /*
                     * 下拉刷新时候走这里
                     */
                    if (isRefreshStatus) {
                        if (mRefreshView != null) {
                            if (refreshScrollStatus == SCROLL_RL_REFRESH) {
                                isRefreshing = true;
                                refresh();
                                if (mCustomScrollListener != null && isUseSelfRefresh) {
                                    mCustomScrollListener.scrollRefreshState(SCROLL_RL_LOADING);
                                } else {
                                    scrollRefreshState(SCROLL_RL_LOADING);
                                }
                                setRefreshScrollAnimation((int) (mRefreshView.getMeasuredHeight() * 1.2));
                            } else {
                                setRefreshScrollAnimation(-mRefreshView.getMeasuredHeight());
                            }
                        }
                        /*
                         * 上拉加载走这里
                         */
                    } else if (isLoadMoreStatus) {
                        if (mLoadMoreView != null) {
                            if (loadMoreScrollStatus == SCROLL_RL_REFRESH) {
                                isLoadMored = true;
                                loadMore();
                                if (mCustomScrollListener != null && isUseSelfLoadMore) {
                                    mCustomScrollListener.scrollLoadMoreState(SCROLL_RL_LOADING);
                                } else {
                                    scrollLoadMoreState(SCROLL_RL_LOADING);
                                }
                                setLoadMoreScrollAnimation(0);
                            } else {
                                setLoadMoreScrollAnimation(-mLoadMoreView.getMeasuredHeight());
                            }
                        }
                    }
                    /**
                     * 将刷新加载的状态重置
                     */
                    isLoadMoreStatus = false;
                    isRefreshStatus = false;
                    break;
            }
        } else if(!isRefresh || !isLoadMore){
            return super.dispatchTouchEvent(ev);
        }
        return super.dispatchTouchEvent(ev);
    }

    /**
     * 会回调刷新接口，需要设置{@link ICustomScrollListener}或{@link IScrollListener}
     */
    private void refresh() {
        if (mCustomScrollListener != null) {
            mCustomScrollListener.refresh();
        } else if (mIScrollListener != null) {
            mIScrollListener.refresh();
        } else {
            Log.w(getClass().getName(), "请设置回调监听器");
        }
    }

    /**
     * 会回调加载接口，需要设置{@link ICustomScrollListener}或{@link IScrollListener}
     */
    private void loadMore() {
        if (mCustomScrollListener != null) {
            mCustomScrollListener.loadMore();
        } else if (mIScrollListener != null) {
            mIScrollListener.loadMore();
        } else {
            Log.w(getClass().getName(), "请设置回调监听器");
        }
    }

    /**
     * 更新加载的View
     *
     * @param phaseDiff
     */
    private void updateFoot(float phaseDiff) {
        isLoadMoreStatus = true;
        isRefreshStatus = false;
        int bottomPadding = getBottomPadding(this);
        int scrollMax = bottomPadding;
        if (phaseDiff < 0) {
            if (scrollMax > mLoadMoreView.getMeasuredHeight() * 0.8) {
                return;
            }
            scrollMax = scrollMax > mLoadMoreView.getMeasuredHeight() * 0.8 ? (int) (mLoadMoreView.getMeasuredHeight() * 0.8) : (int) (scrollMax - phaseDiff);
        } else {
            scrollMax = scrollMax < -mLoadMoreView.getMeasuredHeight() ? -mLoadMoreView.getMeasuredHeight() : (int) (scrollMax - phaseDiff);
        }
        mRecyclerView.scrollToPosition(getAdapter().getItemCount() - 1);
        setPadding(0, 0, 0, scrollMax);
        requestLayout();
        if (isUseSelfLoadMore) {
            if (mCustomScrollListener != null) {
                if (scrollMax < mLoadMoreView.getMeasuredHeight() * 0.5) {
                    this.loadMoreScrollStatus = SCROLL_RL_NOTMET;
                    mCustomScrollListener.scrollLoadMoreState(SCROLL_RL_NOTMET);
                } else if (scrollMax > mLoadMoreView.getMeasuredHeight() * 0.5) {
                    this.loadMoreScrollStatus = SCROLL_RL_REFRESH;
                    mCustomScrollListener.scrollLoadMoreState(SCROLL_RL_REFRESH);
                }
//                mCustomScrollListener.scrollRefreshState(scrollMax > mRefreshView.getMeasuredHeight());
            }
        } else {
            if (scrollMax < mLoadMoreView.getMeasuredHeight() * 0.5) {
                this.scrollLoadMoreState(SCROLL_RL_NOTMET);
            } else if (scrollMax > mLoadMoreView.getMeasuredHeight() *0.5) {
                this.scrollLoadMoreState(SCROLL_RL_REFRESH);
            }
        }
    }

    /**
     * 刷新接口  调用后给动画  并且刷新
     */
    public void setRefresh() {
        isRefreshing = true;
        if (mCustomScrollListener != null && isUseSelfRefresh) {
            mCustomScrollListener.scrollRefreshState(SCROLL_RL_LOADING);
        } else {
            scrollRefreshState(SCROLL_RL_LOADING);
        }
        postDelayed(new Runnable() {
            @Override
            public void run() {
                refresh();
            }
        }, 300);
        setRefreshScrollAnimation((int) (mRefreshView.getMeasuredHeight() * 1.2));
    }

    /**
     * 更新头部的刷新
     *
     * @param phaseDiff
     */
    private void updateHead(float phaseDiff) {
        isRefreshStatus = true;
        isLoadMoreStatus = false;
        MarginLayoutParams marginParams = getMarginParams(mRefreshView);
        int scrollMax = marginParams.topMargin + (int) (phaseDiff);
        if (phaseDiff > 0) {
            scrollMax = scrollMax > mRefreshView.getMeasuredHeight() * 2 ? mRefreshView.getMeasuredHeight() * 2 : scrollMax;
        } else {
            scrollMax = scrollMax < -mRefreshView.getMeasuredHeight() ? -mRefreshView.getMeasuredHeight() : scrollMax;
        }
        marginParams.topMargin = scrollMax;
        requestLayout();
//        setRLayoutPramas(marginParams);
        if (isUseSelfRefresh) {
            if (mCustomScrollListener != null) {
                if (scrollMax < mRefreshView.getMeasuredHeight() * 1.2) {
                    this.refreshScrollStatus = SCROLL_RL_NOTMET;
                    mCustomScrollListener.scrollRefreshState(SCROLL_RL_NOTMET);
                } else if (scrollMax > mRefreshView.getMeasuredHeight() * 1.2) {
                    this.refreshScrollStatus = SCROLL_RL_REFRESH;
                    mCustomScrollListener.scrollRefreshState(SCROLL_RL_REFRESH);
                }
//                mCustomScrollListener.scrollRefreshState(scrollMax > mRefreshView.getMeasuredHeight());
            }
        } else {
            if (scrollMax < mRefreshView.getMeasuredHeight() * 1.2) {
                this.scrollRefreshState(SCROLL_RL_NOTMET);
            } else if (scrollMax > mRefreshView.getMeasuredHeight() * 1.2) {
                this.scrollRefreshState(SCROLL_RL_REFRESH);
            }
        }
    }

    /**
     * 更改头部状态
     *
     * @param scrollReleash
     */
    public void scrollRefreshState(int scrollReleash) {
        switch (scrollReleash) {
            case SCROLL_RL_NOTSLIPPING:
                mRefreshHint.setText("等待刷新");
                break;
            case SCROLL_RL_NOTMET:
                mRefreshHint.setText("下拉刷新");
                break;
            case SCROLL_RL_REFRESH:
                mRefreshHint.setText("松开刷新");
                break;
            case SCROLL_RL_LOADING:
                mRefreshHint.setText("正在刷新...");
                break;
            case SCROLL_RL_SUCCESS:
                mRefreshHint.setText("刷新成功");
                break;
            case SCROLL_RL_FAILD:
                mRefreshHint.setText("刷新失败");
                break;
        }
        this.refreshScrollStatus = scrollReleash;
    }

    /**
     * 更改底部加载状态
     *
     * @param scrollReleash
     */
    public void scrollLoadMoreState(int scrollReleash) {
        switch (scrollReleash) {
            case SCROLL_RL_NOTSLIPPING:
                mLoadMoreHint.setText("加载更多");
                break;
            case SCROLL_RL_NOTMET:
                mLoadMoreHint.setText("上拉加载");
                break;
            case SCROLL_RL_REFRESH:
                mLoadMoreHint.setText("松开立即加载");
                break;
            case SCROLL_RL_LOADING:
                mLoadMoreHint.setText("正在加载...");
                break;
            case SCROLL_RL_SUCCESS:
                mLoadMoreHint.setText("加载成功");
                break;
            case SCROLL_RL_FAILD:
                mLoadMoreHint.setText("加载失败");
                break;
        }
        this.loadMoreScrollStatus = scrollReleash;
    }

    /**
     * 实际的内容size
     *
     * @return
     */
    public int getFullSize() {
        return getAdapter().getItemCount() - (mRefreshView != null ? 1 : 0) - (mLoadMoreView != null ? 1 : 0);
    }

    /**
     * 获取View的Params  用来更改Margin
     *
     * @param view
     * @return
     */
    private MarginLayoutParams getMarginParams(View view) {
        return (MarginLayoutParams) view.getLayoutParams();
    }

    /**
     * 获取View的bottomPadding
     *
     * @param view
     * @return
     */
    private int getBottomPadding(View view) {
        return view.getPaddingBottom();
    }

    /**
     * 设置刷新后隐藏View的动画
     *
     * @param height
     */
    public void setRefreshScrollAnimation(int height) {
        PropertyValuesHolder valuesHolder = PropertyValuesHolder.ofFloat("", getMarginParams(mRefreshView).topMargin, height);
        refreshAnimator.setValues(valuesHolder);
        refreshAnimator.setDuration(300);
        refreshAnimator.start();
    }

    /**
     * 设置加载后隐藏View的动画
     *
     * @param height
     */
    public void setLoadMoreScrollAnimation(int height) {
        PropertyValuesHolder valuesHolder = PropertyValuesHolder.ofFloat("", getBottomPadding(this), height);
        loadmoreAnimator.setValues(valuesHolder);
        loadmoreAnimator.setDuration(0);
        loadmoreAnimator.start();
    }

    /**
     * ====================================================================================
     * 以下提供的方法均调用的是CustomAdapter的方法
     * ====================================================================================
     */
    public void notifyDataSetChanged() {
        this.getAdapter().notifyDataSetChanged();
    }

    public void notifyItemChanged(final int position) {
        this.getAdapter().notifyItemChanged(position);
    }

    public void notifyItemChanged(int position, Object payload) {
        this.getAdapter().notifyItemChanged(position, payload);
    }

    public void notifyItemInserted(int position) {
        this.getAdapter().notifyItemInserted(position);
    }

    public void notifyItemMoved(int fromPosition, int toPosition) {
        this.getAdapter().notifyItemMoved(fromPosition, toPosition);
    }

    public void notifyItemRangeChanged(int positionStart, int itemCount) {
        this.getAdapter().notifyItemRangeChanged(positionStart, itemCount);
    }

    public void notifyItemRangeChanged(int positionStart, int itemCount, Object payload) {
        this.getAdapter().notifyItemRangeChanged(positionStart, itemCount, payload);
    }

    public void notifyItemRangeInserted(int positionStart, int itemCount) {
        this.getAdapter().notifyItemRangeInserted(positionStart, itemCount);
    }

    public void notifyItemRemoved(int positionStart) {
        this.getAdapter().notifyItemRemoved(positionStart);
    }
}
