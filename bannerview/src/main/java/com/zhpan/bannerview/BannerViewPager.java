package com.zhpan.bannerview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Build;
import android.os.Handler;

import androidx.annotation.ColorInt;
import androidx.annotation.DimenRes;
import androidx.annotation.IntDef;
import androidx.viewpager.widget.ViewPager;

import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.zhpan.bannerview.indicator.IIndicator;
import com.zhpan.bannerview.utils.DpUtils;
import com.zhpan.bannerview.adapter.BannerPagerAdapter;
import com.zhpan.bannerview.enums.IndicatorSlideMode;
import com.zhpan.bannerview.holder.HolderCreator;
import com.zhpan.bannerview.holder.ViewHolder;
import com.zhpan.bannerview.provider.BannerScroller;
import com.zhpan.bannerview.provider.ViewStyleSetter;
import com.zhpan.bannerview.transform.PageTransformerFactory;
import com.zhpan.bannerview.enums.TransformerStyle;
import com.zhpan.bannerview.indicator.CircleIndicatorView;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhpan on 2017/3/28.
 */
public class BannerViewPager<T, VH extends ViewHolder> extends FrameLayout implements
        ViewPager.OnPageChangeListener {
    public String tag = "BannerViewPager";
    private ViewPager mViewPager;
    // 轮播数据集合
    private List<T> mList;
    // 图片切换时间间隔
    private int interval;
    // 图片当前位置
    private int currentPosition;
    // 是否正在循环
    private boolean isLooping;
    // 是否开启循环
    private boolean isCanLoop;
    // 是否开启自动播放
    private boolean isAutoPlay = false;
    // 是否显示指示器圆点
    private boolean showIndicator = true;
    // 圆点指示器显示位置
    public static final int START = 1;
    public static final int END = 2;
    public static final int CENTER = 0;
    private int gravity;
    // 未选中时圆点颜色
    private int indicatorNormalColor;
    // 选中时选点颜色
    private int indicatorCheckedColor;
    // 指示器圆点半径
    private float normalIndicatorRadius;
    // 选中时指示器圆点半径
    private float checkedIndicatorRadius;

    // 页面点击事件监听
    private OnPageClickListener mOnPageClickListener;
    // 圆点指示器的Layout
    private IIndicator mIndicatorView;

    private IndicatorSlideMode mIndicatorSlideMode = IndicatorSlideMode.SMOOTH;

    RelativeLayout mRelativeLayout;
    private HolderCreator<VH> holderCreator;
    Handler mHandler = new Handler();
    Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            if (mList.size() > 1) {
                currentPosition = currentPosition % (mList.size() + 1) + 1;
                if (currentPosition == 1) {
                    mViewPager.setCurrentItem(currentPosition, false);
                    mHandler.post(mRunnable);
                } else {
                    mViewPager.setCurrentItem(currentPosition, true);
                    mHandler.postDelayed(mRunnable, interval);
                }
            }
        }
    };
    private BannerScroller mScroller;

    public static final int DEFAULT_SCROLL_DURATION = 800;

    private float indicatorMargin = 0;

    public BannerViewPager(Context context) {
        this(context, null);
    }

    public BannerViewPager(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BannerViewPager(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs, context);
    }

    private void init(AttributeSet attrs, Context context) {
        initValues(attrs, context);
        initView();
        initScroller();
    }

    private void initView() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.layout_banner_view_pager, this);
        mViewPager = view.findViewById(R.id.vp_main);
        mRelativeLayout = view.findViewById(R.id.rl_indicator);
        mList = new ArrayList<>();
    }

    private void initValues(AttributeSet attrs, Context context) {
        if (attrs != null) {
            TypedArray typedArray =
                    getContext().obtainStyledAttributes(attrs, R.styleable.BannerViewPager);
            interval = typedArray.getInteger(R.styleable.BannerViewPager_interval, 3000);
            indicatorCheckedColor =
                    typedArray.getColor(R.styleable.BannerViewPager_indicator_checked_color,
                            Color.parseColor("#8C18171C"));
            indicatorNormalColor =
                    typedArray.getColor(R.styleable.BannerViewPager_indicator_normal_color,
                            Color.parseColor("#8C6C6D72"));
            normalIndicatorRadius = typedArray.getDimension(R.styleable.BannerViewPager_indicator_radius,
                    DpUtils.dp2px(context, 4));
            checkedIndicatorRadius = normalIndicatorRadius;
            isAutoPlay = typedArray.getBoolean(R.styleable.BannerViewPager_isAutoPlay, true);
            isCanLoop = typedArray.getBoolean(R.styleable.BannerViewPager_isCanLoop, true);
            gravity = typedArray.getInt(R.styleable.BannerViewPager_indicator_gravity, 0);
            typedArray.recycle();
        }
    }

    /**
     * 替换ViewPager默认的Scroller
     */
    private void initScroller() {
        try {
            mScroller = new BannerScroller(mViewPager.getContext());
            mScroller.setDuration(DEFAULT_SCROLL_DURATION);
            Field mField = ViewPager.class.getDeclaredField("mScroller");
            mField.setAccessible(true);
            mField.set(mViewPager, mScroller);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 初始化书记及ViewPager
     */
    private void initData() {
        if (mList.size() > 0) {
            if (mIndicatorView == null && mList.size() > 1 && showIndicator) {
                initIndicator(getIndicatorView());
            }
            if (isCanLoop) {
                currentPosition = 1;
            }
            setupViewPager();
        }
    }

    private View getIndicatorView() {
        CircleIndicatorView indicatorView = new CircleIndicatorView(getContext());
        indicatorView.setPageSize(mList.size()).setIndicatorRadius(normalIndicatorRadius, checkedIndicatorRadius)
                .setIndicatorMargin(indicatorMargin).setCheckedColor(indicatorCheckedColor)
                .setNormalColor(indicatorNormalColor).setSlideStyle(mIndicatorSlideMode).invalidate();
        return indicatorView;
    }


    /**
     * 设置触摸事件，当滑动或者触摸时停止自动轮播
     */
    @SuppressLint("ClickableViewAccessibility")
    private void setTouchListener() {
        mViewPager.setOnTouchListener((v, event) -> {
            int action = event.getAction();
            switch (action) {
                case MotionEvent.ACTION_DOWN:
                case MotionEvent.ACTION_MOVE:
                    isLooping = true;
                    stopLoop();
                    break;
                case MotionEvent.ACTION_UP:
                case MotionEvent.ACTION_CANCEL:
                    isLooping = false;
                    startLoop();
                default:
                    break;
            }
            return false;
        });
    }

    /**
     * 构造指示器
     */
    private void initIndicator(View indicatorView) {
        mRelativeLayout.removeAllViews();
        mRelativeLayout.addView(indicatorView);
        mIndicatorView = (IIndicator) indicatorView;
        RelativeLayout.LayoutParams layoutParams =
                (RelativeLayout.LayoutParams) indicatorView.getLayoutParams();
        layoutParams.addRule(RelativeLayout.CENTER_VERTICAL);
        switch (gravity) {
            case CENTER:
                layoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
                break;
            case START:
                layoutParams.addRule(RelativeLayout.ALIGN_PARENT_START);
                break;
            case END:
                layoutParams.addRule(RelativeLayout.ALIGN_PARENT_END);
                break;
        }
    }


    private void setupViewPager() {
        if (holderCreator != null) {
            BannerPagerAdapter<T, VH> bannerPagerAdapter =
                    new BannerPagerAdapter<>(mList, holderCreator);
            bannerPagerAdapter.setPageClickListener(position -> {
                if (mOnPageClickListener != null) {
                    int realPosition = isCanLoop ? position - 1 : position;
                    if (realPosition < mList.size() && realPosition >= 0) {
                        mOnPageClickListener.onPageClick(realPosition);
                    }
                }
            });
            bannerPagerAdapter.setCanLoop(isCanLoop);
            mViewPager.setAdapter(bannerPagerAdapter);
            mViewPager.setCurrentItem(currentPosition);
            mViewPager.addOnPageChangeListener(this);
            startLoop();
            setTouchListener();
        } else {
            throw new NullPointerException("You must set HolderCreator for BannerViewPager");
        }
    }

    @Override
    public void onPageSelected(int position) {
        currentPosition = position;
        if (showIndicator && mIndicatorView != null) {
            mIndicatorView.onPageSelected(getRealPosition(position));
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        if (mIndicatorView != null)
            mIndicatorView.onPageScrollStateChanged(state);

        if (isCanLoop) {
            switch (state) {
                case ViewPager.SCROLL_STATE_IDLE:
                    if (currentPosition == 0) {
                        mViewPager.setCurrentItem(mList.size(), false);
                    } else if (currentPosition == mList.size() + 1) {
                        mViewPager.setCurrentItem(1, false);
                    }
                    break;
                case ViewPager.SCROLL_STATE_DRAGGING:
                    if (currentPosition == mList.size() + 1) {
                        mViewPager.setCurrentItem(1, false);
                    } else if (currentPosition == 0) {
                        mViewPager.setCurrentItem(mList.size(), false);
                    }
                    break;
            }
        } else {
            mViewPager.setCurrentItem(currentPosition);
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        if (mIndicatorView != null) {
            mIndicatorView.onPageScrolled(getRealPosition(position), positionOffset, positionOffsetPixels);
        }
    }

    private int getRealPosition(int position) {
        if (isCanLoop) {
            if (position == 0) {
                return mList.size() - 1;
            } else if (position == mList.size() + 1) {
                return 0;
            } else {
                return --position;
            }
        } else {
            return position;
        }
    }

    private int toUnrealPosition(int position) {
        return isCanLoop ? (position < mList.size()) ? (++position) : mList.size() : position;
    }

    /**
     * 开启轮播
     */
    public void startLoop() {
        if (!isLooping && isAutoPlay && mList.size() > 1) {
            mHandler.postDelayed(mRunnable, interval);
            isLooping = true;
        }
    }

    /**
     * 停止轮播
     */
    public void stopLoop() {
        if (isLooping) {
            mHandler.removeCallbacks(mRunnable);
            isLooping = false;
        }
    }


    public BannerViewPager<T, VH> setHolderCreator(HolderCreator<VH> holderCreator) {
        this.holderCreator = holderCreator;
        return this;
    }

    /**
     * 设置圆角ViewPager
     *
     * @param radius @DimenRes 圆角大小
     */
    public BannerViewPager<T, VH> setRoundCorner(@DimenRes int radius) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ViewStyleSetter viewStyleSetter = new ViewStyleSetter(this);
            viewStyleSetter.setRoundCorner(getResources().getDimension(radius));
        }
        return this;
    }

    /**
     * 设置圆角ViewPager
     *
     * @param radiusDp 圆角大小
     */
    public BannerViewPager<T, VH> setRoundCorner(float radiusDp) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ViewStyleSetter viewStyleSetter = new ViewStyleSetter(this);
            viewStyleSetter.setRoundCorner(DpUtils.dp2px(getContext(), radiusDp));
        }
        return this;
    }


    /**
     * @param checkedColor 选中时指示器颜色
     * @param normalColor  未选中时指示器颜色
     */
    public BannerViewPager<T, VH> setIndicatorColor(@ColorInt int normalColor,
                                                    @ColorInt int checkedColor) {
        indicatorCheckedColor = checkedColor;
        indicatorNormalColor = normalColor;
        return this;
    }

    /**
     * 设置是否自动轮播
     *
     * @param autoPlay 是否自动轮播
     */
    public BannerViewPager<T, VH> setAutoPlay(boolean autoPlay) {
        isAutoPlay = autoPlay;
        if (isAutoPlay) {
            isCanLoop = true;
        }
        return this;
    }

    /**
     * 设置是否可以循环
     *
     * @param canLoop 是否可以循环
     */
    public BannerViewPager<T, VH> setCanLoop(boolean canLoop) {
        isCanLoop = canLoop;
        if (!canLoop) {
            isAutoPlay = false;
        }
        return this;
    }

    /**
     * 设置自动轮播时间间隔
     *
     * @param interval 自动轮播时间间隔
     */
    public BannerViewPager<T, VH> setInterval(int interval) {
        this.interval = interval;
        return this;
    }


    public List<T> getList() {
        return mList;
    }

    /**
     * 设置指示器半径大小
     *
     * @param radiusDp 指示器圆点半径
     */
    public BannerViewPager<T, VH> setIndicatorRadius(float radiusDp) {
        this.normalIndicatorRadius = DpUtils.dp2px(getContext(), radiusDp);
        this.checkedIndicatorRadius = normalIndicatorRadius;
        return this;
    }

    public BannerViewPager<T, VH> setIndicatorRadius(float normalRadius, float checkRadius) {
        this.normalIndicatorRadius = DpUtils.dp2px(getContext(), normalRadius);
        this.checkedIndicatorRadius = DpUtils.dp2px(getContext(), checkRadius);
        return this;
    }

    public BannerViewPager<T, VH> setIndicatorView(IIndicator indicatorView) {
        if (indicatorView instanceof View) {
            initIndicator((View) indicatorView);
        }
        return this;
    }

    /**
     * 设置指示器半径大小
     *
     * @param radiusRes 指示器圆点半径
     */
    public BannerViewPager<T, VH> setIndicatorRadius(@DimenRes int radiusRes) {
        this.normalIndicatorRadius = getContext().getResources().getDimension(radiusRes);
        this.checkedIndicatorRadius = normalIndicatorRadius;
        return this;
    }

    public BannerViewPager<T, VH> setIndicatorRadius(@DimenRes int normalRadius, @DimenRes int checkRadius) {
        this.normalIndicatorRadius = getContext().getResources().getDimension(normalRadius);
        this.checkedIndicatorRadius = getContext().getResources().getDimension(checkRadius);
        return this;
    }

    /**
     * 设置page滚动时间
     *
     * @param scrollDuration page滚动时间
     */
    public BannerViewPager<T, VH> setScrollDuration(int scrollDuration) {
        mScroller.setDuration(scrollDuration);
        return this;
    }

    /**
     * @param showIndicator 是否显示轮播指示器
     */
    public BannerViewPager<T, VH> showIndicator(boolean showIndicator) {
        this.showIndicator = showIndicator;
        return this;
    }

    /**
     * 设置指示器位置
     *
     * @param gravity 指示器位置
     */
    public BannerViewPager<T, VH> setIndicatorGravity(@IndicatorGravity int gravity) {
        this.gravity = gravity;
        return this;
    }

    public BannerViewPager<T, VH> setIndicatorSlideMode(IndicatorSlideMode slideStyle) {
        mIndicatorSlideMode = slideStyle;
        return this;
    }

    /**
     * @param transformer PageTransformer that will modify each page's animation properties
     */
    public BannerViewPager<T, VH> setPageTransformer(ViewPager.PageTransformer transformer) {
        mViewPager.setPageTransformer(true, transformer);
        return this;
    }

    public void setPageTransformerStyle(TransformerStyle style) {
        setPageTransformer(new PageTransformerFactory().createPageTransformer(style));
    }


    public interface OnPageClickListener {
        void onPageClick(int position);
    }


    /**
     * BannerViewPager页面点击事件
     *
     * @param onPageClickListener 页面点击监听
     */
    public BannerViewPager<T, VH> setOnPageClickListener(OnPageClickListener onPageClickListener) {
        this.mOnPageClickListener = onPageClickListener;
        return this;
    }

    public BannerViewPager<T, VH> setIndicatorMargin(float indicatorMarginDp) {
        this.indicatorMargin = indicatorMarginDp;
        return this;
    }

    public BannerViewPager<T, VH> setIndicatorMargin(@DimenRes int marginRes) {
        this.indicatorMargin = getContext().getResources().getDimension(marginRes);
        return this;
    }

    public void create(List<T> list) {
        if (list != null) {
            mList.clear();
            mList.addAll(list);
            initData();
        }
    }

    public ViewPager getViewPager() {
        return mViewPager;
    }

    @IntDef({CENTER, START, END})
    @Retention(RetentionPolicy.SOURCE)
    @Target(ElementType.PARAMETER)
    @interface IndicatorGravity {
    }
}
