package com.ceiec.android.bustrack.utils;

import android.content.Context;

/**
 * Created by jun on 2016/6/7 0007.
 */
public class CommonUtil {

    public static int getDimensSize(Context context, int dimenId) {
        return (int) context.getResources().getDimension(dimenId);
    }

}
