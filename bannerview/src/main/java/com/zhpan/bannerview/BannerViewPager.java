package com.zhpan.bannerview;

import android.content.Context;
import android.os.Build;
import android.os.Handler;

import androidx.annotation.ColorInt;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.zhpan.bannerview.annotation.AIndicatorGravity;
import com.zhpan.bannerview.annotation.APageStyle;
import com.zhpan.bannerview.annotation.Visibility;
import com.zhpan.bannerview.constants.PageStyle;
import com.zhpan.bannerview.manager.BannerManager;
import com.zhpan.bannerview.manager.BannerOptions;
import com.zhpan.bannerview.provider.ScrollDurationManger;
import com.zhpan.bannerview.transform.OverlapPageTransformer;
import com.zhpan.bannerview.transform.ScaleInTransformer;
import com.zhpan.bannerview.utils.BannerUtils;
import com.zhpan.bannerview.provider.ViewStyleSetter;
import com.zhpan.indicator.IndicatorView;
import com.zhpan.indicator.annotation.AIndicatorSlideMode;
import com.zhpan.indicator.annotation.AIndicatorStyle;
import com.zhpan.indicator.base.IIndicator;

import java.util.List;

import static com.zhpan.bannerview.BaseBannerAdapter.MAX_VALUE;
import static com.zhpan.bannerview.constants.IndicatorGravity.CENTER;
import static com.zhpan.bannerview.constants.IndicatorGravity.END;
import static com.zhpan.bannerview.constants.IndicatorGravity.START;
import static com.zhpan.bannerview.transform.ScaleInTransformer.DEFAULT_MIN_SCALE;
import static com.zhpan.bannerview.transform.ScaleInTransformer.MAX_SCALE;

/**
 * Created by zhpan on 2017/3/28.
 */
public class BannerViewPager<T, VH extends BaseViewHolder> extends RelativeLayout {

    private int currentPosition;

    private boolean isCustomIndicator;

    private OnPageClickListener mOnPageClickListener;

    private IIndicator mIndicatorView;

    private RelativeLayout mIndicatorLayout;

    private ViewPager2 mViewPager;

    private BannerManager mBannerManager;

    private Handler mHandler = new Handler();

    private BaseBannerAdapter<T, VH> mBannerPagerAdapter;

    private ViewPager2.OnPageChangeCallback onPageChangeCallback;

    private Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            handlePosition();
        }
    };

    private int startX, startY;

    private CompositePageTransformer mCompositePageTransformer;

    private MarginPageTransformer mMarginPageTransformer;

    private ViewPager2.PageTransformer mPageTransformer;

    private ViewPager2.OnPageChangeCallback mOnPageChangeCallback = new ViewPager2.OnPageChangeCallback() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            super.onPageScrolled(position, positionOffset, positionOffsetPixels);
            int listSize = mBannerPagerAdapter.getListSize();
            int realPosition = BannerUtils.getRealPosition(isCanLoop(), position, listSize);
            if (listSize > 0) {
                if (onPageChangeCallback != null) {
                    onPageChangeCallback.onPageScrolled(realPosition, positionOffset, positionOffsetPixels);
                }
                if (mIndicatorView != null) {
                    mIndicatorView.onPageScrolled(realPosition, positionOffset, positionOffsetPixels);
                }
            }
        }

        @Override
        public void onPageSelected(int position) {
            super.onPageSelected(position);
            int size = mBannerPagerAdapter.getListSize();
            currentPosition = BannerUtils.getRealPosition(isCanLoop(), position, size);
            if (size > 0 && isCanLoop() && position == 0 || position == MAX_VALUE - 1) {
                setCurrentItem(currentPosition, false);
            }
            if (onPageChangeCallback != null)
                onPageChangeCallback.onPageSelected(currentPosition);
            if (mIndicatorView != null) {
                mIndicatorView.onPageSelected(currentPosition);
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {
            super.onPageScrollStateChanged(state);
            if (mIndicatorView != null) {
                mIndicatorView.onPageScrollStateChanged(state);
            }
            if (onPageChangeCallback != null) {
                onPageChangeCallback.onPageScrollStateChanged(state);
            }
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
        mCompositePageTransformer = new CompositePageTransformer();
        mBannerManager = new BannerManager();
        mBannerManager.initAttrs(context, attrs);
        initView();
    }

    private void initView() {
        inflate(getContext(), R.layout.bvp_layout, this);
        mViewPager = findViewById(R.id.vp_main);
        mIndicatorLayout = findViewById(R.id.bvp_layout_indicator);
        mViewPager.setPageTransformer(mCompositePageTransformer);
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
                setLooping(true);
                stopLoop();
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_OUTSIDE:
                setLooping(false);
                startLoop();
                break;
        }
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                startX = (int) ev.getX();
                startY = (int) ev.getY();
                getParent().requestDisallowInterceptTouchEvent(true);
                break;
            case MotionEvent.ACTION_MOVE:
                int endX = (int) ev.getX();
                int endY = (int) ev.getY();
                int disX = Math.abs(endX - startX);
                int disY = Math.abs(endY - startY);
                int orientation = mBannerManager.bannerOptions().getOrientation();
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
                break;
        }
        return super.onInterceptTouchEvent(ev);
    }

    private void onVerticalActionMove(int endY, int disX, int disY) {
        if (disY > disX) {
            if (!isCanLoop()) {
                if (currentPosition == 0 && endY - startY > 0) {
                    getParent().requestDisallowInterceptTouchEvent(false);
                } else if (currentPosition == getData().size() - 1 && endY - startY < 0) {
                    getParent().requestDisallowInterceptTouchEvent(false);
                } else {
                    getParent().requestDisallowInterceptTouchEvent(true);
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
            if (!isCanLoop()) {
                if (currentPosition == 0 && endX - startX > 0) {
                    getParent().requestDisallowInterceptTouchEvent(false);
                } else if (currentPosition == getData().size() - 1 && endX - startX < 0) {
                    getParent().requestDisallowInterceptTouchEvent(false);
                } else {
                    getParent().requestDisallowInterceptTouchEvent(true);
                }
            } else {
                getParent().requestDisallowInterceptTouchEvent(true);
            }
        } else if (disY > disX) {
            getParent().requestDisallowInterceptTouchEvent(false);
        }
    }

    private void handlePosition() {
        if (mBannerPagerAdapter.getListSize() > 1) {
            currentPosition = mViewPager.getCurrentItem() + 1;
            mViewPager.setCurrentItem(currentPosition);
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

    private void setIndicatorValues(List<T> list) {
        BannerOptions bannerOptions = mBannerManager.bannerOptions();
        bannerOptions.resetIndicatorOptions();
        if (isCustomIndicator && null != mIndicatorView) {
            initIndicator(mIndicatorView);
        } else {
            initIndicator(new IndicatorView(getContext()));
        }
        mIndicatorView.setIndicatorOptions(bannerOptions.getIndicatorOptions());
        bannerOptions.getIndicatorOptions().setPageSize(list.size());
        mIndicatorView.notifyDataChanged();
    }

    private void initIndicator(IIndicator indicatorView) {
        mIndicatorLayout.setVisibility(mBannerManager.bannerOptions().getIndicatorVisibility());
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
        switch (mBannerManager.bannerOptions().getIndicatorGravity()) {
            case CENTER:
                layoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
                break;
            case START:
                layoutParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
                break;
            case END:
                layoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                break;
        }
    }

    private void initIndicatorViewMargin() {
        ViewGroup.MarginLayoutParams layoutParams = (MarginLayoutParams) ((View) mIndicatorView).getLayoutParams();
        BannerOptions.IndicatorMargin indicatorMargin = mBannerManager.bannerOptions().getIndicatorMargin();
        if (indicatorMargin == null) {
            int dp10 = BannerUtils.dp2px(10);
            layoutParams.setMargins(dp10, dp10, dp10, dp10);
        } else {
            layoutParams.setMargins(indicatorMargin.getLeft(), indicatorMargin.getTop(),
                    indicatorMargin.getRight(), indicatorMargin.getBottom());
        }
    }

    private void initRoundCorner() {
        int roundCorner = mBannerManager.bannerOptions().getRoundRectRadius();
        if (roundCorner > 0 && Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ViewStyleSetter viewStyleSetter = new ViewStyleSetter(this);
            viewStyleSetter.setRoundRect(roundCorner);
        }
    }

    private void setupViewPager(List<T> list) {
        if (mBannerPagerAdapter == null) {
            throw new NullPointerException("You must set adapter for BannerViewPager");
        }
        BannerOptions bannerOptions = mBannerManager.bannerOptions();
        if (bannerOptions.getScrollDuration() != 0)
            ScrollDurationManger.reflectLayoutManager(mViewPager, bannerOptions.getScrollDuration());
        currentPosition = 0;
        mBannerPagerAdapter.setCanLoop(isCanLoop());
        mBannerPagerAdapter.setPageClickListener(mOnPageClickListener);
        mViewPager.setAdapter(mBannerPagerAdapter);
        if (list.size() > 1 && isCanLoop()) {
            mViewPager.setCurrentItem(MAX_VALUE / 2 - ((MAX_VALUE / 2) % list.size()) + 1, false);
        }
        mViewPager.unregisterOnPageChangeCallback(mOnPageChangeCallback);
        mViewPager.registerOnPageChangeCallback(mOnPageChangeCallback);
        mViewPager.setOrientation(bannerOptions.getOrientation());
        mViewPager.setUserInputEnabled(bannerOptions.isUserInputEnabled());
        mViewPager.setOffscreenPageLimit(bannerOptions.getOffScreenPageLimit());
        initPageStyle();
        startLoop();
    }

    private void initPageStyle() {
        switch (mBannerManager.bannerOptions().getPageStyle()) {
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
        RecyclerView recyclerView = (RecyclerView) mViewPager.getChildAt(0);
        BannerOptions bannerOptions = mBannerManager.bannerOptions();
        int orientation = bannerOptions.getOrientation();
        int padding = bannerOptions.getPageMargin() + bannerOptions.getRevealWidth();
        if (orientation == ViewPager2.ORIENTATION_HORIZONTAL)
            recyclerView.setPadding(padding, 0, padding, 0);
        else if (orientation == ViewPager2.ORIENTATION_VERTICAL) {
            recyclerView.setPadding(0, padding, 0, padding);
        }
        recyclerView.setClipToPadding(false);
        if (mPageTransformer != null) {
            mCompositePageTransformer.removeTransformer(mPageTransformer);
        }
        if (overlap && Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mPageTransformer = new OverlapPageTransformer(orientation, scale, scale, 0, 0);
        } else {
            mPageTransformer = new ScaleInTransformer(scale);
        }
        addPageTransformer(mPageTransformer);
    }

    private int getInterval() {
        return mBannerManager.bannerOptions().getInterval();
    }

    private boolean isAutoPlay() {
        return mBannerManager.bannerOptions().isAutoPlay();
    }

    private boolean isLooping() {
        return mBannerManager.bannerOptions().isLooping();
    }

    private void setLooping(boolean looping) {
        mBannerManager.bannerOptions().setLooping(looping);
    }

    private boolean isCanLoop() {
        return mBannerManager.bannerOptions().isCanLoop();
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
        if (!isLooping() && isAutoPlay() && mBannerPagerAdapter != null &&
                mBannerPagerAdapter.getListSize() > 1) {
            mHandler.postDelayed(mRunnable, getInterval());
            setLooping(true);
        }
    }

    /**
     * stoop loop
     */
    public void stopLoop() {
        if (isLooping()) {
            mHandler.removeCallbacks(mRunnable);
            setLooping(false);
        }
    }

    public BannerViewPager<T, VH> setAdapter(BaseBannerAdapter<T, VH> adapter) {
        this.mBannerPagerAdapter = adapter;
        return this;
    }

    public BaseBannerAdapter<T, VH> getAdapter() {
        return mBannerPagerAdapter;
    }

    /**
     * Set round rectangle effect for BannerViewPager.
     * <p>
     * Require SDK_INT>=LOLLIPOP(API 21)
     *
     * @param radius round radius
     */
    public BannerViewPager<T, VH> setRoundCorner(int radius) {
        mBannerManager.bannerOptions().setRoundRectRadius(radius);
        return this;
    }

    /**
     * Set round rectangle effect for BannerViewPager.
     * <p>
     * Require SDK_INT>=LOLLIPOP(API 21)
     *
     * @param radius round radius
     */
    public BannerViewPager<T, VH> setRoundRect(int radius) {
        setRoundCorner(radius);
        return this;
    }

    /**
     * Enable/disable auto play
     *
     * @param autoPlay is enable auto play
     */
    public BannerViewPager<T, VH> setAutoPlay(boolean autoPlay) {
        mBannerManager.bannerOptions().setAutoPlay(autoPlay);
        if (isAutoPlay()) {
            mBannerManager.bannerOptions().setCanLoop(true);
        }
        return this;
    }

    /**
     * Enable/disable loop
     *
     * @param canLoop is can loop
     */
    public BannerViewPager<T, VH> setCanLoop(boolean canLoop) {
        mBannerManager.bannerOptions().setCanLoop(canLoop);
        if (!canLoop) {
            mBannerManager.bannerOptions().setAutoPlay(false);
        }
        return this;
    }

    /**
     * Set loop interval
     *
     * @param interval loop interval,unit is millisecond.
     */
    public BannerViewPager<T, VH> setInterval(int interval) {
        mBannerManager.bannerOptions().setInterval(interval);
        return this;
    }

    /**
     * @param transformer PageTransformer that will modify each page's animation properties
     */
    public BannerViewPager<T, VH> setPageTransformer(@Nullable ViewPager2.PageTransformer transformer) {
        if (transformer != null)
            mViewPager.setPageTransformer(transformer);
        return this;
    }

    /**
     * @param transformer PageTransformer that will modify each page's animation properties
     */
    public BannerViewPager<T, VH> addPageTransformer(@Nullable ViewPager2.PageTransformer transformer) {
        if (transformer != null) {
            mCompositePageTransformer.addTransformer(transformer);
        }
        return this;
    }

    public void removeTransformer(@Nullable ViewPager2.PageTransformer transformer) {
        if (transformer != null) {
            mCompositePageTransformer.removeTransformer(transformer);
        }
    }


    /**
     * set page margin
     *
     * @param pageMargin page margin
     */
    public BannerViewPager<T, VH> setPageMargin(int pageMargin) {
        mBannerManager.bannerOptions().setPageMargin(pageMargin);
        if (mMarginPageTransformer != null) {
            mCompositePageTransformer.removeTransformer(mMarginPageTransformer);
        }
        mMarginPageTransformer = new MarginPageTransformer(pageMargin);
        mCompositePageTransformer.addTransformer(mMarginPageTransformer);
        return this;
    }


    /**
     * set item click listener
     *
     * @param onPageClickListener item click listener
     */
    public BannerViewPager<T, VH> setOnPageClickListener(OnPageClickListener onPageClickListener) {
        this.mOnPageClickListener = onPageClickListener;
        return this;
    }

    /**
     * Set page scroll duration
     *
     * @param scrollDuration page scroll duration
     */
    public BannerViewPager<T, VH> setScrollDuration(int scrollDuration) {
        mBannerManager.bannerOptions().setScrollDuration(scrollDuration);
        return this;
    }

    /**
     * set indicator color
     *
     * @param checkedColor checked color of indicator
     * @param normalColor  unchecked color of indicator
     */
    public BannerViewPager<T, VH> setIndicatorSliderColor(@ColorInt int normalColor,
                                                          @ColorInt int checkedColor) {
        mBannerManager.bannerOptions().setIndicatorSliderColor(normalColor, checkedColor);
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
    public BannerViewPager<T, VH> setIndicatorSliderRadius(int radius) {
        setIndicatorSliderRadius(radius, radius);
        return this;
    }


    /**
     * set indicator circle radius
     *
     * @param normalRadius  unchecked circle radius
     * @param checkedRadius checked circle radius
     */
    public BannerViewPager<T, VH> setIndicatorSliderRadius(int normalRadius, int checkedRadius) {
        mBannerManager.bannerOptions().setIndicatorSliderWidth(normalRadius * 2, checkedRadius * 2);
        return this;
    }

    public BannerViewPager<T, VH> setIndicatorSliderWidth(int indicatorWidth) {
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
    public BannerViewPager<T, VH> setIndicatorSliderWidth(int normalWidth, int checkWidth) {
        mBannerManager.bannerOptions().setIndicatorSliderWidth(normalWidth, checkWidth);
        return this;
    }

    public BannerViewPager<T, VH> setIndicatorHeight(int indicatorHeight) {
        mBannerManager.bannerOptions().setIndicatorHeight(indicatorHeight);
        return this;
    }

    /**
     * Set Indicator gap of dash/circle
     *
     * @param indicatorGap indicator gap
     */
    public BannerViewPager<T, VH> setIndicatorSliderGap(int indicatorGap) {
        mBannerManager.bannerOptions().setIndicatorGap(indicatorGap);
        return this;
    }

    /**
     * Set the visibility state of indicator view.
     *
     * @param visibility One of {@link View#VISIBLE}, {@link View#INVISIBLE}, or {@link View#GONE}.
     */
    public BannerViewPager<T, VH> setIndicatorVisibility(@Visibility int visibility) {
        mBannerManager.bannerOptions().setIndicatorVisibility(visibility);
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
    public BannerViewPager<T, VH> setIndicatorGravity(@AIndicatorGravity int gravity) {
        mBannerManager.bannerOptions().setIndicatorGravity(gravity);
        return this;
    }

    /**
     * Set Indicator slide mode，default value is {@link com.zhpan.indicator.enums.IndicatorSlideMode#NORMAL}
     *
     * @param slideMode Indicator slide mode
     * @see com.zhpan.indicator.enums.IndicatorSlideMode#NORMAL
     * @see com.zhpan.indicator.enums.IndicatorSlideMode#SMOOTH
     */
    public BannerViewPager<T, VH> setIndicatorSlideMode(@AIndicatorSlideMode int slideMode) {
        mBannerManager.bannerOptions().setIndicatorSlideMode(slideMode);
        return this;
    }


    /**
     * Set custom indicator.
     * the custom indicator view must extends BaseIndicator or implements IIndicator
     *
     * @param customIndicator custom indicator view
     */
    public BannerViewPager<T, VH> setIndicatorView(IIndicator customIndicator) {
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
    public BannerViewPager<T, VH> setIndicatorStyle(@AIndicatorStyle int indicatorStyle) {
        mBannerManager.bannerOptions().setIndicatorStyle(indicatorStyle);
        return this;
    }

    public void create(List<T> data) {
        if (mBannerPagerAdapter == null) {
            throw new NullPointerException("You must set adapter for BannerViewPager");
        }
        mBannerPagerAdapter.setData(data);
        initBannerData();
    }

    /**
     * Sets the orientation of the ViewPager2.
     *
     * @param orientation {@link androidx.viewpager2.widget.ViewPager2#ORIENTATION_HORIZONTAL} or
     *                    {@link androidx.viewpager2.widget.ViewPager2#ORIENTATION_VERTICAL}
     */
    public BannerViewPager<T, VH> setOrientation(@ViewPager2.Orientation int orientation) {
        mBannerManager.bannerOptions().setOrientation(orientation);
        return this;
    }

    public void setData(List<T> list) {
        if (list != null && mBannerPagerAdapter != null) {
            mBannerPagerAdapter.setData(list);
            initBannerData();
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
        if (isCanLoop() && mBannerPagerAdapter.getListSize() > 1) {
            mViewPager.setCurrentItem(MAX_VALUE / 2 - ((MAX_VALUE / 2) % mBannerPagerAdapter.getListSize()) + 1 + item);
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
        if (isCanLoop() && mBannerPagerAdapter.getListSize() > 1) {
            mViewPager.setCurrentItem(MAX_VALUE / 2 - ((MAX_VALUE / 2) % mBannerPagerAdapter.getListSize()) + 1 + item, smoothScroll);
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
    public BannerViewPager<T, VH> setPageStyle(@APageStyle int pageStyle) {
        mBannerManager.bannerOptions().setPageStyle(pageStyle);
        return this;
    }


    /**
     * @param revealWidth 一屏多页模式下两边页面显露出来的宽度
     */
    public BannerViewPager<T, VH> setRevealWidth(int revealWidth) {
        mBannerManager.bannerOptions().setRevealWidth(revealWidth);
        return this;
    }

    /**
     * 建议使用默认的offScreenPageLimit
     */
    public BannerViewPager<T, VH> setOffScreenPageLimit(int offScreenPageLimit) {
        mBannerManager.bannerOptions().setOffScreenPageLimit(offScreenPageLimit);
        return this;
    }


    public BannerViewPager<T, VH> setIndicatorMargin(int left, int top, int right, int bottom) {
        mBannerManager.bannerOptions().setIndicatorMargin(left, top, right, bottom);
        return this;
    }

    public BannerViewPager<T, VH> setUserInputEnabled(boolean userInputEnabled) {
        mBannerManager.bannerOptions().setUserInputEnabled(userInputEnabled);
        return this;
    }

    public interface OnPageClickListener {
        void onPageClick(int position);
    }

    public BannerViewPager<T, VH> registerOnPageChangeCallback(ViewPager2.OnPageChangeCallback onPageChangeCallback) {
        this.onPageChangeCallback = onPageChangeCallback;
        return this;
    }

    /**
     * @param showIndicator is show indicator
     * @deprecated Use {@link #setIndicatorVisibility(int)} instead.
     */
    @Deprecated
    public BannerViewPager<T, VH> showIndicator(boolean showIndicator) {
        mIndicatorLayout.setVisibility(showIndicator ? VISIBLE : GONE);
        return this;
    }

    /**
     * @deprecated user {@link #setUserInputEnabled(boolean)} instead.
     */
    @Deprecated
    public BannerViewPager<T, VH> disableTouchScroll(boolean disableTouchScroll) {
        mBannerManager.bannerOptions().setUserInputEnabled(!disableTouchScroll);
        return this;
    }

}
