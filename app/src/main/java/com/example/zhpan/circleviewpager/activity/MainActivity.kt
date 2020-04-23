package com.example.zhpan.circleviewpager.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.example.zhpan.circleviewpager.R
import com.example.zhpan.circleviewpager.adapter.AdapterFragmentPager
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initView()
        initData()
        setListener()
    }

    private fun initView() {
        toolbar.apply {
            title = getString(R.string.app_name)
            setSupportActionBar(toolbar)
        }
        drawerLayout.apply {
            val toggle = ActionBarDrawerToggle(
                    this@MainActivity,
                    this,
                    toolbar
                    , R.string.navigation_drawer_open,
                    R.string.navigation_drawer_close)
            addDrawerListener(toggle)
            toggle.syncState()
        }
        nav_view.apply {
            setNavigationItemSelectedListener(onDrawerNavigationItemSelectedListener)
        }
    }

    private fun initData() {
        with(vp_fragment) {
            adapter = AdapterFragmentPager(this@MainActivity)
            isUserInputEnabled = true
            registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    rg_tab?.check(getCheckedId(position))
                }
            })
        }
    }

    private fun getCheckedId(position: Int): Int {
        return when (position) {
            0 -> R.id.rb_home
            1 -> R.id.rb_add
            2 -> R.id.rb_find
            3 -> R.id.rb_others
            else -> R.id.rb_home
        }
    }

    private fun setListener() {
        rg_tab?.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.rb_home -> vp_fragment.setCurrentItem(AdapterFragmentPager.PAGE_HOME, true)
                R.id.rb_add -> vp_fragment.setCurrentItem(AdapterFragmentPager.PAGE_FIND, true)
                R.id.rb_find -> vp_fragment.setCurrentItem(AdapterFragmentPager.PAGE_INDICATOR, true)
                R.id.rb_others -> vp_fragment.setCurrentItem(AdapterFragmentPager.PAGE_OTHERS, true)
            }
        }
    }

    companion object {
        fun start(context: Context) {
            context.startActivity(Intent(context, MainActivity::class.java))
        }
    }

    private val onDrawerNavigationItemSelectedListener =
            NavigationView.OnNavigationItemSelectedListener { item ->
                when (item.itemId) {
                    R.id.nav_banner -> {
                        WebViewActivity.start(this@MainActivity, getString(R.string.app_name), "https://github.com/zhpanvip/BannerViewPager")
                    }

                    R.id.nav_indicator -> {
                        WebViewActivity.start(this@MainActivity, getString(R.string.indicator_name), "https://github.com/zhpanvip/ViewPagerIndicator")
                    }
                }
                true
            }
}
