package com.feximin.calendar;

import android.content.Context;
import android.util.DisplayMetrics;

/**
 * Created by Neo on 16/5/14.
 */
public class Tool {

    //能被100整除，且能被400整除
    //不能被100整除，但是能被4整除
    public static boolean isLeap(int year){
        boolean leap = false;
        if (year % 100 == 0){
            if (year % 400 == 0) leap = true;
        }else if (year % 4 == 0){
            leap = true;
        }
        return leap;
    }


    private static final int[] DAY_COUNT = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
    public static int getDayCount(int month){
        return DAY_COUNT[month];
    }


    private static int sScreenWidth;
    private static int sScreenHeight;
    private static float sScreenDensity;
    private static float sScreenScaleDensity;
    public static void initScreenParams(Context context){
        if(sScreenWidth == 0){
            DisplayMetrics metrics = context.getResources().getDisplayMetrics();
            sScreenWidth = metrics.widthPixels;
            sScreenHeight = metrics.heightPixels;
            sScreenDensity = metrics.density;
            sScreenScaleDensity = metrics.scaledDensity;
        }
    }
    public static float getScreenDensity(Context context){
        if(sScreenDensity == 0) initScreenParams(context);
        return sScreenDensity;
    }
    public static int getScreenHeight(Context context){
        if(sScreenHeight == 0) initScreenParams(context);
        return sScreenHeight;
    }

    public static int getScreenWidth(Context context){
        if(sScreenWidth == 0) initScreenParams(context);
        return sScreenWidth;
    }


    public static float getScreenScaleDensity(Context context){
        if(sScreenScaleDensity == 0) initScreenParams(context);
        return sScreenScaleDensity;
    }
    public static int dpToPx(Context context, float dp){
        dp *= getScreenDensity(context);
        return (int) Math.ceil(dp);
    }
    public static int spToPx(Context context, float sp){
        sp *= getScreenScaleDensity(context);
        return (int) Math.ceil(sp);
    }
}
