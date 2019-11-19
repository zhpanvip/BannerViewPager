package com.zhpan.bannerview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Build;
import android.os.Handler;

import androidx.annotation.ColorInt;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.zhpan.bannerview.annotation.AIndicatorGravity;
import com.zhpan.bannerview.annotation.AIndicatorSlideMode;
import com.zhpan.bannerview.annotation.AIndicatorStyle;
import com.zhpan.bannerview.annotation.APageStyle;
import com.zhpan.bannerview.annotation.ATransformerStyle;
import com.zhpan.bannerview.annotation.Visibility;
import com.zhpan.bannerview.constants.IndicatorSlideMode;
import com.zhpan.bannerview.constants.IndicatorStyle;
import com.zhpan.bannerview.constants.PageStyle;
import com.zhpan.bannerview.indicator.DashIndicatorView;
import com.zhpan.bannerview.indicator.IIndicator;
import com.zhpan.bannerview.indicator.IndicatorFactory;
import com.zhpan.bannerview.transform.pagestyle.ScaleInTransformer;
import com.zhpan.bannerview.utils.BannerUtils;
import com.zhpan.bannerview.adapter.BannerPagerAdapter;
import com.zhpan.bannerview.holder.HolderCreator;
import com.zhpan.bannerview.holder.ViewHolder;
import com.zhpan.bannerview.provider.ViewStyleSetter;
import com.zhpan.bannerview.transform.PageTransformerFactory;
import com.zhpan.bannerview.utils.PositionUtils;
import com.zhpan.bannerview.view.CatchViewPager;

import java.util.ArrayList;
import java.util.List;

import static com.zhpan.bannerview.adapter.BannerPagerAdapter.MAX_VALUE;
import static com.zhpan.bannerview.constants.IndicatorGravity.CENTER;
import static com.zhpan.bannerview.constants.IndicatorGravity.END;
import static com.zhpan.bannerview.constants.IndicatorGravity.START;
import static com.zhpan.bannerview.transform.pagestyle.ScaleInTransformer.DEFAULT_MIN_SCALE;
import static com.zhpan.bannerview.transform.pagestyle.ScaleInTransformer.MAX_SCALE;
import static com.zhpan.bannerview.view.CatchViewPager.DEFAULT_SCROLL_DURATION;

/**
 * Created by zhpan on 2017/3/28.
 */
public class BannerViewPager<T, VH extends ViewHolder> extends RelativeLayout implements
        ViewPager.OnPageChangeListener {

    private int interval;

    private int currentPosition;

    private boolean isLooping;

    private boolean isCanLoop;

    private boolean isAutoPlay = false;

    private int indicatorGravity;

    private int indicatorNormalColor;

    private int indicatorCheckedColor;

    private int normalIndicatorWidth;

    private int checkedIndicatorWidth;

    private OnPageClickListener mOnPageClickListener;

    private IIndicator mIndicatorView;

    private RelativeLayout mIndicatorLayout;

    private int mPageMargin;

    private int mRevealWidth;

    private int mIndicatorStyle;

    private int mIndicatorSlideMode;

    private CatchViewPager mViewPager;

    private List<T> mList;

    private HolderCreator<VH> holderCreator;

    private int indicatorGap;

    private int indicatorHeight;

    private boolean isCustomIndicator;

    private int mPageStyle = PageStyle.NORMAL;

    private IndicatorMargin mIndicatorMargin;

    private Handler mHandler = new Handler();

    private Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            if (mList.size() > 1) {
                currentPosition = mViewPager.getCurrentItem() + 1;
                if (isCanLoop) {
                    if (currentPosition == MAX_VALUE - 1) {
                        currentPosition = 0;
                        mViewPager.setCurrentItem(currentPosition, false);
                        mHandler.post(mRunnable);
                    } else {
                        mViewPager.setCurrentItem(currentPosition);
                        mHandler.postDelayed(mRunnable, interval);
                    }
                } else {
                    if (currentPosition >= MAX_VALUE) {
                        stopLoop();
                    } else {
                        mViewPager.setCurrentItem(currentPosition);
                        mHandler.postDelayed(mRunnable, interval);
                    }
                }
            }
        }
    };
    private int mIndicatorVisibility;
    private int mScrollDuration;
    private int mRoundCorner;
    private boolean disableTouchScroll;

    public BannerViewPager(Context context) {
        this(context, null);
    }

    public BannerViewPager(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BannerViewPager(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        initAttrs(attrs);
        initView();
    }

    private void initView() {
        inflate(getContext(), R.layout.layout_banner_view_pager, this);
        mViewPager = findViewById(R.id.vp_main);
        mIndicatorLayout = findViewById(R.id.rl_indicator);
        mList = new ArrayList<>();
    }

    private void initAttrs(AttributeSet attrs) {
        if (attrs != null) {
            TypedArray typedArray =
                    getContext().obtainStyledAttributes(attrs, R.styleable.BannerViewPager);

            interval = typedArray.getInteger(R.styleable.BannerViewPager_bvp_interval, 3000);
            indicatorCheckedColor =
                    typedArray.getColor(R.styleable.BannerViewPager_bvp_indicator_checked_color,
                            Color.parseColor("#8C18171C"));
            indicatorNormalColor =
                    typedArray.getColor(R.styleable.BannerViewPager_bvp_indicator_normal_color,
                            Color.parseColor("#8C6C6D72"));
            normalIndicatorWidth = (int) typedArray.getDimension(R.styleable.BannerViewPager_bvp_indicator_radius,
                    BannerUtils.dp2px(8));
            isAutoPlay = typedArray.getBoolean(R.styleable.BannerViewPager_bvp_auto_play, true);
            isCanLoop = typedArray.getBoolean(R.styleable.BannerViewPager_bvp_can_loop, true);
            mPageMargin = (int) typedArray.getDimension(R.styleable.BannerViewPager_bvp_page_margin, 0);
            mRoundCorner = (int) typedArray.getDimension(R.styleable.BannerViewPager_bvp_round_corner, 0);
            mRevealWidth = (int) typedArray.getDimension(R.styleable.BannerViewPager_bvp_reveal_width, 0);
            indicatorGravity = typedArray.getInt(R.styleable.BannerViewPager_bvp_indicator_gravity, 0);
            mPageStyle = typedArray.getInt(R.styleable.BannerViewPager_bvp_page_style, 0);
            mIndicatorStyle = typedArray.getInt(R.styleable.BannerViewPager_bvp_indicator_style, 0);
            mIndicatorSlideMode = typedArray.getInt(R.styleable.BannerViewPager_bvp_indicator_slide_mode, 0);
            mIndicatorVisibility = typedArray.getInt(R.styleable.BannerViewPager_bvp_indicator_visibility, 0);
            mScrollDuration = typedArray.getInt(R.styleable.BannerViewPager_bvp_scroll_duration, DEFAULT_SCROLL_DURATION);
            typedArray.recycle();
            indicatorGap = normalIndicatorWidth;
            indicatorHeight = normalIndicatorWidth / 2;
            checkedIndicatorWidth = normalIndicatorWidth;
        }
    }

    private void initBannerData(List<T> list) {
        if (list != null) {
            mList.clear();
            mList.addAll(list);
            if (mList.size() > 0) {
                if (mList.size() > 1) {
                    if (isCustomIndicator && null != mIndicatorView) {
                        initIndicator(mIndicatorView);
                    } else {
                        initIndicator(IndicatorFactory.createIndicatorView(getContext(), mIndicatorStyle));
                    }
                }
                if (isCanLoop) {
                    currentPosition = MAX_VALUE / 2 - ((MAX_VALUE / 2) % mList.size()) + 1;
                }
                setupViewPager();
                setIndicatorValues();
            }
        }
    }


    private void setIndicatorValues() {
        if (null != mIndicatorView) {
            mIndicatorView.setPageSize(mList.size());
            mIndicatorView.setCheckedColor(indicatorCheckedColor);
            mIndicatorView.setNormalColor(indicatorNormalColor);
            mIndicatorView.setIndicatorGap(indicatorGap);
            mIndicatorView.setSlideMode(mIndicatorSlideMode);
            mIndicatorView.setIndicatorWidth(normalIndicatorWidth, checkedIndicatorWidth);
            if (mIndicatorView instanceof DashIndicatorView) {
                ((DashIndicatorView) mIndicatorView).setSliderHeight(indicatorHeight);
            }
            mIndicatorView.notifyDataChanged();
        }
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

    private void initIndicator(IIndicator indicatorView) {
        mIndicatorLayout.setVisibility(mIndicatorVisibility);
        mIndicatorView = indicatorView;
        if (((View) mIndicatorView).getParent() == null) {
            mIndicatorLayout.removeAllViews();
            mIndicatorLayout.addView((View) mIndicatorView);
            initIndicatorViewMargin();
            initIndicatorGravity();
        }
    }

    private void initIndicatorGravity() {
        RelativeLayout.LayoutParams layoutParams =
                (RelativeLayout.LayoutParams) ((View) mIndicatorView).getLayoutParams();
        switch (indicatorGravity) {
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

    private void initIndicatorViewMargin() {
        ViewGroup.MarginLayoutParams layoutParams = (MarginLayoutParams) ((View) mIndicatorView).getLayoutParams();
        if (mIndicatorMargin == null) {
            int dp10 = BannerUtils.dp2px(10);
            layoutParams.setMargins(dp10, dp10, dp10, dp10);
        } else {
            layoutParams.setMargins(mIndicatorMargin.left, mIndicatorMargin.top,
                    mIndicatorMargin.right, mIndicatorMargin.bottom);
        }
    }

    private void setupViewPager() {
        if (holderCreator != null) {
            removeAllViews();
            BannerPagerAdapter<T, VH> bannerPagerAdapter =
                    new BannerPagerAdapter<>(mList, holderCreator);
            bannerPagerAdapter.setCanLoop(isCanLoop);
            bannerPagerAdapter.setPageClickListener(position -> {
                if (mOnPageClickListener != null) {
                    mOnPageClickListener.onPageClick(position);
                }
            });

            mViewPager.setAdapter(bannerPagerAdapter);
            mViewPager.setCurrentItem(currentPosition);
            mViewPager.addOnPageChangeListener(this);
            mViewPager.setScrollDuration(mScrollDuration);
            mViewPager.disableTouchScroll(disableTouchScroll);
            mViewPager.setFirstLayout(true);
            addView(mViewPager);
            addView(mIndicatorLayout);
            initPageStyle();
            startLoop();
            setTouchListener();
        } else {
            throw new NullPointerException("You must set HolderCreator for BannerViewPager");
        }

        if (mRoundCorner > 0 && Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ViewStyleSetter viewStyleSetter = new ViewStyleSetter(this);
            viewStyleSetter.setRoundCorner(mRoundCorner);
        }
    }

    private void initPageStyle() {
        switch (mPageStyle) {
            case PageStyle.MULTI_PAGE:
                setMultiPageStyle(false, MAX_SCALE);
                break;
            case PageStyle.MULTI_PAGE_OVERLAP:
                setMultiPageStyle(true, DEFAULT_MIN_SCALE);
                break;
            case PageStyle.MULTI_PAGE_SCALE:
                setMultiPageStyle(false, DEFAULT_MIN_SCALE);
                break;
        }
    }

    private void setMultiPageStyle(boolean overlap, float scale) {
        mPageMargin = mPageMargin == 0 ? BannerUtils.dp2px(20) : mPageMargin;
        mRevealWidth = mRevealWidth == 0 ? BannerUtils.dp2px(20) : mRevealWidth;
        setClipChildren(false);
        ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) mViewPager.getLayoutParams();
        params.leftMargin = mPageMargin + mRevealWidth;
        params.rightMargin = mPageMargin + mRevealWidth;
        mViewPager.setOverlapStyle(overlap);
        mViewPager.setPageMargin(overlap ? -mPageMargin : mPageMargin);
        mViewPager.setOffscreenPageLimit(2);
        setPageTransformer(new ScaleInTransformer(scale));
    }

    @Override
    public void onPageSelected(int position) {
        currentPosition = PositionUtils.getRealPosition(isCanLoop, position, mList.size());
        if (mOnPageChangeListener != null)
            mOnPageChangeListener.onPageSelected(currentPosition);
        if (mIndicatorView != null) {
            mIndicatorView.onPageSelected(currentPosition);
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        if (mIndicatorView != null) {
            mIndicatorView.onPageScrollStateChanged(state);
        }
        if (mOnPageChangeListener != null) {
            mOnPageChangeListener.onPageScrollStateChanged(state);
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        if (mOnPageChangeListener != null) {
            mOnPageChangeListener.onPageScrolled(PositionUtils.getRealPosition(isCanLoop, position, mList.size()),
                    positionOffset, positionOffsetPixels);
        }
        if (mIndicatorView != null)
            mIndicatorView.onPageScrolled(PositionUtils.getRealPosition(isCanLoop, position, mList.size()),
                    positionOffset, positionOffsetPixels);
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
     * 设置圆角ViewPager 只有在SDK_INT>=LOLLIPOP(API 21)时有效
     *
     * @param radius 圆角大小
     */
    public BannerViewPager<T, VH> setRoundCorner(int radius) {
        mRoundCorner = radius;
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
     * @param interval 自动轮播时间间隔 单位毫秒ms
     */
    public BannerViewPager<T, VH> setInterval(int interval) {
        this.interval = interval;
        return this;
    }

    /**
     * 设置页面Transformer内置样式
     */
    public BannerViewPager<T, VH> setPageTransformerStyle(@ATransformerStyle int style) {
        mViewPager.setPageTransformer(true, new PageTransformerFactory().createPageTransformer(style));
        return this;
    }

    /**
     * @param transformer PageTransformer that will modify each page's animation properties
     */
    public void setPageTransformer(@Nullable ViewPager.PageTransformer transformer) {
        mViewPager.setPageTransformer(true, transformer);
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
        mScrollDuration = scrollDuration;
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
     */
    public BannerViewPager<T, VH> setIndicatorRadius(int radius) {
        this.normalIndicatorWidth = radius * 2;
        this.checkedIndicatorWidth = radius * 2;
        return this;
    }

    /**
     * 设置Indicator半径
     *
     * @param normalRadius  未选中时半径
     * @param checkedRadius 选中时半径
     */
    public BannerViewPager<T, VH> setIndicatorRadius(int normalRadius, int checkedRadius) {
        this.normalIndicatorWidth = normalRadius * 2;
        this.checkedIndicatorWidth = checkedRadius * 2;
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
     * @deprecated Use {@link #setIndicatorVisibility(int)} instead.
     */
    @Deprecated
    public BannerViewPager<T, VH> showIndicator(boolean showIndicator) {
        mIndicatorLayout.setVisibility(showIndicator ? VISIBLE : GONE);
        return this;
    }

    public BannerViewPager<T, VH> setIndicatorVisibility(@Visibility int visibility) {
        mIndicatorVisibility = visibility;
        return this;
    }

    /**
     * 设置指示器位置
     *
     * @param gravity 指示器位置
     *                {@link com.zhpan.bannerview.constants.IndicatorGravity#CENTER}
     *                {@link com.zhpan.bannerview.constants.IndicatorGravity#START}
     *                {@link com.zhpan.bannerview.constants.IndicatorGravity#END}
     */
    public BannerViewPager<T, VH> setIndicatorGravity(@AIndicatorGravity int gravity) {
        this.indicatorGravity = gravity;
        return this;
    }

    /**
     * 设置IndicatorView滑动模式，默认值{@link IndicatorSlideMode#NORMAL}
     *
     * @param slideMode Indicator滑动模式
     * @see com.zhpan.bannerview.constants.IndicatorSlideMode#NORMAL
     * @see com.zhpan.bannerview.constants.IndicatorSlideMode#SMOOTH
     */
    public BannerViewPager<T, VH> setIndicatorSlideMode(@AIndicatorSlideMode int slideMode) {
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
    public BannerViewPager<T, VH> setIndicatorStyle(@AIndicatorStyle int indicatorStyle) {
        mIndicatorStyle = indicatorStyle;
        return this;
    }

    /**
     * 构造ViewPager
     *
     * @param list ViewPager数据
     */
    public void create(List<T> list) {
        initBannerData(list);
    }

    /**
     * @return the currently selected page position.
     */
    public int getCurrentItem() {
        return currentPosition;
    }

    /**
     * Set the currently selected page. If the ViewPager has already been through its first
     * layout with its current adapter there will be a smooth animated transition between
     * the current item and the specified item.
     *
     * @param item Item index to select
     */
    public void setCurrentItem(int item) {
        mViewPager.setCurrentItem(isCanLoop ? MAX_VALUE / 2 - ((MAX_VALUE / 2) % mList.size()) + 1 + item : item);
    }

    /**
     * Set the currently selected page.
     *
     * @param item         Item index to select
     * @param smoothScroll True to smoothly scroll to the new item, false to transition immediately
     */
    public void setCurrentItem(int item, boolean smoothScroll) {
        mViewPager.setCurrentItem(isCanLoop ? MAX_VALUE / 2 - ((MAX_VALUE / 2) % mList.size()) + 1 + item : item, smoothScroll);
    }

    /**
     * Set Page Style for Banner
     * {@link PageStyle#NORMAL}
     * {@link PageStyle#MULTI_PAGE}
     *
     * @return BannerViewPager
     */
    public BannerViewPager<T, VH> setPageStyle(@APageStyle int pageStyle) {
        mPageStyle = pageStyle;
        return this;
    }

    /**
     * 设置item间距
     *
     * @param pageMargin item间距
     * @return BannerViewPager
     */
    public BannerViewPager<T, VH> setPageMargin(int pageMargin) {
        mPageMargin = pageMargin;
        mViewPager.setPageMargin(pageMargin);
        return this;
    }

    /**
     * @param revealWidth 一屏多页模式下两边页面显露出来的宽度
     */
    public BannerViewPager<T, VH> setRevealWidth(int revealWidth) {
        mRevealWidth = revealWidth;
        return this;
    }

    /**
     * 获取BannerViewPager中封装的ViewPager，用于设置BannerViewPager未暴露出来的接口，
     * 比如setCurrentItem等。
     * <p>
     * 通过该方法调用getCurrentItem等方法可能会有问题
     * 2.4.1已废弃，可直接调用BannerViewPager中相关方法替代
     *
     * @return BannerViewPager中封装的ViewPager
     */
    @Deprecated
    public ViewPager getViewPager() {
        return mViewPager;
    }

    public BannerViewPager<T, VH> setIndicatorMargin(int left, int top, int right, int bottom) {
        mIndicatorMargin = new IndicatorMargin();
        mIndicatorMargin.bottom = bottom;
        mIndicatorMargin.left = left;
        mIndicatorMargin.top = top;
        mIndicatorMargin.right = right;
        return this;
    }

    public BannerViewPager<T, VH> disableTouchScroll(boolean disableTouchScroll) {
        this.disableTouchScroll = disableTouchScroll;
        return this;
    }


    /**
     * 仅供demo使用
     */
//    @Deprecated
//    public void resetIndicator() {
//        isCustomIndicator = false;
//        mIndicatorView = null;
//    }

    /**
     * 页面点击事件接口
     */
    public interface OnPageClickListener {
        void onPageClick(int position);
    }

    private static class IndicatorMargin {
        private int left, right, top, bottom;
    }

    private ViewPager.OnPageChangeListener mOnPageChangeListener;

    public BannerViewPager<T, VH> setOnPageChangeListener(ViewPager.OnPageChangeListener onPageChangeListener) {
        mOnPageChangeListener = onPageChangeListener;
        return this;
    }
}
