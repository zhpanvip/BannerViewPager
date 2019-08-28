package com.zhpan.bannerview.transform;

import android.support.v4.view.ViewPager;

public class PageTransformerFactory {

    public ViewPager.PageTransformer createPageTransformer(TransformerStyle transformerStyle) {
        ViewPager.PageTransformer transformer = null;
        switch (transformerStyle) {
            case DEPTH:
                transformer = new DepthPageTransformer();
                break;
            case ROTATE_DOWN:
                transformer=new RotateUpTransformer();
                break;
            case STACK:
                transformer=new StackTransformer();
                break;
            case ACCORDION:
                transformer=new AccordionTransformer();
                break;
        }
        return transformer;
    }
}
