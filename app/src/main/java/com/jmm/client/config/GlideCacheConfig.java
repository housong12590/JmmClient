package com.jmm.client.config;

import android.content.Context;

import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.load.engine.cache.DiskLruCacheFactory;
import com.bumptech.glide.module.AppGlideModule;


public class GlideCacheConfig extends AppGlideModule {
    @Override
    public void applyOptions(Context context, GlideBuilder builder) {
        int cacheSize = 128 * 1024 * 1024;
        builder.setDiskCache(new DiskLruCacheFactory(StorageConfig.IMAGE_CACHE_PATH, cacheSize));
    }
}
