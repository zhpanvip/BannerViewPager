package com.example.zhpan.circleviewpager.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import androidx.annotation.ColorInt
import androidx.annotation.ColorRes

import com.trello.rxlifecycle2.components.support.RxFragment

import java.util.ArrayList

import butterknife.ButterKnife
import butterknife.Unbinder

/**
 * MVC模式的Base fragment
 */
abstract class BaseFragment : RxFragment() {
    protected var mDrawableList: MutableList<Int> = ArrayList()
    protected lateinit var mContext: Context
    private var mBind: Unbinder? = null


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
        mBind = ButterKnife.bind(this, view)
        initData()
        initTitle()
        initView(savedInstanceState, view)
        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        if (mBind != null) {
            mBind!!.unbind()
        }
    }

    private fun initData() {
        for (i in 0..3) {
            val drawable = resources.getIdentifier("t$i", "drawable", mContext.packageName)
            mDrawableList.add(drawable)
        }
    }

    @ColorInt
    protected fun getColor(@ColorRes colorRes: Int): Int {
        return context!!.resources.getColor(colorRes)
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
