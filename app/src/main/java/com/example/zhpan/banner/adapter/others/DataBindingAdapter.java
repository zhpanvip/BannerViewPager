package com.example.zhpan.banner.adapter.others;

import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;


/**
 * <pre>
 *   Created by zhpan on 2020/7/18.
 *   Description:
 * </pre>
 */
public class DataBindingAdapter {
    @BindingAdapter(value = {"binding:url", "binding:placeholder"}, requireAll = false)
    public static void bindUrl(ImageView imageView, String url, int placeholder) {
        Glide.with(imageView).load(url).error(placeholder).placeholder(placeholder).into(imageView);
    }
}
