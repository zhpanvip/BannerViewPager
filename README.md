# CircleViewPager
无限轮播的ViewPager

xml中添加
```
<com.example.zhpan.circleviewpager.CircleViewPager
        android:id="@+id/viewpager"
        android:layout_width="match_parent"
        android:layout_height="match_parent"/>
```
Activity中

```
private void initView() {
        mList.add("http://pic.58pic.com/58pic/15/36/01/17C58PICR67_1024.jpg");
        mList.add("http://img.tupianzj.com/uploads/allimg/160822/9-160R2213608.jpg");
        mList.add("http://img1.ph.126.net/3NuwEWzx-efuHLUhoAg1Rw==/1459447754345023507.jpg");
        mList.add("http://pic.58pic.com/58pic/15/36/02/06Q58PICH7S_1024.jpg");
        mList.add("http://images.jfdaily.com/jiefang/life/new/201502/W020150213267781833219.jpg");
        mViewpager = (CircleViewPager) findViewById(R.id.viewpager);
        //mViewpager.setImages(images);
        mViewpager.setDarkDotRes(R.mipmap.ic_launcher);
        mViewpager.setDotWidth(10);
        mViewpager.setInterval(5000);
        mViewpager.setUrlList(mList);
        mViewpager.setOnPageClickListener(new CircleViewPager.OnPageClickListener() {
            @Override
            public void pageClickListener(int position) {
                Toast.makeText(MainActivity.this, mList.get(position), Toast.LENGTH_SHORT).show();
            }
        });
    }
```
