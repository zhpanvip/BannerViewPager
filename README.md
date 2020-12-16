# BannerViewPager

[![License](https://img.shields.io/badge/License%20-Apache%202-337ab7.svg)](https://www.apache.org/licenses/LICENSE-2.0)
![MinSdk](https://img.shields.io/badge/API-16%2B-brightgreen.svg?style=flat)
![JCenter](https://api.bintray.com/packages/zhpanvip/CircleViewPager/bannerview/images/download.svg)
[![Android Arsenal](https://img.shields.io/badge/Android%20Arsenal-BannerViewPager-brightgreen.svg?style=flat)](https://android-arsenal.com/details/1/7961)

## English | [中文](https://github.com/zhpanvip/BannerViewPager/blob/v_2.x/README_CN.md)

> Tencent Video,QQ Music,KuGou,AliPay,Tmall,TaoBao,YouKu,Himalaya,NetEase Music,Bilibili ect. All of above App's Banner can be implements By BannerViewPager.

## Attention：v2.x is depreacted now,please migrate to v3.x.


## Usage

### 1.Gradle dependency
   
add the dependency in your app build.gradle

```
implementation 'com.zhpan.library:bannerview:latestVersion'
```
latestVersion: [ ![latestVersion](https://api.bintray.com/packages/zhpanvip/CircleViewPager/bannerview/images/download.svg) ](https://bintray.com/zhpanvip/CircleViewPager/bannerview/_latestVersion)

### 2.Add BannerViewPager in layout.xml

```
    <com.zhpan.bannerview.BannerViewPager
            android:id="@+id/banner_view"
            android:layout_width="match_parent"
            android:layout_margin="10dp"
            android:layout_height="160dp" />
```

### 3.The item layout of banner:

```
    <RelativeLayout xmlns:android="http://schemas.android.com/apk/res/android"
        android:layout_width="match_parent"
        android:layout_height="wrap_content">

        <ImageView
            android:id="@+id/banner_image"
            android:layout_width="match_parent"
            android:layout_height="match_parent"
            android:scaleType="centerCrop" />

        <LinearLayout
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:layout_alignParentBottom="true"
            android:background="#66000000"
            android:gravity="center_vertical">

            <TextView
                android:id="@+id/tv_describe"
                android:layout_width="wrap_content"
                android:layout_height="match_parent"
                android:layout_gravity="center_vertical"
                android:layout_marginStart="15dp"
                android:gravity="center_vertical"
                android:paddingTop="5dp"
                android:paddingBottom="5dp"
                android:textColor="#FFFFFF"
                android:textSize="16sp" />
        </LinearLayout>

    </RelativeLayout>
```

### 4.Set custom ViewHolder for BannerViewPager, the custom ViewHolder must implements com.zhpan.bannerview.holder.ViewHolder:

```
    public class NetViewHolder implements ViewHolder<CustomBean> {

        @Override
        public int getLayoutId() {
            return R.layout.item_net;
        }

        @Override
        public void onBind(View itemView, CustomBean data, int position, int size) {
            CornerImageView imageView = itemView.findViewById(R.id.banner_image);
            imageView.setRoundCorner(imageView.getContext().getResources().getDimensionPixelOffset(R.dimen.dp_5));
            Glide.with(imageView).load(data.getImagePath()).placeholder(R.drawable.placeholder).into(imageView);
        }
    }
```

### 5.Use BannerViewPager in Activity or Fragment:

Kotlin：

```
    private lateinit var mViewPager: BannerViewPager<CustomBean, NetViewHolder>
    
    private fun initViewPager() {
            mBannerViewPager = findViewById(R.id.banner_view)
            mBannerViewPager.setCanLoop(false)
                .setIndicatorSlideMode(IndicatorSlideMode.SMOOTH)
                .setIndicatorMargin(0, 0, 0, getResources().getDimensionPixelOffset(R.dimen.dp_40))
                .setIndicatorGravity(IndicatorGravity.CENTER)
                .setHolderCreator { NetViewHolder() }
                .setOnPageChangeListener(
                    object : OnPageChangeListenerAdapter() {
                        override fun onPageSelected(position: Int) {
                            pageSelect(position)
                        }
                    }
                ).setOnPageClickListener {
                    onPageClick()
                }
                .create(res.toList())
        }
```    

Java：

```
    private BannerViewPager<CustomBean, NetViewHolder> mBannerViewPager;
    ...
	private void initViewPager() {
             mBannerViewPager = findViewById(R.id.banner_view);
             mBannerViewPager.showIndicator(true)
                .setInterval(3000)
                .setCanLoop(false)
                .setAutoPlay(true)
                .setRoundCorner(getResources().getDimensionPixelOffset(R.dimen.dp_7))
                .setIndicatorSliderColor(Color.parseColor("#935656"), Color.parseColor("#FF4C39"))
                .setIndicatorGravity(IndicatorGravity.END)
                .setScrollDuration(1000).setHolderCreator(NetViewHolder::new)
                .setOnPageClickListener(position -> {
                    CustomBean bannerData = mBannerViewPager.getList().get(position);
                    Toast.makeText(NetworkBannerActivity.this,
                            "点击了图片" + position + " " + bannerData.getImageDescription(), Toast.LENGTH_SHORT).show();

                }).create(mList);
        }
```
### 6.startLoop and stopLoop

***If the version you used is later than 2.5.0,you don't need care of startLoop and stopLoop in Activity or Fragment. But the two methods is still public.***

Recommend call stopLoop in onPause() and startLoop in onResume() to improve performance：

```
    @Override
    protected void onPause() {
        if (mBannerViewPager != null)
                mBannerViewPager.stopLoop();
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mBannerViewPager != null)
            mBannerViewPager.startLoop();
    }
```

### 7.Custom IndicatorView

The example will implement an custom IndicatorView as the follow gif.

| Custom IndicatorView Style|
|--|
| ![NORMAL](https://github.com/zhpanvip/BannerViewPager/blob/master/image/style_custum.gif) |

**(1)Custom View and extends BaseIndicatorView**

```
public class FigureIndicatorView extends BaseIndicatorView {

    private int radius = DpUtils.dp2px(20);

    private int backgroundColor = Color.parseColor("#88FF5252");

    private int textColor = Color.WHITE;

    private int textSize=DpUtils.dp2px(13);

    public FigureIndicatorView(Context context) {
        this(context, null);
    }

    public FigureIndicatorView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FigureIndicatorView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mPaint = new Paint();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(2 * radius, 2 * radius);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPaint.setColor(backgroundColor);
        canvas.drawCircle(getWidth() / 2, getHeight() / 2, radius, mPaint);
        mPaint.setColor(textColor);
        mPaint.setTextSize(textSize);
        String text = currentPosition + 1 + "/" + pageSize;
        int textWidth = (int) mPaint.measureText(text);
        Paint.FontMetricsInt fontMetricsInt = mPaint.getFontMetricsInt();
        int baseline = (getMeasuredHeight() - fontMetricsInt.bottom + fontMetricsInt.top) / 2 - fontMetricsInt.top;
        canvas.drawText(text, (getWidth() - textWidth) / 2, baseline, mPaint);
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }

    @Override
    public void setBackgroundColor(@ColorInt int backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public void setTextSize(int textSize) {
        this.textSize = textSize;
    }

    public void setTextColor(int textColor) {
        this.textColor = textColor;
    }
}
```
**(2)Set custom indicator for BannerViewPager**

```
    FigureIndicatorView indicatorView = new FigureIndicatorView(mContext);
    indicatorView.setRadius(getResources().getDimensionPixelOffset(R.dimen.dp_18));
    indicatorView.setTextSize(getResources().getDimensionPixelOffset(R.dimen.dp_13));
    indicatorView.setBackgroundColor(Color.parseColor("#aa118EEA"));

    mViewPager.setIndicatorGravity(IndicatorGravity.END)
              .setIndicatorView(indicatorView)
              .setHolderCreator(() -> new ImageResourceViewHolder(0))
              .create(mDrawableList);
```



## API

| Method | Description | Default |
|--|--|--|
| BannerViewPager<T, VH> setCanLoop(boolean canLoop) | set is can loop | default value is true|
| BannerViewPager<T, VH> setAutoPlay(boolean autoPlay) | set is atuo play | default value true|
| BannerViewPager<T, VH> setInterval(int interval) | set the interval of item switch interval |The unit is millisecond，default value 3000ms  |
| BannerViewPager<T, VH> setScrollDuration(int scrollDuration) | set page scroll duration | set page scroll duration |unit is millisecond，default is 500ms |
| BannerViewPager<T, VH> setRoundCorner(int radius) | set Round Rectangle style for Banner | required SDK_INT>=LOLLIPOP(API 21)  |
| BannerViewPager<T, VH> setOnPageClickListener(OnPageClickListener onPageClickListener) | set item click listener |  |
| BannerViewPager<T, VH> setHolderCreator(HolderCreator\<VH> holderCreator) |set Holder Creator  |You must set HolderCreator for BannerViewPager，or will throw NullPointerException|
| BannerViewPager<T, VH> setIndicatorVisibility(@Visibility int visibility) | indicator visibility |default value is VISIBLE，added in version 2.4.2|
| BannerViewPager<T, VH> setIndicatorStyle(int indicatorStyle) | set indicator style | enum(CIRCLE, DASH、ROUND_RECT) default CIRCLE  |
| BannerViewPager<T, VH> setIndicatorGravity(int gravity) | set indicator gravity |enum(CENTER、START、END) default value CENTER |
| BannerViewPager<T, VH> setIndicatorColor(int normalColor,int checkedColor) | set indicator color |normalColor：color of indicator dot not selected, default value  "#8C6C6D72"， checkedColor：color of indicator selected default value is "#8C18171C" |
| BannerViewPager<T, VH> setIndicatorSlideMode(int slideMode)  | set indicator slide mode | enum（NORMAL;SMOOTH;WORM），default value NORMAL  |
| BannerViewPager<T, VH> setIndicatorSliderRadius(int radius) | set indicator dot radius | default value is 4dp|
| BannerViewPager<T, VH> setIndicatorSliderRadius(int normalRadius,int checkRadius)  |set indicator dot radius  |  normalRadius:normal radius of indicator dot,  checkedRadius:checked radius of indicator dot,default value is 4dp |
| BannerViewPager<T, VH> setIndicatorSliderWidth(int indicatorWidth) | set indicator dot width，if it's Circle indicator the parameter is diameter of circle | default value is 8dp|
| BannerViewPager<T, VH> setIndicatorSliderWidth(int normalWidth, int checkWidth) | set indicator dot width，if is circle style，the width is diameter of circle | default is 8dp |
| BannerViewPager<T, VH> setIndicatorHeight(int indicatorHeight) | set indicator hight，it's only used when the indicator style is dash | default value is normalIndicatorWidth/2 |
| BannerViewPager<T, VH> setIndicatorSliderGap(int indicatorMargin) | set the gap of indicator dot| default value is indicator dot width（or the diameter of circle）|
| BannerViewPager<T, VH> setIndicatorView(IIndicator indicatorView) | set custom indicator|The custom indicator must extends BaseIndicatorView or implements IIndicator |
| BannerViewPager<T, VH> setPageTransformerStyle(int style) | set transform style |  |
| BannerViewPager<T, VH> setCurrentItem(int item) | Set the currently selected page. |  add in v2.3.5 |
| int getCurrentItem() | get the current page position | added in v2.3.5 |
| BannerViewPager<T, VH> setPageStyle(PageStyle pageStyle) | setPageStyle | support in v2.4.0. enum（MULTI_PAGE、MULTI_PAGE_SCALE、MULTI_PAGE_OVERLAP）|
| BannerViewPager<T, VH> setPageMargin(int pageMargin) | set item margin | added in v2.4.0 |
| BannerViewPager<T, VH> setIndicatorMargin(int left, int top, int right, int bottom) | set margin for indicator | added in v2.4.1 |
| BannerViewPager<T, VH> setOnPageChangeListener(OnPageChangeListener l) | set page change listener for BannerViewPager | added in v2.4.3 |
| void startLoop() |start loop | the method will be called when BannerViewPager was initialized |
| void stopLoop() | stop loop | |
| List\<T> getList() | get data in BannerViewPager |  |
| void create(List<T> list) |initialize BannerViewPager  |You must call this method when data is set |

### Attributes in xml
| Attributes | format | description |
|--|--|--|
| bvp_interval | integer | set page switch interval |
| bvp_scroll_duration | integer | set page scroll duration|
| bvp_can_loop | boolean|set is can loop |
| bvp_auto_play | boolean | set is can auto play  |
| bvp_indicator_checked_color | color | set checked color for indicator |
| bvp_indicator_normal_color | color | set normal color for indicator |
| bvp_indicator_radius | dimension | if it's circle style the value is radius of circle,if the indicator style is DASH or ROUND_RECT the value is width/2  |
| bvp_round_corner| dimension  | set round corner for banner |
| bvp_page_margin | dimension | set item margin |
| bvp_reveal_width | dimension | it's only used when the page style is MULTI_PAGE/MULTI_PAGE_SCALE/MULTI_PAGE_OVERLAP,the value is two side item reveal width |
| bvp_indicator_style | enum | indicator style. enum(circle/dash)  |
| bvp_indicator_slide_mode | enum | indicator slide mode.enum(normal;smooth;worm) |
| bvp_indicator_gravity | enum | indicator gravity. enum(center/start/end) |
| bvp_page_style | enum | page style. enum(normal/multi_page/multi_page_overlap/multi_page_scale) |
| bvp_transformer_style | enum | transform style. enum(normal/depth/stack/accordion) |
| bvp_indicator_visibility| enum | indicator visibility(visible/gone/invisible) |

## TODO 

 - [x] Optimization and Refactoring IndicatorView（2.0.1）

 - [x] Fix a bug which page frozen sometimes when sliding in version 2.1.0  （2.1.0.1）

 - [x] Set Transform Style Supported（2.1.2）

 - [x] Migrate to Androidx（2.2.0）

 - [x] indicator smooth slide Supported（2.2.2）
 
 - [x] Dash IndicatorView Supported（2.3.+）
 
 - [x] MULTI_PAGE Style Supported（2.4.0）
 
 - [x] Optimize code and improve performance in version 2.4.3
 
 - [x] Refactor Indicator again (2.5.0)
 - [x] Fix issue #34 which Indicator smooth slide problem(2.6.1).
 - [ ] Migrate to ViewPager2 （3.0.0）

## FAQ

 **If you have any question regard to BannerViewPager, please scan the QR code and join the QQ group to communicate.**

 ![QQ交流群60902509](https://github.com/zhpanvip/BannerViewPager/blob/master/image/qq_group.png)


## <span id="Sponsor"> Sponsor </span>

**开源不易 随心赞赏**

| Alipay | WeChat |
|--|--|
| ![Alipay](https://github.com/zhpanvip/viewpagerindicator/blob/master/image/pay_alipay.jpg) |  ![WeChat](https://github.com/zhpanvip/viewpagerindicator/blob/master/image/pay_wechat.png) |

##  More details

[《打造一个丝滑般自动轮播无限循环Android库》](https://juejin.im/post/5d6bce24f265da03db0790d1)

[《BannerViewPager源码解析》](https://juejin.im/post/5d74d3faf265da03b5747015)

[《剖析BannerViewPager中Indicator的设计思想》](https://juejin.im/post/5dda0b6d518825731f569a8c)

## Thanks

[banner](https://github.com/youth5201314/banner)

[Android-ConvenientBanner](https://github.com/saiwu-bigkoo/Android-ConvenientBanner)

[ViewPagerTransforms](https://github.com/ToxicBakery/ViewPagerTransforms)

[玩Android](https://wanandroid.com/)


License
-------

    Copyright 2019 zhpanvip

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.






