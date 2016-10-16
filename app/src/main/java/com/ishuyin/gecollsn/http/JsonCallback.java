package com.ishuyin.gecollsn.http;

import android.util.Log;

import com.ishuyin.gecollsn.utils.ToastUtil;
import com.ishuyin.gecollsn.utils.logUtil.KLog;
import com.zhy.http.okhttp.callback.Callback;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import okhttp3.Call;
import okhttp3.Request;
import okhttp3.Response;

public abstract class JsonCallback extends Callback<JSONObject> {

    private String requestTag;
    private String lodingInfo = "";

    public JsonCallback() {
        StackTraceElement[] stes = Thread.currentThread().getStackTrace();
        this.requestTag = stes[5].getClassName();
        requestTag = requestTag.substring(requestTag.lastIndexOf(".") + 1, requestTag.length());
        String iClass = stes[4].getClassName();
        requestTag += "-->" + iClass.substring(iClass.lastIndexOf(".") + 1, iClass.length()) + "(" + stes[4]
                .getMethodName() + ")";
    }

    public JsonCallback(String info) {
        this();
        this.lodingInfo = info;
    }

    @Override
    public JSONObject parseNetworkResponse(Response response) throws Exception {
        String respStr = response.body().string();
        JSONTokener jsonParser = new JSONTokener(respStr);
        JSONObject obj = (JSONObject) jsonParser.nextValue();
        getDataInSubThread(obj);
        return obj;
    }

    /** 在子线程中拿到数据 */
    public void getDataInSubThread(JSONObject jsonObject) {

        try {

            String code = jsonObject.getString("r");
            KLog.json("data", jsonObject.toString());
            code = jsonObject.getString("r");
            Log.e("data", jsonObject.toString() + "");
            if ("1".equals(code)) {
                onSubSuccessed(jsonObject);
            } else {
                onSubFailed(jsonObject, code, "");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onResponse(JSONObject jsonObject) {
        String code="-99";
        try {
            code = jsonObject.getString("r");
            if ("1".equals(code)) onMainSuccessed(jsonObject);
            else {
                if (jsonObject.has("i")) onAllFailed(Integer.parseInt(code), jsonObject.getString("i"));
                else onAllFailed(Integer.parseInt(code), "请求异常");

                String msg = jsonObject.getString("i");
                if (msg.contains("{") || msg.contains("[")) msg = ToastUtil.REQUEST_ERROR;
                onMainFailed(jsonObject, code, msg);
            }
        } catch (JSONException e) {
            e.printStackTrace();
            onAllFailed(Integer.parseInt(code), "请求异常");
            showErrorToast(ToastUtil.DATA_RETURN_ERROR);
        }
    }

    /**
     * 针对出r=1以外的所以异常（包括请求未成功）
     */
    public void onAllFailed(int code, String msg) {

    }

    @Override
    public void onError(Call call, Exception e) {
        onAllFailed(-100, "网络异常");
        showErrorToast(ToastUtil.NET_ERROR);
    }

    /**
     * 若不需要，则重写该方法
     */
    public void showErrorToast(String tag) {
        ToastUtil.show(tag);
    }

    /**
     * 在主线程中拿到数据
     *
     * @param jsonObject
     */
    public abstract void onMainSuccessed(JSONObject jsonObject);

    public void onMainFailed(JSONObject jsonObject, String code) {
        KLog.d(requestTag, jsonObject.toString());
        showErrorToast(ToastUtil.REQUEST_ERROR);
    }

    public void onMainFailed(JSONObject jsonObject, String code, String msg) {
        onMainFailed(jsonObject, code);
    }

    public void onSubSuccessed(JSONObject jsonObject) {
    }

    public void onSubFailed(JSONObject jsonObject, String code) {
    }

    public void onSubFailed(JSONObject jsonObject, String code, String msg) {
        onSubFailed(jsonObject, code);
    }

    /** 获取请求接口的名称 */
    public String getRequestTag() {
        return requestTag;
    }

    @Override
    public void onBefore(Request request) {
        super.onBefore(request);
//        if (TextUtils.isEmpty(lodingInfo)) DialogUtil.loadingDialog("网络加载中...");
//        else DialogUtil.loadingDialog(lodingInfo);
    }

    @Override
    public void onAfter() {
        super.onAfter();
//        DialogUtil.dismissLoading();
    }
}
