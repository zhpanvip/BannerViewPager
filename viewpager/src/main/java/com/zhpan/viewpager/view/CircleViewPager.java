package com.zhpan.viewpager.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Handler;
import android.support.annotation.ColorInt;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.example.viewpager.R;
import com.zhpan.viewpager.adapter.CirclePagerAdapter;
import com.zhpan.viewpager.holder.HolderCreator;
import com.zhpan.viewpager.holder.ViewHolder;
import com.zhpan.viewpager.utils.DensityUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhpan on 2017/3/28.
 */
public class CircleViewPager<T> extends FrameLayout {
    private ViewPager mViewPager;
    //  轮播数据集合
    private List<T> mList;
    //  重新构造后的轮播数据集合
    private List<T> mListAdd;
    //  指示器图片集合
    private List<DotView> mDotList;
    //  图片切换时间间隔
    private int interval;
    //  圆点位置
    private int dotPosition = 0;
    //  图片上一个位置
    private int prePosition = 0;
    //  图片当前位置
    private int currentPosition;
    //  是否正在循环
    private boolean isLooping;
    //  是否开启循环
    private boolean isCanLoop;
    //  是否开启自动播放
    private boolean isAutoPlay = false;
    //  是否显示指示器圆点
    private boolean showIndicator = true;
    // 圆点指示器显示位置
    public static final int START = 1;
    public static final int END = 2;
    public static final int CENTER = 0;
    private int gravity;
    //  未选中时圆点颜色
    private int indicatorNormalColor;
    //  选中时选点颜色
    private int indicatorCheckedColor;
    //  指示器圆点半径
    private float indicatorRadius;
    //  页面点击事件监听
    private OnPageClickListener mOnPageClickListener;
    //  圆点指示器的Layout
    private LinearLayout mLlIndicator;
    private HolderCreator holderCreator;

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
        init(null, context);
    }

    public CircleViewPager(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
        init(attrs, context);
    }

    public CircleViewPager(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs, context);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if (changed) {
            initData();
            setIndicator();
            setViewPager();
        }
    }

    private void init(AttributeSet attrs, Context context) {
        if (attrs != null) {
            TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.CircleViewPager);
            interval = typedArray.getInteger(R.styleable.CircleViewPager_interval, 3000);
            indicatorCheckedColor = typedArray.getColor(R.styleable.CircleViewPager_indicator_checked_color, Color.parseColor("#FF4C39"));
            indicatorNormalColor = typedArray.getColor(R.styleable.CircleViewPager_indicator_normal_color, Color.parseColor("#935656"));
            indicatorRadius = typedArray.getDimension(R.styleable.CircleViewPager_indicator_radius, DensityUtils.dp2px(context,4));
            isAutoPlay = typedArray.getBoolean(R.styleable.CircleViewPager_isAutoPlay, true);
            isCanLoop = typedArray.getBoolean(R.styleable.CircleViewPager_isCanLoop, true);
            gravity = typedArray.getInt(R.styleable.CircleViewPager_indicator_gravity, 0);
            typedArray.recycle();
        }
        View view = LayoutInflater.from(getContext()).inflate(R.layout.view_pager_layout, this);
        mLlIndicator = view.findViewById(R.id.ll_main_dot);
        mViewPager = view.findViewById(R.id.vp_main);
        mList = new ArrayList<>();
        mListAdd = new ArrayList<>();
        mDotList = new ArrayList<>();
    }

    private void setIndicatorGravity() {
        switch (gravity) {
            case CENTER:
                mLlIndicator.setGravity(Gravity.CENTER);
                break;
            case START:
                mLlIndicator.setGravity(Gravity.START);
                break;
            case END:
                mLlIndicator.setGravity(Gravity.END);
                break;
        }
    }

    //  根据mList数据集构造mListAdd
    private void initData() {
        if (mList.size() == 0) {
            setVisibility(GONE);
        } else if (mList.size() == 1) {
            mListAdd.add(mList.get(0));
            setVisibility(VISIBLE);
        } else if (mList.size() > 1) {
            createData();
            setVisibility(VISIBLE);
        }
    }

    private void createData() {
        mListAdd.clear();
        if (isCanLoop) {
            currentPosition = 1;
            for (int i = 0; i < mList.size() + 2; i++) {
                if (i == 0) {   //  判断当i=0为该处的mList的最后一个数据作为mListAdd的第一个数据
                    mListAdd.add(mList.get(mList.size() - 1));
                } else if (i == mList.size() + 1) {   //  判断当i=mList.size()+1时将mList的第一个数据作为mListAdd的最后一个数据
                    mListAdd.add(mList.get(0));
                } else {  //  其他情况
                    mListAdd.add(mList.get(i - 1));
                }
            }
        } else {
            mListAdd.addAll(mList);
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
                        isLooping = true;
                        stopLoop();
                        break;
                    case MotionEvent.ACTION_UP:
                    case MotionEvent.ACTION_CANCEL:
                        isLooping = false;
                        startLoop();
                    default:
                        break;
                }
                return false;
            }
        });
    }


    private void startLoop() {
        if (!isLooping && isAutoPlay && mViewPager != null) {
            mHandler.postDelayed(mRunnable, interval);// 每interval秒执行一次runnable.
            isLooping = true;
        }
    }

    public void stopLoop() {
        if (isLooping && mViewPager != null) {
            mHandler.removeCallbacks(mRunnable);
            isLooping = false;
        }
    }

    //  设置轮播小圆点
    private void setIndicator() {
        // mDotList.clear();
        mLlIndicator.removeAllViews();
        //  设置LinearLayout的子控件的宽高，这里单位是像素。
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams((int) indicatorRadius * 2, (int) indicatorRadius * 2);
        params.rightMargin = (int) (indicatorRadius * 2 / 1.5);
        if (mList.size() > 1) {
            //  for循环创建mUrlList.size()个ImageView（小圆点）
            for (int i = 0; i < mList.size(); i++) {
                DotView dotView = new DotView(getContext());
                dotView.setLayoutParams(params);
                dotView.setNormalColor(indicatorNormalColor);
                dotView.setCheckedColor(indicatorCheckedColor);
                dotView.setChecked(false);
                mLlIndicator.addView(dotView);
                mDotList.add(dotView);
            }
        }
        //设置第一个小圆点图片背景为红色
        if (mList.size() > 1) {
            mDotList.get(dotPosition).setChecked(true);
        }
        setIndicatorGravity();
    }

    private void setViewPager() {
        CirclePagerAdapter<T> adapter = new CirclePagerAdapter<>(mListAdd, this, holderCreator);
        adapter.setCanLoop(isCanLoop);
        mViewPager.setAdapter(adapter);
        mViewPager.setCurrentItem(currentPosition);

        setPageChangeListener();
        startLoop();
        setTouchListener();
        if (showIndicator) {
            mLlIndicator.setVisibility(VISIBLE);
        } else {
            mLlIndicator.setVisibility(GONE);
        }
    }

    public ViewPager getViewPager() {
        return mViewPager;
    }

    //  是否显示轮播指示器
    public void isShowIndicator(boolean showIndicator) {
        this.showIndicator = showIndicator;
    }

    public void setIndicatorGravity(int gravity) {
        this.gravity = gravity;
    }

    public void setPages(List<T> list, HolderCreator<ViewHolder> holderCreator) {
        if (list == null || holderCreator == null) {
            return;
        }
        mList .addAll(list);
        this.holderCreator = holderCreator;
    }

    //  ViewPager页面改变监听
    private void setPageChangeListener() {
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                pageSelected(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                //  当state为SCROLL_STATE_IDLE即没有滑动的状态时切换页面
                if (state == ViewPager.SCROLL_STATE_IDLE) {
                    mViewPager.setCurrentItem(currentPosition, false);
                }
            }
        });
    }

    private void pageSelected(int position) {
        if (isCanLoop) {
            if (position == 0) {    //判断当切换到第0个页面时把currentPosition设置为list.size(),即倒数第二个位置，小圆点位置为length-1
                currentPosition = mList.size();
                dotPosition = mList.size() - 1;
            } else if (position == mList.size() + 1) {    //当切换到最后一个页面时currentPosition设置为第一个位置，小圆点位置为0
                currentPosition = 1;
                dotPosition = 0;
            } else {
                currentPosition = position;
                dotPosition = position - 1;
            }
            //  把之前的小圆点设置背景为暗红，当前小圆点设置为红色
            mDotList.get(prePosition).setChecked(false);
            mDotList.get(dotPosition).setChecked(true);
            prePosition = dotPosition;
        } else {
            currentPosition = position;
            //  把之前的小圆点设置背景为暗红，当前小圆点设置为红色
            mDotList.get(prePosition).setChecked(false);
            mDotList.get(currentPosition).setChecked(true);
            prePosition = currentPosition;
        }

    }

    public interface OnPageClickListener {
        void onPageClick(int position);
    }

    //  adapter中图片点击的回掉方法
    public void imageClick(int position) {
        if (isCanLoop) {
            if (mOnPageClickListener != null)
                mOnPageClickListener.onPageClick(position - 1);
        } else {
            if (mOnPageClickListener != null)
                mOnPageClickListener.onPageClick(position);
        }
    }

    /**
     * @param checkedColor 选中时指示器颜色
     * @param normalColor  未选中时指示器颜色
     */
    public void setIndicatorColor(@ColorInt int normalColor, @ColorInt int checkedColor) {
        indicatorCheckedColor = checkedColor;
        indicatorNormalColor = normalColor;
    }

    public void setOnPageClickListener(OnPageClickListener onPageClickListener) {
        this.mOnPageClickListener = onPageClickListener;
    }

    public boolean isAutoPlay() {
        return isAutoPlay;
    }

    public void setAutoPlay(boolean autoPlay) {
        isAutoPlay = autoPlay;
    }

    public boolean isCanLoop() {
        return isCanLoop;
    }

    public void setCanLoop(boolean canLoop) {
        isCanLoop = canLoop;
    }

    public void setInterval(int interval) {
        this.interval = interval;
    }

    public List<T> getList() {
        return mList;
    }

    public void setList(List<T> list) {
        mList = list;
        mListAdd = new ArrayList<>();
    }

    public float getIndicatorRadius() {
        return indicatorRadius;
    }

    public void setIndicatorRadius(float indicatorRadius) {
        this.indicatorRadius = DensityUtils.dp2px(getContext(), indicatorRadius);
    }
}
