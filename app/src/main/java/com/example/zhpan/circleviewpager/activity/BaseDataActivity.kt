package com.example.zhpan.circleviewpager.activity


import android.os.Bundle
import android.support.v7.app.AppCompatActivity

import java.util.ArrayList

abstract class BaseDataActivity : AppCompatActivity() {

    protected var mDrawableList: MutableList<Int> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initData()
    }

    private fun initData() {
        for (i in 0..2) {
            val drawable = resources.getIdentifier("guide$i", "drawable", packageName)
            mDrawableList.add(drawable)
        }
    }
}
