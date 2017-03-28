package com.example.zhpan.circleviewpager;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by edianzu on 2017/3/28.
 */

public class CircleViewPager extends FrameLayout {
    private View mView;
    private ViewPager mViewPager;
    private List<ImageView> mImageViewList;
    private List<ImageView> mImageViewDotList;
    private int[] images;
    private LinearLayout mLinearLayoutDot;
    private int dotPosition = 0;
    private int prePosition = 0;
    private int currentPosition = 1;
    private CirclePagerAdapter adapter;

    private int mLightDotRes;   //  选中时轮播圆点资源id
    private int mDarkDotRes;    //  未选中时轮播圆点资源id
    private float mDotWidth;   //   轮播原点宽度
    private int interval;   //  图片切换时间间隔

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 1) {
                mViewPager.setCurrentItem(currentPosition, false);
            }
        }
    };

    public CircleViewPager(Context context) {
        super(context);
        initView(null);
    }

    public CircleViewPager(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
        initView(attrs);
    }

    public CircleViewPager(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(attrs);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if (changed) {
            initData();
            setDot();
            setViewPager();
            autoPlay();
        }
    }

    private void initView(AttributeSet attrs) {
        if (attrs != null) {
            TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.MyViewPager);
            mLightDotRes = typedArray.getResourceId(R.styleable.MyViewPager_lightDotRes, R.drawable.red_dot);
            mDarkDotRes = typedArray.getResourceId(R.styleable.MyViewPager_darkDotRes, R.drawable.red_dot_night);
            mDotWidth = typedArray.getDimension(R.styleable.MyViewPager_dotWidth, 20);
            interval=typedArray.getInteger(R.styleable.MyViewPager_interval,3000);
        }
        mView = LayoutInflater.from(getContext()).inflate(R.layout.view_pager_layout, this);
        mLinearLayoutDot = (LinearLayout) mView.findViewById(R.id.ll_main_dot);
        mViewPager = (ViewPager) mView.findViewById(R.id.vp_main);
    }


    private void initData() {
        mImageViewList = new ArrayList<>();
        mImageViewDotList = new ArrayList<>();
        ImageView imageView;
        if (images.length > 0) {
            for (int i = 0; i < images.length + 2; i++) {
                if (i == 0) {   //判断当i=0为该处的ImageView设置最后一张图片作为背景
                    imageView = new ImageView(getContext());
                    imageView.setBackgroundResource(images[images.length - 1]);
                    mImageViewList.add(imageView);
                } else if (i == images.length + 1) {   //判断当i=images.length+1时为该处的ImageView设置第一张图片作为背景
                    imageView = new ImageView(getContext());
                    imageView.setBackgroundResource(images[0]);
                    mImageViewList.add(imageView);
                } else {  //其他情况则为ImageView设置images[i-1]的图片作为背景
                    imageView = new ImageView(getContext());
                    imageView.setBackgroundResource(images[i - 1]);
                    mImageViewList.add(imageView);
                }
            }
        }
    }

    //  设置轮播小圆点
    private void setDot() {
        //  设置LinearLayout的子控件的宽高，这里单位是像素。
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams((int) mDotWidth, (int) mDotWidth);
        params.rightMargin = (int) (mDotWidth / 1.5);
        //  for循环创建images.length个ImageView（小圆点）
        for (int i = 0; i < images.length; i++) {
            ImageView imageViewDot = new ImageView(getContext());
            imageViewDot.setLayoutParams(params);
            //  设置小圆点的背景为暗红图片
            imageViewDot.setBackgroundResource(mDarkDotRes);
            mLinearLayoutDot.addView(imageViewDot);
            mImageViewDotList.add(imageViewDot);
        }
        //设置第一个小圆点图片背景为红色
        if (images.length > 0){
            mImageViewDotList.get(dotPosition).setBackgroundResource(mLightDotRes);
        }
    }


    private void setViewPager() {
        adapter = new CirclePagerAdapter(mImageViewList);

        mViewPager.setAdapter(adapter);

        mViewPager.setCurrentItem(currentPosition);
        //页面改变监听
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }
            @Override
            public void onPageSelected(int position) {
                if (position == 0) {    //判断当切换到第0个页面时把currentPosition设置为images.length,即倒数第二个位置，小圆点位置为length-1
                    currentPosition = images.length;
                    dotPosition = images.length - 1;
                } else if (position == images.length + 1) {    //当切换到最后一个页面时currentPosition设置为第一个位置，小圆点位置为0
                    currentPosition = 1;
                    dotPosition = 0;
                } else {
                    currentPosition = position;
                    dotPosition = position - 1;
                }
                //  把之前的小圆点设置背景为暗红，当前小圆点设置为红色
                mImageViewDotList.get(prePosition).setBackgroundResource(mDarkDotRes);
                mImageViewDotList.get(dotPosition).setBackgroundResource(mLightDotRes);
                prePosition = dotPosition;
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                //当state为SCROLL_STATE_IDLE即没有滑动的状态时切换页面
                if (state == ViewPager.SCROLL_STATE_IDLE) {
                    mViewPager.setCurrentItem(currentPosition, false);
                }
            }
        });
    }


    //  设置自动播放
    private void autoPlay() {
        new Thread() {
            @Override
            public void run() {
                super.run();
                while (true) {
                    SystemClock.sleep(interval);
                    currentPosition++;
                    handler.sendEmptyMessage(1);
                }
            }
        }.start();
    }


    public float getDotWidth() {
        return mDotWidth;
    }

    public void setDotWidth(float dotWidth) {
        mDotWidth = ScreenUtils.dp2px(getContext(), dotWidth);
    }

    public int getLightDotRes() {
        return mLightDotRes;
    }

    public void setLightDotRes(int lightDotRes) {
        mLightDotRes = lightDotRes;
    }

    public int getDarkDotRes() {
        return mDarkDotRes;
    }

    public void setDarkDotRes(int darkDotRes) {
        mDarkDotRes = darkDotRes;
    }

    public int[] getImages() {
        return images;
    }

    public void setImages(int[] images) {
        this.images = images;
        invalidate();
    }

    public void setInterval(int interval) {
        this.interval = interval;
    }
}
