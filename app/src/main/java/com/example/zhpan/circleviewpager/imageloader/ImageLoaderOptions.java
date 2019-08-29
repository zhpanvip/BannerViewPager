package com.example.zhpan.circleviewpager.imageloader;

import android.content.Context;
import androidx.annotation.DimenRes;
import androidx.annotation.DrawableRes;
import android.widget.ImageView;

import com.zhpan.idea.utils.DensityUtils;


/**
 * <pre>
 *   Created by zhangpan on 2019-08-12.
 *   Description:
 * </pre>
 */
public class ImageLoaderOptions {

    private ImageView mImageView;

    private String url;

    private @DrawableRes
    int resource;

    private @DrawableRes
    int placeholder;

    private @DrawableRes
    int errorDrawable;

    private boolean asGif;

    private boolean isCrossFade;

    private boolean skipMemoryCache;

    private float roundCorner;

    private boolean blurImage;

    // 高斯模糊参数，越大越模糊
    private int blurValue;

    private ScaleType mScaleType;

    private ImageSize mImageSize;

    private ImageDiskCacheStrategy mDiskCacheStrategy;


    private ImageLoaderOptions(Builder builder) {
        this.mImageView = builder.mImageView;
        this.url = builder.url;
        this.resource = builder.resource;
        this.placeholder = builder.placeholder;
        this.errorDrawable = builder.errorDrawable;
        this.asGif = builder.asGif;
        this.isCrossFade = builder.isCrossFade;
        this.skipMemoryCache = builder.skipMemoryCache;
        this.mDiskCacheStrategy = builder.mDiskCacheStrategy;
        this.roundCorner = builder.roundCorner;
        this.blurImage = builder.blurImage;
        this.blurValue = builder.blurValue;
        this.mScaleType = builder.mScaleType;
        this.mImageSize = builder.mImageSize;

    }

    public ImageView getImageView() {
        return mImageView;
    }

    public String getUrl() {
        return url;
    }

    public int getResource() {
        return resource;
    }

    public int getPlaceholder() {
        return placeholder;
    }

    public int getErrorDrawable() {
        return errorDrawable;
    }

    public boolean isAsGif() {
        return asGif;
    }

    public boolean isCrossFade() {
        return isCrossFade;
    }

    public boolean isSkipMemoryCache() {
        return skipMemoryCache;
    }

    public float getRoundCorner() {
        return roundCorner;
    }

    public boolean isBlurImage() {
        return blurImage;
    }

    public int getBlurValue() {
        return blurValue;
    }

    public ScaleType getScaleType() {
        return mScaleType;
    }

    public ImageSize getImageSize() {
        return mImageSize;
    }

    public ImageDiskCacheStrategy getDiskCacheStrategy() {
        return mDiskCacheStrategy;
    }

    public static class Builder {
        private ImageView mImageView;
        private String url;
        private @DrawableRes
        int resource = -1;
        private @DrawableRes
        int placeholder = -1;
        private @DrawableRes
        int errorDrawable = -1;
        private boolean asGif;
        private boolean isCrossFade = true;
        private boolean skipMemoryCache;
        private float roundCorner;
        private boolean blurImage;
        private int blurValue; // 高斯模糊参数，越大越模糊
        private ScaleType mScaleType = ScaleType.DEFAULT;
        private ImageSize mImageSize;

        private ImageDiskCacheStrategy mDiskCacheStrategy = ImageDiskCacheStrategy.DEFAULT;

        public Builder into(ImageView imageView) {
            this.mImageView = imageView;
            return this;
        }

        public Builder load(String url) {
            this.url = url;
            return this;
        }

        public Builder load(@DrawableRes int resource) {
            this.resource = resource;
            return this;
        }

        public Builder placeHolder(@DrawableRes int placeholder) {
            this.placeholder = placeholder;
            return this;
        }

        public Builder isCrossFade(boolean isCrossFade) {
            this.isCrossFade = isCrossFade;
            return this;
        }

        public Builder isSkipMemoryCache(boolean skipMemoryCache) {
            this.skipMemoryCache = skipMemoryCache;
            return this;
        }

        public Builder asGif(boolean asGif) {
            this.asGif = asGif;
            return this;
        }

        public Builder error(@DrawableRes int errorDrawable) {
            this.errorDrawable = errorDrawable;
            return this;
        }

        public Builder diskCacheStrategy(ImageDiskCacheStrategy mDiskCacheStrategy) {
            this.mDiskCacheStrategy = mDiskCacheStrategy;
            return this;
        }

        public Builder setRoundCorner(Context context, float roundCornerDp) {
            this.roundCorner = DensityUtils.dp2px(context, roundCornerDp);
            return this;
        }

        public Builder setRoundCorner(Context context, @DimenRes int cornerRes) {
            this.roundCorner = context.getResources().getDimension(cornerRes);
            return this;
        }

        public Builder setBlurImage(int blurValue) {
            this.blurImage = true;
            this.blurValue = blurValue;
            return this;
        }

        public Builder setScaleType(ScaleType scaleType) {
            mScaleType = scaleType;
            return this;
        }

        public Builder override(int imageWidth, int imageHeight) {
            this.mImageSize = new ImageSize(imageWidth, imageHeight);
            return this;
        }


        public ImageLoaderOptions build() {
            return new ImageLoaderOptions(this);
        }

    }

    public static class ImageSize {
        private int imageWidth;

        private int imageHeight;

        public ImageSize(int imageWidth, int imageHeight) {
            this.imageWidth = imageWidth;
            this.imageHeight = imageHeight;
        }

        public int getImageWidth() {
            return imageWidth;
        }

        public int getImageHeight() {
            return imageHeight;
        }
    }


    // 对应磁盘缓存策略
    public enum ImageDiskCacheStrategy {
        All, NONE, SOURCE, RESULT, DEFAULT
    }
}
