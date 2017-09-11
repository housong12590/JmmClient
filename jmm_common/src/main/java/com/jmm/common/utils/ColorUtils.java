package com.jmm.common.utils;

import android.graphics.Color;

public class ColorUtils {

    public static int getTranslucentColor(float percent, int color) {
        int alpha = color >>> 24;
        int red = color >> 16 & 0xff;
        int green = color >> 8 & 0xff;
        int blue = color & 0xff;
        return Color.argb(Math.round(alpha * percent), red, green, blue);
    }

    public static int getColorRed(int color) {
        return color >> 16 & 0xff;
    }

    public static int getColorGreen(int color) {
        return color >> 8 & 0xff;
    }

    public static int getColorBlue(int color){
        return color & 0xff;
    }
}