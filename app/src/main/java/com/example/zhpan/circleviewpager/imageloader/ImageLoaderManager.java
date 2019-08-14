package com.example.zhpan.circleviewpager.imageloader;

/**
 * <pre>
 *   Created by zhangpan on 2019-08-12.
 *   Description:
 * </pre>
 */
public class ImageLoaderManager {

    private static volatile ImageLoaderManager mImageLoaderManager;

    private IImageLoaderStrategy imageLoader;

    private ImageLoaderManager() {}

    public static ImageLoaderManager getInstance() {
        if(mImageLoaderManager == null) {
            synchronized (ImageLoaderManager.class) {
                if(mImageLoaderManager == null) {
                    mImageLoaderManager = new ImageLoaderManager();
                }
            }
        }
        return mImageLoaderManager;
    }


    public void loadImage(ImageLoaderOptions options) {
        if(imageLoader != null) {
            imageLoader.loadImage(options);
        }
    }

    public void init(IImageLoaderStrategy imageLoader) {
        this.imageLoader = imageLoader;
    }
}
