# 全新2.0.0版本发布，正式改名为BannerViewPager.


![这里写图片描述](https://github.com/zhpanvip/BannerViewPager/blob/v_2.0.0/image/ezgif-4-f4cd74cd939d.gif)

1.gradle中添加依赖
```
implementation 'com.zhpan.library:bannerview:2.0.0'
```

2.在xml文件中添加如下代码：
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
3.BannerViewPager属性
Banner支持圆角s
```
	
	2.0.0新增Api
	// Banner支持圆角
	mViewPager.setRoundCorner(R.dimen.banner_corner)
	// 新增页面滑动时间
	mViewPager.setScrollDuration(1000)
	
	
	
	
	//  是否显示指示器
        mViewpager.isShowIndicator(true);
        //  设置指示器位置
        mViewpager.setIndicatorGravity(CircleViewPager.IndicatorGravity.END);
	 //  设置指示器圆点半径
        mViewpager.setIndicatorRadius(6);
	// 设置指示器颜色
	mViewPager.setIndicatorColor(Color.parseColor("#6C6D72"),
                Color.parseColor("#FFFFFF"));
	
        
        //  设置图片切换时间间隔
        mViewpager.setInterval(3000);
        //  设置是否无限循环
        mViewpager.setCanLoop(true);
        //  设置是否自动轮播
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
        mViewpager.setData(mList, new HolderCreator<ViewHolder>() {
            @Override
            public ViewHolder createViewHolder() {
                return new MyViewHolder();
            }
        });
```

 4.自定义ViewHolder 
  
  

```
public class MyViewHolder implements ViewHolder<Object> {
            private ImageView mImageView;

            @Override
            public View createView(Context context) {
                // 返回页面布局文件
                View view = LayoutInflater.from(context).inflate(R.layout.banner_item, null);
                mImageView = (ImageView) view.findViewById(R.id.banner_image);
                return view;
            }

            @Override
            public void onBind(Context context, int position, Object data) {
                // 数据绑定
                if (data instanceof Integer)
                    mImageView.setImageResource((Integer) data);
                else if (data instanceof String) {
                    ImageLoaderUtil.loadImg(mImageView, (String) data);
                }
            }
        }
```

5.为防止内存泄露在onDestory()中停止图片轮播
```
	@Override
    protected void onDestroy() {
        super.onDestroy();
        mViewPager2.stopLoop();
        mViewpager.stopLoop();
    }
```


[详情请点击此处](http://blog.csdn.net/qq_20521573/article/details/52037929)
