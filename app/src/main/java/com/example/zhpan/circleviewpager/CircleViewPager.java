package com.example.zhpan.circleviewpager;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.zhpan.circleviewpager.utils.ImageLoaderUtil;
import com.example.zhpan.circleviewpager.utils.ScreenUtils;

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
    //    图片连接集合
    private List<String> mUrlList;
    private int[] images;
    private LinearLayout mLinearLayoutDot;
    private int dotPosition = 0;
    private int prePosition = 0;
    private int currentPosition = 1;
    private CirclePagerAdapter adapter;

    //  选中时轮播圆点资源id
    private int mLightDotRes;
    //  未选中时轮播圆点资源id
    private int mDarkDotRes;
    //   轮播原点宽度
    private float mDotWidth;
    //  图片切换时间间隔
    private int interval;
    //图片的数量，item的数量
    private int imgsize = 0;

    private OnPageClickListener mOnPageClickListener;

    private Context mContext;

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

    private boolean isLoop;

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
        this.mContext=context;
        initView(attrs);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if (changed) {
            initData();
            setDot();
            setViewPager();
            startLoopViewPager();
        }
    }

    private void initView(AttributeSet attrs) {
        if (attrs != null) {
            TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.MyViewPager);
            mLightDotRes = typedArray.getResourceId(R.styleable.MyViewPager_lightDotRes, R.drawable.red_dot);
            mDarkDotRes = typedArray.getResourceId(R.styleable.MyViewPager_darkDotRes, R.drawable.red_dot_night);
            mDotWidth = typedArray.getDimension(R.styleable.MyViewPager_dotWidth, 20);
            interval = typedArray.getInteger(R.styleable.MyViewPager_interval, 3000);
        }
        mView = LayoutInflater.from(getContext()).inflate(R.layout.view_pager_layout, this);
        mLinearLayoutDot = (LinearLayout) mView.findViewById(R.id.ll_main_dot);
        mViewPager = (ViewPager) mView.findViewById(R.id.vp_main);
    }

    private void initAdimgs(List<String> urls) {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);

        int length=urls.size();
        if(urls.size()==1){
            length=1;
        }else if (urls.size()>1) {
            length = urls.size() + 2;
        }
        mImageViewList = new ArrayList<>(length);
        for (int i = 0; i < length; i++) {
            ImageView imageView = new ImageView(mContext);
            imageView.setLayoutParams(params);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            mImageViewList.add(i,imageView);
            //mImageViewList[i] = imageView;
        }
        setImg(length, urls);
    }

    private void setImg(int length, List<String> urls) {
        if(urls.size()==1){
            Glide.with(mContext).load(urls.get(0)).placeholder(R.mipmap.ic_launcher)
                    .into(mImageViewList.get(0));
            mImageViewList.get(0).setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    /*if (onAdItemClickListener != null) {
                        onAdItemClickListener.onItemClick(mImageViews[0], (forwardUrl!=null &&forwardUrl.size()>0)?forwardUrl.get(0):"");
                    }*/
                }
            });
        }else if (urls.size() > 1) {
            imgsize = length;
            for (int i = 0; i < length; i++) {
                if (i < length - 2) {
                    final int index = i;
                    Glide.with(mContext).load(urls.get(i)).placeholder(R.mipmap.ic_launcher)
                            .into(mImageViewList.get(i+1));
                    mImageViewList.get(i+1).setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View v) {
                           /* if (onAdItemClickListener != null) {
                                onAdItemClickListener.onItemClick(mImageViews[index + 1], (forwardUrl!=null &&forwardUrl.size()>index)?forwardUrl.get(index):"");
                            }*/
                        }
                    });
                }
            }

            Glide.with(mContext).load(urls.get(urls.size() - 1)).placeholder(R.mipmap.ic_launcher)
                    .into(mImageViewList.get(0));
            Glide.with(mContext).load(urls.get(0)).placeholder(R.mipmap.ic_launcher)
                    .into(mImageViewList.get(length-1));
        }
    }


    private void initData() {
        mImageViewList = new ArrayList<>();
        mImageViewDotList = new ArrayList<>();
        ImageView imageView;
        if (mUrlList.size() > 0) {
            for (int i = 0; i < mUrlList.size() + 2; i++) {
                if (i == 0) {   //判断当i=0为该处的ImageView设置最后一张图片作为背景
                    imageView = new ImageView(getContext());
                    //imageView.setBackgroundResource(images[images.length - 1]);
                    ImageLoaderUtil.loadImg(imageView,mUrlList.get(mUrlList.size()-1));
                    mImageViewList.add(imageView);

                } else if (i == mUrlList.size() + 1) {   //判断当i=images.length+1时为该处的ImageView设置第一张图片作为背景
                    imageView = new ImageView(getContext());
                   // imageView.setBackgroundResource(images[0]);
                     ImageLoaderUtil.loadImg(imageView,mUrlList.get(0));
                    mImageViewList.add(imageView);
                } else {  //其他情况则为ImageView设置images[i-1]的图片作为背景
                    imageView = new ImageView(getContext());
                   // imageView.setBackgroundResource(images[i - 1]);
                     ImageLoaderUtil.loadImg(imageView,mUrlList.get(i-1));
                    mImageViewList.add(imageView);
                }
            }



            mViewPager.setOnTouchListener(new OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    int action = event.getAction();
                    switch (action) {
                        case MotionEvent.ACTION_DOWN:
                        case MotionEvent.ACTION_MOVE:
                            isLoop = true;
                            stopLoopViewPager();
                            break;
                        case MotionEvent.ACTION_UP:
                        case MotionEvent.ACTION_CANCEL:
                            isLoop = false;
                            startLoopViewPager();
                        default:
                            break;
                    }
                    return false;
                }
            });
        }
    }

    private void startLoopViewPager() {
        if (!isLoop && mViewPager != null) {
            mHandler.postDelayed(mRunnable, interval);// 每两秒执行一次runnable.
            isLoop = true;
        }
    }

    public void stopLoopViewPager() {
        if (isLoop && mViewPager != null) {
            mHandler.removeCallbacks(mRunnable);
            isLoop = false;
        }
    }

    //  设置轮播小圆点
    private void setDot() {
        //  设置LinearLayout的子控件的宽高，这里单位是像素。
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams((int) mDotWidth, (int) mDotWidth);
        params.rightMargin = (int) (mDotWidth / 1.5);
        //  for循环创建images.length个ImageView（小圆点）
        for (int i = 0; i < mUrlList.size(); i++) {
            ImageView imageViewDot = new ImageView(getContext());
            imageViewDot.setLayoutParams(params);
            //  设置小圆点的背景为暗红图片
            imageViewDot.setBackgroundResource(mDarkDotRes);
            mLinearLayoutDot.addView(imageViewDot);
            mImageViewDotList.add(imageViewDot);
        }
        //设置第一个小圆点图片背景为红色
        if (mUrlList.size() > 0) {
            mImageViewDotList.get(dotPosition).setBackgroundResource(mLightDotRes);
        }
    }


    private void setViewPager() {
        adapter = new CirclePagerAdapter(mImageViewList,this);

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

    public interface OnPageClickListener{
        void pageClickListener(int position);
    }

    public void imageClick(int position){
        mOnPageClickListener.pageClickListener(position);
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

    public void setOnPageClickListener(OnPageClickListener onPageClickListener){
        this.mOnPageClickListener=onPageClickListener;
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
