package com.zhpan.bannerview.transform;

import android.support.v4.view.ViewPager;

public class PageTransformerFactory {

    public ViewPager.PageTransformer createPageTransformer(TransformerStyle transformerStyle) {
        ViewPager.PageTransformer transformer = null;
        switch (transformerStyle) {
            case CUBES:
//                transformer = new CubeInTransformer();
                break;
            case ZOOMIN:
                transformer = new ZoomOutSlideTransformer();
                break;
            case CARD_STACK:
                transformer = new CubeOutTransformer();
                break;
            case DEPTH_CARD:
                transformer = new DepthPageTransformer();
                break;
            case BOOK_FLIE_FADE:
                transformer = new BackgroundToForegroundTransformer();
                break;
            case CASCADE_ZOOM:
                transformer = new RotateDownTransformer();
                break;
        }
        return transformer;
    }
}
