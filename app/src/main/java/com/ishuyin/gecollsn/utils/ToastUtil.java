package com.ishuyin.gecollsn.utils;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.ishuyin.gecollsn.R;


/**
 * Toast统一管理类
 */
public class ToastUtil {

    public static final String NO_INTERNET = "当前无网络连接！";
    public static final String NET_ERROR = "服务端异常，请稍后重试！";
    public static final String PARSE_ERROR = "数据解析异常，请重试！";
    public static final String REQUEST_ERROR = "数据请求异常！";
    public static final String DATA_RETURN_ERROR = "服务端数据格式错误！";
    private static Toast toast;

    /**
     * 设置一个统一的上下文环境
     */
    private static Context context;

    public static void initContext(Context context) { ToastUtil.context = context; }

    private ToastUtil() {
        /* cannot be instantiated */
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    public static boolean isShow = true;

    /**
     * 短时间显示Toast
     *
     * @param message
     */
    public static void showShort(CharSequence message) {
        if (isShow) Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    /**
     * 短时间显示Toast
     *
     * @param message
     */
    public static void showShort(int message) {
        if (isShow) Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    /**
     * 长时间显示Toast
     *
     * @param message
     */
    public static void showLong(CharSequence message) {
        show(message, Toast.LENGTH_LONG);
    }

    /**
     * 长时间显示Toast
     *
     * @param message
     */
    public static void showLong(int message) {
        if (isShow) Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }

    /**
     * 自定义显示Toast时间
     *
     * @param message
     */
    public static void show(CharSequence message) {
        show(message, Toast.LENGTH_SHORT);
    }

    public static void show(CharSequence message, int duration) {
        if (!isShow) return;
        View layout = LayoutInflater.from(context).inflate(R.layout.toast, null);
        if (toast != null) {
            toast.cancel();
        }
        toast = new Toast(context);
        // 查找ImageView控件
        // 注意是在layout中查找
        TextView text = (TextView) layout.findViewById(R.id.tv_toast);
        text.setText(message);

        // 设置Toast的位置
        toast.setGravity(Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, ScreenUtil.getScreenHeight(context) / 8);
        toast.setDuration(duration);
        // 让Toast显示为我们自定义的样子
        toast.setView(layout);
        toast.show();
    }

    /**
     * 自定义显示Toast时间
     *
     * @param message
     * @param duration
     */
    public static void show(int message, int duration) {
        if (isShow) Toast.makeText(context, message, duration).show();
    }

}