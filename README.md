

# 效果预览

## 1.基础功能

| 嵌套RecyclerView | 自定义页面 | 嵌套PhotoView   |
|--|--|--|
| ![嵌套RecyclerView](https://github.com/zhpanvip/BannerViewPager/blob/master/image/preview1.gif) | ![自定义页面](https://github.com/zhpanvip/BannerViewPager/blob/master/image/preview2.gif) | ![嵌套PhotoView](https://github.com/zhpanvip/BannerViewPager/blob/master/image/preview3.gif)   |

## 2.自定义Indicator样式

| NORMAL | SMOOTH |
|--|--|
| ![NORMAL](https://github.com/zhpanvip/BannerViewPager/blob/master/image/slide_normal.gif) |  ![SMOOTH](https://github.com/zhpanvip/BannerViewPager/blob/master/image/slide_smooth.gif) |

## 3.自定义IndicatorView
如果以上样式不能满足你的需求，BannerViewPager还提供了完全自定义IndicatorView的功能。只要继承BaseIndicatorView或者实现IIndicator接口，并重写相应方法，就可以为所欲为的打造任意的Indicator了。下图是一个自定义仿支付宝的Indicator：

| Custom Indicator |
|--|
| ![NORMAL](https://github.com/zhpanvip/BannerViewPager/blob/v_2.3.1/image/custom_indicator.gif) |

## 4.内置Transform样式

| 参数 | STACK | ROTATE | DEPTH | ACCORDION |
|--|--|--|--|--|
| 预览 | ![STACK](https://github.com/zhpanvip/BannerViewPager/blob/master/image/transform_stack.gif) | ![ROTATE_DOWN](https://github.com/zhpanvip/BannerViewPager/blob/master/image/transform_rotate.gif) | ![DEPTH](https://github.com/zhpanvip/BannerViewPager/blob/master/image/transform_depth.gif)  |![ACCORDION](https://github.com/zhpanvip/BannerViewPager/blob/master/image/transform_accordion.gif)  |



# 开放API

| 方法名 | 方法描述 | 说明 |
|--|--|--|
| BannerViewPager<T, VH> setCanLoop(boolean canLoop) | 是否开启循环 | 默认值true|
| BannerViewPager<T, VH> setAutoPlay(boolean autoPlay) | 是否开启自动轮播 | 默认值true|
| BannerViewPager<T, VH> setInterval(int interval) | 自动轮播时间间隔 |单位毫秒，默认值3000  |
| BannerViewPager<T, VH> setScrollDuration(int scrollDuration) | 设置页面滚动时间 | 设置页面滚动时间 |单位毫秒，默认值800  |
| BannerViewPager<T, VH> setRoundCorner(int radius) | 设置圆角 |默认无圆角 需要SDK_INT>=LOLLIPOP(API 21)  |
| BannerViewPager<T, VH> setOnPageClickListener(OnPageClickListener onPageClickListener) | 设置页面点击事件 |  |
| BannerViewPager<T, VH> setHolderCreator(HolderCreator\<VH> holderCreator) |设置HolderCreator  |必须设置HolderCreator，否则会抛出NullPointerException|
| BannerViewPager<T, VH> showIndicator(boolean showIndicator) |  是否显示指示器|默认值true  |
| BannerViewPager<T, VH> setIndicatorStyle(IndicatorStyle indicatorStyle) | 设置指示器样式 | 可选枚举(CIRCLE, DASH) 默认CIRCLE  |
| BannerViewPager<T, VH> setIndicatorGravity(int gravity) | 指示器位置 |可选值(CENTER、START、END)默认值CENTER |
| BannerViewPager<T, VH> setIndicatorColor(int normalColor,int checkedColor) | 指示器圆点颜色 |normalColor：未选中时颜色默认"#8C6C6D72"， checkedColor：选中时颜色 默认"#8C18171C" |
| BannerViewPager<T, VH> setIndicatorSlideMode(IndicatorSlideMode slideMode)  | 设置Indicator滑动模式 | 可选（NORMAL、SMOOTH），默认值SMOOTH  |
| BannerViewPager<T, VH> setIndicatorRadius(int radius) | 设置指示器圆点半径 | 默认值4dp|
| BannerViewPager<T, VH> setIndicatorRadius(int normalRadius,int checkRadius)  |设置指示器圆点半径  |  normalRadius:未选中时半径  checkedRadius:选中时的半径,默认值4dp |
| BannerViewPager<T, VH> setIndicatorWidth(int indicatorWidth) | 设置指示器宽度，如果是圆形指示器，则为直径 |  默认值8dp|
| BannerViewPager<T, VH> setIndicatorWidth(int normalWidth, int checkWidth) | 设置指示器宽度，如果是圆形指示器，则为直径 | 默认值8dp |
| BannerViewPager<T, VH> setIndicatorHeight(int indicatorHeight) | 设置指示器高度，仅在Indicator样式为DASH时有效 | 默认值normalIndicatorWidth/2 |
| BannerViewPager<T, VH> setIndicatorGap(int indicatorMargin) | 指示器圆点间距| 默认值为指示器宽度（或者是圆的直径）|
| BannerViewPager<T, VH> setIndicatorView(IIndicator indicatorView) | 设置自定义指示器| 设置自定义指示器后以上关于IndicatorView的参数会部分失效|
| BannerViewPager<T, VH> setPageTransformerStyle(TransformerStyle style) | 设置页面Transformer内置样式 |  |
| void startLoop() |开启自动轮播 | 初始化BannerViewPager时不必调用该方法,设置setAutoPlay后会调用startLoop() |
| void stopLoop() | 停止自动轮播 | 如果开启自动轮播，为避免内存泄漏需要在onStop()或onDestory中调用此方法 |
| ViewPager getViewPager() | 获取BannerViewPager内部封装的ViewPager |  |
| List\<T> getList() | 获取Banner中的集合数据 |  |
| void create(List<T> list) |初始化并构造BannerViewPager  |必须调用，否则前面设置的参数无效  |



# 如何使用

## 1.gradle中添加依赖
   
latestVersion is: [ ![latestVersion](https://api.bintray.com/packages/zhpanvip/CircleViewPager/bannerview/images/download.svg) ](https://bintray.com/zhpanvip/CircleViewPager/bannerview/_latestVersion)

如果您已迁移到AndroidX请使用latestVersion(>=2.3.0)
```
implementation 'com.zhpan.library:bannerview:latestVersion'

```
如果未迁移到AndroidX请使用：
```
implementation 'com.zhpan.library:bannerview:2.2.6'
```

## 2.在xml文件中添加如下代码：

```
    <com.zhpan.bannerview.BannerViewPager
            android:id="@+id/banner_view"
            android:layout_width="match_parent"
            android:layout_margin="10dp"
            android:layout_height="160dp" />
```

## 3.Banner的页面布局

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

## 4.自定义ViewHolder

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

## 5.BannerViewPager参数配置

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
                .setIndicatorGravity(BannerViewPager.END)
                .setScrollDuration(1000).setHolderCreator(NetViewHolder::new)
                .setOnPageClickListener(position -> {
                    BannerData bannerData = mBannerViewPager.getList().get(position);
                    Toast.makeText(NetworkBannerActivity.this,
                            "点击了图片" + position + " " + bannerData.getDesc(), Toast.LENGTH_SHORT).show();

                }).create(mList);
        }
```

## 6.开启与停止轮播

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
## 7.高级功能---自定义IndicatorView

**(1)自定义View并继承BaseIndicatorView**

```
public class DashIndicatorView extends BaseIndicatorView {
    private Paint mPaint;
    private float sliderHeight;

   ...内部省略部分代码

    public DashIndicatorView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mPaint = new Paint();
        mPaint.setColor(normalColor);
        mPaint.setAntiAlias(true);
        sliderHeight = normalIndicatorWidth / 2;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension((int) ((mPageSize - 1) *mIndicatorGap  + normalIndicatorWidth * mPageSize),
                (int) (sliderHeight));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPaint.setColor(normalColor);
        for (int i = 0; i < mPageSize; i++) {
            mPaint.setColor(normalColor);
            canvas.drawRect(i * (normalIndicatorWidth) + i * +mIndicatorGap, 0, i * (normalIndicatorWidth) + i * +mIndicatorGap + normalIndicatorWidth, sliderHeight, mPaint);
        }
        drawSliderStyle(canvas);
    }

    @Override
    public void onPageSelected(int position) {
        super.onPageSelected(position);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        super.onPageScrolled(position, positionOffset, positionOffsetPixels);
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        super.onPageScrollStateChanged(state);
    }

    private void drawSliderStyle(Canvas canvas) {
        mPaint.setColor(checkedColor);
        float left = currentPosition * (checkedIndicatorWidth) + currentPosition * +mIndicatorGap + (checkedIndicatorWidth + mIndicatorGap) * slideProgress;
        canvas.drawRect(left, 0, left + checkedIndicatorWidth, sliderHeight, mPaint);
    }

}
```
**(2)BannerViewPager设置自定义Indicator**

```
 private void setUpViewPager() {
        viewPager = findViewById(R.id.banner_view);
        List<String> list = Arrays.asList(picUrls);
        viewPager.setAutoPlay(false).setCanLoop(true)
                .setRoundCorner(DpUtils.dp2px(5))
                .setIndicatorView(setupIndicatorView(list.size()))
                .setHolderCreator(SlideModeViewHolder::new).create(list);
    }

    private DashIndicatorView setupIndicatorView(int pageSize) {
        DashIndicatorView indicatorView = new DashIndicatorView(this);
        indicatorView.setPageSize(pageSize);
        indicatorView.setIndicatorWidth(DpUtils.dp2px(8), DpUtils.dp2px(8));
        indicatorView.setSliderHeight(DpUtils.dp2px(4));
        indicatorView.setIndicatorGap(DpUtils.dp2px(5));
        indicatorView.setCheckedColor(getResources().getColor(R.color.colorAccent));
        indicatorView.setNormalColor(getResources().getColor(R.color.colorPrimary));
        return indicatorView;
    }
```

# TODO 版本计划

 - [x] 优化及重构IndicatorView（2.0.1）

 - [x] 修复2.1.0以前版本循环滑动时第一张切换卡顿问题（2.1.0.1）

 - [x] 增加页面滑动动画（2.1.2）

 - [x] 迁移AndroidX（2.2.0）

 - [x] 增加IndicatorView的滑动样式（2.2.2）

 - [x] 增添更多Indicator样式（2.3.+）
 - [ ]  ViewPager更换为ViewPager2 （3.0.0）
 - [ ] 目前Indicator部分代码比较乱，还有很大很大的优化空间，后续版本将持续优化
 


[更多详情请点击此处](https://juejin.im/post/5d6bce24f265da03db0790d1)

## 感谢

[banner](https://github.com/youth5201314/banner)

[Android-ConvenientBanner](https://github.com/saiwu-bigkoo/Android-ConvenientBanner)

[ViewPagerTransforms](https://github.com/ToxicBakery/ViewPagerTransforms)

[玩Android](https://wanandroid.com/)






