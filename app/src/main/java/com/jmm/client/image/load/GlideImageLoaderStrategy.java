package com.jmm.client.image.load;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.jmm.client.R;
import com.jmm.common.utils.NetworkUtils;

public class GlideImageLoaderStrategy implements BaseImageLoaderStrategy {


    /**
     * @param cxt
     * @param url
     * @param iv
     * @param flag 是否开启非WIFI环境下手动加载图片
     */
    @Override
    public void loadImage(Context cxt, String url, ImageView iv, boolean flag) {
        boolean wifiConnected = NetworkUtils.isWifiConnected();
        if (flag && !wifiConnected) {
            Glide.with(cxt).asBitmap().load(R.mipmap.ic_launcher).into(iv);
            iv.setOnLongClickListener(v -> {
                Glide.with(cxt).load(url).apply(getOptions(new ImageLoader.Builder().build()))
                        .into(new SimpleTarget<Drawable>() {
                            @Override
                            public void onResourceReady(Drawable resource, Transition<? super Drawable> transition) {
                                iv.setImageDrawable(resource);
                                iv.setOnLongClickListener(v1 -> false);
                            }
                        });
                return false;
            });
        } else {
            Glide.with(cxt).asBitmap().load(url).apply(getOptions(new ImageLoader.Builder().build()))
                    .into(iv);
        }
    }

    @Override
    public void loadImage(Context cxt, ImageLoader imageLoader) {
        Glide.with(cxt).asBitmap().apply(getOptions(imageLoader))
                .into(imageLoader.getImgView());
    }

    private RequestOptions getOptions(ImageLoader loader) {
        return new RequestOptions().placeholder(loader.getPlaceHolder())
                .error(loader.getErrorImg())
                .skipMemoryCache(!loader.isCache())
                .diskCacheStrategy(loader.isCache() ? DiskCacheStrategy.DATA : DiskCacheStrategy.NONE);

    }

}
