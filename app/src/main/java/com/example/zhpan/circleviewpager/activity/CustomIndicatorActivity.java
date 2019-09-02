package com.example.zhpan.circleviewpager.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.zhpan.circleviewpager.R;
import com.example.zhpan.circleviewpager.RectangleIndicatorView;
import com.example.zhpan.circleviewpager.viewholder.SlideModeViewHolder;
import com.zhpan.bannerview.BannerViewPager;

import java.util.Arrays;
import java.util.List;

public class CustomIndicatorActivity extends AppCompatActivity {
    private String[] picUrls = {"http://pic31.nipic.com/20130801/11604791_100539834000_2.jpg",
            "http://pic37.nipic.com/20140115/7430301_100825571157_2.jpg",
            "http://pic29.nipic.com/20130507/8952533_183922555000_2.jpg",
            "http://b-ssl.duitang.com/uploads/item/201706/10/20170610095055_G5LM8.jpeg"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_indicator_slide_mode);
        setTitle(getString(R.string.custom_indicator_view));
        BannerViewPager<String, SlideModeViewHolder> viewPager = findViewById(R.id.banner_view);
        List<String> list = Arrays.asList(picUrls);
        RectangleIndicatorView indicatorView = new RectangleIndicatorView(this);
        indicatorView.setPageSize(list.size()).setSliderWidth(8f).setSliderHeight(3.5f)
                .setIndicatorMargin(5f).setCheckedColor(getResources().getColor(R.color.colorAccent))
                .setNormalColor(getResources().getColor(R.color.colorPrimary));
        viewPager.setAutoPlay(false).setCanLoop(true)
                .setRoundCorner(15f)
                .setIndicatorView(indicatorView)
                .setHolderCreator(SlideModeViewHolder::new).create(list);
    }
}
