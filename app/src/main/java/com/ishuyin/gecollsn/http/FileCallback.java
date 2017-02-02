package com.ishuyin.gecollsn.http;


import com.zhy.http.okhttp.callback.Callback;

import java.io.InputStream;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by gecollsn on 2016/4/14 22:18.
 * <p>
 * Company: zjp-company
 * Email: cs@zhijiepay.com
 * TEL: 0592-5101911
 * <p>
 * Declare: FileCallback
 */
public abstract class FileCallback extends Callback<InputStream> {

    private String clsName;

    public FileCallback() {
        StackTraceElement[] stes = Thread.currentThread().getStackTrace();
        this.clsName = stes[5].getClassName();
        clsName = clsName.substring(clsName.lastIndexOf(".") + 1, clsName.length());
        String iClass = stes[4].getClassName();
        clsName += "-->" + iClass.substring(iClass.lastIndexOf(".") + 1, iClass.length()) + "(" + stes[4]
                .getMethodName() + ")";
    }
    @Override
    public InputStream parseNetworkResponse(Response response) throws Exception {
        InputStream is = response.body().byteStream();
        onSubResponse(is);
        return is;
    }

    @Override
    public void onError(Call call, Exception e) {

    }

    @Override
    public void onResponse(InputStream is) {

    }

    public abstract void onSubResponse(InputStream inputStream);

    public String getClsName() {
        return clsName;
    }
}
