package com.zhpan.bannerview.transform;

import androidx.viewpager.widget.ViewPager;

import static com.zhpan.bannerview.constants.TransformerStyle.ACCORDION;
import static com.zhpan.bannerview.constants.TransformerStyle.DEPTH;
import static com.zhpan.bannerview.constants.TransformerStyle.ROTATE;
import static com.zhpan.bannerview.constants.TransformerStyle.STACK;


public class PageTransformerFactory {

    public ViewPager.PageTransformer createPageTransformer(int transformerStyle) {
        ViewPager.PageTransformer transformer = null;
        switch (transformerStyle) {
            case DEPTH:
                transformer = new DepthPageTransformer();
                break;
            case ROTATE:
                transformer=new RotateUpTransformer();
                break;
            case STACK:
                transformer = new StackTransformer();
                break;
            case ACCORDION:
                transformer = new AccordionTransformer();
                break;
        }
        return transformer;
    }
}
