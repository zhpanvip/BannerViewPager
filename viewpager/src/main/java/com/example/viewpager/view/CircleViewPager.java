package com.example.viewpager.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Handler;
import android.support.annotation.DrawableRes;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.example.viewpager.R;
import com.example.viewpager.adapter.CirclePagerAdapter;
import com.example.viewpager.utils.ImageLoaderUtil;
import com.example.viewpager.utils.ScreenUtils;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhpan on 2017/3/28.
 */

public class CircleViewPager extends FrameLayout {
    private ViewPager mViewPager;
    //  图片对应的ImageView的集合
    private List<ImageView> mIvList;
    //  圆点对应的ImageView的集合
    private List<ImageView> mIvDotList;
    //    图片连接集合
    private List<String> mUrlList;
    //  选中时轮播圆点资源id
    private int mLightDotRes;
    //  未选中时轮播圆点资源id
    private int mDarkDotRes;
    //   轮播原点宽度
    private float mDotWidth;
    //  图片切换时间间隔
    private int interval;
    //  圆点位置
    private int dotPosition = 0;
    //  图片上一个位置
    private int prePosition = 0;
    //  图片当前位置
    private int currentPosition = 1;
    //  是否循环
    private boolean isLoop;

    private LinearLayout mLlDot;
    private OnPageClickListener mOnPageClickListener;

    Handler mHandler = new Handler();
    Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            if (mViewPager.getChildCount() > 1) {
                mHandler.postDelayed(this, interval);
                currentPosition++;
                mViewPager.setCurrentItem(currentPosition, true);
            }
        }
    };


    public CircleViewPager(Context context) {
        super(context);
        init(null);
    }

    public CircleViewPager(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
        init(attrs);
    }

    public CircleViewPager(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if (changed) {
            initImage();
            setDotImage();
            setViewPager();
        }
    }

    private void init(AttributeSet attrs) {
        if (attrs != null) {
            TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.CircleViewPager);
            mLightDotRes = typedArray.getResourceId(R.styleable.CircleViewPager_lightDotRes, R.drawable.red_dot);
            mDarkDotRes = typedArray.getResourceId(R.styleable.CircleViewPager_darkDotRes, R.drawable.red_dot_night);
            mDotWidth = typedArray.getDimension(R.styleable.CircleViewPager_dotWidth, 20);
            interval = typedArray.getInteger(R.styleable.CircleViewPager_interval, 3000);
            typedArray.recycle();
        }
        View mView = LayoutInflater.from(getContext()).inflate(R.layout.view_pager_layout, this);
        mLlDot = (LinearLayout) mView.findViewById(R.id.ll_main_dot);
        mViewPager = (ViewPager) mView.findViewById(R.id.vp_main);
        mIvList = new ArrayList<>();
        mIvDotList = new ArrayList<>();
    }

    //  根据图片URL创建对应的ImageView并添加到集合
    private void initImage() {
        ImageView imageView;
        if (mUrlList.size() > 1) {
            for (int i = 0; i < mUrlList.size() + 2; i++) {
                if (i == 0) {   //判断当i=0为该处的ImageView设置最后一张图片作为背景
                    imageView = new ImageView(getContext());
                    imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                    ImageLoaderUtil.loadImg(imageView, mUrlList.get(mUrlList.size() - 1));
                    mIvList.add(imageView);
                } else if (i == mUrlList.size() + 1) {   //判断当i=images.length+1时为该处的ImageView设置第一张图片作为背景
                    imageView = new ImageView(getContext());
                    imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                    ImageLoaderUtil.loadImg(imageView, mUrlList.get(0));
                    mIvList.add(imageView);
                } else {  //其他情况则为ImageView设置images[i-1]的图片作为背景
                    imageView = new ImageView(getContext());
                    imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                    ImageLoaderUtil.loadImg(imageView, mUrlList.get(i - 1));
                    mIvList.add(imageView);
                }
            }
        }else if(mUrlList.size()==1) {
            imageView = new ImageView(getContext());
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            ImageLoaderUtil.loadImg(imageView, mUrlList.get(0));
            mIvList.add(imageView);
        }
    }

    //  设置触摸事件，当滑动或者触摸时停止自动轮播
    private void setTouchListener() {
        mViewPager.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = event.getAction();
                switch (action) {
                    case MotionEvent.ACTION_DOWN:
                    case MotionEvent.ACTION_MOVE:
                        isLoop = true;
                        stopCircleViewPager();
                        break;
                    case MotionEvent.ACTION_UP:
                    case MotionEvent.ACTION_CANCEL:
                        isLoop = false;
                        startCircleViewPager();
                    default:
                        break;
                }
                return false;
            }
        });
    }

    private void startCircleViewPager() {
        if (!isLoop && mViewPager != null) {
            mHandler.postDelayed(mRunnable, interval);// 每interval秒执行一次runnable.
            isLoop = true;
        }
    }

    public void stopCircleViewPager() {
        if (isLoop && mViewPager != null) {
            mHandler.removeCallbacks(mRunnable);
            isLoop = false;
        }
    }

    //  设置轮播小圆点
    private void setDotImage() {
        //  设置LinearLayout的子控件的宽高，这里单位是像素。
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams((int) mDotWidth, (int) mDotWidth);
        params.rightMargin = (int) (mDotWidth / 1.5);
        if(mUrlList.size()>1){
            //  for循环创建mUrlList.size()个ImageView（小圆点）
            for (int i = 0; i < mUrlList.size(); i++) {
                ImageView imageViewDot = new ImageView(getContext());
                imageViewDot.setLayoutParams(params);
                //  设置小圆点的背景为暗红图片
                imageViewDot.setBackgroundResource(mDarkDotRes);
                mLlDot.addView(imageViewDot);
                mIvDotList.add(imageViewDot);
            }
        }

        //设置第一个小圆点图片背景为红色
        if (mUrlList.size() > 1) {
            mIvDotList.get(dotPosition).setBackgroundResource(mLightDotRes);
        }
    }


    private void setViewPager() {
        CirclePagerAdapter adapter = new CirclePagerAdapter(mIvList, this);
        mViewPager.setAdapter(adapter);
        mViewPager.setCurrentItem(currentPosition);

        setPageChangeListener();
        startCircleViewPager();
        setTouchListener();
    }

    //  ViewPager页面改变监听
    private void setPageChangeListener() {
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == 0) {    //判断当切换到第0个页面时把currentPosition设置为images.length,即倒数第二个位置，小圆点位置为length-1
                    currentPosition = mUrlList.size();
                    dotPosition = mUrlList.size() - 1;
                } else if (position == mUrlList.size() + 1) {    //当切换到最后一个页面时currentPosition设置为第一个位置，小圆点位置为0
                    currentPosition = 1;
                    dotPosition = 0;
                } else {
                    currentPosition = position;
                    dotPosition = position - 1;
                }
                //  把之前的小圆点设置背景为暗红，当前小圆点设置为红色
                mIvDotList.get(prePosition).setBackgroundResource(mDarkDotRes);
                mIvDotList.get(dotPosition).setBackgroundResource(mLightDotRes);
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

    public interface OnPageClickListener {
        void onPageClick(int position);
    }

    //  adapter中图片点击的回掉方法
    public void imageClick(int position) {
        if(mOnPageClickListener!=null)
        mOnPageClickListener.onPageClick(position);
    }


    public void setDotWidth(float dotWidth) {
        mDotWidth = ScreenUtils.dp2px(getContext(), dotWidth);
    }

    public void setLightDotRes(@DrawableRes int lightDotRes) {
        mLightDotRes = lightDotRes;
    }

    public void setDarkDotRes(@DrawableRes int darkDotRes) {
        mDarkDotRes = darkDotRes;
    }

    public void setOnPageClickListener(OnPageClickListener onPageClickListener) {
        this.mOnPageClickListener = onPageClickListener;
    }

    public void setInterval(int interval) {
        this.interval = interval;
    }

    public List<String> getUrlList() {
        return mUrlList;
    }

    public void setUrlList(List<String> urlList) {
        mUrlList = urlList;
    }
}
