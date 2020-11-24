package com.example.zhpan.banner.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat

import com.trello.rxlifecycle2.components.support.RxFragment

import java.util.ArrayList

/**
 * MVC模式的Base fragment
 */
abstract class BaseFragment : RxFragment() {
    protected var mDrawableList: MutableList<Int> = ArrayList()
    protected lateinit var mContext: Context

    protected var mPictureList: MutableList<Int> = ArrayList()

    /**
     * 获取当前Activity的UI布局
     *
     * @return 布局id
     */
    protected abstract val layout: Int

    override fun onAttach(context: Context) {
        super.onAttach(context)
        this.mContext = context.applicationContext
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(layout, container, false)
        initData(3)
        initTitle()
        initView(savedInstanceState, view)
        return view
    }

    protected fun getPicList(count:Int): MutableList<Int> {
        mPictureList.clear()
        for (i in 0..count) {
            val drawable = resources.getIdentifier("advertise$i", "drawable", mContext.packageName)
            mPictureList.add(drawable)
        }
        return mPictureList;
    }

    protected fun initData(j: Int) {
        mDrawableList.clear()
        for (i in 0..j) {
            val drawable = resources.getIdentifier("bg_card$i", "drawable", mContext.packageName)
            mDrawableList.add(drawable)
        }
    }

    @ColorInt
    protected fun getColor(@ColorRes colorRes: Int): Int {
        return ContextCompat.getColor(context!!, colorRes)
    }

    /**
     * 初始化标题
     */
    protected abstract fun initTitle()

    /**
     * 初始化数据
     */
    protected abstract fun initView(savedInstanceState: Bundle?, view: View)

}
