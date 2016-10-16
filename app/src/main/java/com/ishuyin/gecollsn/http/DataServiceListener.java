package com.ishuyin.gecollsn.http;


import com.ishuyin.gecollsn.utils.ToastUtil;

import okhttp3.Call;

public abstract class DataServiceListener {

    public abstract void onSuccessed(Object... objs);

    public void onFailed(Object... objs) {
        if (objs.length > 0) ToastUtil.show((String) objs[0]);
    }

    public void onError(Call call, Exception e) {}
}
