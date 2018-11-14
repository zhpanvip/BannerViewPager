package com.example.zhpan.circleviewpager;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.zhpan.viewpager.holder.HolderCreator;
import com.zhpan.viewpager.view.CircleViewPager;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private CircleViewPager<DataBean, DataViewHolder> mViewpager;
    private CircleViewPager<Integer, PhotoViewHolder> mViewPager2;
    private List<DataBean> mDataList = new ArrayList<>();
    private List<Integer> mPicResList = new ArrayList<>();
    private String[] picUrls = {"http://img0.imgtn.bdimg.com/it/u=3159618424,497154385&fm=214&gp=0.jpg",
            "http://img4.imgtn.bdimg.com/it/u=928730363,1881984966&fm=214&gp=0.jpg",
            "http://img4.imgtn.bdimg.com/it/u=3779410813,199087977&fm=214&gp=0.jpg",
            "http://img2.niutuku.com/desk/1208/1450/ntk-1450-9891.jpg"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initData();
        setViewPager();
    }

    private void initView() {
        mViewpager = findViewById(R.id.viewpager);
        mViewPager2 = findViewById(R.id.viewpager2);
    }

    private void initData() {
        for (int i = 0; i < picUrls.length; i++) {
            DataBean dataBean = new DataBean(picUrls[i], "图片" + (i + 1));
            mDataList.add(dataBean);
        }

        for (int i = 1; i <= 4; i++) {
            int drawable = getResources().getIdentifier("a" + i, "drawable", getPackageName());
            mPicResList.add(drawable);
        }
    }

    private void setViewPager() {
        //  设置指示器位置
        // mViewpager.setIndicatorGravity(CircleViewPager.END);
        //  是否显示指示器
        mViewpager.isShowIndicator(true);
        //  设置图片切换时间间隔
        mViewpager.setInterval(3000);
        //  设置指示器圆点半径
        // mViewpager.setIndicatorRadius(6);

        //  设置页面点击事件
        mViewpager.setOnPageClickListener(new CircleViewPager.OnPageClickListener() {
            @Override
            public void onPageClick(int position) {
                List<DataBean> list = mViewpager.getList();
                String describe = list.get(position).getDescribe();
                Toast.makeText(MainActivity.this, "点击了" + list.get(position).getDescribe(), Toast.LENGTH_SHORT).show();
            }
        });
        //  设置数据
        mViewpager.setPages(mDataList, new HolderCreator<DataViewHolder>() {
            @Override
            public DataViewHolder createViewHolder() {
                return new DataViewHolder();
            }
        });

        mViewPager2.setAutoPlay(false);
        mViewPager2.setCanLoop(false);
        mViewPager2.setPages(mPicResList, new HolderCreator<PhotoViewHolder>() {
            @Override
            public PhotoViewHolder createViewHolder() {
                return new PhotoViewHolder();
            }
        });
        //  设置指示器资源图片
        mViewPager2.setIndicatorColor(Color.parseColor("#6C6D72"),
                Color.parseColor("#18171C"));
        mViewPager2.setOnPageClickListener(new CircleViewPager.OnPageClickListener() {
            @Override
            public void onPageClick(int position) {
                Toast.makeText(MainActivity.this, "图片" + (position + 1), Toast.LENGTH_SHORT).show();
            }
        });
        mViewPager2.setCurrentItem(2);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mViewPager2.stopLoop();
        mViewpager.stopLoop();
    }
}
