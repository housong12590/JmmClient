package com.jmm.client.config;


import com.jmm.common.utils.FileUtils;
import com.jmm.common.utils.SDCardUtils;

import java.io.File;

public class StorageConfig {

    private static final String ROOT_PATH = SDCardUtils.getSDCardPath() + File.separator + "51jmm" + File.separator + "jmm";

    public static final String IMAGE_CACHE_PATH = ROOT_PATH + File.separator + "image" + File.separator;

    public static final String VIDEO_CACHE_PATH = ROOT_PATH + File.separator + "video" + File.separator;


    public static void initAppDirectory() {
        FileUtils.createOrExistsDir(IMAGE_CACHE_PATH);
        FileUtils.createOrExistsDir(VIDEO_CACHE_PATH);
    }
}
