package com.zhpan.bannerview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Build;
import android.os.Handler;

import androidx.annotation.ColorInt;
import androidx.annotation.IntDef;
import androidx.viewpager.widget.ViewPager;

import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.zhpan.bannerview.enums.IndicatorStyle;
import com.zhpan.bannerview.indicator.BaseIndicatorView;
import com.zhpan.bannerview.indicator.DashIndicatorView;
import com.zhpan.bannerview.indicator.IIndicator;
import com.zhpan.bannerview.indicator.IndicatorFactory;
import com.zhpan.bannerview.utils.DpUtils;
import com.zhpan.bannerview.adapter.BannerPagerAdapter;
import com.zhpan.bannerview.enums.IndicatorSlideMode;
import com.zhpan.bannerview.holder.HolderCreator;
import com.zhpan.bannerview.holder.ViewHolder;
import com.zhpan.bannerview.provider.BannerScroller;
import com.zhpan.bannerview.provider.ViewStyleSetter;
import com.zhpan.bannerview.transform.PageTransformerFactory;
import com.zhpan.bannerview.enums.TransformerStyle;

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

    private ViewPager mViewPager;
    // 轮播数据集合
    private List<T> mList;
    // 页面切换时间间隔
    private int interval;
    // 当前页面位置
    private int currentPosition;
    // 是否正在循环
    private boolean isLooping;
    // 是否开启循环
    private boolean isCanLoop;
    // 是否开启自动播放
    private boolean isAutoPlay = false;
    // 是否显示指示器
    private boolean showIndicator = true;
    // 指示器显示位置
    public static final int START = 1;
    public static final int END = 2;
    public static final int CENTER = 0;
    private int gravity;
    // 未选中时指示器颜色
    private int indicatorNormalColor;
    // 选中时的指示器颜色
    private int indicatorCheckedColor;
    // 指示器宽度/直径
    private int normalIndicatorWidth;
    // 选中时指示宽度/直径
    private int checkedIndicatorWidth;

    // 页面点击事件监听
    private OnPageClickListener mOnPageClickListener;
    // 轮播指示器
    private IIndicator mIndicatorView;
    //  存放IndicatorView的容器
    RelativeLayout mRelativeLayout;
    /**
     * 指示器Style样式
     *
     * @see IndicatorStyle#CIRCLE 圆形指示器
     * @see IndicatorStyle#DASH  虚线指示器
     */
    private IndicatorStyle mIndicatorStyle;


    private HolderCreator<VH> holderCreator;
    // IndicatorView的滑动模式
    private IndicatorSlideMode mIndicatorSlideMode;

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

    private int indicatorGap;
    private int indicatorHeight;
    private boolean isCustomIndicator;

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
            normalIndicatorWidth = (int) typedArray.getDimension(R.styleable.BannerViewPager_indicator_radius,
                    DpUtils.dp2px(8));
            indicatorGap = normalIndicatorWidth;
            indicatorHeight = normalIndicatorWidth / 2;
            checkedIndicatorWidth = normalIndicatorWidth;
            mIndicatorStyle = IndicatorStyle.CIRCLE;
            mIndicatorSlideMode = IndicatorSlideMode.NORMAL;
            isAutoPlay = typedArray.getBoolean(R.styleable.BannerViewPager_isAutoPlay, true);
            isCanLoop = typedArray.getBoolean(R.styleable.BannerViewPager_isCanLoop, true);
            gravity = typedArray.getInt(R.styleable.BannerViewPager_indicator_gravity, 0);
            typedArray.recycle();
        }
    }

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
     * 初始化IndicatorView及ViewPager
     */
    private void initData() {
        if (mList.size() > 0) {
            if (mList.size() > 1 && showIndicator) {
                if (isCustomIndicator && null != mIndicatorView) {
                    initIndicator(mIndicatorView);
                } else {
                    initIndicator(getIndicatorView());
                }
            }
            if (isCanLoop) {
                currentPosition = 1;
            }
            setupViewPager();
        }
    }

    private BaseIndicatorView getIndicatorView() {
        BaseIndicatorView indicatorView = IndicatorFactory.createIndicatorView(getContext(), mIndicatorStyle);
        indicatorView.setPageSize(mList.size());
        indicatorView.setIndicatorWidth(normalIndicatorWidth, checkedIndicatorWidth);
        indicatorView.setIndicatorGap(indicatorGap);
        indicatorView.setCheckedColor(indicatorCheckedColor);
        indicatorView.setNormalColor(indicatorNormalColor);
        indicatorView.setSlideMode(mIndicatorSlideMode);
        if (indicatorView instanceof DashIndicatorView) {
            ((DashIndicatorView) indicatorView).setSliderHeight(indicatorHeight);
        }
        indicatorView.invalidate();
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
    private void initIndicator(IIndicator indicatorView) {
        mRelativeLayout.removeAllViews();
        mRelativeLayout.addView((View) indicatorView);
        mIndicatorView = indicatorView;
        RelativeLayout.LayoutParams layoutParams =
                (RelativeLayout.LayoutParams) ((View) indicatorView).getLayoutParams();
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
        if (showIndicator && mIndicatorView != null) {
            mIndicatorView.onPageScrollStateChanged(state);
        }
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
        if (showIndicator && mIndicatorView != null) {
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
     * @return BannerViewPager数据集合
     */
    public List<T> getList() {
        return mList;
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

    /**
     * 必须为BannerViewPager设置HolderCreator,HolderCreator中创建ViewHolder，
     * 在ViewHolder中管理BannerViewPager的ItemView.
     *
     * @param holderCreator HolderCreator
     */
    public BannerViewPager<T, VH> setHolderCreator(HolderCreator<VH> holderCreator) {
        this.holderCreator = holderCreator;
        return this;
    }


    /**
     * 设置圆角ViewPager
     *
     * @param radius 圆角大小
     */
    public BannerViewPager<T, VH> setRoundCorner(int radius) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ViewStyleSetter viewStyleSetter = new ViewStyleSetter(this);
            viewStyleSetter.setRoundCorner(radius);
        }
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

    /**
     * 设置页面Transformer内置样式
     */
    public BannerViewPager<T, VH> setPageTransformerStyle(TransformerStyle style) {
        mViewPager.setPageTransformer(true, new PageTransformerFactory().createPageTransformer(style));
        return this;
    }


    /**
     * 设置页面点击事件
     *
     * @param onPageClickListener 页面点击监听
     */
    public BannerViewPager<T, VH> setOnPageClickListener(OnPageClickListener onPageClickListener) {
        this.mOnPageClickListener = onPageClickListener;
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
     * 设置指示器半径大小，选中与未选中半径大小相等
     *
     * @param radius 指示器圆点半径
     * @return
     */
    public BannerViewPager<T, VH> setIndicatorRadius(int radius) {
        this.normalIndicatorWidth = radius * 2;
        this.checkedIndicatorWidth = radius * 2;
        return this;
    }

    /**
     * 设置Indicator半径
     *
     * @param normalRadius 未选中时半径
     * @param checkRadius  选中时半径
     */
    public BannerViewPager<T, VH> setIndicatorRadius(int normalRadius, int checkRadius) {
        this.normalIndicatorWidth = normalRadius * 2;
        this.checkedIndicatorWidth = checkRadius * 2;
        return this;
    }


    /**
     * 设置单个Indicator宽度，如果是圆则为圆的直径
     *
     * @param indicatorWidth 单个Indicator宽度/直径
     */
    public BannerViewPager<T, VH> setIndicatorWidth(int indicatorWidth) {
        this.normalIndicatorWidth = indicatorWidth;
        this.checkedIndicatorWidth = indicatorWidth;
        return this;
    }


    /**
     * 设置单个Indicator宽度，如果是圆则为圆的直径
     *
     * @param normalWidth 未选中时宽度/直径
     * @param checkWidth  选中时宽度/直径
     */
    public BannerViewPager<T, VH> setIndicatorWidth(int normalWidth, int checkWidth) {
        this.normalIndicatorWidth = normalWidth;
        this.checkedIndicatorWidth = checkWidth;
        return this;
    }

    public BannerViewPager<T, VH> setIndicatorHeight(int indicatorHeight) {
        this.indicatorHeight = indicatorHeight;
        return this;
    }

    /**
     * 设置指示器间隔
     *
     * @param indicatorGap 指示器间隔
     * @return BannerViewPager
     */
    public BannerViewPager<T, VH> setIndicatorGap(int indicatorGap) {
        this.indicatorGap = indicatorGap;
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
     *                {@link BannerViewPager#CENTER}
     *                {@link BannerViewPager#START}
     *                {@link BannerViewPager#END}
     */
    public BannerViewPager<T, VH> setIndicatorGravity(@IndicatorGravity int gravity) {
        this.gravity = gravity;
        return this;
    }

    /**
     * 设置IndicatorView滑动模式，默认值{@link IndicatorSlideMode#SMOOTH}
     *
     * @param slideMode Indicator滑动模式
     * @see com.zhpan.bannerview.enums.IndicatorSlideMode#NORMAL
     * @see com.zhpan.bannerview.enums.IndicatorSlideMode#SMOOTH
     */
    public BannerViewPager<T, VH> setIndicatorSlideMode(IndicatorSlideMode slideMode) {
        mIndicatorSlideMode = slideMode;
        return this;
    }


    /**
     * 设置自定义View指示器,自定义View需要需要继承BaseIndicator或者实现IIndicator接口自行绘制指示器。
     * 注意，一旦设置了自定义IndicatorView,通过BannerViewPager设置的部分IndicatorView参数将失效。
     *
     * @param customIndicator 自定义指示器
     */
    public BannerViewPager<T, VH> setIndicatorView(IIndicator customIndicator) {
        if (customIndicator instanceof View) {
            isCustomIndicator = true;
            mIndicatorView = customIndicator;
//            initIndicator((View) customIndicator);
        }
        return this;
    }

    /**
     * 设置Indicator样式
     *
     * @param indicatorStyle indicator样式，目前有圆和断线两种样式
     *                       {@link IndicatorStyle#CIRCLE}
     *                       {@link IndicatorStyle#DASH}
     */
    public BannerViewPager<T, VH> setIndicatorStyle(IndicatorStyle indicatorStyle) {
        mIndicatorStyle = indicatorStyle;
        return this;
    }

    /**
     * 构造ViewPager
     *
     * @param list ViewPager数据
     */
    public void create(List<T> list) {
        if (list != null) {
            mList.clear();
            mList.addAll(list);
            initData();
            if (showIndicator && null != mIndicatorView) {
                mIndicatorView.setPageSize(mList.size());
                mIndicatorView.notifyDataChanged();
            }
        }
    }

    /**
     * 获取BannerViewPager中封装的ViewPager，用于设置BannerViewPager未暴露出来的接口，
     * 比如setCurrentItem等。
     *
     * @return BannerViewPager中封装的ViewPager
     */
    public ViewPager getViewPager() {
        return mViewPager;
    }

    @IntDef({CENTER, START, END})
    @Retention(RetentionPolicy.SOURCE)
    @Target(ElementType.PARAMETER)
    @interface IndicatorGravity {
    }

    /**
     * 页面点击事件接口
     */
    public interface OnPageClickListener {
        void onPageClick(int position);
    }
}
