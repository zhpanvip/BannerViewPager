# CircleViewPager
无限循环轮播的ViewPager

![这里写图片描述](https://github.com/zhpanvip/CircleViewPager/blob/master/app/src/main/res/ezgif-3-b826de74b0.gif)

1.gradle中添加依赖
```
compile 'com.zhpan.library:viewpager:1.0.5'
```

2.在xml文件中添加如下代码：
```
<com.zhpan.viewpager.view.CircleViewPager
        android:id="@+id/viewpager"
        android:layout_width="match_parent"
        android:layout_height="150dp"
        app:interval="5000" />

    <com.zhpan.viewpager.view.CircleViewPager
        android:id="@+id/viewpager2"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:layout_marginEnd="35dp"
        android:layout_marginStart="35dp"
        app:interval="5000" />
```
3.CircleViewPager属性

```
        //  设置指示器位置
        mViewpager.setIndicatorGravity(CircleViewPager.IndicatorGravity.END);
        //  是否显示指示器
        mViewpager.isShowIndicator(true);
        //  设置图片切换时间间隔
        mViewpager.setInterval(3000);
        //  设置是否无限循环
        mViewpager.setCanLoop(true);
        //  设置是否自动轮播
        mViewpager.setAutoPlay(true);
        //  设置指示器圆点半径
        mViewpager.setIndicatorRadius(6);
	
        //  设置页面点击事件
        mViewpager.setOnPageClickListener(new CircleViewPager.OnPageClickListener() {
            @Override
            public void onPageClick(int position) {
                List<DataBean> list = mViewpager.getList();
                Toast.makeText(MainActivity.this, "点击了" + list.get(position).getDescribe(), Toast.LENGTH_SHORT).show();
            }
        });
        //  设置数据
        mViewpager.setPages(mList, new HolderCreator<ViewHolder>() {
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
