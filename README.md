

## 效果预览

![这里写图片描述](https://github.com/zhpanvip/BannerViewPager/blob/v_2.0.0/image/ezgif-4-f4cd74cd939d.gif)

## 开放API

| 方法名 | 方法描述 | 说明 |
|--|--|--|
| startLoop() |开启自动轮播  |  |
| stopLoop() | 停止自动轮播 |  |
| setInterval(int interval) | 自动轮播事件间隔 |单位毫秒，默认值3000  |
| setCanLoop(boolean canLoop) | 是否可以循环 |  默认值true|
| setRoundCorner(@DimenRes int radius) | 设置圆角 |默认无圆角 需要SDK_INT>=LOLLIPOP(21)  |
| setRoundCorner(float radiusDp) | 设置圆角 | 单位dp,默认无圆角 需要SDK_INT>=LOLLIPOP(21)|
| showIndicator(boolean showIndicator) |  是否显示指示器|默认值true  |
| setIndicatorGravity(int gravity) | 指示器位置（0 Center、1 Start、2 End） |默认值0 Center |
| setIndicatorRadius(float indicatorRadius) | 指示器圆点半径 | 单位dp 默认值4dp|
|setCurrentItem(final int position)  |  切换到第position个页面|  |
| setCurrentItem(final int position, final boolean smoothScroll) | 平滑切换到第position个页面 |  |
| setData(List<T> list) |设置Banner数据  |  |
| setHolderCreator(HolderCreator<VH> holderCreator) |设置HolderCreator  |必须设置HolderCreator，否则会抛出RuntimeException  |
| create() |初始化并构造BannerViewPager  |必须调用，否则前面设置的参数无效  |

## 如何使用

   **gradle中添加依赖**

```
implementation 'com.zhpan.library:bannerview:2.0.0'
```

  **在xml文件中添加如下代码：**

```
    <com.zhpan.bannerview.view.BannerViewPager
            android:id="@+id/viewpager"
            android:layout_width="match_parent"
            android:layout_height="150dp"
            android:layout_margin="10dp" />
```

 **BannerViewPager参数配置**

```
	private void initViewPager() {
            mViewpager = findViewById(R.id.viewpager);
            mViewpager.showIndicator(true)
                    .setInterval(3000)
                    .setRoundCorner(R.dimen.banner_corner)
                    .setScrollDuration(1000)
                    .setData(mDataList)
                    .setHolderCreator(new HolderCreator<DataViewHolder>() {
                        @Override
                        public DataViewHolder createViewHolder() {
                            return new DataViewHolder();
                        }
                    })
                    .setOnPageClickListener(new BannerViewPager.OnPageClickListener() {
                        @Override
                        public void onPageClick(int position) {
                            Toast.makeText(ViewPagerActivity.this, "点击了图片" + position, Toast.LENGTH_SHORT).show();
                        }
                    }).create();
        }
```

**自定义ViewHolder** 
  
```
public class DataViewHolder implements ViewHolder<DataBean> {
    private ImageView mImageView;

    @Override
    public View createView(ViewGroup viewGroup, Context context, int position) {
        // 返回页面布局文件
        View view = LayoutInflater.from(context).inflate(R.layout.item_view, viewGroup, false);
        mImageView = view.findViewById(R.id.banner_image);
        return view;
    }

    @Override
    public void onBind(final Context context, DataBean data, final int position, final int size) {
        ImageLoaderUtil.loadImg(mImageView, data.getUrl(), R.drawable.placeholder);
    }
}
```
**为防止内存泄露在onDestroy()中停止图片轮播**
```
	@Override
    protected void onDestroy() {
    	mViewpager.stopLoop();
        super.onDestroy();
    }
```


## TODO 接下来的版本计划

（1）目前版本循环滑动时会出现偶尔划不动的情况，会在后续版本中修复

（2）优化及重构IndicatorView，增加IndicatorView的滑动样式。

（3）增加页面滑动动画。

（4）如有问题欢迎提issue，该库会持续更新优化。


[详情请点击此处](http://blog.csdn.net/qq_20521573/article/details/52037929)
