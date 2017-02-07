package com.jyutwaa.zhaoziliang.glimpse.Utils;

import android.content.Context;
import android.util.TypedValue;

/**
 * Created by zhaoziliang on 17/2/7.
 */

public class ViewUtils {
    private static int actionBarSize = -1;

    public static int getActionBarSize(Context context) {
        if (actionBarSize < 0) {
            TypedValue value = new TypedValue();
            context.getTheme().resolveAttribute(android.R.attr.actionBarSize, value, true);
            actionBarSize = TypedValue.complexToDimensionPixelSize(value.data, context
                    .getResources().getDisplayMetrics());
        }
        return actionBarSize;
    }
}
