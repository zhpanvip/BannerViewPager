

## 效果预览

| 嵌套RecyclerView | 自定义页面 | 嵌套PhotoView   |
|--|--|--|
| ![嵌套RecyclerView](https://github.com/zhpanvip/BannerViewPager/blob/master/image/preview1.gif) | ![自定义页面](https://github.com/zhpanvip/BannerViewPager/blob/master/image/preview2.gif) | ![嵌套PhotoView](https://github.com/zhpanvip/BannerViewPager/blob/master/image/preview3.gif)   |
## 开放API

| 方法名 | 方法描述 | 说明 |
|--|--|--|
| BannerViewPager<T, VH> setInterval(int interval) | 自动轮播事件间隔 |单位毫秒，默认值3000  |
| BannerViewPager<T, VH> setCanLoop(boolean canLoop) | 是否可以循环 |  默认值true|
| BannerViewPager<T, VH> setRoundCorner(@DimenRes int radius) | 设置圆角 |默认无圆角 需要SDK_INT>=LOLLIPOP(21)  |
| BannerViewPager<T, VH> setRoundCorner(float radiusDp) | 设置圆角 | 单位dp,默认无圆角 需要SDK_INT>=LOLLIPOP(21)|
| BannerViewPager<T, VH> showIndicator(boolean showIndicator) |  是否显示指示器|默认值true  |
| BannerViewPager<T, VH> setIndicatorGravity(int gravity) | 指示器位置 |可选值(Center、Start、End)默认值Center |
| BannerViewPager<T, VH> setIndicatorColor(int normalColor,int checkedColor) | 指示器圆点颜色 |normalColor：未选中时颜色默认"#000000"， checkedColor：选中时颜色 默认"#FFFFFF" |
| BannerViewPager<T, VH> setIndicatorRadius(float radiusDp) | 指示器圆点半径 | 单位dp 默认值4dp|
| BannerViewPager<T, VH> setIndicatorRadius(@DimenRes int radiusRes) | 指示器圆点半径| DimenRes资源 默认值4dp|
| BannerViewPager<T, VH> setIndicatorMargin(float indicatorMarginDp) | 指示器圆点间距| 单位dp  默认值圆点直径|
| BannerViewPager<T, VH> setIndicatorMargin(@DimenRes int marginRes) | 指示器圆点间距| DimenRes 默认值圆点直径|
| BannerViewPager<T, VH> setCurrentItem(final int position)  |  切换到第position个页面|  |
| BannerViewPager<T, VH> setCurrentItem(final int position, final boolean smoothScroll) | 平滑切换到第position个页面 |  |
| BannerViewPager<T, VH> setHolderCreator(HolderCreator<VH> holderCreator) |设置HolderCreator  |必须设置HolderCreator，否则会抛出RuntimeException  |
| BannerViewPager<T, VH> setPageTransformer(ViewPager.PageTransformer transformer) |设置transformer  |2.1.2新增  |
| BannerViewPager<T, VH> setPageTransformerStyle(TransformerStyle style) |内置transformer样式  |2.1.2新增 可选参数（DEPTH, ROTATE_DOWN, STACK, ACCORDION）  |
| void startLoop() |开启自动轮播  |  |
| void stopLoop() | 停止自动轮播 |  |
| void create(List<T> list) |初始化并构造BannerViewPager  |必须调用，否则前面设置的参数无效  |

Transform内置样式

| 参数 | STACK | ROTATE_DOWN | DEPTH | ACCORDION |
|--|--|--|--|--|
| 预览 | ![STACK](https://github.com/zhpanvip/BannerViewPager/blob/master/image/stack.gif) | ![ROTATE_DOWN](https://github.com/zhpanvip/BannerViewPager/blob/master/image/rotate_down.gif) | ![DEPTH](https://github.com/zhpanvip/BannerViewPager/blob/master/image/depth.gif)  |![ACCORDION](https://github.com/zhpanvip/BannerViewPager/blob/master/image/accordion.gif)  |


## 如何使用

   **gradle中添加依赖**
   
latestVersion is: [ ![latestVersion](https://api.bintray.com/packages/zhpanvip/CircleViewPager/bannerview/images/download.svg) ](https://bintray.com/zhpanvip/CircleViewPager/bannerview/_latestVersion)

如果您已迁移到AndroidX请使用2.2.0及以上版本
```
implementation 'com.zhpan.library:bannerview:latestVersion'

```
如果未迁移到AndroidX请使用：
```
implementation 'com.zhpan.library:bannerview:2.1.3'
```

  **在xml文件中添加如下代码：**

```
    <com.zhpan.bannerview.BannerViewPager
            android:id="@+id/banner_view"
            android:layout_width="match_parent"
            android:layout_margin="10dp"
            android:layout_height="160dp" />
```

 **BannerViewPager参数配置**

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

**自定义ViewHolder** 
  
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
**如果开启自动轮播，请在onDestroy()中停止图片轮播，以免内存泄漏**
```
	@Override
    protected void onDestroy() {
    	mViewpager.stopLoop();
        super.onDestroy();
    }
```

## TODO 接下来的版本计划

~~（1）优化及重构IndicatorView~~（2.0.1）

~~（2）修复2.1.0以前版本循环滑动时第一张切换卡顿问题~~ （2.1.0.1）

~~（3）增加页面滑动动画。~~（2.1.2）

~~（4）迁移AndroidX~~（2.2.0）

（5）增加IndicatorView的滑动样式。

（6）ViewPager更换为ViewPager2

（7）如有问题欢迎提issue，该库会持续更新优化。


[详情请点击此处](http://blog.csdn.net/qq_20521573/article/details/52037929)
