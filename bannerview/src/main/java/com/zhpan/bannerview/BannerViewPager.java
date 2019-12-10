package com.zhpan.bannerview;

import android.annotation.SuppressLint;
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
import com.zhpan.bannerview.annotation.AIndicatorSlideMode;
import com.zhpan.bannerview.annotation.AIndicatorStyle;
import com.zhpan.bannerview.annotation.APageStyle;
import com.zhpan.bannerview.annotation.ATransformerStyle;
import com.zhpan.bannerview.annotation.Visibility;
import com.zhpan.bannerview.constants.IndicatorSlideMode;
import com.zhpan.bannerview.constants.IndicatorStyle;
import com.zhpan.bannerview.constants.PageStyle;
import com.zhpan.bannerview.indicator.IndicatorView;
import com.zhpan.bannerview.indicator.IIndicator;
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

    private Runnable mRunnable = this::handlePosition;

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
        inflate(getContext(), R.layout.layout_banner_view_pager, this);
        mViewPager = findViewById(R.id.vp_main);
        mIndicatorLayout = findViewById(R.id.rl_indicator);
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

    @SuppressLint("ClickableViewAccessibility")
    private void setTouchListener() {
        mViewPager.setOnTouchListener((v, event) -> {
            int action = event.getAction();
            switch (action) {
                case MotionEvent.ACTION_DOWN:
                case MotionEvent.ACTION_MOVE:
                    setLooping(true);
                    stopLoop();
                    break;
                case MotionEvent.ACTION_UP:
                case MotionEvent.ACTION_CANCEL:
                    setLooping(false);
                    startLoop();
                default:
                    break;
            }
            return false;
        });
    }

    @Override
    public void onPageSelected(int position) {
        // Optimized For Issue #42
        int size = mBannerPagerAdapter.getListSize();
        if (size > 0 && isCanLoop() && position == 0) {
            position = MAX_VALUE / 2 - ((MAX_VALUE / 2) % size) + 1;
            setCurrentItem(0, false);
        }
        currentPosition = BannerUtils.getRealPosition(isCanLoop(), position, size);
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
        if (listSize > 0) {
            if (mOnPageChangeListener != null) {
                mOnPageChangeListener.onPageScrolled(BannerUtils.getRealPosition(isCanLoop(), position, listSize),
                        positionOffset, positionOffsetPixels);
            }
            if (mIndicatorView != null)
                mIndicatorView.onPageScrolled(BannerUtils.getRealPosition(isCanLoop(), position, listSize),
                        positionOffset, positionOffsetPixels);
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

//    private void initList(List<T> list) {
//        mList.clear();
//        mList.addAll(list);
//        mIndicatorView.setPageSize(mList.size());
//        if (mList.size() > 1) {
//            setIndicatorValues();
//        } else if (mIndicatorView != null) {
//            mIndicatorView.setPageSize(mList.size());
//        }
//        setIndicatorValues(list);
//
//    }

    private void setIndicatorValues(List<T> list) {
        BannerOptions bannerOptions = mBannerManager.bannerOptions();
        if (isCustomIndicator && null != mIndicatorView) {
            initIndicator(mIndicatorView);
        } else {
            initIndicator(new IndicatorView(getContext()));
        }
        mIndicatorView.setIndicatorOptions(bannerOptions.getIndicatorOptions());
        mIndicatorView.setPageSize(list.size());
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
        if (holderCreator != null) {
            if (list.size() > 0 && isCanLoop()) {
                currentPosition = MAX_VALUE / 2 - ((MAX_VALUE / 2) % list.size()) + 1;
            }
            removeAllViews();
            mViewPager.setAdapter(getPagerAdapter(list));
            mViewPager.setCurrentItem(currentPosition);
            mViewPager.removeOnPageChangeListener(this);
            mViewPager.addOnPageChangeListener(this);
            BannerOptions bannerOptions = mBannerManager.bannerOptions();
            mViewPager.setScrollDuration(bannerOptions.getScrollDuration());
            mViewPager.disableTouchScroll(bannerOptions.isDisableTouchScroll());
            mViewPager.setFirstLayout(true);
            addView(mViewPager);
            addView(mIndicatorLayout);
            initPageStyle();
            startLoop();
            setTouchListener();
        } else {
            throw new NullPointerException("You must set HolderCreator for BannerViewPager");
        }
    }

    private BannerPagerAdapter<T, VH> mBannerPagerAdapter;

    private PagerAdapter getPagerAdapter(List<T> list) {
        mBannerPagerAdapter =
                new BannerPagerAdapter<>(list, holderCreator);
        mBannerPagerAdapter.setCanLoop(isCanLoop());
        mBannerPagerAdapter.setPageClickListener(position -> {
            if (mOnPageClickListener != null) {
                mOnPageClickListener.onPageClick(position);
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
        mViewPager.setOffscreenPageLimit(2);
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
     * @return BannerViewPager数据集合
     */
    public List<T> getList() {
        return mBannerPagerAdapter.getList();
    }

    /**
     * 开启轮播
     */
    public void startLoop() {
        if (!isLooping() && isAutoPlay() && mBannerPagerAdapter != null &&
                mBannerPagerAdapter.getListSize() > 1) {
            mHandler.postDelayed(mRunnable, getInterval());
            setLooping(true);
        }
    }

    /**
     * 停止轮播
     */
    public void stopLoop() {
        if (isLooping()) {
            mHandler.removeCallbacks(mRunnable);
            setLooping(false);
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
     * @param radius 圆角大小
     * @deprecated Use{@link BannerViewPager#setRoundRect(int)} instead.
     * <p>
     * 设置圆角ViewPager 只有在SDK_INT>=LOLLIPOP(API 21)时有效
     */
    @Deprecated
    public BannerViewPager<T, VH> setRoundCorner(int radius) {
        mBannerManager.bannerOptions().setRoundRectRadius(radius);
        return this;
    }

    /**
     * Set round rectangle effect for BannerViewPager.
     *
     * Require SDK_INT>=LOLLIPOP(API 21)
     *
     * @param radius round radius
     */
    public BannerViewPager<T, VH> setRoundRect(int radius) {
        mBannerManager.bannerOptions().setRoundRectRadius(radius);
        return this;
    }

    /**
     * 设置是否自动轮播
     *
     * @param autoPlay 是否自动轮播
     */
    public BannerViewPager<T, VH> setAutoPlay(boolean autoPlay) {
        mBannerManager.bannerOptions().setAutoPlay(autoPlay);
        if (isAutoPlay()) {
            mBannerManager.bannerOptions().setCanLoop(true);
        }
        return this;
    }

    /**
     * 设置是否可以循环
     *
     * @param canLoop 是否可以循环
     */
    public BannerViewPager<T, VH> setCanLoop(boolean canLoop) {
        mBannerManager.bannerOptions().setCanLoop(canLoop);
        if (!canLoop) {
            mBannerManager.bannerOptions().setAutoPlay(false);
        }
        return this;
    }

    /**
     * 设置自动轮播时间间隔
     *
     * @param interval 自动轮播时间间隔 单位毫秒ms
     */
    public BannerViewPager<T, VH> setInterval(int interval) {
        mBannerManager.bannerOptions().setInterval(interval);
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
        mBannerManager.bannerOptions().setScrollDuration(scrollDuration);
        return this;
    }

    /**
     * @param checkedColor 选中时指示器颜色
     * @param normalColor  未选中时指示器颜色
     */
    public BannerViewPager<T, VH> setIndicatorColor(@ColorInt int normalColor,
                                                    @ColorInt int checkedColor) {
        mBannerManager.bannerOptions().setIndicatorCheckedColor(checkedColor);
        mBannerManager.bannerOptions().setIndicatorNormalColor(normalColor);
        return this;
    }

    /**
     * 设置指示器半径大小，选中与未选中半径大小相等
     *
     * @param radius 指示器圆点半径
     */
    public BannerViewPager<T, VH> setIndicatorRadius(int radius) {
        setIndicatorRadius(radius, radius);
        return this;
    }

    /**
     * 设置Indicator半径
     *
     * @param normalRadius  未选中时半径
     * @param checkedRadius 选中时半径
     */
    public BannerViewPager<T, VH> setIndicatorRadius(int normalRadius, int checkedRadius) {
        mBannerManager.bannerOptions().setNormalIndicatorWidth(normalRadius * 2);
        mBannerManager.bannerOptions().setCheckedIndicatorWidth(checkedRadius * 2);
        return this;
    }


    /**
     * 设置单个Indicator宽度，如果是圆则为圆的直径
     *
     * @param indicatorWidth 单个Indicator宽度/直径
     */
    public BannerViewPager<T, VH> setIndicatorWidth(int indicatorWidth) {
        setIndicatorWidth(indicatorWidth, indicatorWidth);
        return this;
    }


    /**
     * 设置单个Indicator宽度，如果是圆则为圆的直径
     *
     * @param normalWidth 未选中时宽度/直径
     * @param checkWidth  选中时宽度/直径
     */
    public BannerViewPager<T, VH> setIndicatorWidth(int normalWidth, int checkWidth) {
        mBannerManager.bannerOptions().setNormalIndicatorWidth(normalWidth);
        mBannerManager.bannerOptions().setCheckedIndicatorWidth(checkWidth);
        return this;
    }

    public BannerViewPager<T, VH> setIndicatorHeight(int indicatorHeight) {
        mBannerManager.bannerOptions().setIndicatorHeight(indicatorHeight);
        return this;
    }

    /**
     * 设置指示器间隔
     *
     * @param indicatorGap 指示器间隔
     * @return BannerViewPager
     */
    public BannerViewPager<T, VH> setIndicatorGap(int indicatorGap) {
        mBannerManager.bannerOptions().setIndicatorGap(indicatorGap);
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
        mBannerManager.bannerOptions().setIndicatorVisibility(visibility);
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
        mBannerManager.bannerOptions().setIndicatorGravity(gravity);
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
        mBannerManager.bannerOptions().setIndicatorSlideMode(slideMode);
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
//            mBannerManager.bannerOptions().setCustomIndicator(true);
            isCustomIndicator = true;
            mIndicatorView = customIndicator;
        }
        return this;
    }

    /**
     * 设置Indicator样式
     *
     * @param indicatorStyle indicator样式，目前有圆、短线及圆角矩形三种样式
     *                       {@link IndicatorStyle#CIRCLE}
     *                       {@link IndicatorStyle#DASH}
     *                       {@link IndicatorStyle#ROUND_RECT}
     */
    public BannerViewPager<T, VH> setIndicatorStyle(@AIndicatorStyle int indicatorStyle) {
        mBannerManager.bannerOptions().setIndicatorStyle(indicatorStyle);
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

//    public void update(List<T> list) {
//        if (null != list) {
//            if (mBannerPagerAdapter != null && mBannerManager.bannerOptions().getPageStyle() == PageStyle.NORMAL) {
//                mBannerPagerAdapter.setList(list);
//                mIndicatorView.setPageSize(list.size());
////                setCurrentItem(0, false);
//            } else {
//                initBannerData(list);
//            }
//        }
//    }

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
            removeAllViews();
            mViewPager.setCurrentItem(MAX_VALUE / 2 - ((MAX_VALUE / 2) % mBannerPagerAdapter.getListSize()) + 1 + item);
            addView(mViewPager);
            addView(mIndicatorLayout);
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
            removeAllViews();
            mViewPager.setCurrentItem(MAX_VALUE / 2 - ((MAX_VALUE / 2) % mBannerPagerAdapter.getListSize()) + 1 + item, smoothScroll);
            addView(mViewPager);
            addView(mIndicatorLayout);
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
     * 设置item间距
     *
     * @param pageMargin item间距
     * @return BannerViewPager
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

    public BannerViewPager<T, VH> setIndicatorMargin(int left, int top, int right, int bottom) {
        mBannerManager.bannerOptions().setIndicatorMargin(left, top, right, bottom);
        return this;
    }

    public BannerViewPager<T, VH> disableTouchScroll(boolean disableTouchScroll) {
        mBannerManager.bannerOptions().setDisableTouchScroll(disableTouchScroll);
        return this;
    }

    /**
     * 页面点击事件接口
     */
    public interface OnPageClickListener {
        void onPageClick(int position);
    }

    private ViewPager.OnPageChangeListener mOnPageChangeListener;

    public BannerViewPager<T, VH> setOnPageChangeListener(ViewPager.OnPageChangeListener onPageChangeListener) {
        mOnPageChangeListener = onPageChangeListener;
        return this;
    }
}
