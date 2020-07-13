# BannerViewPager

[![License](https://img.shields.io/badge/License%20-Apache%202-337ab7.svg)](https://www.apache.org/licenses/LICENSE-2.0)
![MinSdk](https://img.shields.io/badge/API-16%2B-brightgreen.svg?style=flat)
[![JitPack](https://jitpack.io/v/zhpanvip/BannerViewPager.svg)](https://jitpack.io/#zhpanvip/BannerViewPager)
[![JCenter](https://api.bintray.com/packages/zhpanvip/CircleViewPager/bannerview/images/download.svg) ](https://bintray.com/zhpanvip/CircleViewPager/bannerview/_latestVersion)
[![Android Arsenal](https://img.shields.io/badge/Android%20Arsenal-BannerViewPager-brightgreen.svg?style=flat)](https://android-arsenal.com/details/1/7961)
[![Android Arsenal](https://img.shields.io/badge/Android%20Arsenal-ViewPagerIndicator-brightgreen.svg?style=flat)](https://github.com/zhpanvip/viewpagerindicator)

## English | [中文](https://github.com/zhpanvip/BannerViewPager/blob/master/README_CN.md)

> Tencent Video,QQ Music,KuGou,AliPay,Tmall,TaoBao,YouKu,Himalaya,NetEase Music,Bilibili ect. All of above App's Banner can be implements By BannerViewPager.


## What's new in version 3.x

- Migrate to ViewPager2
- Multiple item type supported
- setOrientation supported
- addPageTransformer and removeTransformer supported
- setAdapter replaces setHolderCreator
- getData replaces getList
- registerOnPageChangeCallback replaces setOnPageChangeListener
- setUserInputEnabled replaces disableTouchScroll
- remove setPageTransformerStyle
- remove some deprecate methods in v2.x
- no longer support android.support library
- SCALE slide mode and COLOR slide mode supported(V3.1.0)
- refreshData() supported (V3.1.0)
- setLifecycleRegistry(Lifecycle) supported (v3.1.4)
## Preview

 ### [Click here or scan the QR code to download demo apk](https://github.com/zhpanvip/BannerViewPager/raw/master/app/release/app-release.apk)

![QRCode](https://github.com/zhpanvip/Resource/blob/master/image/banner/qrcode.png)


### 1.PageStyle

[Sample Click Here](https://github.com/zhpanvip/BannerViewPager/blob/master/app/src/main/java/com/example/zhpan/circleviewpager/fragment/PageFragment.java)

| MULTI_PAGE |MULTI_PAGE_SCALE | MULTI_PAGE_OVERLAP |
|--|--|--|
| ![MULTI_PAGE](https://github.com/zhpanvip/Resource/blob/master/image/banner/page_style_multi.gif) |![MULTI_PAGE](https://github.com/zhpanvip/Resource/blob/master/image/banner/page_style_multi_scale.gif) |![MULTI_PAGE](https://github.com/zhpanvip/Resource/blob/master/image/banner/page_style_multi_overlay.gif) |

### [2.Indicator](https://github.com/zhpanvip/viewpagerindicator)

The Indicator library was split from BannerViewPager,the new repo is [ViewPagerIndicator](https://github.com/zhpanvip/viewpagerindicator)，Click the link to see more information about [ViewPagerIndicator](https://github.com/zhpanvip/viewpagerindicator)

#### (1)setIndicatorStyle and setIndicatorSlideMode

BannerViewPager supports three Indicator Styles and five Indicator Slide mode now.

[Sample Click Here](https://github.com/zhpanvip/BannerViewPager/blob/master/app/src/main/java/com/example/zhpan/circleviewpager/fragment/IndicatorFragment.java)

| Attrs | CIRCLE | DASH | ROUND_RECT |
|--|--|--|--|
| NORMAL| ![CIRCLE_NORMAL](https://github.com/zhpanvip/Resource/blob/master/image/indicator/slide_circle_normal.gif) | ![DASH_NORMAL](https://github.com/zhpanvip/Resource/blob/master/image/indicator/style_dash_normal.gif) | ![ROUND_RECT_NORMAL](https://github.com/zhpanvip/Resource/blob/master/image/indicator/style_round_rect_normal.gif) |
| SMOOTH| ![CIRCLE_SMOOTH](https://github.com/zhpanvip/Resource/blob/master/image/indicator/slide_circle_smooth.gif) | ![DASH_SMOOTH](https://github.com/zhpanvip/Resource/blob/master/image/indicator/style_dash_smooth.gif) | ![ROUND_RECT_SMOOTH](https://github.com/zhpanvip/Resource/blob/master/image/indicator/style_round_rect_smooth.gif) |
| WORM| ![CIRCLE_WORM](https://github.com/zhpanvip/Resource/blob/master/image/indicator/slide_circle_worm.gif) | ![DASH_WORM](https://github.com/zhpanvip/Resource/blob/master/image/indicator/style_dash_worm.gif) | ![ROUND_WORM](https://github.com/zhpanvip/Resource/blob/master/image/indicator/style_round_rect_worm.gif) |
| COLOR| ![CIRCLE_COLOR](https://github.com/zhpanvip/Resource/blob/master/image/indicator/slide_circle_color.gif) | ![DASH_COLOR](https://github.com/zhpanvip/Resource/blob/master/image/indicator/style_dash_color.gif) | ![ROUND_COLOR](https://github.com/zhpanvip/Resource/blob/master/image/indicator/style_round_rect_color.gif) |
| SCALE| ![CIRCLE_SCALE](https://github.com/zhpanvip/Resource/blob/master/image/indicator/slide_circle_scale.gif) | ![DASH_SCALE](https://github.com/zhpanvip/Resource/blob/master/image/indicator/style_dash_scale.gif) | ![ROUND_SCALE](https://github.com/zhpanvip/Resource/blob/master/image/indicator/style_round_rect_scale.gif) |

#### (2)Custom Indicator

It's also support to custom indicator style,just need extends BaseIndicatorView or implement the IIndicator and override methods, then you can draw Indicators for whatever you want.

[Sample Click Here](https://github.com/zhpanvip/BannerViewPager/blob/master/app/src/main/java/com/example/zhpan/circleviewpager/fragment/OthersFragment.java)

| Figure Indicator | Drawable Indicator | Indicator below of Banner |
|--|--|--|
| ![CIRCLE](https://github.com/zhpanvip/Resource/blob/master/image/banner/style_custum.gif) | ![DASH](https://github.com/zhpanvip/Resource/blob/master/image/banner/style_custom2.gif) | ![NORMAL](https://github.com/zhpanvip/Resource/blob/master/image/banner/style_custom1.gif) |


## API

| Methods | Description | Default |
|--|--|--|
| BannerViewPager<T, VH> setCanLoop(boolean) | set it's can loop | default value is true|
| BannerViewPager<T, VH> setAutoPlay(boolean) | set can auto play | default value is true|
| BannerViewPager<T, VH> setInterval(int) | set the interval of item switch |unit is millisecond，default value is 3000ms  |
| BannerViewPager<T, VH> setScrollDuration(int) | set item scroll duration |Unit is millisecond，default value is equals ViewPager2 item scroll time  |
| BannerViewPager<T, VH> setRoundCorner(int) | set round corner for BVP. equals setRoundRect method |Require SDK_INT>=LOLLIPOP  |
| BannerViewPager<T, VH> setOnPageClickListener(OnPageClickListener) | set a callback that when the item is clicked |  |
| BannerViewPager<T, VH> setAdapter(BaseBannerAdapter\<T, VH>) |set Adapter for BVP  |This method must be called，Otherwise,BVP will throw a NullPointerException|
| BannerViewPager<T, VH> setIndicatorVisibility(int) | indicator visibility |default is VISIBLE |
| BannerViewPager<T, VH> setIndicatorStyle(int) | set indicator style | enum(CIRCLE, DASH、ROUND_RECT) default value is CIRCLE  |
| BannerViewPager<T, VH> setIndicatorGravity(int) | set gravity of indicator |enum(CENTER、START、END),default value is CENTER |
| BannerViewPager<T, VH> setIndicatorColor(int,int) | set indicator slider color |default normalColor: "#8C6C6D72"，default checkedColor:"#8C18171C" |
| BannerViewPager<T, VH> setIndicatorSlideMode(int slideMode)  | set indicator slider mode | enum（NORMAL;SMOOTH;WORM;COLOR;SCALE），default value NORMAL  |
| BannerViewPager<T, VH> setIndicatorSliderRadius(int radius) | set indicator slider radius | default value is 4dp|
| BannerViewPager<T, VH> setIndicatorSliderRadius(int normalRadius,int checkRadius)  |set indicator slider radius  |  default value is 4dp |
| BannerViewPager<T, VH> setIndicatorSliderWidth(int) | set Indicator slider width |  default value is 8dp|
| BannerViewPager<T, VH> setIndicatorSliderWidth(int normalWidth, int checkWidth) | set indicator slider width| default value is 8dp |
| BannerViewPager<T, VH> setIndicatorHeight(int) | set indicator slider height | default value is normalIndicatorSliderWidth/2 |
| BannerViewPager<T, VH> setIndicatorSliderGap(int) | set space for indicator slider| default value is normalIndicatorSliderWidth|
| BannerViewPager<T, VH> setIndicatorView(IIndicator) | set custom indicator|Custom indicator must extends BaseIndicatorView or implements IIndicator |
| BannerViewPager<T, VH> setPageTransformer(ViewPager2.PageTransformer) | set page transformer,call this method will cover old transformer |  |
| BannerViewPager<T, VH> addPageTransformer(ViewPager2.PageTransformer) | add page transformer | supported v3.0.0 |
| BannerViewPager<T, VH> removeTransformer(ViewPager2.PageTransformer) | Remvoe page transformer | supported v3.0.0 |
| BannerViewPager<T, VH> setCurrentItem(int) | Set the currently selected page. | supported v2.3.5|
| int getCurrentItem() | return current position | Supported v2.3.5 |
| BannerViewPager<T, VH> setPageStyle(PageStyle) | set page style for BVP | supported v2.4.0 enum(MULTI_PAGE_SCALE;MULTI_PAGE_OVERLAP)|
| BannerViewPager<T, VH> setPageMargin(int) |  | supported v2.4.0 |
| BannerViewPager<T, VH> setIndicatorMargin(int left, int top, int right, int bottom) | set margin for indicator | supported v2.4.1 |
| BannerViewPager<T, VH> registerOnPageChangeCallback(OnPageChangeListener) | Set a callback that will be invoked whenever the page changes or is incrementally scrolled. |  |
| BannerViewPager<T, VH> setRoundRect(int) | set round corner for BVP| Require SDK_INT>=LOLLIPOP |
| BannerViewPager<T, VH> setOrientation(int) | Sets the orientation of the BVP| support in v3.0.0 {@link #ORIENTATION_HORIZONTAL} or {@link #ORIENTATION_VERTICAL} |
| BannerViewPager<T, VH> setUserInputEnabled(int) | Enable or disable user initiated scrolling | |
| BannerViewPager<T, VH> setLifecycleRegistry(Lifecycle) | Add lifecycle for BVP, | |
| void startLoop() |start loop | Don't need call this method while init BVP |
| void stopLoop() | Stop loop | |
| List\<T> getData() | return data in BVP |  |
| void create(List<T> list) |Create BannerViewPager with data  | If data has fetched while you setup BannerViewPager,you can call this method |
| void create() |Create BannerViewPager with no data  | You can call this，If fetching data asynchronously(for example,The data is from remote server or database).While fetch data successfully,just need call refreshData() method to refresh |、
| void refreshData(List<T>) | Refresh if data is update  | Supported in v3.1.0 |
### Attributes

| Attributes | format | description |
|--|--|--|
| bvp_interval | integer | set the interval of item switch |
| bvp_scroll_duration | integer | set item scroll duration|
| bvp_can_loop | boolean| set BVP can cycle  |
| bvp_auto_play | boolean | set BVP can auto play  |
| bvp_indicator_checked_color | color | set checked color for indicator slider |
| bvp_indicator_normal_color | color | set normal color for indicator slider |
| bvp_indicator_radius | dimension | if it's circle style the value is radius of circle,if the indicator style is DASH or ROUND_RECT the value is width/2   |
| bvp_round_corner| dimension  | set round corner for BVP |
| bvp_page_margin | dimension | set item margin |
| bvp_reveal_width | dimension | it's only used when the page style is MULTI_PAGE_SCALE/MULTI_PAGE_OVERLAP,the value is two side item reveal width  |
| bvp_indicator_style | enum | indicator style(circle/dash/round_rect)  |
| bvp_indicator_slide_mode | enum | indicator slide mode(normal;smooth;worm;color;scale) |
| bvp_indicator_gravity | enum | indicator gravity(center/start/end) |
| bvp_page_style | enum | page style(normal/multi_page/multi_page_overlap/multi_page_scale) |
| bvp_indicator_visibility| enum | indicator visibility(visible/gone/invisible) |


## Usage

Because Viewpager2 is not supported in android.support library, So BannerViewPager V3.0 is no longer support android support.library. If you are still using android.support library, please use [BannerViewPager v2.x](https://github.com/zhpanvip/BannerViewPager/tree/v_2.x)

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
 **Attention:The layout width and height required "match_parent".Otherwise,the ViewPager2 will throws a IllegalStateException.**
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

### 4.Extends BaseViewHolder,and override methods.

 **Attention:Don't use {@link RecyclerView.ViewHolder#getAdapterPosition} method to get position in ViewHolder，this method will return a fake position.**

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
**If you need get position with getAdapterPosition() method,you can do like the following code to get real position:**

```
     int adapterPosition = getAdapterPosition();
     int realPosition = BannerUtils.getRealPosition(isCanLoop, adapterPosition, mList.size());
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

  You can call create() method with no parameter，If fetching data asynchronously(for example,The data is from remote server or database):

 #### Java code

```
    private BannerViewPager<CustomBean, NetViewHolder> mViewPager;
    ...
	private void setupViewPager() {
             mViewPager = findViewById(R.id.banner_view);
             mViewPager
                       .setAutoPlay(true)
                       .setScrollDuration(800)
                       .setLifecycleRegistry(getLifecycle())
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

  While fetch data successfully,just need call refreshData() method to refresh:

  ```
      mViewPager.refreshData(data)
  ```


If fetching data synchronously，you can call create(List<T>) method with parameters.

 ```
      private lateinit var mViewPager: BannerViewPager<CustomBean, NetViewHolder>
      ...

      private fun setupViewPager() {
              mViewPager = findViewById(R.id.banner_view)
              mViewPager.apply {
                  adapter = HomeAdapter()
                  setAutoPlay(true)
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
              }.create(data)
          }

  ```


### 6.startLoop and stopLoop

***You can user setLifecycleRegistry(Lifecycle) method to instead of call stopLoop and startLoop in Activity or Fragment***

```

    mViewPager.setLifecycleRegistry(getLifecycle())

    // the follwoing code is deprecated.

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

### 7.Advanced Features---Custom IndicatorView

The example will implement an custom IndicatorView as the follow gif.

| Custom IndicatorView Style|
|--|
| ![NORMAL](https://github.com/zhpanvip/Resource/blob/master/image/banner/style_custum.gif) |

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
    indicatorView.setTextSize(getResources().getDimensionPixelSize(R.dimen.sp_13));
    indicatorView.setBackgroundColor(Color.parseColor("#aa118EEA"));

    mViewPager.setAutoPlay(false).setCanLoop(true)
               .setIndicatorSlideMode(IndicatorSlideMode.NORMAL)
               .setIndicatorVisibility(View.VISIBLE)
               .setIndicatorGravity(IndicatorGravity.END)
               .setIndicatorView(indicatorView).create(getPicList(4));
```

## 8. Proguard

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
 
![QQ交流群60902509](https://github.com/zhpanvip/Resource/blob/master/image/group/qq_group.png)

## <span id="Sponsor"> Donation </span>

**如果您觉得BVP库还不错，您可以对BVP打赏哦，您的支持将是我持续维护的动力。**

| AliPay | WeChat |
|--|--|
| ![AliPay](https://github.com/zhpanvip/Resource/blob/master/image/pay/pay_alipay.jpg) |  ![WeChat](https://github.com/zhpanvip/Resource/blob/master/image/pay/pay_wechat.png) |


## Thanks

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






