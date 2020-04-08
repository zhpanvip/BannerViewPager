# BannerViewPager

[![License](https://img.shields.io/badge/License%20-Apache%202-337ab7.svg)](https://www.apache.org/licenses/LICENSE-2.0)
![MinSdk](https://img.shields.io/badge/API-16%2B-brightgreen.svg?style=flat)
[![JitPack](https://jitpack.io/v/zhpanvip/BannerViewPager.svg)](https://jitpack.io/#zhpanvip/BannerViewPager)
[ ![JCenter](https://api.bintray.com/packages/zhpanvip/CircleViewPager/bannerview/images/download.svg) ](https://bintray.com/zhpanvip/CircleViewPager/bannerview/_latestVersion)
[![Android Arsenal](https://img.shields.io/badge/Android%20Arsenal-BannerViewPager-brightgreen.svg?style=flat)](https://android-arsenal.com/details/1/7961)

## English | [中文](https://github.com/zhpanvip/BannerViewPager/blob/master/README_CN.md)

> Tencent Video,QQ Music,KuGou,AliPay,Tmall,TaoBao,YouKu,Himalaya,NetEase Music,Bilibili ect. All of above App's Banner can be implements By BannerViewPager.


## What's new in version 3.0

- Migrate to ViewPager2
- Mutiple item type supported
- Optimize memory，improve perfermance.
- add setOrientation method，support vertical orientation
- add addPageTransformer method and removeTransformer method
- setAdapter replaced setHolderCreator
- getData replaced getList
- registerOnPageChangeCallback replaced setOnPageChangeListener
- setUserInputEnabled replaced disableTouchScroll
- remove setPageTransformerStyle
- remvoe some deprecate methods in 2.x

## Preview

 ### [Click here or scan the QR code to download demo apk](https://github.com/zhpanvip/BannerViewPager/raw/master/app/release/app-release.apk)

![QRCode](https://github.com/zhpanvip/BannerViewPager/blob/master/image/qrcode.png)


### 1.PageStyle

[Sample Click Here](https://github.com/zhpanvip/BannerViewPager/blob/master/app/src/main/java/com/example/zhpan/circleviewpager/fragment/PageFragment.java)

| MULTI_PAGE |MULTI_PAGE_SCALE | MULTI_PAGE_OVERLAP |
|--|--|--|
| ![MULTI_PAGE](https://github.com/zhpanvip/BannerViewPager/blob/master/image/page_style_multi.gif) |![MULTI_PAGE](https://github.com/zhpanvip/BannerViewPager/blob/master/image/page_style_multi_scale.gif) |![MULTI_PAGE](https://github.com/zhpanvip/BannerViewPager/blob/master/image/page_style_multi_overlay.gif) |

### 2.Indicator

The IndicatorView was split from BannerViewPager,the new repo is [ViewPagerIndicator](https://github.com/zhpanvip/viewpagerindicator)，Click the link to see more information about [ViewPagerIndicator](https://github.com/zhpanvip/viewpagerindicator)

#### (1)setIndicatorStyle and setIndicatorSlideMode

BannerViewPager supports three Indicator Styles and three Indicator Slide mode now. 

[Sample Click Here](https://github.com/zhpanvip/BannerViewPager/blob/master/app/src/main/java/com/example/zhpan/circleviewpager/fragment/IndicatorFragment.java)

| Attrs | CIRCLE | DASH | ROUND_RECT |
|--|--|--|--|
| NORMAL| ![CIRCLE_NORMAL](https://github.com/zhpanvip/viewpagerindicator/blob/master/image/slide_circle_normal.gif) | ![DASH_NORMAL](https://github.com/zhpanvip/viewpagerindicator/blob/master/image/style_dash_normal.gif) | ![ROUND_RECT_NORMAL](https://github.com/zhpanvip/viewpagerindicator/blob/master/image/style_round_rect_normal.gif) |
| SMOOTH| ![CIRCLE_SMOOTH](https://github.com/zhpanvip/viewpagerindicator/blob/master/image/slide_circle_smooth.gif) | ![DASH_SMOOTH](https://github.com/zhpanvip/viewpagerindicator/blob/master/image/style_dash_smooth.gif) | ![ROUND_RECT_SMOOTH](https://github.com/zhpanvip/viewpagerindicator/blob/master/image/style_round_rect_smooth.gif) |
| WORM| ![CIRCLE_WORM](https://github.com/zhpanvip/viewpagerindicator/blob/master/image/slide_circle_worm.gif) | ![DASH_WORM](https://github.com/zhpanvip/viewpagerindicator/blob/master/image/style_dash_worm.gif) | ![ROUND_WORM](https://github.com/zhpanvip/viewpagerindicator/blob/master/image/style_round_rect_worm.gif) |

#### (2)Custom Indicator

It's also support to custom indicator style,just need extends BaseIndicatorView or implement the IIndicator and override methods, then you can draw Indicators for whatever you want.

[Sample Click Here](https://github.com/zhpanvip/BannerViewPager/blob/master/app/src/main/java/com/example/zhpan/circleviewpager/fragment/OthersFragment.java)

| Figure Indicator | Drawable Indicator | Indicator below of Banner |
|--|--|--|
| ![CIRCLE](https://github.com/zhpanvip/BannerViewPager/blob/master/image/style_custum.gif) | ![DASH](https://github.com/zhpanvip/BannerViewPager/blob/master/image/style_custom1.gif) | ![NORMAL](https://github.com/zhpanvip/BannerViewPager/blob/master/image/style_custom2.gif) |


## API

| Methods | Description | Default |
|--|--|--|
| BannerViewPager<T, VH> setCanLoop(boolean) | 是否开启循环 | 默认值true|
| BannerViewPager<T, VH> setAutoPlay(boolean) | 是否开启自动轮播 | 默认值true|
| BannerViewPager<T, VH> setInterval(int) | 自动轮播时间间隔 |单位毫秒，默认值3000  |
| BannerViewPager<T, VH> setScrollDuration(int) | 设置页面滚动时间 | 设置页面滚动时间 |单位毫秒，默认值500  |
| BannerViewPager<T, VH> setRoundCorner(int) | 设置圆角 |默认无圆角 需要SDK_INT>=LOLLIPOP(API 21)  |
| BannerViewPager<T, VH> setOnPageClickListener(OnPageClickListener) | 设置页面点击事件 |  |
| BannerViewPager<T, VH> setAdapter(BaseBannerAdapter\<T, VH>) |设置Adapter  |必须设置Adapter，否则会抛出NullPointerException|
| BannerViewPager<T, VH> setIndicatorVisibility(int) | indicator visibility |默认值VISIBLE 2.4.2 新增|
| BannerViewPager<T, VH> setIndicatorStyle(int) | 设置指示器样式 | 可选枚举(CIRCLE, DASH、ROUND_RECT) 默认CIRCLE  |
| BannerViewPager<T, VH> setIndicatorGravity(int) | 指示器位置 |可选值(CENTER、START、END)默认值CENTER |
| BannerViewPager<T, VH> setIndicatorColor(int,int) | 指示器圆点颜色 |normalColor：未选中时颜色默认"#8C6C6D72"， checkedColor：选中时颜色 默认"#8C18171C" |
| BannerViewPager<T, VH> setIndicatorSlideMode(int slideMode)  | 设置Indicator滑动模式 | 可选（NORMAL、SMOOTH、WORM），默认值NORMAL  |
| BannerViewPager<T, VH> setIndicatorSliderRadius(int radius) | 设置指示器圆点半径 | 默认值4dp|
| BannerViewPager<T, VH> setIndicatorSliderRadius(int normalRadius,int checkRadius)  |设置指示器圆点半径  |  normalRadius:未选中时半径  checkedRadius:选中时的半径,默认值4dp |
| BannerViewPager<T, VH> setIndicatorSliderWidth(int) | 设置指示器宽度，如果是圆形指示器，则为直径 |  默认值8dp|
| BannerViewPager<T, VH> setIndicatorSliderWidth(int normalWidth, int checkWidth) | 设置指示器宽度，如果是圆形指示器，则为直径 | 默认值8dp |
| BannerViewPager<T, VH> setIndicatorHeight(int) | 设置指示器高度，仅在Indicator样式为DASH时有效 | 默认值normalIndicatorWidth/2 |
| BannerViewPager<T, VH> setIndicatorSliderGap(int) | 指示器圆点间距| 默认值为指示器宽度（或者是圆的直径）|
| BannerViewPager<T, VH> setIndicatorView(IIndicator) | 设置自定义指示器|自定义View需要继承BaseIndicatorView或实现IIndicator |
| BannerViewPager<T, VH> setPageTransformer(ViewPager2.PageTransformer) | 设置页面Transformer内置样式 |  |
| BannerViewPager<T, VH> addPageTransformer(ViewPager2.PageTransformer) | 3.0.0新增，添加页面Transformer样式 |  |
| BannerViewPager<T, VH> removeTransformer(ViewPager2.PageTransformer) | 3.0.0新增，移除页面Transformer |  |
| BannerViewPager<T, VH> setCurrentItem(int) | Set the currently selected page. | 2.3.5新增 |
| int getCurrentItem() | 获取当前position | 2.3.5新增 |
| BannerViewPager<T, VH> setPageStyle(PageStyle) | 设置页面样式 | 2.4.0新增 可选（MULTI_PAGE、MULTI_PAGE_SCALE、MULTI_PAGE_OVERLAP）|
| BannerViewPager<T, VH> setPageMargin(int) | 设置页面间隔 | 2.4.0新增 |
| BannerViewPager<T, VH> setIndicatorMargin(int left, int top, int right, int bottom) | 设置Indicator边距 | 2.4.1新增 |
| BannerViewPager<T, VH> registerOnPageChangeCallback(OnPageChangeListener) | 页面改变的监听事件 | 2.4.3新增 |
| BannerViewPager<T, VH> setRoundRect(int) | 设置页面滑动方向|为BannerViewPager设置圆角 |
| BannerViewPager<T, VH> setOrientation(int) | 设置页面滑动方向| 3.0.0新增 支持水平和竖直滑动 |
| BannerViewPager<T, VH> setUserInputEnabled(int) | 是否开启用户输入 | |
| void startLoop() |开启自动轮播 | 初始化BannerViewPager时不必调用该方法,设置setAutoPlay后会调用startLoop() |
| void stopLoop() | 停止自动轮播 | |
| List\<T> getData() | 获取Banner中的集合数据 |  |
| void create(List<T> list) |初始化并构造BannerViewPager  |必须调用，否则前面设置的参数无效  |

### Attributes

| Attributes | format | description |
|--|--|--|
| bvp_interval | integer | 自动轮播时间间隔 |
| bvp_scroll_duration | integer | 页面切换时滑动时间|
| bvp_can_loop | boolean| 是否循环 |
| bvp_auto_play | boolean | 是否自动播放  |
| bvp_indicator_checked_color | color | indicator选中时颜色 |
| bvp_indicator_normal_color | color | indicator未选中时颜色 |
| bvp_indicator_radius | dimension | indicator圆点半径或者Dash模式的1/2宽度  |
| bvp_round_corner| dimension  | Banner圆角大小 |
| bvp_page_margin | dimension | 页面item间距 |
| bvp_reveal_width | dimension | 一屏多页模式下两边item漏出的宽度 |
| bvp_indicator_style | enum | indicator样式(circle/dash)  |
| bvp_indicator_slide_mode | enum | indicator滑动模式(normal;smooth;worm) |
| bvp_indicator_gravity | enum | indicator位置(center/start/end) |
| bvp_page_style | enum | page样式(normal/multi_page/multi_page_overlap/multi_page_scale) |
| bvp_indicator_visibility| enum | indicator visibility(visible/gone/invisible) |


## Useage

Since Viewpager2 does not support android support, BannerViewPager 3.0 no longer supports android support. If you are still using Android support, please use [BannerViewPager V2.x](https://github.com/zhpanvip/BannerViewPager/tree/v_2.x)

### 1.Gradle dependency

Add it in your root build.gradle at the end of repositories:

```
allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}

```
Then Add the dependency

```
implementation 'com.github.zhpanvip:BannerViewPager:latestVersion'

```
 latestVersion:[![latestVersion](https://jitpack.io/v/zhpanvip/BannerViewPager.svg)](https://jitpack.io/#zhpanvip/BannerViewPager)

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
        android:layout_height="match_parent">

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

### 4.Extends BaseViewHolder,and override methods.

```
    public class NetViewHolder extends BaseViewHolder<BannerData> {

        public NetViewHolder(@NonNull View itemView) {
            super(itemView);
            CornerImageView imageView = findView(R.id.banner_image);
            imageView.setRoundCorner(BannerUtils.dp2px(0));
        }

        @Override
        public void bindData(BannerData data, int position, int pageSize) {
            CornerImageView imageView = findView(R.id.banner_image);
            Glide.with(imageView).load(data.getImagePath()).placeholder(R.drawable.placeholder).into(imageView);
        }
    }
```
### 5.Extends BaseBannerAdapter,and override methods

```
public class HomeAdapter extends BaseBannerAdapter<BannerData, NetViewHolder> {
    @Override
    protected void onBind(NetViewHolder holder, BannerData data, int position, int pageSize) {
        holder.bindData(data, position, pageSize);
    }

    @Override
    public NetViewHolder createViewHolder(View itemView, int viewType) {
        return new NetViewHolder(itemView);
    }

    @Override
    public int getLayoutId(int viewType) {
        return R.layout.item_net;
    }
}

```

### 5.Use in Activity or Fragment:

```
    private BannerViewPager<CustomBean, NetViewHolder> mBannerViewPager;
    ...
	private void initViewPager() {
             mBannerViewPager = findViewById(R.id.banner_view);
             mViewPager
                      .setAutoPlay(true)
                      .setIndicatorStyle(IndicatorStyle.ROUND_RECT)
                      .setIndicatorSliderGap(getResources().getDimensionPixelOffset(R.dimen.dp_4))
                      .setIndicatorSliderWidth(getResources().getDimensionPixelOffset(R.dimen.dp_4), getResources().getDimensionPixelOffset(R.dimen.dp_10))
                      .setIndicatorSliderColor(getColor(R.color.red_normal_color), getColor(R.color.red_checked_color))
                      .setOrientation(ViewPager2.ORIENTATION_VERTICAL)
                      .setInterval(2000)
                      .setAdapter(new HomeAdapter())
                      .registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
                          @Override
                          public void onPageSelected(int position) {
                              super.onPageSelected(position);
                              BannerData bannerData = mViewPagerHorizontal.getData().get(position);
                              mTvTitle.setText(bannerData.getTitle());
                          }
                      }).create(getPicList(4));
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

## 8. Proguard config

you must add proguard rules，If you have called setScrollDuration method in your project:

```
    -keep class androidx.recyclerview.widget.**{*;}
    -keep class androidx.viewpager2.widget.**{*;}
```

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
 - [x] Migrate to ViewPager2 （3.0.0）

## FAQ

 **If you have any question regard to BannerViewPager, please scan the QR code and join the QQ group to communicate.**


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

[玩Android](https://wanandroid.com/)


License
-------

    Copyright 2017-2020 zhpanvip

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.






