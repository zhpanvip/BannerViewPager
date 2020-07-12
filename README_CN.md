# BannerViewPager

[![License](https://img.shields.io/badge/License%20-Apache%202-337ab7.svg)](https://www.apache.org/licenses/LICENSE-2.0)
![MinSdk](https://img.shields.io/badge/API-16%2B-brightgreen.svg?style=flat)
[![JitPack](https://jitpack.io/v/zhpanvip/BannerViewPager.svg)](https://jitpack.io/#zhpanvip/BannerViewPager)
[ ![JCenter](https://api.bintray.com/packages/zhpanvip/CircleViewPager/bannerview/images/download.svg) ](https://bintray.com/zhpanvip/CircleViewPager/bannerview/_latestVersion)
[![Android Arsenal](https://img.shields.io/badge/Android%20Arsenal-BannerViewPager-brightgreen.svg?style=flat)](https://android-arsenal.com/details/1/7961)
[![Android Arsenal](https://img.shields.io/badge/Android%20Arsenal-ViewPagerIndicator-brightgreen.svg?style=flat)](https://github.com/zhpanvip/viewpagerindicator)

## [English](https://github.com/zhpanvip/BannerViewPager) | 中文


> 腾讯视频、QQ音乐、酷狗音乐、支付宝、天猫、淘宝、优酷视频、喜马拉雅、网易云音乐、哔哩哔哩、全民K歌等App的Banner样式都可以通过BannerViewPager实现哦！

## 3.0版本新特性

- 基于ViewPager2实现
- 支持多类型Item
- 内存大幅优化，性能大幅提升
- 新增setOrientation，支持竖直滑动
- 新增addPageTransformer与removeTransformer
- setAdapter替换setHolderCreator
- getData替换了getList方法
- registerOnPageChangeCallback替换setOnPageChangeListener
- setUserInputEnabled取代disableTouchScroll
- 移除setPageTransformerStyle
- 移除部分2.x版本已废弃的方法
- 不再支持android support.
- 新增Indicator SCALE与COLOR滑动样式（V3.1.0）
- 支持刷新通过refreshData()方法刷新数据（V3.1.0）

## 效果预览

 ### [点击或扫描二维码下载apk](https://github.com/zhpanvip/BannerViewPager/raw/master/download/app.apk)

![扫描下载Demo](https://github.com/zhpanvip/Resource/blob/master/image/banner/qrcode.png)


### 1.PageStyle

[一屏多页Demo](https://github.com/zhpanvip/BannerViewPager/blob/master/app/src/main/java/com/example/zhpan/circleviewpager/fragment/PageFragment.java)

| MULTI_PAGE |MULTI_PAGE_SCALE | MULTI_PAGE_OVERLAP |
|--|--|--|
| ![MULTI_PAGE](https://github.com/zhpanvip/Resource/blob/master/image/banner/page_style_multi.gif) |![MULTI_PAGE](https://github.com/zhpanvip/Resource/blob/master/image/banner/page_style_multi_scale.gif) |![MULTI_PAGE](https://github.com/zhpanvip/Resource/blob/master/image/banner/page_style_multi_overlay.gif) |

### 2.[Indicator](https://github.com/zhpanvip/viewpagerindicator)

目前指示器已经从BannerViewPager中分离出来，现在单独为一个仓库，新的仓库地址为[ViewPagerIndicator](https://github.com/zhpanvip/viewpagerindicator)，你可以点击连接了解更多关于[ViewPagerIndicator](https://github.com/zhpanvip/viewpagerindicator)的信息。

#### (1)IndicatorStyle 与 IndicatorSlideMode

BannerViewPager目前已支持三种IndicatorViewStyle,以及五种IndicatorSlideMode,分别如下：

| 属性 | CIRCLE | DASH | ROUND_RECT |
|--|--|--|--|
| NORMAL| ![CIRCLE_NORMAL](https://github.com/zhpanvip/Resource/blob/master/image/indicator/slide_circle_normal.gif) | ![DASH_NORMAL](https://github.com/zhpanvip/Resource/blob/master/image/indicator/style_dash_normal.gif) | ![ROUND_RECT_NORMAL](https://github.com/zhpanvip/Resource/blob/master/image/indicator/style_round_rect_normal.gif) |
| SMOOTH| ![CIRCLE_SMOOTH](https://github.com/zhpanvip/Resource/blob/master/image/indicator/slide_circle_smooth.gif) | ![DASH_SMOOTH](https://github.com/zhpanvip/Resource/blob/master/image/indicator/style_dash_smooth.gif) | ![ROUND_RECT_SMOOTH](https://github.com/zhpanvip/Resource/blob/master/image/indicator/style_round_rect_smooth.gif) |
| WORM| ![CIRCLE_WORM](https://github.com/zhpanvip/Resource/blob/master/image/indicator/slide_circle_worm.gif) | ![DASH_WORM](https://github.com/zhpanvip/Resource/blob/master/image/indicator/style_dash_worm.gif) | ![ROUND_WORM](https://github.com/zhpanvip/Resource/blob/master/image/indicator/style_round_rect_worm.gif) |
| COLOR| ![CIRCLE_COLOR](https://github.com/zhpanvip/Resource/blob/master/image/indicator/slide_circle_color.gif) | ![DASH_COLOR](https://github.com/zhpanvip/Resource/blob/master/image/indicator/style_dash_color.gif) | ![ROUND_COLOR](https://github.com/zhpanvip/Resource/blob/master/image/indicator/style_round_rect_color.gif) |
| SCALE| ![CIRCLE_SCALE](https://github.com/zhpanvip/Resource/blob/master/image/indicator/slide_circle_scale.gif) | ![DASH_SCALE](https://github.com/zhpanvip/Resource/blob/master/image/indicator/style_dash_scale.gif) | ![ROUND_SCALE](https://github.com/zhpanvip/Resource/blob/master/image/indicator/style_round_rect_scale.gif) |

#### (2)Custom Indicator

同时BannerViewPager还提供了自定义IndicatorView的功能。只要继承BaseIndicatorView或者实现IIndicator接口，并重写相应方法，就可以为所欲为的打造任意的Indicator了。

[Sample Click Here](https://github.com/zhpanvip/BannerViewPager/blob/master/app/src/main/java/com/example/zhpan/circleviewpager/fragment/OthersFragment.java)

| Figure Indicator | Drawable Indicator | Indicator below of Banner |
|--|--|--|
| ![CIRCLE](https://github.com/zhpanvip/Resource/blob/master/image/banner/style_custum.gif) | ![DASH](https://github.com/zhpanvip/Resource/blob/master/image/banner/style_custom2.gif) | ![NORMAL](https://github.com/zhpanvip/Resource/blob/master/image/banner/style_custom1.gif) |


## 开放API

| 方法名 | 方法描述 | 说明 |
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
| BannerViewPager<T, VH> setIndicatorSlideMode(int slideMode)  | 设置Indicator滑动模式 | 可选（NORMAL、SMOOTH、WORM、COLOR、SCALE），默认值NORMAL  |
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
| BannerViewPager<T, VH> setPageStyle(PageStyle) | 设置页面样式 | 2.4.0新增 可选（MULTI_PAGE_SCALE、MULTI_PAGE_OVERLAP）|
| BannerViewPager<T, VH> setPageMargin(int) | 设置页面间隔 | 2.4.0新增 |
| BannerViewPager<T, VH> setIndicatorMargin(int left, int top, int right, int bottom) | 设置Indicator边距 | 2.4.1新增 |
| BannerViewPager<T, VH> registerOnPageChangeCallback(OnPageChangeListener) | 页面改变的监听事件 | 2.4.3新增 |
| BannerViewPager<T, VH> setRoundRect(int) | 设置页面滑动方向|为BannerViewPager设置圆角 |
| BannerViewPager<T, VH> setOrientation(int) | 设置页面滑动方向| 3.0.0新增 支持水平和竖直滑动 |
| BannerViewPager<T, VH> setUserInputEnabled(int) | 是否开启用户输入 | |
| BannerViewPager<T, VH> setLifecycleRegistry(Lifecycle) | 为BVP设置Lifecycle | |
| void startLoop() |开启自动轮播 | 初始化BannerViewPager时不必调用该方法,设置setAutoPlay后会调用startLoop() |
| void stopLoop() | 停止自动轮播 | |
| List\<T> getData() | 获取Banner中的集合数据 |  |
| void create(List<T> list) |初始化并构造BannerViewPager  |如果创建BannerViewPager时已经有数据可以调用此方法  |
| void create() |创建没有数据的BannerViewPager  | 如果创建BannerViewPager时还没有数据，比如数据是来自服务器，可以调用此方法，等到成功获取数据后调用refreshData()刷新数据  |
### xml支持的attrs
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
| bvp_indicator_style | enum | indicator样式(circle/dash/round_rect)  |
| bvp_indicator_slide_mode | enum | indicator滑动模式(normal;smooth;worm;color;scale) |
| bvp_indicator_gravity | enum | indicator位置(center/start/end) |
| bvp_page_style | enum | page样式(normal/multi_page/multi_page_overlap/multi_page_scale) |
| bvp_indicator_visibility| enum | indicator visibility(visible/gone/invisible) |


## 如何使用

由于ViewPager2不支持Android support，因此BannerViewPager 3.0不再支持Android support，如果你仍在使用Android support请使用，请移步[BannerViewPager 2.x版本](https://github.com/zhpanvip/BannerViewPager/tree/v_2.x)

### 1.gradle中添加依赖
   
在项目的root build.gradle中添加如下配置：
```
allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
	
```
添加依赖

```
implementation 'com.github.zhpanvip:BannerViewPager:latestVersion'

```
 latestVersion:[![latestVersion](https://jitpack.io/v/zhpanvip/BannerViewPager.svg)](https://jitpack.io/#zhpanvip/BannerViewPager)

### 2.在xml文件中添加如下代码：

```
    <com.zhpan.bannerview.BannerViewPager
            android:id="@+id/banner_view"
            android:layout_width="match_parent"
            android:layout_margin="10dp"
            android:layout_height="160dp" />
```

### 3.Banner的Item页面布局
**注意：Item的布局必须是"match_parent",否则ViewPager2会抛出一个IllegalStateException.
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

### 4.继承BaseViewHolder,并重写相关方法

**注意:在ViewHolder中不要用{@link RecyclerView.ViewHolder#getAdapterPosition} 方法获取position，这个方法会返回一个不正确的position**

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

**如果你需要通过getAdapterPosition()方法获取position，可参考如下代码:**

```
     int adapterPosition = getAdapterPosition();
     int realPosition = BannerUtils.getRealPosition(isCanLoop, adapterPosition, mList.size());
```

### 5.继承BaseBannerAdapter,并重写相关方法

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


### 5.BannerViewPager参数配置

如果是异步获取数据（例如从服务器或数据库获取数据），可以调用不带参数的create()方法：

#### Java code
```
    private BannerViewPager<CustomBean, NetViewHolder> mViewPager;
    ...
	private void setupViewPager() {
             mViewPager = findViewById(R.id.banner_view);
             mViewPager
                       .setAutoPlay(true)
                       .setScrollDuration(800)
                       .setIndicatorStyle(IndicatorStyle.ROUND_RECT)
                       .setLifecycleRegistry(getLifecycle())
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
                       }).create();
        }
```

#### Kotlin Code

```
    private lateinit var mViewPager: BannerViewPager<CustomBean, NetViewHolder>
    ...

    private fun setupViewPager() {
            mViewPager = findViewById(R.id.banner_view)
            mViewPager.apply {
                adapter = HomeAdapter()
                setAutoPlay(true)
                setLifecycleRegistry(lifecycle)
                setIndicatorStyle(IndicatorStyle.ROUND_RECT)
                setIndicatorSliderGap(getResources().getDimensionPixelOffset(R.dimen.dp_4))
                setIndicatorMargin(0, 0, 0, resources.getDimension(R.dimen.dp_100).toInt())
                setIndicatorSlideMode(IndicatorSlideMode.SMOOTH)
                setIndicatorSliderRadius(resources.getDimension(R.dimen.dp_3).toInt(), resources.getDimension(R.dimen.dp_4_5).toInt())
                setIndicatorSliderColor(ContextCompat.getColor(this@WelcomeActivity, R.color.white),
                        ContextCompat.getColor(this@WelcomeActivity, R.color.white_alpha_75))
                registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                    override fun onPageSelected(position: Int) {
                        BannerUtils.log("position:$position")
                    }
                })
            }.create()
        }

```

当成功拿到数据后再调用refreshData()方法刷新数据：

```
    mViewPager.refreshData(data)
```

如果是同步获取数据，则可以调用带参数的create方法，如下：

```
    private BannerViewPager<CustomBean, NetViewHolder> mViewPager;
    ...
	private void setupViewPager() {
             mViewPager = findViewById(R.id.banner_view);
             mViewPager
                       .setAutoPlay(true)
                       .setScrollDuration(800)
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

### 6.开启与停止轮播

***使用setLifecycleRegistry(getLifecycle())方法替代在Activity或Fragment中调用startLoop和stopLoop***

```
    mViewPager.setLifecycleRegistry(getLifecycle())

    // 下边代码已过时
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

### 7.高级功能---自定义IndicatorView

在内置Indicator不满足需求时可以通过自定义IndicatorView实现,例子将实现一个如下图所示的IndicatorView。

| Custom IndicatorView|
|--|
| ![NORMAL](https://github.com/zhpanvip/Resource/blob/master/image/banner/style_custum.gif) |

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
**(2)设置自定义指示器**

```
    FigureIndicatorView indicatorView = new FigureIndicatorView(mContext);
    indicatorView.setRadius(getResources().getDimensionPixelOffset(R.dimen.dp_18));
    indicatorView.setTextSize(getResources().getDimensionPixelSize(R.dimen.sp_13));
    indicatorView.setBackgroundColor(Color.parseColor("#aa118EEA"));

   mViewPager.setAutoPlay(false).setCanLoop(true)
               .setIndicatorSlideMode(IndicatorSlideMode.NORMAL)
               .setIndicatorVisibility(View.VISIBLE)
               .setIndicatorGravity(IndicatorGravity.END)
               .setIndicatorView(indicatorView).create(getPicList(4));
```

## 8. 混淆

如果你的项目开启了混淆，并且在项目中使用了setScrollDuration方法，则必须添加以下混淆规则：

```
    -keep class androidx.recyclerview.widget.**{*;}
    -keep class androidx.viewpager2.widget.**{*;}
```

## TODO 版本计划

 - [x] 优化及重构IndicatorView（2.0.1）

 - [x] 修复2.1.0以前版本循环滑动时第一张切换卡顿问题（2.1.0.1）

 - [x] 增加页面滑动动画（2.1.2）

 - [x] 迁移AndroidX（2.2.0）

 - [x] 增加IndicatorView的滑动样式（2.2.2）

 - [x] 增添更多Indicator样式（2.3.+）
 - [x] 支持一屏显示多页 （2.4.0）
 - [x] v2.4.3版本着重优化提升性能
 - [x] 重构Indicator，~~尽量修复Indicator SMOOTH模式下滑动问题~~ (2.5.0)
 - [x] 目前Indicator部分代码比较乱，还有很大很大的优化空间，后续版本将持续优化(2.5.0对Indicator再次进行了重构，重构后代码已经很整洁，但仍然有优化空间)
 - [x] 修复 issue #34 Indicator 在Smooth模式下存在的问题 (2.6.1).
 - [x] ViewPager更换为ViewPager2 （3.0.0）

## 有问题可以扫码加QQ群交流

 ![QQ交流群60902509](https://github.com/zhpanvip/Resource/blob/master/image/group/qq_group.png)



## <span id="Sponsor"> 赞赏 </span>


**如果您觉得BVP库还不错，您可以对BVP打赏哦，您的支持将是我持续维护的动力。**

| 支付宝 | 微信支付 |
|--|--|
| ![NORMAL](https://github.com/zhpanvip/Resource/blob/master/image/pay/pay_alipay.jpg) |  ![SMOOTH](https://github.com/zhpanvip/Resource/blob/master/image/pay/pay_wechat.png) |


## 感谢

[玩Android](https://wanandroid.com/)

[finite-cover-flow](https://github.com/KoderLabs/finite-cover-flow)

[zguop-banner](https://github.com/zguop/banner)

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






