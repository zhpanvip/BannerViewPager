package com.example.zhpan.circleviewpager.imageloader;

import android.graphics.Bitmap;
import android.text.TextUtils;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.Transformation;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.example.zhpan.circleviewpager.imageloader.transformer.BlurTransformation;

import java.util.ArrayList;
import java.util.List;

/**
 * <pre>
 *   Created by zhangpan on 2019-08-12.
 *   Description:
 * </pre>
 */
public class GlideImageLoader implements IImageLoaderStrategy {

    @Override
    public void loadImage(ImageLoaderOptions options) {
        getRequestBuilder(options).apply(getRequestOption(options)).into(options.getImageView());
    }

    private RequestOptions getRequestOption(ImageLoaderOptions options) {
        RequestOptions requestOptions = new RequestOptions();
        setScaleType(options.getScaleType(), requestOptions);
        setDiskCacheStrategy(options.getDiskCacheStrategy(), requestOptions);
        setDrawable(options, requestOptions);
        setTransformation(options, requestOptions);
        requestOptions.skipMemoryCache(options.isSkipMemoryCache());
        if(options.getImageSize() != null) {
            requestOptions.override(options.getImageSize().getImageWidth(),
                    options.getImageSize().getImageHeight());
        }
        return requestOptions;
    }

    private void setDrawable(ImageLoaderOptions options, RequestOptions requestOptions) {
        if(options.getPlaceholder() != -1) {
            requestOptions.placeholder(options.getPlaceholder());
        }
        if(options.getErrorDrawable() != -1) {
            requestOptions.error(options.getErrorDrawable());
        }
    }

    private void setTransformation(ImageLoaderOptions options, RequestOptions requestOptions) {
        List<Transformation<Bitmap>> list = new ArrayList<>();
        if(options.getRoundCorner() > 0) {
            list.add(new RoundedCorners((int)options.getRoundCorner()));
        }

        if(options.isBlurImage()) {
            list.add(new BlurTransformation(options.getBlurValue()));
        }

        for(Transformation<Bitmap> transformation : list) {
            requestOptions.transform(transformation);
        }
    }

    private void setDiskCacheStrategy(ImageLoaderOptions.ImageDiskCacheStrategy diskCacheStrategy,
            RequestOptions requestOptions) {
        if(diskCacheStrategy != ImageLoaderOptions.ImageDiskCacheStrategy.DEFAULT) {
            if(ImageLoaderOptions.ImageDiskCacheStrategy.NONE == diskCacheStrategy) {
                requestOptions.diskCacheStrategy(DiskCacheStrategy.NONE);
            } else if(ImageLoaderOptions.ImageDiskCacheStrategy.All == diskCacheStrategy) {
                requestOptions.diskCacheStrategy(DiskCacheStrategy.ALL);
            } else if(ImageLoaderOptions.ImageDiskCacheStrategy.SOURCE == diskCacheStrategy) {
                requestOptions.diskCacheStrategy(DiskCacheStrategy.RESOURCE);
            } else if(ImageLoaderOptions.ImageDiskCacheStrategy.RESULT == diskCacheStrategy) {
                requestOptions.diskCacheStrategy(DiskCacheStrategy.DATA);
            }
        }
    }

    private void setScaleType(ScaleType scaleType, RequestOptions requestOptions) {
        switch(scaleType) {
            case CENTER_INSIDE:
                requestOptions.centerInside();
                break;
            case CENTER_OUTSIDE:
                requestOptions.centerInside();
                break;
            case CENTER_CROP:
                requestOptions.centerCrop();
                break;
            case FIT_CENTER:
            case DEFAULT:
                requestOptions.fitCenter();
                break;
            default:
                break;
        }
    }

    private RequestBuilder getRequestBuilder(ImageLoaderOptions options) {
        RequestManager requestManager = Glide.with(options.getImageView());
        RequestBuilder builder =
                options.isAsGif() ? requestManager.asGif() : requestManager.asBitmap();
        builder.load(options.getResource());
        if(TextUtils.isEmpty(options.getUrl())) {
            builder.load(options.getResource());
        } else {
            builder.load(options.getUrl());
        }
        return builder;
    }
}
