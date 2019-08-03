package com.example.zhpan.circleviewpager.activity;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.example.zhpan.circleviewpager.R;
import com.example.zhpan.circleviewpager.viewholder.PhotoViewHolder;
import com.zhpan.bannerview.holder.HolderCreator;
import com.zhpan.bannerview.view.BannerViewPager;

import java.util.ArrayList;
import java.util.List;

public class BannerPhotoViewActivity extends AppCompatActivity {
    private List<Integer> mDrawableList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_banner_photo_view);
        setTitle(R.string.wrapper_photo_view);
        initData();
        initViewPager();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //  没有开启自动轮播，不需要stopLoop
    }

    private void initViewPager() {
        BannerViewPager<Integer, PhotoViewHolder> bannerViewPager = findViewById(R.id.viewpager);
        bannerViewPager.setAutoPlay(false)
                .setCanLoop(false)
                .setIndicatorColor(Color.parseColor("#6C6D72"), Color.parseColor("#18171C"))
                .setData(mDrawableList)
                .setHolderCreator(new HolderCreator<PhotoViewHolder>() {
                    @Override
                    public PhotoViewHolder createViewHolder() {
                        return new PhotoViewHolder();
                    }
                })
                .setOnPageClickListener(new BannerViewPager.OnPageClickListener() {
                    @Override
                    public void onPageClick(int position) {
                        Toast.makeText(BannerPhotoViewActivity.this, "图片" + (position + 1), Toast.LENGTH_SHORT).show();
                    }
                }).create();
    }

    private void initData() {
        for (int i = 0; i <= 3; i++) {
            int drawable2 = getResources().getIdentifier("c" + i, "drawable", getPackageName());
            mDrawableList.add(drawable2);
        }
    }
}
