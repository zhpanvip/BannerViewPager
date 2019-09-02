

## 效果预览

**1.基础功能**

| 嵌套RecyclerView | 自定义页面 | 嵌套PhotoView   |
|--|--|--|
| ![嵌套RecyclerView](https://github.com/zhpanvip/BannerViewPager/blob/master/image/preview1.gif) | ![自定义页面](https://github.com/zhpanvip/BannerViewPager/blob/master/image/preview2.gif) | ![嵌套PhotoView](https://github.com/zhpanvip/BannerViewPager/blob/master/image/preview3.gif)   |

**2.自定义Indicator滑动样式**

| NORMAL | SMOOTH |
|--|--|
| ![NORMAL](https://github.com/zhpanvip/BannerViewPager/blob/master/image/slide_normal.gif) |  ![SMOOTH](https://github.com/zhpanvip/BannerViewPager/blob/master/image/slide_smooth.gif) |

**3.内置Transform样式**

| 参数 | STACK | ROTATE | DEPTH | ACCORDION |
|--|--|--|--|--|
| 预览 | ![STACK](https://github.com/zhpanvip/BannerViewPager/blob/master/image/transform_stack.gif) | ![ROTATE_DOWN](https://github.com/zhpanvip/BannerViewPager/blob/master/image/transform_rotate.gif) | ![DEPTH](https://github.com/zhpanvip/BannerViewPager/blob/master/image/transform_depth.gif)  |![ACCORDION](https://github.com/zhpanvip/BannerViewPager/blob/master/image/transform_accordion.gif)  |



## 开放API

| 方法名 | 方法描述 | 说明 |
|--|--|--|
| BannerViewPager<T, VH> setInterval(int interval) | 自动轮播事件间隔 |单位毫秒，默认值3000  |
| BannerViewPager<T, VH> setCanLoop(boolean canLoop) | 是否可以循环 | 默认值true|
| BannerViewPager<T, VH> setAutoPlay(boolean autoPlay) | 是否开启自动轮播 | 默认值true|
| BannerViewPager<T, VH> setRoundCorner(@DimenRes int radius) | 设置圆角 |默认无圆角 需要SDK_INT>=LOLLIPOP(API 21)  |
| BannerViewPager<T, VH> setRoundCorner(float radiusDp) | 设置圆角 | 单位dp,默认无圆角 需要SDK_INT>=LOLLIPOP(API 21)|
| BannerViewPager<T, VH> showIndicator(boolean showIndicator) |  是否显示指示器|默认值true  |
| BannerViewPager<T, VH> setIndicatorGravity(int gravity) | 指示器位置 |可选值(Center、Start、End)默认值Center |
| BannerViewPager<T, VH> setIndicatorColor(int normalColor,int checkedColor) | 指示器圆点颜色 |normalColor：未选中时颜色默认"#000000"， checkedColor：选中时颜色 默认"#FFFFFF" |
|BannerViewPager<T, VH> setIndicatorSlideMode  | 设置Indicator滑动模式 | V2.2.2新增，可选（NORMAL、SMOOTH），默认值SMOOTH  |
| BannerViewPager<T, VH> setIndicatorRadius(float radiusDp) | 设置指示器圆点半径 | 单位dp 默认值4dp|
| BannerViewPager<T, VH> setIndicatorRadius(@DimenRes int radiusRes) | 设置指示器圆点半径| DimenRes资源 默认值4dp|
| BannerViewPager<T, VH> setIndicatorRadius(float normalRadius, float checkRadius) | 设置指示器圆点半径 |单位dp normalRadius:未选中时半径  checkedRadius:选中时的半径 |
|BannerViewPager<T, VH> setIndicatorRadius(@DimenRes int normalRadius, @DimenRes int checkRadius)  |设置指示器圆点半径  |  normalRadius:未选中时半径  checkedRadius:选中时的半径 |
| BannerViewPager<T, VH> setIndicatorMargin(float indicatorMarginDp) | 指示器圆点间距| 单位dp  默认值圆点直径|
| BannerViewPager<T, VH> setIndicatorMargin(@DimenRes int marginRes) | 指示器圆点间距| DimenRes 默认值圆点直径|
| BannerViewPager<T, VH> setCurrentItem(final int position)  |  切换到第position个页面|  |
| BannerViewPager<T, VH> setCurrentItem(final int position, final boolean smoothScroll) | 平滑切换到第position个页面 |  |
| BannerViewPager<T, VH> setHolderCreator(HolderCreator<VH> holderCreator) |设置HolderCreator  |必须设置HolderCreator，否则会抛出NullPointerException|
| BannerViewPager<T, VH> setPageTransformer(ViewPager.PageTransformer transformer) |设置transformer  |V2.1.2新增  |
| BannerViewPager<T, VH> setPageTransformerStyle(TransformerStyle style) |内置transformer样式  |V2.1.2新增 可选参数（DEPTH, ROTATE_DOWN, STACK, ACCORDION）  |
| void startLoop() |开启自动轮播 | 初始化BannerViewPager时不必调用该方法,设置setAutoPlay后会调用startLoop() |
| void stopLoop() | 停止自动轮播 |  |
| void create(List<T> list) |初始化并构造BannerViewPager  |必须调用，否则前面设置的参数无效  |


## 如何使用

  **1.gradle中添加依赖**
   
latestVersion is: [ ![latestVersion](https://api.bintray.com/packages/zhpanvip/CircleViewPager/bannerview/images/download.svg) ](https://bintray.com/zhpanvip/CircleViewPager/bannerview/_latestVersion)

如果您已迁移到AndroidX请使用2.2.0以上版本
```
implementation 'com.zhpan.library:bannerview:latestVersion'

```
如果未迁移到AndroidX请使用：
```
implementation 'com.zhpan.library:bannerview:2.1.4'
```

  **2.在xml文件中添加如下代码：**

```
    <com.zhpan.bannerview.BannerViewPager
            android:id="@+id/banner_view"
            android:layout_width="match_parent"
            android:layout_margin="10dp"
            android:layout_height="160dp" />
```

**3.自定义ViewHolder**

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

 **4.BannerViewPager参数配置**

```
    private BannerViewPager<BannerData, NetViewHolder> mBannerViewPager;
    ...
	private void initViewPager() {
             mBannerViewPager = findViewById(R.id.banner_view);
             mBannerViewPager.showIndicator(true)
                            .setInterval(3000)
                            .setRoundCorner(7f)
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

**5.开启与停止轮播**

如果开启了自动轮播功能，请务必在onDestroy中停止轮播，以免出现内存泄漏。

```
	@Override
    protected void onDestroy() {
        super.onDestroy();
        if (mBannerViewPager != null)
    		mViewpager.stopLoop();
    }
```
为了节省性能也可以在onPause中停止轮播，在onResume中开启轮播：

```
    @Override
    protected void onPause() {
        super.onPause();
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

## TODO 版本计划

~~（1）优化及重构IndicatorView~~（2.0.1）

~~（2）修复2.1.0以前版本循环滑动时第一张切换卡顿问题~~ （2.1.0.1）

~~（3）增加页面滑动动画~~（2.1.2）

~~（4）迁移AndroidX~~（2.2.0）

~~（5）增加IndicatorView的滑动样式~~（2.2.2）

 （6）增添更多Indicator滑动模式（2.3.+）

 （7）ViewPager更换为ViewPager2 （3.0.0）

 （8）如有问题欢迎提issue，该库会持续更新优化。


## 感谢

[banner](https://github.com/youth5201314/banner)

[Android-ConvenientBanner](https://github.com/saiwu-bigkoo/Android-ConvenientBanner)

[ViewPagerTransforms](https://github.com/ToxicBakery/ViewPagerTransforms)



[更多详情请点击此处](http://blog.csdn.net/qq_20521573/article/details/52037929)



