package com.example.viewpager.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.viewpager.view.CircleViewPager;

import java.util.List;

/**
 * Created by zhpan on 2017/3/28.
 */

public class CirclePagerAdapter extends PagerAdapter {
    private List<ImageView> list;
    private CircleViewPager viewPager;

    public CirclePagerAdapter(List<ImageView> list, CircleViewPager viewPager) {
        this.list = list;
        this.viewPager = viewPager;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(final ViewGroup container, final int position) {
        ImageView imageView = list.get(position);
        container.addView(imageView);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(list.size()>1){
                    viewPager.imageClick(position - 1);
                }else {
                    viewPager.imageClick(position);
                }

            }
        });
        return list.get(position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(list.get(position));
    }
}
