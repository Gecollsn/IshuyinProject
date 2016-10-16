package com.ishuyin.gecollsn.utils;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;

/**
 * Created by gecollsn on 2016/3/24 11:11.
 *
 * Company: zjp-company
 * Email: cs@zhijiepay.com
 * TEL: 0592-5101911
 *
 * Declare: 网络相关的工具类
 */
public class NetUtil {
    private NetUtil() {
        /* cannot be instantiated */
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    /** 设置一个统一的上下文环境 */
    private static Context context;
    public static void initContext(Context context) { NetUtil.context = context; }

    /**
     * 判断网络是否连接
     *
     * @return
     */
    public static boolean isConnected() {
        ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (null != connectivity) {
            NetworkInfo info = connectivity.getActiveNetworkInfo();
            if (null != info && info.isConnected()) {
                if (info.getState() == NetworkInfo.State.CONNECTED) {
                    return true;
                }
            }
        }
        return false;
    }

    /** 判断是否是wifi连接 */
    public static boolean isWifi() {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm == null) return false;
        NetworkInfo info = cm.getActiveNetworkInfo();
        if (info == null) return false;
        return info.getType() == ConnectivityManager.TYPE_WIFI;

    }

    /**
     * 判断是否手机网络连接
     * @return
     */
    public static boolean isMobile() {
        if (isConnected()) {
            ConnectivityManager cm = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo info = cm.getActiveNetworkInfo();
            int type = info.getType();
            if (ConnectivityManager.TYPE_MOBILE == type) {
                return true;
            }
        }
        return false;
    }

    /**
     * 获取wifi名称
     *
     * @return
     */
    public static String getWifiName() {
        if (isWifi()) {
            WifiManager wifiMgr = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
            String bssid = wifiMgr.getConnectionInfo().getSSID();
            boolean contains = bssid.contains("\"");
            if (contains) {
                String[] split = bssid.split("\"");// 去掉双引号
                return split[1];
            }
            return bssid;
        }
        return "";
    }

    /** 打开网络设置界面 */
    public static void openSetting(Activity activity) {
        Intent intent = new Intent("/");
        ComponentName cm = new ComponentName("com.android.settings", "com.android.settings.WirelessSettings");
        intent.setComponent(cm);
        intent.setAction("android.intent.action.VIEW");
        activity.startActivityForResult(intent, 0);
    }

}
