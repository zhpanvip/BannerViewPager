package com.example.zhpan.circleviewpager;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.example.viewpager.holder.HolderCreator;
import com.example.viewpager.holder.ViewHolder;
import com.example.viewpager.view.CircleViewPager;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private CircleViewPager mViewpager;
    private CircleViewPager mViewPager2;
    private List<String> mList = new ArrayList<>();
    private List<Integer> mListInt = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initData();
        setViewPager();
    }

    private void initView() {
        mViewpager = (CircleViewPager) findViewById(R.id.viewpager);
        mViewPager2 = (CircleViewPager) findViewById(R.id.viewpager2);
    }

    private void initData() {
        mList.add("http://img0.imgtn.bdimg.com/it/u=3159618424,497154385&fm=214&gp=0.jpg");
        mList.add("http://imgsrc.baidu.com/imgad/pic/item/810a19d8bc3eb1359d5a74a4ac1ea8d3fd1f4414.jpg");
        mList.add("http://img4.imgtn.bdimg.com/it/u=928730363,1881984966&fm=214&gp=0.jpg");
        mList.add("http://img4.imgtn.bdimg.com/it/u=3779410813,199087977&fm=214&gp=0.jpg");
        mList.add("http://img2.niutuku.com/desk/1208/1450/ntk-1450-9891.jpg");

        for (int i = 1; i <= 5; i++) {
            int drawable = getResources().getIdentifier("a" + i, "drawable", getPackageName());
            mListInt.add(drawable);
        }
    }

    private void setViewPager() {
        //  设置指示器资源图片
        mViewpager.setIndicator(R.drawable.red_dot,R.drawable.red_dot_night);
        //  设置指示器半径大小（dp）
        mViewpager.setDotWidth(8);
        //  设置指示器位置
        mViewpager.setIndicatorGravity(CircleViewPager.IndicatorGravity.END);
        //  是否显示指示器
        mViewpager.isShowIndicator(true);
        //  设置图片切换时间间隔
        mViewpager.setInterval(3000);
        //  设置页面点击事件
        mViewpager.setOnPageClickListener(new CircleViewPager.OnPageClickListener() {
            @Override
            public void onPageClick(int position) {
                List<String> list = mViewpager.getList();
                Toast.makeText(MainActivity.this, "点击了第" + (position+1) + "个图片\n URL: " +list.get(position), Toast.LENGTH_SHORT).show();
            }
        });

        mViewpager.setPages(mList, new HolderCreator<ViewHolder>() {
            @Override
            public ViewHolder createViewHolder() {
                return new MyViewHolder();
            }
        });


        mViewPager2.setPages(mListInt, new HolderCreator<ViewHolder>() {
            @Override
            public ViewHolder createViewHolder() {
                return new MyViewHolder();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mViewPager2.stopLoop();
        mViewpager.stopLoop();
    }
}
