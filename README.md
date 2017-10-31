# CircleViewPager
无限循环轮播的ViewPager


![这里写图片描述](http://img.blog.csdn.net/20160726200103033)

xml中添加
```
<com.example.viewpager.view.CircleViewPager
        android:id="@+id/viewpager"
        android:layout_width="match_parent"
        android:layout_height="150dp"
        app:lightDotRes="@drawable/red_dot"
        app:darkDotRes="@drawable/red_dot_night"
        app:interval="5000"/>

    <com.example.viewpager.view.CircleViewPager
        android:id="@+id/viewpager2"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        app:lightDotRes="@drawable/red_dot"
        android:layout_marginStart="35dp"
        android:layout_marginEnd="35dp"
        app:darkDotRes="@drawable/red_dot_night"
        app:interval="5000"/>
```
Activity中


1.加载网路图片
```
        private void initView() {
                //    设置为选中时候的指示器
                mViewpager.setDarkIndicator(R.drawable.red_dot_night);
                //    设置选中时的指示器
                mViewpager.setLightIndicator(R.drawable.red_dot);
                //    设置指示器的半径大小
                mViewpager.setDotWidth(7);
                //    设置切换图片时间间隔
                mViewpager.setInterval(5000);
                //    设置页面点击事件
                mViewpager.setOnPageClickListener(new CircleViewPager.OnPageClickListener() {
                    @Override
                    public void onPageClick(int position) {
                        List list = mViewpager.getList();
                        Toast.makeText(MainActivity.this, "点击了第" + position + "个图片 \nURL:" +list.get(position), Toast.LENGTH_SHORT).show();
                    }
                });
            }
                //    设置数据和页面
                mViewpager.setPages(mList, new HolderCreator() {
                    @Override
                    public ViewHolder createViewHolder() {
                        return new MyViewHolder();
                    }
                });

                
 ```
 2.加载本地图片
 ```
         //     初始化本地图片集合
         for (int i = 1; i <= 5; i++) {
                    int drawable = getResources().getIdentifier("a" + i, "drawable", getPackageName());
                    mListInt.add(drawable);
                }
         mViewPager2.isShowIndicator(false);
                mViewPager2.setPages(mListInt, new HolderCreator() {
                    @Override
                    public ViewHolder createViewHolder() {

                        return new MyViewHolder();
                    }
                });
        
 ```
 3.自定义ViewHolder 
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
        mViewPager2.stopCircleViewPager();
        mViewpager.stopCircleViewPager();
    }
```


[详情请点击此处](http://blog.csdn.net/qq_20521573/article/details/52037929)
