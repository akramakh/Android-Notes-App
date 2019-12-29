package com.akram_akh.notes;

import android.graphics.Color;
import android.os.Build;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

public class Utils {


    public static void setDarkIconsStatusBar(AppCompatActivity activity, int statusBarColor) {
        View view = activity.getWindow().getDecorView().getRootView();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int flags = view.getSystemUiVisibility();
            flags |= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
            view.setSystemUiVisibility(flags);
            activity.getWindow().setStatusBarColor(ContextCompat.getColor(activity, statusBarColor));
        }
    }

    public static void setLightIconsStatusBar(AppCompatActivity activity,int statusBarColor) {
        View view = activity.getWindow().getDecorView().getRootView();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int flags = view.getSystemUiVisibility();
            flags &= ~View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
            view.setSystemUiVisibility(flags);
            activity.getWindow().setStatusBarColor(ContextCompat.getColor(activity, statusBarColor));
        }
    }


    public boolean isColorDark(int color) {
        double darkness = 1 - (0.299 * Color.red(color) + 0.587 * Color.green(color) + 0.114 * Color.blue(color)) / 255;
        if (darkness < 0.5) {
            return false; // It's a light color
        } else {
            return true; // It's a dark color
        }
    }

}
