package com.example.zhpan.banner.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.example.zhpan.banner.R
import com.example.zhpan.banner.adapter.others.AdapterFragmentPager
import com.example.zhpan.banner.databinding.ActivityMainBinding
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity() {
  private lateinit var binding: ActivityMainBinding
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityMainBinding.inflate(layoutInflater)
    setContentView(binding.root)
    initView()
    initData()
    setListener()
  }

  override fun onResume() {
    super.onResume()
    val width = window.decorView.width
    val height = window.decorView.height
    Log.d("Main", "width = $width,height = $height")
  }

  private fun initView() {
    binding.toolbar.apply {
      title = getString(R.string.app_name)
      setSupportActionBar(binding.toolbar)
    }
    binding.drawerLayout.apply {
      val toggle = ActionBarDrawerToggle(
        this@MainActivity,
        this,
        binding.toolbar, R.string.navigation_drawer_open,
        R.string.navigation_drawer_close
      )
      addDrawerListener(toggle)
      toggle.syncState()
    }
    binding.navView.apply {
      setNavigationItemSelectedListener(onDrawerNavigationItemSelectedListener)
    }
  }

  private fun initData() {
    with(binding.vpFragment) {
      adapter = AdapterFragmentPager(this@MainActivity)
      registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
        override fun onPageSelected(position: Int) {
          super.onPageSelected(position)
          binding.rgTab.check(getCheckedId(position))
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
    binding.rgTab.setOnCheckedChangeListener { _, checkedId ->
      when (checkedId) {
        R.id.rb_home -> binding.vpFragment.setCurrentItem(AdapterFragmentPager.PAGE_HOME, true)
        R.id.rb_add -> binding.vpFragment.setCurrentItem(AdapterFragmentPager.PAGE_FIND, true)
        R.id.rb_find -> binding.vpFragment.setCurrentItem(
          AdapterFragmentPager.PAGE_INDICATOR,
          true
        )
        R.id.rb_others -> binding.vpFragment.setCurrentItem(AdapterFragmentPager.PAGE_OTHERS, true)
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
          WebViewActivity.start(
            this@MainActivity, getString(R.string.app_name),
            "https://github.com/zhpanvip/BannerViewPager"
          )
        }

        R.id.nav_indicator -> {
          WebViewActivity.start(
            this@MainActivity, getString(R.string.indicator_name),
            "https://github.com/zhpanvip/ViewPagerIndicator"
          )
        }
      }
      true
    }
}
