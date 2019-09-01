package com.zhpan.bannerview.transform;

import androidx.viewpager.widget.ViewPager;

import com.zhpan.bannerview.enums.TransformerStyle;

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
