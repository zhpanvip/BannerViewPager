# BannerViewPager

[![License](https://img.shields.io/badge/License%20-Apache%202-337ab7.svg)](https://www.apache.org/licenses/LICENSE-2.0)
[![MinSdk](https://img.shields.io/badge/API-19%2B-brightgreen.svg?style=flat)](https://android-arsenal.com/api?level=19)
[![JitPack](https://jitpack.io/v/zhpanvip/BannerViewPager.svg)](https://jitpack.io/#zhpanvip/BannerViewPager)
[ ![JCenter](https://api.bintray.com/packages/zhpanvip/CircleViewPager/bannerview/images/download.svg) ](https://bintray.com/zhpanvip/CircleViewPager/bannerview/_latestVersion)

## 效果预览

 ### [点击或扫描二维码下载apk](https://github.com/zhpanvip/BannerViewPager/raw/master/download/app.apk)

![扫描下载Demo](https://github.com/zhpanvip/BannerViewPager/blob/master/image/qrcode.png)


### 1.setPageStyle

[一屏多页Demo](https://github.com/zhpanvip/BannerViewPager/blob/master/app/src/main/java/com/example/zhpan/circleviewpager/activity/PageStyleActivity.java)

| MULTI_PAGE |MULTI_PAGE_SCALE | MULTI_PAGE_OVERLAP |
|--|--|--|
| ![MULTI_PAGE](https://github.com/zhpanvip/BannerViewPager/blob/master/image/page_style_multi.gif) |![MULTI_PAGE](https://github.com/zhpanvip/BannerViewPager/blob/master/image/page_style_multi_scale.gif) |![MULTI_PAGE](https://github.com/zhpanvip/BannerViewPager/blob/master/image/page_style_multi_overlay.gif) |

### 2.setIndicatorStyle
BannerViewPager支持多种IndicatorViewStyle,同时还提供了完全自定义IndicatorView的功能。只要继承BaseIndicatorView或者实现IIndicator接口，并重写相应方法，就可以为所欲为的打造任意的Indicator了。

[IndicatorViewStyle Demo](https://github.com/zhpanvip/BannerViewPager/blob/master/app/src/main/java/com/example/zhpan/circleviewpager/activity/IndicatorStyleActivity.java)

| CIRCLE | DASH | Custom |
|--|--|--|
| ![CIRCLE](https://github.com/zhpanvip/BannerViewPager/blob/master/image/style_circle.gif) | ![DASH](https://github.com/zhpanvip/BannerViewPager/blob/master/image/style_dash.gif) | ![NORMAL](https://github.com/zhpanvip/BannerViewPager/blob/master/image/style_custum.gif) |

### 3.setIndicatorSlideMode

| NORMAL | SMOOTH |
|--|--|
| ![NORMAL](https://github.com/zhpanvip/BannerViewPager/blob/master/image/slide_normal.gif) |  ![SMOOTH](https://github.com/zhpanvip/BannerViewPager/blob/master/image/slide_smooth.gif) |


### 4.setPageTransformerStyle

[TransformStyle Demo](https://github.com/zhpanvip/BannerViewPager/blob/master/app/src/main/java/com/example/zhpan/circleviewpager/activity/PageTransformerActivity.java)

| 参数 | STACK | ROTATE | DEPTH | ACCORDION |
|--|--|--|--|--|
| 预览 | ![STACK](https://github.com/zhpanvip/BannerViewPager/blob/master/image/transform_stack.gif) | ![ROTATE_DOWN](https://github.com/zhpanvip/BannerViewPager/blob/master/image/transform_rotate.gif) | ![DEPTH](https://github.com/zhpanvip/BannerViewPager/blob/master/image/transform_depth.gif)  |![ACCORDION](https://github.com/zhpanvip/BannerViewPager/blob/master/image/transform_accordion.gif)  |



## 开放API

| 方法名 | 方法描述 | 说明 |
|--|--|--|
| BannerViewPager<T, VH> setCanLoop(boolean canLoop) | 是否开启循环 | 默认值true|
| BannerViewPager<T, VH> setAutoPlay(boolean autoPlay) | 是否开启自动轮播 | 默认值true|
| BannerViewPager<T, VH> setInterval(int interval) | 自动轮播时间间隔 |单位毫秒，默认值3000  |
| BannerViewPager<T, VH> setScrollDuration(int scrollDuration) | 设置页面滚动时间 | 设置页面滚动时间 |单位毫秒，默认值800  |
| BannerViewPager<T, VH> setRoundCorner(int radius) | 设置圆角 |默认无圆角 需要SDK_INT>=LOLLIPOP(API 21)  |
| BannerViewPager<T, VH> setOnPageClickListener(OnPageClickListener onPageClickListener) | 设置页面点击事件 |  |
| BannerViewPager<T, VH> setHolderCreator(HolderCreator\<VH> holderCreator) |设置HolderCreator  |必须设置HolderCreator，否则会抛出NullPointerException|
| BannerViewPager<T, VH> setIndicatorVisibility(@Visibility int visibility) | indicator vibility |默认值VISIBLE 2.4.2 新增|
| BannerViewPager<T, VH> setIndicatorStyle(int indicatorStyle) | 设置指示器样式 | 可选枚举(CIRCLE, DASH) 默认CIRCLE  |
| BannerViewPager<T, VH> setIndicatorGravity(int gravity) | 指示器位置 |可选值(CENTER、START、END)默认值CENTER |
| BannerViewPager<T, VH> setIndicatorColor(int normalColor,int checkedColor) | 指示器圆点颜色 |normalColor：未选中时颜色默认"#8C6C6D72"， checkedColor：选中时颜色 默认"#8C18171C" |
| BannerViewPager<T, VH> setIndicatorSlideMode(int slideMode)  | 设置Indicator滑动模式 | 可选（NORMAL、SMOOTH），默认值SMOOTH  |
| BannerViewPager<T, VH> setIndicatorRadius(int radius) | 设置指示器圆点半径 | 默认值4dp|
| BannerViewPager<T, VH> setIndicatorRadius(int normalRadius,int checkRadius)  |设置指示器圆点半径  |  normalRadius:未选中时半径  checkedRadius:选中时的半径,默认值4dp |
| BannerViewPager<T, VH> setIndicatorWidth(int indicatorWidth) | 设置指示器宽度，如果是圆形指示器，则为直径 |  默认值8dp|
| BannerViewPager<T, VH> setIndicatorWidth(int normalWidth, int checkWidth) | 设置指示器宽度，如果是圆形指示器，则为直径 | 默认值8dp |
| BannerViewPager<T, VH> setIndicatorHeight(int indicatorHeight) | 设置指示器高度，仅在Indicator样式为DASH时有效 | 默认值normalIndicatorWidth/2 |
| BannerViewPager<T, VH> setIndicatorGap(int indicatorMargin) | 指示器圆点间距| 默认值为指示器宽度（或者是圆的直径）|
| BannerViewPager<T, VH> setIndicatorView(IIndicator indicatorView) | 设置自定义指示器| 设置自定义指示器后以上关于IndicatorView的参数会部分失效|
| BannerViewPager<T, VH> setPageTransformerStyle(int style) | 设置页面Transformer内置样式 |  |
| BannerViewPager<T, VH> setCurrentItem(int item) | Set the currently selected page. | 2.3.5新增 |
| void getCurrentItem() | 获取当前position | 2.3.5新增 |
| BannerViewPager<T, VH> setPageStyle(PageStyle pageStyle) | 设置页面样式 | 2.4.0新增 可选（MULTI_PAGE、NORMAL）MULTI_PAGE：一屏多页样式 |
| BannerViewPager<T, VH> setPageMargin(int pageMargin) | 设置页面间隔 | 2.4.0新增 |
| BannerViewPager<T, VH> setIndicatorMargin(int left, int top, int right, int bottom) | 设置Indicator边距 | 2.4.1新增 |
| void startLoop() |开启自动轮播 | 初始化BannerViewPager时不必调用该方法,设置setAutoPlay后会调用startLoop() |
| void stopLoop() | 停止自动轮播 | 如果开启自动轮播，为避免内存泄漏需要在onStop()或onDestroy中调用此方法 |
| List\<T> getList() | 获取Banner中的集合数据 |  |
| void create(List<T> list) |初始化并构造BannerViewPager  |必须调用，否则前面设置的参数无效  |

### xml支持的attrs
| Attributes | format | description |
|--|--|--|
| bvp_interval | integer | 自动轮播事件间隔 |
| bvp_scroll_duration | integer | 页面切换时滑动时间|
| bvp_can_loop | bvp_can_loop | 是否循环 |
| bvp_auto_play | boolean | 是否自动播放  |
| bvp_indicator_checked_color | color | indicator选中时颜色 |
| bvp_indicator_normal_color | color | indicator未选中时颜色 |
| bvp_indicator_radius | dimension | indicator圆点半径或者Dash模式的1/2宽度  |
| bvp_round_corner| dimension  | Banner圆角大小 |
| bvp_page_margin | dimension | 页面item间距 |
| bvp_reveal_width | dimension | 一屏多页模式下两边item漏出的宽度 |
| bvp_indicator_style | enum | indicator样式(circle/dash)  |
| bvp_indicator_slide_mode | enum | indicator滑动模式(normal/smooth) |
| bvp_indicator_gravity | enum | indicator位置(center/start/end) |
| bvp_page_style | enum | page样式(normal/multi_page/multi_page_overlap/multi_page_scale) |
| bvp_transformer_style | enum | transform样式(normal/depth/stack/accordion) |
| bvp_indicator_visibility| enum | indicator visibility(visible/gone/invisible) |


## 如何使用

### 1.gradle中添加依赖
   

如果您已迁移到AndroidX，请在项目的root build.gradle中添加如下配置：
```
allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
	
```
Add the dependency

```
implementation 'com.github.zhpanvip:BannerViewPager:latestVersion'

```

Androidx latestVersion:[![latestVersion](https://jitpack.io/v/zhpanvip/BannerViewPager.svg)](https://jitpack.io/#zhpanvip/BannerViewPager)

如果未迁移到AndroidX请使用（非Androidx的包托管在JCenter上，2.3.5之后不再维护）：
```
implementation 'com.zhpan.library:bannerview:latestVersion'
```

非Androidx latestVersion: [ ![latestVersion](https://api.bintray.com/packages/zhpanvip/CircleViewPager/bannerview/images/download.svg) ](https://bintray.com/zhpanvip/CircleViewPager/bannerview/_latestVersion)

### 2.在xml文件中添加如下代码：

```
    <com.zhpan.bannerview.BannerViewPager
            android:id="@+id/banner_view"
            android:layout_width="match_parent"
            android:layout_margin="10dp"
            android:layout_height="160dp" />
```

### 3.Banner的页面布局

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

### 4.自定义ViewHolder

```
public class NetViewHolder implements ViewHolder<BannerData> {
    private ImageView mImageView;
    private TextView mTextView;

    @Override
    public View createView(ViewGroup viewGroup, Context context, int position) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_net, viewGroup, false);
        mImageView = view.findViewById(R.id.banner_image);
        mTextView = view.findViewById(R.id.tv_describe);
        return view;
    }

    @Override
    public void onBind(Context context, BannerData data, int position, int size) {
        ImageLoaderOptions options = new ImageLoaderOptions.Builder().into(mImageView).load(data.getImagePath()).placeHolder(R.drawable.placeholder).build();
        ImageLoaderManager.getInstance().loadImage(options);
        mTextView.setText(data.getTitle());
    }
}
```

### 5.BannerViewPager参数配置

```
    private BannerViewPager<BannerData, NetViewHolder> mBannerViewPager;
    ...
	private void initViewPager() {
             mBannerViewPager = findViewById(R.id.banner_view);
             mBannerViewPager.showIndicator(true)
                .setInterval(3000)
                .setCanLoop(false)
                .setAutoPlay(true)
                .setRoundCorner(DpUtils.dp2px(7))
                .setIndicatorColor(Color.parseColor("#935656"), Color.parseColor("#FF4C39"))
                .setIndicatorGravity(IndicatorGravity.END)
                .setScrollDuration(1000).setHolderCreator(NetViewHolder::new)
                .setOnPageClickListener(position -> {
                    BannerData bannerData = mBannerViewPager.getList().get(position);
                    Toast.makeText(NetworkBannerActivity.this,
                            "点击了图片" + position + " " + bannerData.getDesc(), Toast.LENGTH_SHORT).show();

                }).create(mList);
        }
```

### 6.开启与停止轮播

如果开启了自动轮播功能，请务必在onDestroy中停止轮播，以免出现内存泄漏。

```
	@Override
    protected void onDestroy() {
        super.onDestroy();
        if (mBannerViewPager != null)
    		mViewpager.stopLoop();
    }
```
为了节省性能也可以在onStop中停止轮播，在onResume中开启轮播：

```
    @Override
    protected void onStop() {
        super.onStop();
        if (mBannerViewPager != null)
            mBannerViewPager.stopLoop();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mBannerViewPager != null)
            mBannerViewPager.startLoop();
    }
```
### 7.高级功能---自定义IndicatorView

**(1)自定义View并继承BaseIndicatorView**

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

## TODO 版本计划

 - [x] 优化及重构IndicatorView（2.0.1）

 - [x] 修复2.1.0以前版本循环滑动时第一张切换卡顿问题（2.1.0.1）

 - [x] 增加页面滑动动画（2.1.2）

 - [x] 迁移AndroidX（2.2.0）

 - [x] 增加IndicatorView的滑动样式（2.2.2）

 - [x] 增添更多Indicator样式（2.3.+）
 - [x]  支持一屏显示多页 （2.4.0）
 - [ ]  ViewPager更换为ViewPager2 （3.0.0）
 - [ ] 目前Indicator部分代码比较乱，还有很大很大的优化空间，后续版本将持续优化
 


[更多详情请点击此处](https://juejin.im/post/5d6bce24f265da03db0790d1)

## 感谢

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






