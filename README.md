

## 全新2.0.0版本发布，正式改名为BannerViewPager.


![这里写图片描述](https://github.com/zhpanvip/BannerViewPager/blob/v_2.0.0/image/ezgif-4-f4cd74cd939d.gif)

 - **1.gradle中添加依赖**

```
implementation 'com.zhpan.library:bannerview:2.0.0'
```

 - **2.在xml文件中添加如下代码：**

```
    <com.zhpan.bannerview.view.BannerViewPager
        android:id="@+id/viewpager"
        android:layout_width="match_parent"
        android:layout_height="150dp"
        android:layout_margin="10dp"
        app:indicator_gravity="end"
        app:indicator_radius="6dp"
        app:interval="5000" />

    <com.zhpan.bannerview.view.BannerViewPager
        android:id="@+id/viewpager2"
        android:layout_width="match_parent"
        android:layout_height="150dp"
        android:layout_margin="10dp" />
```

 - **3.BannerViewPager开放API**

开放接口
| 方法名 | 方法描述 | 默认值 |
|--|--|--|
| startLoop() |开启自动轮播  |  |
| stopLoop() | 停止自动轮播 |  |
| setInterval(int interval) | 自动轮播事件间隔 |3000  |
| setCanLoop(boolean canLoop) | 是否可以循环 |  true|
| setRoundCorner(@DimenRes int radius) | 设置圆角 |  |
| setRoundCorner(float radius) | 设置圆角 |  |
| isShowIndicator(boolean showIndicator) |  是否显示指示器|true  |
| setIndicatorGravity(int gravity) | 指示器位置（Start、Center、End） | Center |
| setIndicatorRadius(float indicatorRadius) | 指示器圆点半径 |  4|
|setCurrentItem(final int position)  |  设置当前选中的页面|  |
| setCurrentItem(final int position, final boolean smoothScroll) | 设置当前选中的页面 |  |
| setData(List<T> list, HolderCreator<VH> holderCreator) |设置Banner数据  |  |

 - **4.Demo示例**

```
	
	mViewPager.setRoundCorner(R.dimen.banner_corner)
	mViewPager.setScrollDuration(1000)
        mViewpager.isShowIndicator(true); 
        mViewpager.setIndicatorGravity(CircleViewPager.IndicatorGravity.END);
        mViewpager.setIndicatorRadius(6);
	mViewPager.setIndicatorColor(Color.parseColor("#6C6D72"),
                Color.parseColor("#FFFFFF"));
        mViewpager.setInterval(3000);
        mViewpager.setCanLoop(true);
        mViewpager.setAutoPlay(true);
       
	
        //  设置页面点击事件
        mViewpager.setOnPageClickListener(new CircleViewPager.OnPageClickListener() {
            @Override
            public void onPageClick(int position) {
                List<DataBean> list = mViewpager.getList();
                Toast.makeText(MainActivity.this, "点击了" + list.get(position).getDescribe(), Toast.LENGTH_SHORT).show();
            }
        });
        //  设置数据
        mViewpager.setData(mDataList, new HolderCreator<DataViewHolder>() {
            @Override
            public DataViewHolder createViewHolder() {
                return new DataViewHolder();
            }
        });
```

**自定义ViewHolder** 
  
```
public class DataViewHolder implements ViewHolder<DataBean> {
    private ImageView mImageView;
    private TextView mTvDescribe;

    @Override
    public View createView(ViewGroup viewGroup, Context context, int position) {
        // 返回页面布局文件
        View view = LayoutInflater.from(context).inflate(R.layout.item_view, viewGroup, false);
        mImageView = view.findViewById(R.id.banner_image);
        mTvDescribe = view.findViewById(R.id.tv_describe);
        return view;
    }

    @Override
    public void onBind(final Context context, DataBean data, final int position, final int size) {
        final DataBean dataBean = data;
        ImageLoaderUtil.loadImg(mImageView, dataBean.getUrl(), R.drawable.placeholder);
        mTvDescribe.setText(dataBean.getDescribe());
        mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, position + "点击了" + dataBean.getDescribe() + "  页面数" + size, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
```
**为防止内存泄露在onDestory()中停止图片轮播**
```
	@Override
    protected void onDestroy() {
    	mViewpager.stopLoop();
        super.onDestroy();
    }
```


[详情请点击此处](http://blog.csdn.net/qq_20521573/article/details/52037929)
