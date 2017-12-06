# CircleViewPager
无限循环轮播的ViewPager

1.gradle中添加依赖
```
compile 'com.zhpan.library:viewpager:1.0.0'
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
3.加载网路图片

```
        //  设置指示器资源图片
        mViewpager.setIndicator(R.drawable.red_dot,R.drawable.red_dot_night);
        //  设置指示器半径大小（单位dp）
        mViewpager.setDotWidth(8);
        //  设置指示器位置
        mViewpager.setIndicatorGravity(CircleViewPager.IndicatorGravity.END);
        //  是否显示指示器
        mViewpager.isShowIndicator(true);
        //  设置图片切换时间间隔
        mViewpager.setInterval(3000);
        //  设置页面点击事件
        mViewpager.setOnPageClickListener(new CircleViewPager.OnPageClickListener() {
            @Override
            public void onPageClick(int position) {
                List<String> list = mViewpager.getList();
                Toast.makeText(MainActivity.this, "点击了第" + (position+1) + "个图片\n URL: " +list.get(position), Toast.LENGTH_SHORT).show();
            }
        });

        mViewpager.setPages(mList, new HolderCreator<ViewHolder>() {
            @Override
            public ViewHolder createViewHolder() {
                return new MyViewHolder();
            }
        });
```

 .加载本地图片

```
		 //  初始化本地图片集合
         for (int i = 1; i <= 5; i++) {
                    int drawable = getResources().getIdentifier("a" + i, "drawable", getPackageName());
                    mListInt.add(drawable);
                }
         
         mViewPager2.setPages(mListInt, new HolderCreator() {
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
