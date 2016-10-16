package com.ishuyin.gecollsn.utils;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.ResolveInfo;
import android.net.Uri;

import java.io.File;
import java.util.List;

/**
 * Created by gecollsn on 2016/3/24.
 * <p>
 * Company: zjp-company
 * Email: cs@zhijiepay.com
 * TEL: 0592-5101911
 * <p>
 * Declare: 与应用相关的辅助类
 * 需要在application中初始化context
 */
public class AppUtil {
    private AppUtil() {
        /* cannot be instantiated */
        throw new UnsupportedOperationException("cannot be instantiated");

    }

    /** 设置一个统一的上下文环境 */
    private static Context context;

    public static void initContext(Context context) { AppUtil.context = context; }

    /**
     * 获取应用程序名称
     */
    public static String getAppName() {
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            int labelRes = packageInfo.applicationInfo.labelRes;
            return context.getResources().getString(labelRes);
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * [获取应用程序版本名称信息]
     *
     * @return 当前应用的版本名称
     */
    public static String getVersionName(String packet) {
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(packet, 0);
            return packageInfo.versionName;

        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        return "0";
    }

    /**
     * [获取应用程序版本号信息]
     */
    public static int getVersionCode(String packet) {
        try {
            return context.getPackageManager().getPackageInfo(packet, 0).versionCode;
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 安装指定路径的APK文件
     *
     * @param filePath
     */
    public static void intallApk(Context context, String filePath) {
        File apkfile = new File(filePath);
        if (!apkfile.exists()) return;
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setDataAndType(Uri.parse("file://" + apkfile.toString()), "application/vnd.android.package-archive");
        context.startActivity(i);
    }

    /**
     * 用于跳转到各个市场的app页面 ，可用于下载或评论
     */
    public static void startMarket(Context context, String packageName) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        // setData，传入的是Uri，并且可以指定Data 的Type，用于数据的过滤。setData可以被系统用来寻找匹配目标组件。
        intent.setData(Uri.parse("market://details?id=" + packageName));
        PackageManager pm = context.getPackageManager();
        List<ResolveInfo> resolve = pm.queryIntentActivities(intent, 0);
        if (resolve.size() == 0) {
            ToastUtil.show("没有安装任何市场应用");
            return;
        }
        context.startActivity(intent);
    }
}
