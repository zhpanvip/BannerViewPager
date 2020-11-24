package com.example.zhpan.banner.activity

import androidx.appcompat.app.AppCompatActivity

import android.os.Bundle

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
