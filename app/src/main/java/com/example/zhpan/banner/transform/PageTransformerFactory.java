package com.example.zhpan.banner.transform;

import androidx.viewpager2.widget.ViewPager2;

import com.zhpan.bannerview.transform.ScaleInTransformer;

import static com.example.zhpan.banner.transform.TransformerStyle.ACCORDION;
import static com.example.zhpan.banner.transform.TransformerStyle.DEPTH;
import static com.example.zhpan.banner.transform.TransformerStyle.DEPTH_SCALE;
import static com.example.zhpan.banner.transform.TransformerStyle.ROTATE;
import static com.example.zhpan.banner.transform.TransformerStyle.ROTATE_UP;
import static com.example.zhpan.banner.transform.TransformerStyle.SCALE_IN;

public class PageTransformerFactory {

  public static ViewPager2.PageTransformer createPageTransformer(int transformerStyle) {
    ViewPager2.PageTransformer transformer = null;
    switch (transformerStyle) {
      case DEPTH:
        transformer = new DepthPageTransformer();
        break;
      case ROTATE:
        transformer = new RotateTransformer();
        break;
      case ROTATE_UP:
        transformer = new RotateUpTransformer();
        break;
      case DEPTH_SCALE:
        transformer = new DepthScaleTransformer();
        break;
      case ACCORDION:
        transformer = new AccordionTransformer();
        break;
      case SCALE_IN:
        transformer = new ScaleInTransformer(ScaleInTransformer.DEFAULT_MIN_SCALE, false);
        break;
    }
    return transformer;
  }
}
