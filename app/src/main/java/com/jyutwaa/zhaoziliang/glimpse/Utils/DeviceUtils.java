package com.jyutwaa.zhaoziliang.glimpse.Utils;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;

/**
 * Created by zhaoziliang on 17/2/14.
 */

public class DeviceUtils {
    private static int[] mDeviceInfos = new int[2];

    public static int[] getDeviceInfos(Context context){
        if(mDeviceInfos[0] == 0 && mDeviceInfos[1] == 0){
            DisplayMetrics metrics = new DisplayMetrics();
            ((Activity)context).getWindowManager().getDefaultDisplay().getMetrics(metrics);
            mDeviceInfos[0] = metrics.widthPixels;
            mDeviceInfos[1] = metrics.heightPixels;
        }
        return mDeviceInfos;
    }
}
