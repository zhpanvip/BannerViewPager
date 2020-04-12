package com.example.zhpan.circleviewpager.net;

import android.net.Uri;

/**
 * <pre>
 *   Created by zhangpan on 2019-08-14.
 *   Description:
 * </pre>
 */
public class BannerData {

    /**
     * desc :
     * id : 20
     * imagePath : https://www.wanandroid.com/blogimgs/90c6cc12-742e-4c9f-b318-b912f163b8d0.png
     * isVisible : 1
     * order : 2
     * title : flutter 中文社区
     * type : 1
     * url : https://flutter.cn/
     */

    public static final int TYPE_NEW = 10000;

    private String desc;
    private int id;
    private String imagePath;
    private int isVisible;
    private int order;
    private String title;
    private int type;
    private String url;
    private int drawable;

    public int getDrawable() {
        return drawable;
    }

    public void setDrawable(int drawable) {
        this.drawable = drawable;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public int getIsVisible() {
        return isVisible;
    }

    public void setIsVisible(int isVisible) {
        this.isVisible = isVisible;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
