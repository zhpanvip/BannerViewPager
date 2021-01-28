package com.zhpan.bannerview;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.zhpan.bannerview.annotation.AIndicatorGravity;
import com.zhpan.bannerview.annotation.APageStyle;
import com.zhpan.bannerview.annotation.Visibility;
import com.zhpan.bannerview.constants.PageStyle;
import com.zhpan.bannerview.manager.BannerManager;
import com.zhpan.bannerview.manager.BannerOptions;
import com.zhpan.bannerview.provider.ReflectLayoutManager;
import com.zhpan.bannerview.provider.ViewStyleSetter;
import com.zhpan.bannerview.utils.BannerUtils;
import com.zhpan.indicator.IndicatorView;
import com.zhpan.indicator.annotation.AIndicatorSlideMode;
import com.zhpan.indicator.annotation.AIndicatorStyle;
import com.zhpan.indicator.base.IIndicator;
import com.zhpan.indicator.enums.IndicatorOrientation;
import com.zhpan.indicator.option.IndicatorOptions;

import java.util.ArrayList;
import java.util.List;

import static android.os.Build.VERSION_CODES.JELLY_BEAN_MR1;
import static com.zhpan.bannerview.BaseBannerAdapter.MAX_VALUE;
import static com.zhpan.bannerview.constants.IndicatorGravity.CENTER;
import static com.zhpan.bannerview.constants.IndicatorGravity.END;
import static com.zhpan.bannerview.constants.IndicatorGravity.START;
import static com.zhpan.bannerview.manager.BannerOptions.DEFAULT_REVEAL_WIDTH;
import static com.zhpan.bannerview.transform.ScaleInTransformer.DEFAULT_MIN_SCALE;
import static com.zhpan.bannerview.utils.BannerUtils.getOriginalPosition;

/**
 * Created by zhpan on 2017/3/28.
 */
@SuppressWarnings({"unused", "UnusedReturnValue"})
public class BannerViewPager<T> extends RelativeLayout implements LifecycleObserver {

    private int currentPosition;

    private boolean isCustomIndicator;

    private boolean isLooping;

    private OnPageClickListener mOnPageClickListener;

    private IIndicator mIndicatorView;

    private RelativeLayout mIndicatorLayout;

    private ViewPager2 mViewPager;

    private BannerManager mBannerManager;

    private final Handler mHandler = new Handler();

    private BaseBannerAdapter<T> mBannerPagerAdapter;

    private ViewPager2.OnPageChangeCallback onPageChangeCallback;

    private final Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            handlePosition();
        }
    };

    private int startX, startY;

    private final ViewPager2.OnPageChangeCallback mOnPageChangeCallback = new ViewPager2.OnPageChangeCallback() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            super.onPageScrolled(position, positionOffset, positionOffsetPixels);
            pageScrolled(position, positionOffset, positionOffsetPixels);
        }

        @Override
        public void onPageSelected(int position) {
            super.onPageSelected(position);
            pageSelected(position);
        }

        @Override
        public void onPageScrollStateChanged(int state) {
            super.onPageScrollStateChanged(state);
            pageScrollStateChanged(state);
        }
    };

    public BannerViewPager(Context context) {
        this(context, null);
    }

    public BannerViewPager(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BannerViewPager(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        mBannerManager = new BannerManager();
        mBannerManager.initAttrs(context, attrs);
        initView();
    }

    private void initView() {
        inflate(getContext(), R.layout.bvp_layout, this);
        mViewPager = findViewById(R.id.vp_main);
        mIndicatorLayout = findViewById(R.id.bvp_layout_indicator);
        mViewPager.setPageTransformer(mBannerManager.getCompositePageTransformer());
    }

    @Override
    protected void onDetachedFromWindow() {
        stopLoop();
        super.onDetachedFromWindow();
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        startLoop();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                isLooping = true;
                stopLoop();
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_OUTSIDE:
                isLooping = false;
                startLoop();
                break;
            default:
                break;
        }
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        boolean doNotNeedIntercept = !mViewPager.isUserInputEnabled()
                || mBannerPagerAdapter != null
                && mBannerPagerAdapter.getData().size() <= 1;
        if (doNotNeedIntercept) {
            return super.onInterceptTouchEvent(ev);
        }
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                startX = (int) ev.getX();
                startY = (int) ev.getY();
                getParent().requestDisallowInterceptTouchEvent(!mBannerManager
                        .getBannerOptions().isDisallowParentInterceptDownEvent());
                break;
            case MotionEvent.ACTION_MOVE:
                int endX = (int) ev.getX();
                int endY = (int) ev.getY();
                int disX = Math.abs(endX - startX);
                int disY = Math.abs(endY - startY);
                int orientation = mBannerManager.getBannerOptions().getOrientation();
                if (orientation == ViewPager2.ORIENTATION_VERTICAL) {
                    onVerticalActionMove(endY, disX, disY);
                } else if (orientation == ViewPager2.ORIENTATION_HORIZONTAL) {
                    onHorizontalActionMove(endX, disX, disY);
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                getParent().requestDisallowInterceptTouchEvent(false);
                break;
            case MotionEvent.ACTION_OUTSIDE:
            default:
                break;
        }
        return super.onInterceptTouchEvent(ev);
    }

    private void onVerticalActionMove(int endY, int disX, int disY) {
        if (disY > disX) {
            boolean canLoop = mBannerManager.getBannerOptions().isCanLoop();
            if (!canLoop) {
                if (currentPosition == 0 && endY - startY > 0) {
                    getParent().requestDisallowInterceptTouchEvent(false);
                } else {
                    getParent().requestDisallowInterceptTouchEvent(currentPosition != getData().size() - 1
                            || endY - startY >= 0);
                }
            } else {
                getParent().requestDisallowInterceptTouchEvent(true);
            }
        } else if (disX > disY) {
            getParent().requestDisallowInterceptTouchEvent(false);
        }
    }

    private void onHorizontalActionMove(int endX, int disX, int disY) {
        if (disX > disY) {
            boolean canLoop = mBannerManager.getBannerOptions().isCanLoop();
            if (!canLoop) {
                if (currentPosition == 0 && endX - startX > 0) {
                    getParent().requestDisallowInterceptTouchEvent(false);
                } else {
                    getParent().requestDisallowInterceptTouchEvent(currentPosition != getData().size() - 1
                            || endX - startX >= 0);
                }
            } else {
                getParent().requestDisallowInterceptTouchEvent(true);
            }
        } else if (disY > disX) {
            getParent().requestDisallowInterceptTouchEvent(false);
        }
    }


    private void pageScrollStateChanged(int state) {
        if (mIndicatorView != null) {
            mIndicatorView.onPageScrollStateChanged(state);
        }
        if (onPageChangeCallback != null) {
            onPageChangeCallback.onPageScrollStateChanged(state);
        }
    }

    private void pageSelected(int position) {
        int size = mBannerPagerAdapter.getListSize();
        boolean canLoop = mBannerManager.getBannerOptions().isCanLoop();
        currentPosition = BannerUtils.getRealPosition(position, size);
        boolean needResetCurrentItem = size > 0 && canLoop && (position == 0 || position == MAX_VALUE - 1);
        if (needResetCurrentItem) {
            resetCurrentItem(currentPosition);
        }
        if (onPageChangeCallback != null) {
            onPageChangeCallback.onPageSelected(currentPosition);
        }
        if (mIndicatorView != null) {
            mIndicatorView.onPageSelected(currentPosition);
        }
    }

    private void pageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        int listSize = mBannerPagerAdapter.getListSize();
        boolean canLoop = mBannerManager.getBannerOptions().isCanLoop();
        int realPosition = BannerUtils.getRealPosition(position, listSize);
        if (listSize > 0) {
            if (onPageChangeCallback != null) {
                onPageChangeCallback.onPageScrolled(realPosition, positionOffset, positionOffsetPixels);
            }
            if (mIndicatorView != null) {
                mIndicatorView.onPageScrolled(realPosition, positionOffset, positionOffsetPixels);
            }
        }
    }

    private void handlePosition() {
        if (mBannerPagerAdapter.getListSize() > 1 && isAutoPlay()) {
            mViewPager.setCurrentItem(mViewPager.getCurrentItem() + 1);
            mHandler.postDelayed(mRunnable, getInterval());
        }
    }

    private void initBannerData() {
        List<T> list = mBannerPagerAdapter.getData();
        if (list != null) {
            setIndicatorValues(list);
            setupViewPager(list);
            initRoundCorner();
        }
    }

    private void setIndicatorValues(List<? extends T> list) {
        mIndicatorLayout.setVisibility(mBannerManager.getBannerOptions().getIndicatorVisibility());
        BannerOptions bannerOptions = mBannerManager.getBannerOptions();
        bannerOptions.resetIndicatorOptions();
        if (!isCustomIndicator || null == mIndicatorView) {
            mIndicatorView = new IndicatorView(getContext());
        }
        initIndicator(bannerOptions.getIndicatorOptions(), list);
    }

    private void initIndicator(IndicatorOptions indicatorOptions, List<? extends T> list) {
        if (((View) mIndicatorView).getParent() == null) {
            mIndicatorLayout.removeAllViews();
            mIndicatorLayout.addView((View) mIndicatorView);
            initIndicatorSliderMargin();
            initIndicatorGravity();
        }
        mIndicatorView.setIndicatorOptions(indicatorOptions);
        indicatorOptions.setPageSize(list.size());
        mIndicatorView.notifyDataChanged();
    }

    private void initIndicatorGravity() {
        LayoutParams layoutParams =
                (LayoutParams) ((View) mIndicatorView).getLayoutParams();
        switch (mBannerManager.getBannerOptions().getIndicatorGravity()) {
            case CENTER:
                layoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
                break;
            case START:
                layoutParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
                break;
            case END:
                layoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                break;
            default:
                break;
        }
    }

    private void initIndicatorSliderMargin() {
        MarginLayoutParams layoutParams = (MarginLayoutParams) ((View) mIndicatorView).getLayoutParams();
        BannerOptions.IndicatorMargin indicatorMargin = mBannerManager.getBannerOptions().getIndicatorMargin();
        if (indicatorMargin == null) {
            int dp10 = BannerUtils.dp2px(10);
            layoutParams.setMargins(dp10, dp10, dp10, dp10);
        } else {
            layoutParams.setMargins(indicatorMargin.getLeft(), indicatorMargin.getTop(),
                    indicatorMargin.getRight(), indicatorMargin.getBottom());
        }
    }

    private void initRoundCorner() {
        int roundCorner = mBannerManager.getBannerOptions().getRoundRectRadius();
        if (roundCorner > 0 && Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ViewStyleSetter.applyRoundCorner(this, roundCorner);
        }
    }

    private void setupViewPager(List<T> list) {
        if (mBannerPagerAdapter == null) {
            throw new NullPointerException("You must set adapter for BannerViewPager");
        }
        BannerOptions bannerOptions = mBannerManager.getBannerOptions();
        if (bannerOptions.getScrollDuration() != 0) {
            ReflectLayoutManager.reflectLayoutManager(mViewPager, bannerOptions.getScrollDuration());
        }
        currentPosition = 0;
        mBannerPagerAdapter.setCanLoop(bannerOptions.isCanLoop());
        mBannerPagerAdapter.setPageClickListener(mOnPageClickListener);
        mViewPager.setAdapter(mBannerPagerAdapter);
        if (isCanLoopSafely()) {
            mViewPager.setCurrentItem(getOriginalPosition(list.size()), false);
        }
        mViewPager.unregisterOnPageChangeCallback(mOnPageChangeCallback);
        mViewPager.registerOnPageChangeCallback(mOnPageChangeCallback);
        mViewPager.setOrientation(bannerOptions.getOrientation());
        mViewPager.setOffscreenPageLimit(bannerOptions.getOffScreenPageLimit());
        initRevealWidth(bannerOptions);
        initPageStyle(bannerOptions.getPageStyle());
        startLoop();
    }

    private void initRevealWidth(BannerOptions bannerOptions) {
        int rightRevealWidth = bannerOptions.getRightRevealWidth();
        int leftRevealWidth = bannerOptions.getLeftRevealWidth();
        if (leftRevealWidth != DEFAULT_REVEAL_WIDTH || rightRevealWidth != DEFAULT_REVEAL_WIDTH) {
            RecyclerView recyclerView = (RecyclerView) mViewPager.getChildAt(0);
            int orientation = bannerOptions.getOrientation();
            int padding2 = bannerOptions.getPageMargin() + rightRevealWidth;
            int padding1 = bannerOptions.getPageMargin() + leftRevealWidth;
            if (orientation == ViewPager2.ORIENTATION_HORIZONTAL) {
                recyclerView.setPadding(padding1, 0, padding2, 0);
            } else if (orientation == ViewPager2.ORIENTATION_VERTICAL) {
                recyclerView.setPadding(0, padding1, 0, padding2);
            }
            recyclerView.setClipToPadding(false);
        }
        mBannerManager.createMarginTransformer();
    }

    private void initPageStyle(@APageStyle int pageStyle) {
        float pageScale = mBannerManager.getBannerOptions().getPageScale();
        if (pageStyle == PageStyle.MULTI_PAGE_OVERLAP) {
            mBannerManager.setMultiPageStyle(true, pageScale);
        } else if (pageStyle == PageStyle.MULTI_PAGE_SCALE) {
            mBannerManager.setMultiPageStyle(false, pageScale);
        }
    }

    private void resetCurrentItem(int item) {
        if (isCanLoopSafely()) {
            mViewPager.setCurrentItem(getOriginalPosition(mBannerPagerAdapter.getListSize()) + item, false);
        } else {
            mViewPager.setCurrentItem(item, false);
        }
    }

    private void refreshIndicator(List<? extends T> data) {
        setIndicatorValues(data);
        boolean canLoop = mBannerManager.getBannerOptions().isCanLoop();
        mBannerManager.getBannerOptions().getIndicatorOptions()
                .setCurrentPosition(BannerUtils.getRealPosition(mViewPager.getCurrentItem(), data.size()));
        mIndicatorView.notifyDataChanged();
    }

    private static final String KEY_SUPER_STATE = "SUPER_STATE";
    private static final String KEY_CURRENT_POSITION = "CURRENT_POSITION";
    private static final String KEY_IS_CUSTOM_INDICATOR = "IS_CUSTOM_INDICATOR";

    private int getInterval() {
        return mBannerManager.getBannerOptions().getInterval();
    }

    private boolean isAutoPlay() {
        return mBannerManager.getBannerOptions().isAutoPlay();
    }

    private boolean isCanLoopSafely() {
        return mBannerManager != null && mBannerManager.getBannerOptions() != null
                && mBannerManager.getBannerOptions().isCanLoop()
                && mBannerPagerAdapter != null
                && mBannerPagerAdapter.getListSize() > 1;
    }

    @Nullable
    @Override
    protected Parcelable onSaveInstanceState() {
        Parcelable superState = super.onSaveInstanceState();
        Bundle bundle = new Bundle();
        bundle.putParcelable(KEY_SUPER_STATE, superState);
        bundle.putInt(KEY_CURRENT_POSITION, currentPosition);
        bundle.putBoolean(KEY_IS_CUSTOM_INDICATOR, isCustomIndicator);
        return bundle;
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        Bundle bundle = (Bundle) state;
        Parcelable superState = bundle.getParcelable(KEY_SUPER_STATE);
        super.onRestoreInstanceState(superState);
        currentPosition = bundle.getInt(KEY_CURRENT_POSITION);
        isCustomIndicator = bundle.getBoolean(KEY_IS_CUSTOM_INDICATOR);
        setCurrentItem(currentPosition);
    }

    /**
     * @return BannerViewPager data set
     */
    public List<T> getData() {
        return mBannerPagerAdapter.getData();
    }

    /**
     * Start loop
     */
    public void startLoop() {
        if (!isLooping && isAutoPlay() && mBannerPagerAdapter != null &&
                mBannerPagerAdapter.getListSize() > 1) {
            mHandler.postDelayed(mRunnable, getInterval());
            isLooping = true;
        }
    }

    /**
     * stoop loop
     */
    public void stopLoop() {
        if (isLooping) {
            mHandler.removeCallbacks(mRunnable);
            isLooping = false;
        }
    }

    public BannerViewPager<T> setAdapter(BaseBannerAdapter<T> adapter) {
        this.mBannerPagerAdapter = adapter;
        return this;
    }

    public BaseBannerAdapter<T> getAdapter() {
        return mBannerPagerAdapter;
    }

    /**
     * Set round rectangle effect for BannerViewPager.
     * <p>
     * Require SDK_INT>=LOLLIPOP(API 21)
     *
     * @param radius round radius
     */
    public BannerViewPager<T> setRoundCorner(int radius) {
        mBannerManager.getBannerOptions().setRoundRectRadius(radius);
        return this;
    }

    /**
     * Set round rectangle effect for BannerViewPager.
     * <p>
     * Require SDK_INT>=LOLLIPOP(API 21)
     *
     * @param radius round radius
     */
    public BannerViewPager<T> setRoundRect(int radius) {
        setRoundCorner(radius);
        return this;
    }

    /**
     * Enable/disable auto play
     *
     * @param autoPlay is enable auto play
     */
    public BannerViewPager<T> setAutoPlay(boolean autoPlay) {
        mBannerManager.getBannerOptions().setAutoPlay(autoPlay);
        if (isAutoPlay()) {
            mBannerManager.getBannerOptions().setCanLoop(true);
        }
        return this;
    }

    /**
     * Enable/disable loop
     *
     * @param canLoop is can loop
     */
    public BannerViewPager<T> setCanLoop(boolean canLoop) {
        mBannerManager.getBannerOptions().setCanLoop(canLoop);
        if (!canLoop) {
            mBannerManager.getBannerOptions().setAutoPlay(false);
        }
        return this;
    }

    /**
     * Set loop interval
     *
     * @param interval loop interval,unit is millisecond.
     */
    public BannerViewPager<T> setInterval(int interval) {
        mBannerManager.getBannerOptions().setInterval(interval);
        return this;
    }

    /**
     * @param transformer PageTransformer that will modify each page's animation properties
     */
    public BannerViewPager<T> setPageTransformer(@Nullable ViewPager2.PageTransformer transformer) {
        if (transformer != null) {
            mViewPager.setPageTransformer(transformer);
        }
        return this;
    }

    /**
     * @param transformer PageTransformer that will modify each page's animation properties
     */
    public BannerViewPager<T> addPageTransformer(@Nullable ViewPager2.PageTransformer transformer) {
        if (transformer != null) {
            mBannerManager.addTransformer(transformer);
        }
        return this;
    }

    public void removeTransformer(@Nullable ViewPager2.PageTransformer transformer) {
        if (transformer != null) {
            mBannerManager.removeTransformer(transformer);
        }
    }

    public void removeDefaultPageTransformer() {
        mBannerManager.removeDefaultPageTransformer();
    }

    public void removeMarginPageTransformer() {
        mBannerManager.removeMarginPageTransformer();
    }

    /**
     * set page margin
     *
     * @param pageMargin page margin
     */
    public BannerViewPager<T> setPageMargin(int pageMargin) {
        mBannerManager.setPageMargin(pageMargin);
        return this;
    }

    /**
     * set item click listener
     *
     * @param onPageClickListener item click listener
     */
    public BannerViewPager<T> setOnPageClickListener(OnPageClickListener onPageClickListener) {
        this.mOnPageClickListener = onPageClickListener;
        return this;
    }

    /**
     * Set page scroll duration
     *
     * @param scrollDuration page scroll duration
     */
    public BannerViewPager<T> setScrollDuration(int scrollDuration) {
        mBannerManager.getBannerOptions().setScrollDuration(scrollDuration);
        return this;
    }

    /**
     * set indicator color
     *
     * @param checkedColor checked color of indicator
     * @param normalColor  unchecked color of indicator
     */
    public BannerViewPager<T> setIndicatorSliderColor(@ColorInt int normalColor,
                                                      @ColorInt int checkedColor) {
        mBannerManager.getBannerOptions().setIndicatorSliderColor(normalColor, checkedColor);
        return this;
    }

    /**
     * set indicator circle radius
     * <p>
     * if the indicator style is {@link com.zhpan.indicator.enums.IndicatorStyle#DASH}
     * or {@link com.zhpan.indicator.enums.IndicatorStyle#ROUND_RECT}
     * the indicator dash width=2*radius
     *
     * @param radius 指示器圆点半径
     */
    public BannerViewPager<T> setIndicatorSliderRadius(int radius) {
        setIndicatorSliderRadius(radius, radius);
        return this;
    }

    /**
     * set indicator circle radius
     *
     * @param normalRadius  unchecked circle radius
     * @param checkedRadius checked circle radius
     */
    public BannerViewPager<T> setIndicatorSliderRadius(int normalRadius, int checkedRadius) {
        mBannerManager.getBannerOptions().setIndicatorSliderWidth(normalRadius * 2, checkedRadius * 2);
        return this;
    }

    public BannerViewPager<T> setIndicatorSliderWidth(int indicatorWidth) {
        setIndicatorSliderWidth(indicatorWidth, indicatorWidth);
        return this;
    }

    /**
     * Set indicator dash width，if indicator style is {@link com.zhpan.indicator.enums.IndicatorStyle#CIRCLE},
     * the indicator circle radius is indicatorWidth/2.
     *
     * @param normalWidth if the indicator style is {@link com.zhpan.indicator.enums.IndicatorStyle#DASH} the params means unchecked dash width
     *                    if the indicator style is {@link com.zhpan.indicator.enums.IndicatorStyle#ROUND_RECT}  means unchecked round rectangle width
     *                    if the indicator style is {@link com.zhpan.indicator.enums.IndicatorStyle#CIRCLE } means unchecked circle diameter
     * @param checkWidth  if the indicator style is {@link com.zhpan.indicator.enums.IndicatorStyle#DASH} the params means checked dash width
     *                    if the indicator style is {@link com.zhpan.indicator.enums.IndicatorStyle#ROUND_RECT} the params means checked round rectangle width
     *                    if the indicator style is {@link com.zhpan.indicator.enums.IndicatorStyle#CIRCLE } means checked circle diameter
     */
    public BannerViewPager<T> setIndicatorSliderWidth(int normalWidth, int checkWidth) {
        mBannerManager.getBannerOptions().setIndicatorSliderWidth(normalWidth, checkWidth);
        return this;
    }

    public BannerViewPager<T> setIndicatorHeight(int indicatorHeight) {
        mBannerManager.getBannerOptions().setIndicatorHeight(indicatorHeight);
        return this;
    }

    /**
     * Set Indicator gap of dash/circle
     *
     * @param indicatorGap indicator gap
     */
    public BannerViewPager<T> setIndicatorSliderGap(int indicatorGap) {
        mBannerManager.getBannerOptions().setIndicatorGap(indicatorGap);
        return this;
    }

    /**
     * Set the visibility state of indicator view.
     *
     * @param visibility One of {@link View#VISIBLE}, {@link View#INVISIBLE}, or {@link View#GONE}.
     */
    public BannerViewPager<T> setIndicatorVisibility(@Visibility int visibility) {
        mBannerManager.getBannerOptions().setIndicatorVisibility(visibility);
        return this;
    }

    /**
     * set indicator gravity in BannerViewPager
     *
     * @param gravity indicator gravity
     *                {@link com.zhpan.bannerview.constants.IndicatorGravity#CENTER}
     *                {@link com.zhpan.bannerview.constants.IndicatorGravity#START}
     *                {@link com.zhpan.bannerview.constants.IndicatorGravity#END}
     */
    public BannerViewPager<T> setIndicatorGravity(@AIndicatorGravity int gravity) {
        mBannerManager.getBannerOptions().setIndicatorGravity(gravity);
        return this;
    }

    /**
     * Set Indicator slide mode，default value is {@link com.zhpan.indicator.enums.IndicatorSlideMode#NORMAL}
     *
     * @param slideMode Indicator slide mode
     * @see com.zhpan.indicator.enums.IndicatorSlideMode#NORMAL
     * @see com.zhpan.indicator.enums.IndicatorSlideMode#SMOOTH
     */
    public BannerViewPager<T> setIndicatorSlideMode(@AIndicatorSlideMode int slideMode) {
        mBannerManager.getBannerOptions().setIndicatorSlideMode(slideMode);
        return this;
    }

    /**
     * Set custom indicator.
     * the custom indicator view must extends BaseIndicator or implements IIndicator
     *
     * @param customIndicator custom indicator view
     */
    public BannerViewPager<T> setIndicatorView(IIndicator customIndicator) {
        if (customIndicator instanceof View) {
            isCustomIndicator = true;
            mIndicatorView = customIndicator;
        }
        return this;
    }

    /**
     * Set indicator style
     *
     * @param indicatorStyle indicator style
     * @see com.zhpan.indicator.enums.IndicatorStyle#CIRCLE
     * @see com.zhpan.indicator.enums.IndicatorStyle#DASH
     * @see com.zhpan.indicator.enums.IndicatorStyle#ROUND_RECT
     */
    public BannerViewPager<T> setIndicatorStyle(@AIndicatorStyle int indicatorStyle) {
        mBannerManager.getBannerOptions().setIndicatorStyle(indicatorStyle);
        return this;
    }

    /**
     * Create BannerViewPager with data.
     * If data has fetched when create BannerViewPager,you can call this method.
     */
    public void create(List<T> data) {
        if (mBannerPagerAdapter == null) {
            throw new NullPointerException("You must set adapter for BannerViewPager");
        }
        mBannerPagerAdapter.setData(data);
        initBannerData();
    }

    /**
     * Create BannerViewPager with no data
     * If there is no data while you create BannerViewPager(for example,The data is from remote server)，you can call this method.
     * Then,while you fetch data successfully,just need call {@link #refreshData(List)} method to refresh.
     */
    public void create() {
        create(new ArrayList<T>());
    }

    /**
     * Sets the orientation of the ViewPager2.
     *
     * @param orientation {@link ViewPager2#ORIENTATION_HORIZONTAL} or
     *                    {@link ViewPager2#ORIENTATION_VERTICAL}
     */
    public BannerViewPager<T> setOrientation(@ViewPager2.Orientation int orientation) {
        mBannerManager.getBannerOptions().setOrientation(orientation);
        return this;
    }

    public void addItemDecoration(@NonNull RecyclerView.ItemDecoration decor, int index) {
        if (isCanLoopSafely()) {
            int pageSize = mBannerPagerAdapter.getListSize();
            int currentItem = mViewPager.getCurrentItem();
            boolean canLoop = mBannerManager.getBannerOptions().isCanLoop();
            int realPosition = BannerUtils.getRealPosition(currentItem, pageSize);
            if (currentItem != index) {
                if (index == 0 && realPosition == pageSize - 1) {
                    mViewPager.addItemDecoration(decor, currentItem + 1);
                } else if (realPosition == 0 && index == pageSize - 1) {
                    mViewPager.addItemDecoration(decor, currentItem - 1);
                } else {
                    mViewPager.addItemDecoration(decor, currentItem + (index - realPosition));
                }
            }
        } else {
            mViewPager.addItemDecoration(decor, index);
        }
    }

    public void addItemDecoration(@NonNull RecyclerView.ItemDecoration decor) {
        mViewPager.addItemDecoration(decor);
    }

    /**
     * Refresh data.
     * Confirm the {@link #create()} or {@link #create(List)} method has been called,
     * else the data won't be shown.
     */
    public void refreshData(List<? extends T> list) {
        if (list != null && mBannerPagerAdapter != null) {
            stopLoop();
            mBannerPagerAdapter.setData(list);
            mBannerPagerAdapter.notifyDataSetChanged();
            resetCurrentItem(getCurrentItem());
            refreshIndicator(list);
            startLoop();
        }
    }

    public void addData(List<? extends T> list) {
        if (list != null && mBannerPagerAdapter != null) {
            List<T> data = mBannerPagerAdapter.getData();
            data.addAll(list);
            mBannerPagerAdapter.notifyDataSetChanged();
            resetCurrentItem(getCurrentItem());
            refreshIndicator(data);
        }
    }

    /**
     * Removes the item at the specified position in this list.
     *
     * @param index the index of the item to be removed
     */
    public void removeItem(int index) {
        List<T> data = mBannerPagerAdapter.getData();
        if (index >= 0 && index < data.size()) {
            data.remove(index);
            mBannerPagerAdapter.notifyDataSetChanged();
            resetCurrentItem(getCurrentItem());
            refreshIndicator(data);
        }
    }

    /**
     * Inserts the specified element at the specified position in this list
     *
     * @param index index at which the specified element is to be inserted
     * @param item  item element to be inserted
     */
    public void insertItem(int index, T item) {
        List<T> data = mBannerPagerAdapter.getData();
        if (index >= 0 && index <= data.size()) {
            data.add(index, item);
            mBannerPagerAdapter.notifyDataSetChanged();
            resetCurrentItem(getCurrentItem());
            refreshIndicator(data);
        }
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
        if (isCanLoopSafely()) {
            int currentItem = mViewPager.getCurrentItem();
            int pageSize = mBannerPagerAdapter.getListSize();
            boolean canLoop = mBannerManager.getBannerOptions().isCanLoop();
            int realPosition = BannerUtils.getRealPosition(currentItem, mBannerPagerAdapter.getListSize());
            if (currentItem != item) {
                if (item == 0 && realPosition == pageSize - 1) {
                    mViewPager.setCurrentItem(currentItem + 1);
                } else if (realPosition == 0 && item == pageSize - 1) {
                    mViewPager.setCurrentItem(currentItem - 1);
                } else {
                    mViewPager.setCurrentItem(currentItem + (item - realPosition));
                }
            }
        } else {
            mViewPager.setCurrentItem(item);
        }
    }

    /**
     * Set the currently selected page.
     *
     * @param item         Item index to select
     * @param smoothScroll True to smoothly scroll to the new item, false to transition immediately
     */
    public void setCurrentItem(int item, boolean smoothScroll) {
        if (isCanLoopSafely()) {
            int pageSize = mBannerPagerAdapter.getListSize();
            int currentItem = mViewPager.getCurrentItem();
            boolean canLoop = mBannerManager.getBannerOptions().isCanLoop();
            int realPosition = BannerUtils.getRealPosition(currentItem, pageSize);
            if (currentItem != item) {
                if (item == 0 && realPosition == pageSize - 1) {
                    mViewPager.setCurrentItem(currentItem + 1, smoothScroll);
                } else if (realPosition == 0 && item == pageSize - 1) {
                    mViewPager.setCurrentItem(currentItem - 1, smoothScroll);
                } else {
                    mViewPager.setCurrentItem(currentItem + (item - realPosition), smoothScroll);
                }
            }
        } else {
            mViewPager.setCurrentItem(item, smoothScroll);
        }
    }

    /**
     * Set Page Style for Banner
     * {@link PageStyle#NORMAL}
     * {@link PageStyle#MULTI_PAGE}
     *
     * @return BannerViewPager
     */
    public BannerViewPager<T> setPageStyle(@APageStyle int pageStyle) {
        return setPageStyle(pageStyle, DEFAULT_MIN_SCALE);
    }

    public BannerViewPager<T> setPageStyle(@APageStyle int pageStyle, float pageScale) {
        mBannerManager.getBannerOptions().setPageStyle(pageStyle);
        mBannerManager.getBannerOptions().setPageScale(pageScale);
        return this;
    }

    /**
     * @param revealWidth 一屏多页模式下两边页面显露出来的宽度
     */
    public BannerViewPager<T> setRevealWidth(int revealWidth) {
        setRevealWidth(revealWidth, revealWidth);
        return this;
    }

    /**
     * @param leftRevealWidth  left page item  reveal width
     * @param rightRevealWidth right page item reveal width
     */
    public BannerViewPager<T> setRevealWidth(int leftRevealWidth, int rightRevealWidth) {
        mBannerManager.getBannerOptions().setRightRevealWidth(rightRevealWidth);
        mBannerManager.getBannerOptions().setLeftRevealWidth(leftRevealWidth);
        return this;
    }

    /**
     * Suggest to use default offScreenPageLimit.
     */
    public BannerViewPager<T> setOffScreenPageLimit(int offScreenPageLimit) {
        mBannerManager.getBannerOptions().setOffScreenPageLimit(offScreenPageLimit);
        return this;
    }

    public BannerViewPager<T> setIndicatorMargin(int left, int top, int right, int bottom) {
        mBannerManager.getBannerOptions().setIndicatorMargin(left, top, right, bottom);
        return this;
    }

    /**
     * Enable or disable user initiated scrolling
     */
    public BannerViewPager<T> setUserInputEnabled(boolean userInputEnabled) {
        mBannerManager.getBannerOptions().setUserInputEnabled(userInputEnabled);
        mViewPager.setUserInputEnabled(userInputEnabled);
        return this;
    }

    public interface OnPageClickListener {
        void onPageClick(View clickedView, int position);
    }

    public BannerViewPager<T> registerOnPageChangeCallback(ViewPager2.OnPageChangeCallback onPageChangeCallback) {
        this.onPageChangeCallback = onPageChangeCallback;
        return this;
    }

    public BannerViewPager<T> setLifecycleRegistry(Lifecycle lifecycleRegistry) {
        lifecycleRegistry.addObserver(this);
        return this;
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    public void onPause() {
        stopLoop();
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    public void onResume() {
        startLoop();
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    public void onDestroy() {
        stopLoop();
    }

    /**
     * 设置是否允许在BVP的{@link MotionEvent#ACTION_DOWN}事件中禁止父View对事件的拦截，该方法
     * 用于解决CoordinatorLayout+CollapsingToolbarLayout在嵌套BVP时引起的滑动冲突问题。
     * <p>
     * BVP在处理ViewPager2嵌套滑动冲突时，在{@link #onInterceptTouchEvent(MotionEvent)}
     * 方法的{@link MotionEvent#ACTION_DOWN}事件中禁止了BVP的父View对触摸事件的拦截，
     * 导致CollapsingToolbarLayout的布局无法获取{@link MotionEvent#ACTION_DOWN}事件，
     * 致使CollapsingToolbarLayout无法处理down事件后的一系列事件而无法滑动。
     * 对于这种情况可以调用该方法不允许在BVP在{@link MotionEvent#ACTION_DOWN}事件中禁止父View的事件拦截。
     * </p>
     * 调用该方法将disallowIntercept设置为true后虽然解决了滑动冲突，但也会造成一定的不良影响，即如果BVP设置
     * 水平滑动，同时BVP外部也是可以水平滑动的ViewPager，则存在较小概率的滑动冲突，即滑动BVP的同时可能会触发
     * 外部ViewPager的滑动。但这一问题到目前为止似乎没有好的解决方案。
     *
     * @param disallowParentInterceptDownEvent 是否允许BVP在{@link MotionEvent#ACTION_DOWN}事件中禁止父View拦截事件，默认值为false
     *                                         true 不允许BVP在{@link MotionEvent#ACTION_DOWN}时间中禁止父View的时间拦截，
     *                                         设置disallowIntercept为true可以解决CoordinatorLayout+CollapsingToolbarLayout的滑动冲突
     *                                         false 允许BVP在{@link MotionEvent#ACTION_DOWN}时间中禁止父View的时间拦截，
     */

    public BannerViewPager<T> disallowParentInterceptDownEvent(boolean disallowParentInterceptDownEvent) {
        mBannerManager.getBannerOptions().setDisallowParentInterceptDownEvent(disallowParentInterceptDownEvent);
        return this;
    }

    /**
     * Set right to left mode.
     *
     * @param rtlMode true:right to left mode,
     *                false:right to left mode.
     */
    public BannerViewPager<T> setRTLMode(boolean rtlMode) {
        mViewPager.setLayoutDirection(rtlMode ? View.LAYOUT_DIRECTION_RTL : View.LAYOUT_DIRECTION_LTR);
        mBannerManager.getBannerOptions().setRtl(rtlMode);
        return this;
    }

    /**
     * @deprecated Use {@link BannerViewPager#disallowParentInterceptDownEvent(boolean)} instead.
     */
    @Deprecated
    public BannerViewPager<T> disallowInterceptTouchEvent(boolean disallowIntercept) {
        mBannerManager.getBannerOptions().setDisallowParentInterceptDownEvent(disallowIntercept);
        return this;
    }

}
