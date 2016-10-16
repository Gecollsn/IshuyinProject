package com.ishuyin.gecollsn.http;

import com.ishuyin.gecollsn.utils.NetUtil;
import com.ishuyin.gecollsn.utils.ToastUtil;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import java.util.Map;
import java.util.TreeMap;

public abstract class Api {

/**---------------------------------> 凸 不用怀疑，我就是分割线 凸 <-------------------------------------**/

/***************************************************************************************************************/
/**=============================    专业占坑，尔等休要来抢     ====================================================*/


/**============================    不来捣乱，咱们还是好朋友      ==================================================*/
/***************************************************************************************************************/

    /**
     * 普通post,返回json
     *
     * @param params   参数列表
     * @param callback 回调
     */
    protected static void post(Map<String, String> params, String url, Callback callback) {
        OkHttpUtils.post().url(url).params(params).tag(url).build().execute(callback);
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
