package com.example.zhpan.circleviewpager;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.example.zhpan.circleviewpager.viewholder.DataViewHolder;
import com.example.zhpan.circleviewpager.viewholder.LocalImageViewHolder;
import com.example.zhpan.circleviewpager.viewholder.PhotoViewHolder;
import com.zhpan.bannerview.holder.HolderCreator;
import com.zhpan.bannerview.view.BannerViewPager;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private BannerViewPager<DataBean, DataViewHolder> mViewpager;
    private BannerViewPager<Integer, LocalImageViewHolder> mViewPager2;
    private BannerViewPager<Integer, PhotoViewHolder> mViewPager3;
    private List<DataBean> mDataList = new ArrayList<>();
    private List<Integer> mPicResList = new ArrayList<>();
    private List<Integer> mDrawableList = new ArrayList<>();
    private String[] picUrls = {"http://pic31.nipic.com/20130801/11604791_100539834000_2.jpg",
            "http://pic37.nipic.com/20140115/7430301_100825571157_2.jpg",
            "http://pic29.nipic.com/20130507/8952533_183922555000_2.jpg",
            "http://b-ssl.duitang.com/uploads/item/201706/10/20170610095055_G5LM8.jpeg"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initData();
        initViewPager1();
        initViewPager2();
        initViewPager3();
    }

    private void initViewPager1() {
        //  设置指示器位置
        // mViewpager.setIndicatorGravity(CircleViewPager.END);
        //  是否显示指示器
        mViewpager.isShowIndicator(true);
        //  设置图片切换时间间隔
        mViewpager.setInterval(3000);
        //  设置指示器圆点半径
        // mViewpager.setIndicatorRadius(6);

        //  设置页面点击事件
        mViewpager.setOnPageClickListener(new BannerViewPager.OnPageClickListener() {
            @Override
            public void onPageClick(int position) {
                List<DataBean> list = mViewpager.getList();
                String describe = list.get(position).getDescribe();
                Toast.makeText(MainActivity.this, "点击了" + list.get(position).getDescribe(), Toast.LENGTH_SHORT).show();
            }
        });
        //  设置数据
        mViewpager.setData(mDataList, new HolderCreator<DataViewHolder>() {
            @Override
            public DataViewHolder createViewHolder() {
                return new DataViewHolder();
            }
        });
        mViewpager.setRoundCorner(R.dimen.banner_corner);
        mViewpager.setScrollDuration(1000);
    }

    private void initViewPager2() {
        mViewPager2.setAutoPlay(false);
        mViewPager2.setCanLoop(true);
        //  设置指示器资源图片
        mViewPager2.setIndicatorColor(Color.parseColor("#6C6D72"),
                Color.parseColor("#FFFFFF"));

        mViewPager2.setCurrentItem(2, false);
        mViewPager2.setOnPageClickListener(new BannerViewPager.OnPageClickListener() {
            @Override
            public void onPageClick(int position) {
                Toast.makeText(MainActivity.this, "图片" + (position + 1), Toast.LENGTH_SHORT).show();
            }
        });
        mViewPager2.setData(mPicResList, new HolderCreator<LocalImageViewHolder>() {
            @Override
            public LocalImageViewHolder createViewHolder() {
                return new LocalImageViewHolder();
            }
        });

    }

    private void initViewPager3() {
        mViewPager3.setAutoPlay(false);
        mViewPager3.setCanLoop(false);
        //  设置指示器资源图片
        mViewPager3.setIndicatorColor(Color.parseColor("#6C6D72"),
                Color.parseColor("#18171C"));
        mViewPager3.setOnPageClickListener(new BannerViewPager.OnPageClickListener() {
            @Override
            public void onPageClick(int position) {
                Toast.makeText(MainActivity.this, "图片" + (position + 1), Toast.LENGTH_SHORT).show();
            }
        });
        mViewPager3.setCurrentItem(3, false);
        mViewPager3.setData(mDrawableList, new HolderCreator<PhotoViewHolder>() {
            @Override
            public PhotoViewHolder createViewHolder() {
                return new PhotoViewHolder();
            }
        });
    }

    private void initView() {
        mViewpager = findViewById(R.id.viewpager);
        mViewPager2 = findViewById(R.id.viewpager2);
        mViewPager3 = findViewById(R.id.viewpager3);
    }

    private void initData() {
        for (int i = 0; i < picUrls.length; i++) {
            DataBean dataBean = new DataBean(picUrls[i], "图片" + (i + 1));
            mDataList.add(dataBean);
        }

        for (int i = 0; i <= 3; i++) {
            int drawable = getResources().getIdentifier("b" + i, "drawable", getPackageName());
            mPicResList.add(drawable);
            int drawable2 = getResources().getIdentifier("c" + i, "drawable", getPackageName());
            mDrawableList.add(drawable2);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mViewPager2.stopLoop();
        mViewpager.stopLoop();
    }
}
