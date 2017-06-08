package com.example.zhpan.circleviewpager;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.List;

/**
 * Created by edianzu on 2017/3/28.
 */

public class CirclePagerAdapter extends PagerAdapter {
    List<ImageView> list;
    CircleViewPager viewPager;

    public CirclePagerAdapter(List<ImageView> list,CircleViewPager viewPager) {
        this.list = list;
        this.viewPager=viewPager;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }

    @Override
    public Object instantiateItem(final ViewGroup container, final int position) {
        ImageView imageView=list.get(position);
        container.addView(imageView);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // Toast.makeText(container.getContext(), "点击了="+position, Toast.LENGTH_SHORT).show();
                viewPager.imageClick(position);
            }
        });
        return list.get(position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(list.get(position));
    }

    public interface OnPageClickListener{
        void pageClickListener(int position);
    }
}
