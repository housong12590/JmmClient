package com.jmm.client.image.load;

import android.content.Context;
import android.widget.ImageView;

public class ImageLoaderUtils {

    private static ImageLoaderUtils mInstance;
    private BaseImageLoaderStrategy strategy;

    private ImageLoaderUtils() {
        strategy = new GlideImageLoaderStrategy();
    }

    public static ImageLoaderUtils getInstance() {
        if (mInstance == null) {
            synchronized (ImageLoaderUtils.class) {
                if (mInstance == null) {
                    mInstance = new ImageLoaderUtils();
                }
            }
        }
        return mInstance;
    }


    public void loadImage(Context cxt, ImageLoader imageLoader) {
        strategy.loadImage(cxt, imageLoader);
    }

    public void loadImage(Context cxt, String url, ImageView iv, Boolean flag) {
        strategy.loadImage(cxt, url, iv, flag);
    }
}
