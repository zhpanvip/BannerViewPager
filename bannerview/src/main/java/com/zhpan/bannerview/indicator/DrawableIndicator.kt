package com.zhpan.bannerview.indicator

import android.content.Context
import android.graphics.*
import android.os.Build
import android.util.AttributeSet
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import com.zhpan.indicator.base.BaseIndicatorView

/**
 * @ author : zhouweibin
 * @ time: 2019/12/18 17:04.
 * @ desc: 选中与未选中的图片长宽可能不一样
 */
class DrawableIndicator @JvmOverloads constructor(context: Context?, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : BaseIndicatorView(context!!, attrs, defStyleAttr) {
    // 选中时的Bitmap
    private var mCheckedBitmap: Bitmap? = null

    // 未选中时的Bitmap
    private var mNormalBitmap: Bitmap? = null

    // 图片之间的间距
    private var mIndicatorPadding = 0

    // 选中图片的宽度
    private var mCheckedBitmapWidth = 0

    // 选中图片的高度
    private var mCheckedBitmapHeight = 0

    //未选中图片的宽高
    private var mNormalBitmapWidth = 0
    private var mNormalBitmapHeight = 0
    private var mIndicatorSize: IndicatorSize? = null
    private var normalCanResize = true
    private var checkCanResize = true
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val maxHeight = mCheckedBitmapHeight.coerceAtLeast(mNormalBitmapHeight)
        val realWidth = mCheckedBitmapWidth + (mNormalBitmapWidth + mIndicatorPadding) * (getPageSize() - 1)
        setMeasuredDimension(realWidth, maxHeight)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        if (getPageSize() > 1 && mCheckedBitmap != null && mNormalBitmap != null) {
            for (i in 1 until getPageSize() + 1) {
                var left: Int
                var top: Int
                var bitmap = mNormalBitmap
                val index = i - 1
                when {
                    index < getCurrentPosition() -> {
                        left = (i - 1) * (mNormalBitmapWidth + mIndicatorPadding)
                        top = measuredHeight / 2 - mNormalBitmapHeight / 2
                    }
                    index == getCurrentPosition() -> {
                        left = (i - 1) * (mNormalBitmapWidth + mIndicatorPadding)
                        top = measuredHeight / 2 - mCheckedBitmapHeight / 2
                        bitmap = mCheckedBitmap
                    }
                    else -> {
                        left = (i - 1) * mIndicatorPadding + (i - 2) * mNormalBitmapWidth + mCheckedBitmapWidth
                        top = measuredHeight / 2 - mNormalBitmapHeight / 2
                    }
                }
                drawIcon(canvas, left, top, bitmap)
            }
        }
    }

    override fun rotateCanvas(canvas: Canvas) {

    }

    private fun drawIcon(canvas: Canvas, left: Int, top: Int, icon: Bitmap?) {
        if (icon == null) {
            return
        }
        canvas.drawBitmap(icon, left.toFloat(), top.toFloat(), null)
    }

    private fun initIconSize() {
        mCheckedBitmap?.let {
            mIndicatorSize?.let {
                if (mCheckedBitmap!!.isMutable && checkCanResize) {
                    mCheckedBitmap!!.width = mIndicatorSize!!.checkedWidth
                    mCheckedBitmap!!.height = mIndicatorSize!!.checkedHeight
                } else {
                    val width = mCheckedBitmap!!.width
                    val height = mCheckedBitmap!!.height
                    val scaleWidth = mIndicatorSize!!.checkedWidth.toFloat() / width
                    val scaleHeight = mIndicatorSize!!.checkedHeight.toFloat() / height
                    val matrix = Matrix()
                    matrix.postScale(scaleWidth, scaleHeight)
                    mCheckedBitmap = Bitmap.createBitmap(mCheckedBitmap!!, 0, 0, width, height, matrix, true)
                }
            }
            mCheckedBitmapWidth = mCheckedBitmap!!.width
            mCheckedBitmapHeight = mCheckedBitmap!!.height
        }
        mNormalBitmap?.let {
            mIndicatorSize?.let {
                if (mNormalBitmap!!.isMutable && normalCanResize) {
                    mNormalBitmap!!.width = mIndicatorSize!!.normalWidth
                    mNormalBitmap!!.height = mIndicatorSize!!.normalHeight
                } else {
                    val width = mNormalBitmap!!.width
                    val height = mNormalBitmap!!.height
                    val scaleWidth = mIndicatorSize!!.normalWidth.toFloat() / mNormalBitmap!!.width
                    val scaleHeight = mIndicatorSize!!.normalHeight.toFloat() / mNormalBitmap!!.height
                    val matrix = Matrix()
                    matrix.postScale(scaleWidth, scaleHeight)
                    mNormalBitmap = Bitmap.createBitmap(mNormalBitmap!!, 0, 0, width, height, matrix, true)
                }
            }
            mNormalBitmapWidth = mNormalBitmap!!.width
            mNormalBitmapHeight = mNormalBitmap!!.height
        }
    }

    fun setIndicatorDrawable(@DrawableRes normalDrawable: Int, @DrawableRes checkedDrawable: Int): DrawableIndicator {
        mNormalBitmap = BitmapFactory.decodeResource(resources, normalDrawable)
        mCheckedBitmap = BitmapFactory.decodeResource(resources, checkedDrawable)
        if (mNormalBitmap == null) {
            mNormalBitmap = getBitmapFromVectorDrawable(context, normalDrawable)
            normalCanResize = false
        }
        if (mCheckedBitmap == null) {
            mCheckedBitmap = getBitmapFromVectorDrawable(context, checkedDrawable)
            checkCanResize = false
        }
        initIconSize()
        postInvalidate()
        return this
    }

    fun setIndicatorSize(normalWidth: Int, normalHeight: Int, checkedWidth: Int, checkedHeight: Int): DrawableIndicator {
        mIndicatorSize = IndicatorSize(normalWidth, normalHeight, checkedWidth, checkedHeight)
        initIconSize()
        postInvalidate()
        return this
    }

    fun setIndicatorGap(padding: Int): DrawableIndicator {
        if (padding >= 0) {
            mIndicatorPadding = padding
            postInvalidate()
        }
        return this
    }

    internal class IndicatorSize(var normalWidth: Int, var normalHeight: Int, var checkedWidth: Int, var checkedHeight: Int)

    private fun getBitmapFromVectorDrawable(context: Context, drawableId: Int): Bitmap? {
        var drawable = ContextCompat.getDrawable(context, drawableId)
        drawable?.let {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                drawable = DrawableCompat.wrap(drawable!!).mutate()
            }
            val bitmap = Bitmap.createBitmap(drawable!!.intrinsicWidth,
                    drawable!!.intrinsicHeight, Bitmap.Config.ARGB_8888)
            val canvas = Canvas(bitmap)
            drawable!!.setBounds(0, 0, canvas.width, canvas.height)
            drawable!!.draw(canvas)
            return bitmap
        }
        return null
    }
}