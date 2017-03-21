package com.itbird.countdown.util;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.Configuration;
import android.os.Build;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.math.BigDecimal;
import java.security.InvalidParameterException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * 用于机型判断，及分辨率等获取的工具类
 * Created by itbird on 2016/6/6
 */

public final class PhoneInfoUtils {

    private static DisplayMetrics mMetrics;

    public static int getScreenWidth(Context context) {
        DisplayMetrics dm = getDisplayMetrics(context);
        return dm.widthPixels;
    }

    public static int getScreenHeight(Context context) {
        DisplayMetrics dm = getDisplayMetrics(context);
        return dm.heightPixels;
    }

    public static DisplayMetrics getDisplayMetrics(Context context) {
        DisplayMetrics dm = null;

        // 获取系统服务时有可能错误，此处不崩溃
        try {
            Display display = null;
            WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
            display = wm.getDefaultDisplay();

            if (display == null) {
                // 取出来的对象可能为空，http://bugly.qq.com/detail?app=900007917&pid=1&ii=15629#stack
                dm = context.getResources().getDisplayMetrics();
            } else {
                if (mMetrics == null) {
                    mMetrics = new DisplayMetrics();
                }
                display.getMetrics(mMetrics);
                dm = mMetrics;
            }
        } catch (Exception e) {
        }
        return dm;
    }

    /**
     * 判断某个Activity是否显示在前台
     *
     * @param context
     * @param activitySimpleName
     * @return
     */
    public static boolean isActivityRunFront(Context context, String activitySimpleName) {
        if (context == null || TextUtils.isEmpty(activitySimpleName)) {
            return false;
        }
        try {
            ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
            List<ActivityManager.RunningTaskInfo> tasks = am.getRunningTasks(1);
            if (tasks != null && tasks.size() > 0) {
                ActivityManager.RunningTaskInfo info = tasks.get(0);
                String runPackage = info.baseActivity.getPackageName();
                String activity = info.topActivity.getShortClassName();
                if (context.getPackageName().equals(runPackage) && activity != null
                        && activity.contains(activitySimpleName)) {
                    return true;
                }
            }
        } catch (Exception e) {
        }
        return false;
    }

    /**
     * 获得属于桌面的应用的应用包名称
     *
     * @return 返回包含所有包名的字符串列表
     */
    public static List<String> getHomes(Context context) {
        try {
            List<String> names = new ArrayList<String>();
            PackageManager packageManager = context.getPackageManager();
            // 属性
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            List<ResolveInfo> resolveInfo = packageManager.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
            for (ResolveInfo ri : resolveInfo) {
                names.add(ri.activityInfo.packageName);
            }
            return names;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 获取当前应用程序的版本号
     */
    public static String getVersion(Context context) {
        String st = null;
        PackageManager pm = context.getPackageManager();
        try {
            PackageInfo packinfo = pm.getPackageInfo(context.getPackageName(), 0);
            String version = packinfo.versionName;
            return version;
        } catch (PackageManager.NameNotFoundException e) {
            return st;
        }
    }
}
