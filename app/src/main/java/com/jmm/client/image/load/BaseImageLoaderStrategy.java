package com.jmm.client.image.load;

import android.content.Context;
import android.widget.ImageView;

;

public interface BaseImageLoaderStrategy {

    void loadImage(Context cxt, String url, ImageView iv, boolean flag);


    void loadImage(Context cxt, ImageLoader imageLoader);
}
