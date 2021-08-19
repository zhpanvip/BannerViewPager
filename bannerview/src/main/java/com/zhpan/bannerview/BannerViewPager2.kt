package com.zhpan.bannerview

import android.content.Context
import android.graphics.Canvas
import android.graphics.Path
import android.graphics.RectF
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Parcelable
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.widget.RelativeLayout
import androidx.annotation.ColorInt
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import com.zhpan.bannerview.annotation.AIndicatorGravity
import com.zhpan.bannerview.annotation.APageStyle
import com.zhpan.bannerview.annotation.Visibility
import com.zhpan.bannerview.constants.IndicatorGravity
import com.zhpan.bannerview.constants.PageStyle
import com.zhpan.bannerview.manager.BannerManager
import com.zhpan.bannerview.manager.BannerOptions
import com.zhpan.bannerview.provider.ReflectLayoutManager
import com.zhpan.bannerview.provider.ViewStyleSetter
import com.zhpan.bannerview.transform.ScaleInTransformer
import com.zhpan.bannerview.utils.BannerUtils
import com.zhpan.indicator.IndicatorView
import com.zhpan.indicator.annotation.AIndicatorSlideMode
import com.zhpan.indicator.annotation.AIndicatorStyle
import com.zhpan.indicator.base.IIndicator
import com.zhpan.indicator.option.IndicatorOptions

class BannerViewPager2 @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : RelativeLayout(context, attrs, defStyleAttr), LifecycleObserver {

    private var currentPosition = 0
    private var isCustomIndicator = false
    private var isLooping = false
    private var mOnPageClickListener: BannerViewPager.OnPageClickListener? = null
    private var mIndicatorView: IIndicator? = null
    private val mIndicatorLayout: RelativeLayout by lazy {
        findViewById(R.id.bvp_layout_indicator)
    }
    private val mViewPager: ViewPager2 by lazy {
        findViewById(R.id.vp_main)
    }
    private val mBannerManager: BannerManager by lazy {
        BannerManager()
    }
    private val mHandler = Handler()

    private var mBannerPagerAdapter: BaseBannerAdapter<*>? = null

    private var onPageChangeCallback: OnPageChangeCallback? = null
    private val mRunnable = Runnable { this.handlePosition() }
    private var mRadiusRectF: RectF? = null
    private var mRadiusPath: Path? = null
    private var startX = 0
    private var startY: Int = 0

    private val mOnPageChangeCallback = object : ViewPager2.OnPageChangeCallback() {
        override fun onPageScrolled(
            position: Int,
            positionOffset: Float,
            positionOffsetPixels: Int
        ) {
            super.onPageScrolled(position, positionOffset, positionOffsetPixels)
            pageScrolled(position, positionOffset, positionOffsetPixels)
        }

        override fun onPageScrollStateChanged(state: Int) {
            super.onPageScrollStateChanged(state)
            pageScrollStateChanged(state)
        }

        override fun onPageSelected(position: Int) {
            super.onPageSelected(position)
            pageSelected(position)
        }
    }

    init {
        mBannerManager.initAttrs(context, attrs)
        initView()
    }

    private fun initView() {
        inflate(context, R.layout.bvp_layout, this)
        mViewPager.setPageTransformer(mBannerManager.compositePageTransformer)
    }


    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        if (mBannerManager.bannerOptions
                .isStopLoopWhenDetachedFromWindow
        ) {
            stopLoop()
        }

    }


    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        when (ev.action) {
            MotionEvent.ACTION_DOWN -> {
                isLooping = true
                stopLoop()
            }
            MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL, MotionEvent.ACTION_OUTSIDE -> {
                isLooping = false
                startLoop()
            }
            else -> {
            }
        }
        return super.dispatchTouchEvent(ev)
    }

    override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {
        val doNotNeedIntercept = (!mViewPager.isUserInputEnabled
                || mBannerPagerAdapter != null
                && mBannerPagerAdapter!!.data.size <= 1)


        if (doNotNeedIntercept) {
            return super.onInterceptTouchEvent(ev)
        }
        when (ev.action) {
            MotionEvent.ACTION_DOWN -> {
                startX = ev.x.toInt()
                startY = ev.y.toInt()
                parent.requestDisallowInterceptTouchEvent(
                    !mBannerManager
                        .bannerOptions.isDisallowParentInterceptDownEvent
                )
            }
            MotionEvent.ACTION_MOVE -> {
                val endX = ev.x.toInt()
                val endY = ev.y.toInt()
                val disX = Math.abs(endX - startX)
                val disY = Math.abs(endY - startY)
                val orientation = mBannerManager.bannerOptions.orientation
                if (orientation == ViewPager2.ORIENTATION_VERTICAL) {
                    onVerticalActionMove(endY, disX, disY)
                } else if (orientation == ViewPager2.ORIENTATION_HORIZONTAL) {
                    onHorizontalActionMove(endX, disX, disY)
                }
            }
            MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> parent.requestDisallowInterceptTouchEvent(
                false
            )
            MotionEvent.ACTION_OUTSIDE -> {
            }
            else -> {
            }
        }
        return super.onInterceptTouchEvent(ev)
    }

    private fun onVerticalActionMove(endY: Int, disX: Int, disY: Int) {
        if (disY > disX) {
            val canLoop = mBannerManager.bannerOptions.isCanLoop
            if (!canLoop) {
                if (currentPosition == 0 && endY - startY > 0) {
                    parent.requestDisallowInterceptTouchEvent(false)
                } else {
                    parent.requestDisallowInterceptTouchEvent(
                        currentPosition != getData().size - 1
                                || endY - startY >= 0
                    )
                }
            } else {
                parent.requestDisallowInterceptTouchEvent(true)
            }
        } else if (disX > disY) {
            parent.requestDisallowInterceptTouchEvent(false)
        }
    }

    private fun onHorizontalActionMove(endX: Int, disX: Int, disY: Int) {
        if (disX > disY) {
            val canLoop = mBannerManager.bannerOptions.isCanLoop
            if (!canLoop) {
                if (currentPosition == 0 && endX - startX > 0) {
                    parent.requestDisallowInterceptTouchEvent(false)
                } else {
                    parent.requestDisallowInterceptTouchEvent(
                        currentPosition != getData().size - 1
                                || endX - startX >= 0
                    )
                }
            } else {
                parent.requestDisallowInterceptTouchEvent(true)
            }
        } else if (disY > disX) {
            parent.requestDisallowInterceptTouchEvent(false)
        }
    }

    private fun pageScrollStateChanged(state: Int) {
        if (mIndicatorView != null) {
            mIndicatorView!!.onPageScrollStateChanged(state)
        }
        onPageChangeCallback?.onPageScrollStateChanged(state)
    }

    private fun pageSelected(position: Int) {
        val size = mBannerPagerAdapter!!.listSize
        val canLoop = mBannerManager.bannerOptions.isCanLoop
        currentPosition = BannerUtils.getRealPosition(position, size)
        val needResetCurrentItem =
            size > 0 && canLoop && (position == 0 || position == BaseBannerAdapter.MAX_VALUE - 1)
        if (needResetCurrentItem) {
            resetCurrentItem(currentPosition)
        }
        onPageChangeCallback?.onPageSelected(currentPosition)
        if (mIndicatorView != null) {
            mIndicatorView!!.onPageSelected(currentPosition)
        }
    }

    private fun pageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
        mBannerPagerAdapter?.let {
            val listSize = it.listSize
            val realPosition = BannerUtils.getRealPosition(position, listSize)
            if (listSize > 0) {
                onPageChangeCallback?.onPageScrolled(
                    realPosition,
                    positionOffset,
                    positionOffsetPixels
                )
                mIndicatorView?.onPageScrolled(
                    realPosition,
                    positionOffset,
                    positionOffsetPixels
                )

            }
        }

    }

    private fun handlePosition() {
        mBannerPagerAdapter?.let {
            if (it.listSize > 1 && isAutoPlay()) {
                mViewPager.currentItem = mViewPager.currentItem + 1
                mHandler.postDelayed(mRunnable, getInterval().toLong())
            }
        }

    }


    private fun initBannerData() {
        if (mBannerPagerAdapter == null) {
            throw RuntimeException("mBannerPagerAdapter is Null")
        } else {
            val list: MutableList<*>? = mBannerPagerAdapter!!.data
            if (list != null) {
                setIndicatorValues(list)
                setupViewPager(list)
                initRoundCorner()
            }
        }

    }

    private fun <T> setIndicatorValues(list: List<T>) {
        val bannerOptions = mBannerManager.bannerOptions
        mIndicatorLayout.visibility = bannerOptions.indicatorVisibility
        bannerOptions.resetIndicatorOptions()
        if (!isCustomIndicator || null == mIndicatorView) {
            mIndicatorView = IndicatorView(context)
        }
        initIndicator(bannerOptions.indicatorOptions, list)
    }

    private fun initIndicator(indicatorOptions: IndicatorOptions, list: List<*>) {

        mIndicatorView?.let {
            if ((it as View).parent == null) {
                mIndicatorLayout.removeAllViews()
                mIndicatorLayout.addView(it as View)
                initIndicatorSliderMargin()
                initIndicatorGravity()
            }
            it.setIndicatorOptions(indicatorOptions)
            indicatorOptions.pageSize = list.size
            it.notifyDataChanged()
        }

    }

    private fun initIndicatorGravity() {
        val layoutParams = (mIndicatorView as View).layoutParams as LayoutParams
        when (mBannerManager.bannerOptions.indicatorGravity) {
            IndicatorGravity.CENTER -> layoutParams.addRule(
                CENTER_HORIZONTAL
            )
            IndicatorGravity.START -> layoutParams.addRule(ALIGN_PARENT_LEFT)
            IndicatorGravity.END -> layoutParams.addRule(ALIGN_PARENT_RIGHT)
            else -> {
            }
        }
    }

    private fun initIndicatorSliderMargin() {
        val layoutParams = (mIndicatorView as View).layoutParams as MarginLayoutParams
        val indicatorMargin = mBannerManager.bannerOptions.indicatorMargin
        if (indicatorMargin == null) {
            val dp10 = BannerUtils.dp2px(10f)
            layoutParams.setMargins(dp10, dp10, dp10, dp10)
        } else {
            layoutParams.setMargins(
                indicatorMargin.left, indicatorMargin.top,
                indicatorMargin.right, indicatorMargin.bottom
            )
        }
    }

    override fun dispatchDraw(canvas: Canvas) {
        val roundRectRadiusArray = mBannerManager.bannerOptions.roundRectRadiusArray
        if (mRadiusRectF != null && mRadiusPath != null && roundRectRadiusArray != null) {
            mRadiusRectF!!.right = this.width.toFloat()
            mRadiusRectF!!.bottom = this.height.toFloat()
            mRadiusPath!!.addRoundRect(mRadiusRectF, roundRectRadiusArray, Path.Direction.CW)
            canvas.clipPath(mRadiusPath!!)
        }
        super.dispatchDraw(canvas)
    }

    private fun initRoundCorner() {
        val roundCorner = mBannerManager.bannerOptions.roundRectRadius
        if (roundCorner > 0 && Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ViewStyleSetter.applyRoundCorner(this, roundCorner.toFloat())
        }
    }

    private fun <T> setupViewPager(list: List<T?>) {
        if (mBannerPagerAdapter == null) {
            throw NullPointerException("You must set adapter for BannerViewPager")
        }
        val bannerOptions = mBannerManager.bannerOptions
        if (bannerOptions.scrollDuration != 0) {
            ReflectLayoutManager.reflectLayoutManager(mViewPager, bannerOptions.scrollDuration)
        }
        currentPosition = 0
        mBannerPagerAdapter!!.isCanLoop = bannerOptions.isCanLoop
        if (mOnPageClickListener != null) {
            mBannerPagerAdapter!!.setPageClickListener(mOnPageClickListener!!)
        }
        mViewPager.adapter = mBannerPagerAdapter
        if (isCanLoopSafely()) {
            mViewPager.setCurrentItem(BannerUtils.getOriginalPosition(list.size), false)
        }
        mViewPager.unregisterOnPageChangeCallback(mOnPageChangeCallback)
        mViewPager.registerOnPageChangeCallback(mOnPageChangeCallback)
        mViewPager.orientation = bannerOptions.orientation
        mViewPager.offscreenPageLimit = bannerOptions.offScreenPageLimit
        initRevealWidth(bannerOptions)
        initPageStyle(bannerOptions.pageStyle)
        startLoop()
    }

    private fun initRevealWidth(bannerOptions: BannerOptions) {
        val rightRevealWidth = bannerOptions.rightRevealWidth
        val leftRevealWidth = bannerOptions.leftRevealWidth
        if (leftRevealWidth != BannerOptions.DEFAULT_REVEAL_WIDTH || rightRevealWidth != BannerOptions.DEFAULT_REVEAL_WIDTH) {
            val recyclerView = mViewPager.getChildAt(0) as RecyclerView
            val orientation = bannerOptions.orientation
            val padding2 = bannerOptions.pageMargin + rightRevealWidth
            val padding1 = bannerOptions.pageMargin + leftRevealWidth
            if (orientation == ViewPager2.ORIENTATION_HORIZONTAL) {
                recyclerView.setPadding(padding1, 0, padding2, 0)
            } else if (orientation == ViewPager2.ORIENTATION_VERTICAL) {
                recyclerView.setPadding(0, padding1, 0, padding2)
            }
            recyclerView.clipToPadding = false
        }
        mBannerManager.createMarginTransformer()
    }

    private fun initPageStyle(@APageStyle pageStyle: Int) {
        val pageScale = mBannerManager.bannerOptions.pageScale
        if (pageStyle == PageStyle.MULTI_PAGE_OVERLAP) {
            mBannerManager.setMultiPageStyle(true, pageScale)
        } else if (pageStyle == PageStyle.MULTI_PAGE_SCALE) {
            mBannerManager.setMultiPageStyle(false, pageScale)
        }
    }

    private fun resetCurrentItem(item: Int) {
        if (isCanLoopSafely()) {
            mViewPager.setCurrentItem(
                BannerUtils.getOriginalPosition(mBannerPagerAdapter!!.listSize) + item,
                false
            )
        } else {
            mViewPager.setCurrentItem(item, false)
        }
    }

    private fun <T> refreshIndicator(data: List<T?>) {
        setIndicatorValues(data)
        mBannerManager.bannerOptions.indicatorOptions
            .currentPosition = BannerUtils.getRealPosition(mViewPager.currentItem, data.size)
        mIndicatorView!!.notifyDataChanged()
    }

    companion object {
        private const val KEY_SUPER_STATE = "SUPER_STATE"
        private const val KEY_CURRENT_POSITION = "CURRENT_POSITION"
        private const val KEY_IS_CUSTOM_INDICATOR = "IS_CUSTOM_INDICATOR"
    }

    private fun getInterval(): Int {
        return mBannerManager.bannerOptions.interval
    }

    private fun isAutoPlay(): Boolean {
        return mBannerManager.bannerOptions.isAutoPlay
    }

    private fun isCanLoopSafely(): Boolean {
        return (mBannerManager.bannerOptions != null && mBannerManager.bannerOptions.isCanLoop && mBannerPagerAdapter != null && mBannerPagerAdapter!!.listSize > 1)
    }


    override fun onSaveInstanceState(): Parcelable {
        val superState = super.onSaveInstanceState()
        val bundle = Bundle()
        bundle.putParcelable(KEY_SUPER_STATE, superState)
        bundle.putInt(KEY_CURRENT_POSITION, currentPosition)
        bundle.putBoolean(KEY_IS_CUSTOM_INDICATOR, isCustomIndicator)
        return bundle
    }

    override fun onRestoreInstanceState(state: Parcelable) {
        val bundle = state as Bundle
        val superState = bundle.getParcelable<Parcelable>(KEY_SUPER_STATE)
        super.onRestoreInstanceState(superState)
        currentPosition = bundle.getInt(KEY_CURRENT_POSITION)
        isCustomIndicator = bundle.getBoolean(KEY_IS_CUSTOM_INDICATOR)
        setCurrentItem(currentPosition, false)
    }

    fun getData(): List<*> {
        return if (mBannerPagerAdapter != null) {
            mBannerPagerAdapter!!.data
        } else emptyList<Any>()
    }

    /**
     * Start loop
     */
    fun startLoop() {
        if (!isLooping && isAutoPlay() && mBannerPagerAdapter != null && mBannerPagerAdapter!!.listSize > 1) {
            mHandler.postDelayed(mRunnable, getInterval().toLong())
            isLooping = true
        }
    }

    /**
     * Stop loop
     */
    fun stopLoop() {
        if (isLooping) {
            mHandler.removeCallbacks(mRunnable)
            isLooping = false
        }
    }

    fun setAdapter(adapter: BaseBannerAdapter<*>): BannerViewPager2 {
        mBannerPagerAdapter = adapter
        return this
    }

    fun getAdapter(): BaseBannerAdapter<*>? {
        return mBannerPagerAdapter
    }

    fun setRoundCorner(radius: Int): BannerViewPager2 {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mBannerManager.bannerOptions.roundRectRadius = radius
        } else {
            setRoundCorner(radius, radius, radius, radius)
        }
        return this
    }

    fun setRoundCorner(
        topLeftRadius: Int, topRightRadius: Int,
        bottomLeftRadius: Int,
        bottomRightRadius: Int
    ): BannerViewPager2 {
        mRadiusRectF = RectF()
        mRadiusPath = Path()
        mBannerManager.bannerOptions
            .setRoundRectRadius(topLeftRadius, topRightRadius, bottomLeftRadius, bottomRightRadius)
        return this
    }

    fun setAutoPlay(autoPlay: Boolean): BannerViewPager2 {
        mBannerManager.bannerOptions.isAutoPlay = autoPlay
        if (isAutoPlay()) {
            mBannerManager.bannerOptions.isCanLoop = true
        }
        return this
    }

    fun setCanLoop(canLoop: Boolean): BannerViewPager2 {
        mBannerManager.bannerOptions.isCanLoop = canLoop
        if (!canLoop) {
            mBannerManager.bannerOptions.isAutoPlay = false
        }
        return this
    }

    fun setInterval(interval: Int): BannerViewPager2 {
        mBannerManager.bannerOptions.interval = interval
        return this
    }

    fun setPageTransformer(transformer: ViewPager2.PageTransformer): BannerViewPager2 {
        mViewPager.setPageTransformer(transformer)
        return this
    }

    fun addPageTransformer(transformer: ViewPager2.PageTransformer): BannerViewPager2 {
        mBannerManager.addTransformer(transformer)
        return this
    }

    fun removeTransformer(transformer: ViewPager2.PageTransformer) {
        mBannerManager.removeTransformer(transformer)
    }

    fun removeDefaultPageTransformer() {
        mBannerManager.removeDefaultPageTransformer()
    }

    fun removeMarginPageTransformer() {
        mBannerManager.removeMarginPageTransformer()
    }

    fun setPageMargin(pageMargin: Int): BannerViewPager2 {
        mBannerManager.setPageMargin(pageMargin)
        return this
    }

    fun setOnPageClickListener(onPageClickListener: BannerViewPager.OnPageClickListener): BannerViewPager2 {
        mOnPageClickListener = onPageClickListener
        return this
    }

    fun setScrollDuration(scrollDuration: Int): BannerViewPager2 {
        mBannerManager.bannerOptions.scrollDuration = scrollDuration
        return this
    }

    fun setIndicatorSliderColor(
        @ColorInt normalColor: Int,
        @ColorInt checkedColor: Int
    ): BannerViewPager2 {
        mBannerManager.bannerOptions.setIndicatorSliderColor(normalColor, checkedColor)
        return this
    }

    fun setIndicatorSliderRadius(
        normalRadius: Int,
        checkedRadius: Int = normalRadius
    ): BannerViewPager2 {
        mBannerManager.bannerOptions.setIndicatorSliderWidth(normalRadius * 2, checkedRadius * 2)
        return this
    }

    fun setIndicatorSliderWidth(normalWidth: Int, checkWidth: Int): BannerViewPager2 {
        mBannerManager.bannerOptions.setIndicatorSliderWidth(normalWidth, checkWidth)
        return this
    }

    fun setIndicatorHeight(indicatorHeight: Int): BannerViewPager2 {
        mBannerManager.bannerOptions.setIndicatorHeight(indicatorHeight)
        return this
    }

    fun setIndicatorSliderGap(indicatorGap: Int): BannerViewPager2 {
        mBannerManager.bannerOptions.indicatorGap = indicatorGap.toFloat()
        return this
    }

    fun setIndicatorVisibility(@Visibility visibility: Int): BannerViewPager2 {
        mBannerManager.bannerOptions.indicatorVisibility = visibility
        return this
    }

    fun setIndicatorGravity(@AIndicatorGravity gravity: Int): BannerViewPager2 {
        mBannerManager.bannerOptions.indicatorGravity = gravity
        return this
    }

    fun setIndicatorSlideMode(@AIndicatorSlideMode slideMode: Int): BannerViewPager2 {
        mBannerManager.bannerOptions.indicatorSlideMode = slideMode
        return this
    }

    fun setIndicatorView(customIndicator: IIndicator): BannerViewPager2 {
        if (customIndicator is View) {
            isCustomIndicator = true
            mIndicatorView = customIndicator
        }
        return this
    }

    fun setIndicatorStyle(@AIndicatorStyle indicatorStyle: Int): BannerViewPager2 {
        mBannerManager.bannerOptions.indicatorStyle = indicatorStyle
        return this
    }

    fun <T> create(data: List<T>?) {
        if (mBannerPagerAdapter == null) {
            throw NullPointerException("You must set adapter for BannerViewPager")
        }
        mBannerPagerAdapter!!.data = data
        initBannerData()
    }

    fun <T> create() {
        create(listOf<T>())
    }

    fun setOrientation(@ViewPager2.Orientation orientation: Int): BannerViewPager2 {
        mBannerManager.bannerOptions.orientation = orientation
        return this
    }

    fun addItemDecoration(decor: RecyclerView.ItemDecoration, index: Int) {
        if (isCanLoopSafely()) {
            val pageSize = mBannerPagerAdapter!!.listSize
            val currentItem = mViewPager.currentItem
            val realPosition = BannerUtils.getRealPosition(currentItem, pageSize)
            if (currentItem != index) {
                if (index == 0 && realPosition == pageSize - 1) {
                    mViewPager.addItemDecoration(decor, currentItem + 1)
                } else if (realPosition == 0 && index == pageSize - 1) {
                    mViewPager.addItemDecoration(decor, currentItem - 1)
                } else {
                    mViewPager.addItemDecoration(decor, currentItem + (index - realPosition))
                }
            }
        } else {
            mViewPager.addItemDecoration(decor, index)
        }
    }

    fun addItemDecoration(decor: RecyclerView.ItemDecoration) {
        mViewPager.addItemDecoration(decor)
    }

    fun <T> refreshData(list: List<T>) {
        post {
            if (isAttachedToWindow && mBannerPagerAdapter != null) {
                stopLoop()
                mBannerPagerAdapter!!.data = list
                mBannerPagerAdapter!!.notifyDataSetChanged()
                resetCurrentItem(getCurrentItem())
                refreshIndicator(list)
                startLoop()
            }
        }
    }

    fun <T> addData(list: List<T>) {
        mBannerPagerAdapter?.let {
            if (isAttachedToWindow) {
                val data: MutableList<T> = it.data as MutableList<T>
                data.addAll(list)
                mBannerPagerAdapter!!.notifyItemRangeInserted(
                    mBannerPagerAdapter!!.data.size - 1,
                    list.size
                )
                resetCurrentItem(getCurrentItem())
                refreshIndicator(data)
            }
        }


    }

    fun removeItem(index: Int) {
        val data: MutableList<*> = mBannerPagerAdapter!!.data
        if (isAttachedToWindow && index >= 0 && index < data.size) {
            data.removeAt(index)
            mBannerPagerAdapter!!.notifyItemRemoved(index)
            resetCurrentItem(getCurrentItem())
            refreshIndicator(data)
        }
    }

    fun <T> insertItem(index: Int, item: T) {
        mBannerPagerAdapter?.let {
            val data: MutableList<T> = it.data as MutableList<T>
            if (isAttachedToWindow && index >= 0 && index <= data.size) {
                data.add(index, item)
                mBannerPagerAdapter!!.notifyItemRangeChanged(mBannerPagerAdapter!!.data.size, 1)
                resetCurrentItem(getCurrentItem())
                refreshIndicator(data)
            }
        }

    }

    fun getCurrentItem(): Int {
        return currentPosition
    }

    fun setCurrentItem(item: Int) {
        setCurrentItem(item, true)
    }

    fun setCurrentItem(item: Int, smoothScroll: Boolean) {
        var newItem = item
        if (isCanLoopSafely()) {
            val pageSize = mBannerPagerAdapter!!.listSize
            newItem = if (newItem >= pageSize) pageSize - 1 else newItem
            val currentItem = mViewPager.currentItem
            val realPosition = BannerUtils.getRealPosition(currentItem, pageSize)
            if (currentItem != newItem) {
                if (newItem == 0 && realPosition == pageSize - 1) {
                    mViewPager.setCurrentItem(currentItem + 1, smoothScroll)
                } else if (realPosition == 0 && newItem == pageSize - 1) {
                    mViewPager.setCurrentItem(currentItem - 1, smoothScroll)
                } else {
                    mViewPager.setCurrentItem(currentItem + (newItem - realPosition), smoothScroll)
                }
            }
        } else {
            mViewPager.setCurrentItem(newItem, smoothScroll)
        }
    }

    fun setPageStyle(@APageStyle pageStyle: Int): BannerViewPager2 {
        return setPageStyle(pageStyle, ScaleInTransformer.DEFAULT_MIN_SCALE)
    }

    fun setPageStyle(@APageStyle pageStyle: Int, pageScale: Float): BannerViewPager2 {
        mBannerManager.bannerOptions.pageStyle = pageStyle
        mBannerManager.bannerOptions.pageScale = pageScale
        return this
    }

    fun setRevealWidth(
        leftRevealWidth: Int,
        rightRevealWidth: Int = leftRevealWidth
    ): BannerViewPager2 {
        mBannerManager.bannerOptions.rightRevealWidth = rightRevealWidth
        mBannerManager.bannerOptions.leftRevealWidth = leftRevealWidth
        return this
    }

    fun setOffScreenPageLimit(offScreenPageLimit: Int): BannerViewPager2 {
        mBannerManager.bannerOptions.offScreenPageLimit = offScreenPageLimit
        return this
    }

    fun setIndicatorMargin(left: Int, top: Int, right: Int, bottom: Int): BannerViewPager2 {
        mBannerManager.bannerOptions.setIndicatorMargin(left, top, right, bottom)
        return this
    }

    fun setUserInputEnabled(userInputEnabled: Boolean): BannerViewPager2 {
        mBannerManager.bannerOptions.isUserInputEnabled = userInputEnabled
        mViewPager.isUserInputEnabled = userInputEnabled
        return this
    }

    fun registerOnPageChangeCallback(
        onPageChangeCallback: OnPageChangeCallback
    ): BannerViewPager2 {
        this.onPageChangeCallback = onPageChangeCallback
        return this
    }

    fun setLifecycleRegistry(lifecycleRegistry: Lifecycle): BannerViewPager2 {
        lifecycleRegistry.addObserver(this)
        return this
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun onPause() {
        stopLoop()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun onResume() {
        startLoop()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestroy() {
        stopLoop()
    }

    fun disallowParentInterceptDownEvent(
        disallowParentInterceptDownEvent: Boolean
    ): BannerViewPager2 {
        mBannerManager.bannerOptions.isDisallowParentInterceptDownEvent =
            disallowParentInterceptDownEvent
        return this
    }

    fun setRTLMode(rtlMode: Boolean): BannerViewPager2 {
        mViewPager.layoutDirection = if (rtlMode) LAYOUT_DIRECTION_RTL else LAYOUT_DIRECTION_LTR
        mBannerManager.bannerOptions.isRtl = rtlMode
        return this
    }

    fun stopLoopWhenDetachedFromWindow(stopLoopWhenDetachedFromWindow: Boolean): BannerViewPager2 {
        mBannerManager.bannerOptions.isStopLoopWhenDetachedFromWindow =
            stopLoopWhenDetachedFromWindow
        return this
    }

    fun showIndicatorWhenOneItem(showIndicatorWhenOneItem: Boolean): BannerViewPager2 {
        mBannerManager.bannerOptions
            .showIndicatorWhenOneItem(showIndicatorWhenOneItem)
        return this
    }
}