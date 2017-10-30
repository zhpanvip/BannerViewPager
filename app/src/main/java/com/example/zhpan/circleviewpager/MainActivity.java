package com.example.zhpan.circleviewpager;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.viewpager.holder.HolderCreator;
import com.example.viewpager.holder.ViewHolder;
import com.example.viewpager.utils.ImageLoaderUtil;
import com.example.viewpager.view.CircleViewPager;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
        mViewpager.setDarkDotRes(R.drawable.red_dot_night);
        mViewpager.setLightDotRes(R.drawable.red_dot);
        mViewpager.setDotWidth(7);
        mViewpager.setInterval(5000);
        mViewpager.setPages(mList, new HolderCreator() {
            @Override
            public ViewHolder createViewHolder() {
                return new BannerViewHolder();
            }
        });

        mViewpager.setOnPageClickListener(new CircleViewPager.OnPageClickListener() {
            @Override
            public void onPageClick(int position) {
                Toast.makeText(MainActivity.this, "点击了第" + position + "个图片 \nURL:" + mViewpager.getUrlList().get(position), Toast.LENGTH_SHORT).show();
            }
        });

        mViewPager2.setPages(mListInt, new HolderCreator() {
            @Override
            public ViewHolder createViewHolder() {

                return new BannerViewHolder();
            }
        });
    }

    public static class BannerViewHolder implements ViewHolder<Object> {
        private ImageView mImageView;

        @Override
        public View createView(Context context) {
            // 返回页面布局文件
            View view = LayoutInflater.from(context).inflate(R.layout.banner_item, null);
            mImageView = (ImageView) view.findViewById(R.id.banner_image);
            return view;
        }

        @Override
        public void onBind(Context context, int position, Object data) {
            // 数据绑定
            if (data instanceof Integer)
                mImageView.setImageResource((Integer) data);
            else if (data instanceof String) {
                ImageLoaderUtil.loadImg(mImageView, (String) data);
            }
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mViewPager2.stopCircleViewPager();
        mViewpager.stopCircleViewPager();
    }
}
