package com.example.zhpan.banner.adapter.others

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.zhpan.banner.fragment.*

/**
 * <pre>
 * Created by zhangpan on 2019-12-05.
 * Description: MainActivity Fragment Adapter.
</pre> *
 */
class AdapterFragmentPager(fragmentActivity: FragmentActivity) : FragmentStateAdapter(
    fragmentActivity
) {

  override fun createFragment(position: Int): Fragment {
    return when (position) {
      PAGE_HOME -> HomeFragment.getInstance();
      PAGE_FIND -> PageFragment.instance;
      PAGE_INDICATOR -> IndicatorFragment.getInstance();
      PAGE_OTHERS -> OthersFragment.getInstance();
      else -> EmptyFragment.getInstance();
    }
  }

  override fun getItemCount(): Int {
    return 4
  }

  companion object {
    const val PAGE_HOME = 0
    const val PAGE_FIND = 1
    const val PAGE_INDICATOR = 2
    const val PAGE_OTHERS = 3
  }
}
