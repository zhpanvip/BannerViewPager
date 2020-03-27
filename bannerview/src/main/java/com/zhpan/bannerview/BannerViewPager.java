package com.zhpan.bannerview;

import android.content.Context;
import android.os.Build;
import android.os.Handler;

import androidx.annotation.ColorInt;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.zhpan.bannerview.annotation.AIndicatorGravity;
import com.zhpan.bannerview.annotation.APageStyle;
import com.zhpan.bannerview.annotation.ATransformerStyle;
import com.zhpan.bannerview.annotation.Visibility;
import com.zhpan.bannerview.constants.PageStyle;
import com.zhpan.bannerview.manager.BannerManager;
import com.zhpan.bannerview.manager.BannerOptions;
import com.zhpan.bannerview.transform.ScaleInTransformer;
import com.zhpan.bannerview.utils.BannerUtils;
import com.zhpan.bannerview.adapter.BannerPagerAdapter;
import com.zhpan.bannerview.holder.HolderCreator;
import com.zhpan.bannerview.holder.ViewHolder;
import com.zhpan.bannerview.provider.ViewStyleSetter;
import com.zhpan.bannerview.transform.PageTransformerFactory;
import com.zhpan.bannerview.view.CatchViewPager;
import com.zhpan.indicator.IndicatorView;
import com.zhpan.indicator.annotation.AIndicatorSlideMode;
import com.zhpan.indicator.annotation.AIndicatorStyle;
import com.zhpan.indicator.base.IIndicator;

import java.util.List;

import static com.zhpan.bannerview.adapter.BannerPagerAdapter.MAX_VALUE;
import static com.zhpan.bannerview.constants.IndicatorGravity.CENTER;
import static com.zhpan.bannerview.constants.IndicatorGravity.END;
import static com.zhpan.bannerview.constants.IndicatorGravity.START;
import static com.zhpan.bannerview.transform.ScaleInTransformer.DEFAULT_MIN_SCALE;
import static com.zhpan.bannerview.transform.ScaleInTransformer.MAX_SCALE;

/**
 * Created by zhpan on 2017/3/28.
 */
public class BannerViewPager<T, VH extends ViewHolder> extends RelativeLayout implements
        ViewPager.OnPageChangeListener {

    private int currentPosition;

    private boolean isCustomIndicator;

    private OnPageClickListener mOnPageClickListener;

    private IIndicator mIndicatorView;

    private RelativeLayout mIndicatorLayout;

    private CatchViewPager mViewPager;

    private BannerManager mBannerManager;

    private HolderCreator<VH> holderCreator;

    private Handler mHandler = new Handler();

    private Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            handlePosition();
        }
    };

    private int startX, startY;

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
                startX = (int) ev.getX();
                startY = (int) ev.getY();
                getParent().requestDisallowInterceptTouchEvent(true);
                break;
            case MotionEvent.ACTION_MOVE:
                int endX = (int) ev.getX();
                int endY = (int) ev.getY();
                int disX = Math.abs(endX - startX);
                int disY = Math.abs(endY - startY);
                if (disX > disY) {
                    if (!isCanLoop()) {
                        if (currentPosition == 0 && endX - startX > 0) {
                            getParent().requestDisallowInterceptTouchEvent(false);
                        } else if (currentPosition == getList().size() - 1 && endX - startX < 0) {
                            getParent().requestDisallowInterceptTouchEvent(false);
                        } else {
                            getParent().requestDisallowInterceptTouchEvent(true);
                        }
                    } else {
                        getParent().requestDisallowInterceptTouchEvent(true);
                    }
                } else if (2 * disX < disY) {
                    getParent().requestDisallowInterceptTouchEvent(false);
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                setLooping(false);
                startLoop();
                getParent().requestDisallowInterceptTouchEvent(false);
                break;
            case MotionEvent.ACTION_OUTSIDE:
                setLooping(false);
                startLoop();
                break;
        }
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public void onPageSelected(int position) {
        int size = mBannerPagerAdapter.getListSize();
        currentPosition = BannerUtils.getRealPosition(isCanLoop(), position, size);
        if (size > 0 && isCanLoop() && position == 0 || position == MAX_VALUE - 1) {
            setCurrentItem(currentPosition, false);
        }
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
        int listSize = mBannerPagerAdapter.getListSize();
        int realPosition = BannerUtils.getRealPosition(isCanLoop(), position, listSize);
        if (listSize > 0) {
            if (mOnPageChangeListener != null) {
                mOnPageChangeListener.onPageScrolled(realPosition, positionOffset, positionOffsetPixels);
            }
            if (mIndicatorView != null) {
                mIndicatorView.onPageScrolled(realPosition, positionOffset, positionOffsetPixels);
            }
        }
    }

    private void handlePosition() {
        if (mBannerPagerAdapter.getListSize() > 1) {
            currentPosition = mViewPager.getCurrentItem() + 1;
            mViewPager.setCurrentItem(currentPosition);
            mHandler.postDelayed(mRunnable, getInterval());
        }
    }

    private void initBannerData(List<T> list) {
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
        if (holderCreator == null) {
            throw new NullPointerException("You must set HolderCreator for BannerViewPager");
        }
        currentPosition = 0;
        mViewPager.setAdapter(getPagerAdapter(list));
        if (list.size() > 1 && isCanLoop()) {
            mViewPager.setCurrentItem(MAX_VALUE / 2 - ((MAX_VALUE / 2) % list.size()) + 1);
        }
        mViewPager.removeOnPageChangeListener(this);
        mViewPager.addOnPageChangeListener(this);
        BannerOptions bannerOptions = mBannerManager.bannerOptions();
        mViewPager.setScrollDuration(bannerOptions.getScrollDuration());
        mViewPager.disableTouchScroll(bannerOptions.isDisableTouchScroll());
        mViewPager.setFirstLayout(true);
        mViewPager.setOffscreenPageLimit(bannerOptions.getOffScreenPageLimit());
        initPageStyle();
        startLoop();
    }

    private BannerPagerAdapter<T, VH> mBannerPagerAdapter;

    private PagerAdapter getPagerAdapter(List<T> list) {
        mBannerPagerAdapter =
                new BannerPagerAdapter<>(list, holderCreator);
        mBannerPagerAdapter.setCanLoop(isCanLoop());
        mBannerPagerAdapter.setPageClickListener(new BannerPagerAdapter.PageClickListener() {
            @Override
            public void onPageClick(int position) {
                if (mOnPageClickListener != null) {
                    mOnPageClickListener.onPageClick(position);
                }
            }
        });
        return mBannerPagerAdapter;
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
        setClipChildren(false);
        ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) mViewPager.getLayoutParams();
        BannerOptions bannerOptions = mBannerManager.bannerOptions();
        params.leftMargin = bannerOptions.getPageMargin() + bannerOptions.getRevealWidth();
        params.rightMargin = params.leftMargin;
        mViewPager.setOverlapStyle(overlap);
        mViewPager.setPageMargin(overlap ? -bannerOptions.getPageMargin() : bannerOptions.getPageMargin());
        int offScreenPageLimit = bannerOptions.getOffScreenPageLimit();
        mViewPager.setOffscreenPageLimit(Math.max(offScreenPageLimit, 2));
        setPageTransformer(new ScaleInTransformer(scale));
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
    public List<T> getList() {
        return mBannerPagerAdapter.getList();
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

    /**
     * Must set HolderCreator for BannerViewPager
     * <p>
     * In BannerPagerAdapter, the HolderCreator will return custom ViewHolder,then get item layout id by ViewHolder.
     *
     * @param holderCreator HolderCreator
     */
    public BannerViewPager<T, VH> setHolderCreator(HolderCreator<VH> holderCreator) {
        this.holderCreator = holderCreator;
        return this;
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
     * PageTransformer Style.
     *
     * @param style PageTransformerStyle
     * @see com.zhpan.bannerview.constants.TransformerStyle#NONE
     * @see com.zhpan.bannerview.constants.TransformerStyle#DEPTH
     * @see com.zhpan.bannerview.constants.TransformerStyle#SCALE_IN
     * @see com.zhpan.bannerview.constants.TransformerStyle#STACK
     * @see com.zhpan.bannerview.constants.TransformerStyle#ROTATE
     * @see com.zhpan.bannerview.constants.TransformerStyle#ACCORDION
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

    /**
     * Create BannerViewPager
     *
     * @param list the data set of Banner
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
     * set page margin
     *
     * @param pageMargin page margin
     */
    public BannerViewPager<T, VH> setPageMargin(int pageMargin) {
        mBannerManager.bannerOptions().setPageMargin(pageMargin);
        mViewPager.setPageMargin(pageMargin);
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

    public BannerViewPager<T, VH> setOffScreenPageLimit(int offScreenPageLimit) {
        mBannerManager.bannerOptions().setOffScreenPageLimit(offScreenPageLimit);
        return this;
    }


    public BannerViewPager<T, VH> setIndicatorMargin(int left, int top, int right, int bottom) {
        mBannerManager.bannerOptions().setIndicatorMargin(left, top, right, bottom);
        return this;
    }

    public BannerViewPager<T, VH> disableTouchScroll(boolean disableTouchScroll) {
        mBannerManager.bannerOptions().setDisableTouchScroll(disableTouchScroll);
        return this;
    }

    public interface OnPageClickListener {
        void onPageClick(int position);
    }

    private ViewPager.OnPageChangeListener mOnPageChangeListener;

    public BannerViewPager<T, VH> setOnPageChangeListener(ViewPager.OnPageChangeListener onPageChangeListener) {
        mOnPageChangeListener = onPageChangeListener;
        return this;
    }


    /**
     * set indicator circle radius
     *
     * @param normalRadius  unchecked circle radius
     * @param checkedRadius checked circle radius
     * @deprecated use {@link #setIndicatorSliderRadius(int, int)} instead
     */
    @Deprecated
    public BannerViewPager<T, VH> setIndicatorRadius(int normalRadius, int checkedRadius) {
        mBannerManager.bannerOptions().setIndicatorSliderWidth(normalRadius * 2, checkedRadius * 2);
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
     * @deprecated use {@link #setIndicatorSliderRadius(int)} instead
     */
    @Deprecated
    public BannerViewPager<T, VH> setIndicatorRadius(int radius) {
        setIndicatorSliderRadius(radius, radius);
        return this;
    }


    /**
     * Set indicator dash width，if indicator style is {@link com.zhpan.indicator.enums.IndicatorStyle#CIRCLE},
     * the indicator circle radius is indicatorWidth/2.
     *
     * @param indicatorWidth indicator dash width.
     * @deprecated Use {@link #setIndicatorSliderWidth(int)} instead.
     */
    @Deprecated
    public BannerViewPager<T, VH> setIndicatorWidth(int indicatorWidth) {
        setIndicatorSliderWidth(indicatorWidth, indicatorWidth);
        return this;
    }


    /**
     * @deprecated Use {@link #setIndicatorSliderWidth(int, int)} instead.
     */
    @Deprecated
    public BannerViewPager<T, VH> setIndicatorWidth(int normalWidth, int checkWidth) {
        mBannerManager.bannerOptions().setIndicatorSliderWidth(normalWidth, checkWidth);
        return this;
    }

    /**
     * set indicator color
     *
     * @param checkedColor checked color of indicator
     * @param normalColor  unchecked color of indicator
     * @deprecated use {@link #setIndicatorSliderColor(int, int)} instead
     */
    @Deprecated
    public BannerViewPager<T, VH> setIndicatorColor(@ColorInt int normalColor,
                                                    @ColorInt int checkedColor) {
        mBannerManager.bannerOptions().setIndicatorSliderColor(normalColor, checkedColor);
        return this;
    }


    /**
     * Set Indicator gap of dash/circle
     *
     * @param indicatorGap indicator gap
     * @deprecated Use {@link #setIndicatorSliderGap(int)} instead.
     */
    public BannerViewPager<T, VH> setIndicatorGap(int indicatorGap) {
        mBannerManager.bannerOptions().setIndicatorGap(indicatorGap);
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
}
