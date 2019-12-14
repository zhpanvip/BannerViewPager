package com.zhpan.bannerview.transform;


import android.support.v4.view.ViewPager;

import static com.zhpan.bannerview.constants.TransformerStyle.ACCORDION;
import static com.zhpan.bannerview.constants.TransformerStyle.DEPTH;
import static com.zhpan.bannerview.constants.TransformerStyle.ROTATE;
import static com.zhpan.bannerview.constants.TransformerStyle.SCALE_IN;
import static com.zhpan.bannerview.constants.TransformerStyle.STACK;


public class PageTransformerFactory {

    public ViewPager.PageTransformer createPageTransformer(int transformerStyle) {
        ViewPager.PageTransformer transformer = null;
        switch (transformerStyle) {
            case DEPTH:
                transformer = new DepthPageTransformer();
                break;
            case ROTATE:
                transformer = new RotateUpTransformer();
                break;
            case STACK:
                transformer = new StackTransformer();
                break;
            case ACCORDION:
                transformer = new AccordionTransformer();
                break;
            case SCALE_IN:
                transformer = new ScaleInTransformer(ScaleInTransformer.DEFAULT_MIN_SCALE);
                break;
        }
        return transformer;
    }
}
