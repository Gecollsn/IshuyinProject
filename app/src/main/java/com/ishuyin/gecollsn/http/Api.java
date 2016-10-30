package com.ishuyin.gecollsn.http;

import com.ishuyin.gecollsn.utils.NetUtil;
import com.ishuyin.gecollsn.utils.ToastUtil;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import java.util.Map;
import java.util.TreeMap;

public abstract class Api {

    /*------------------------------------ 接口的URL ------------------------------------------*/
    private final static String HOST = "http://www.ishuyin.com/";
    public static final String IMAGE_HOST = "http://mp3-45.oss-cn-hangzhou.aliyuncs.com/";

    /*----------------------------- 首页所有接口 ------------------------------*/
    protected final static String HAND_PICK_DETAIL = HOST + "service.php?action=index&type=1";

    /**
     * 普通post,返回json
     *
     * @param params   参数列表
     * @param callback 回调
     */
    protected static void post(Map<String, String> params, String url, Callback callback) {
        OkHttpUtils.post().url(url).params(params).tag(url).build().execute(callback);
    }

    /**
     * 普通post,返回json
     *
     * @param params   参数列表
     * @param callback 回调
     */
    protected static void get(Map<String, String> params, String url, Callback callback) {
        OkHttpUtils.get().url(url).params(params).tag(url).build().execute(callback);
    }

    protected static void post(String url, Callback callback) {
        Map<String, String> params = getParams();
        post(params, url, callback);
    }

    protected static void cancel(Object tag) {
        OkHttpUtils.getInstance().cancelTag(tag);
    }

    protected static Map<String, String> getParams() {
        return new TreeMap<String, String>();
    }

    /**
     * 请求接口前，检查当前网络状态
     */
    protected static boolean checkNet() {
        if (NetUtil.isWifi()) return true;
        else {
            ToastUtil.showLong(ToastUtil.NO_INTERNET);
            return false;
        }
    }
}
