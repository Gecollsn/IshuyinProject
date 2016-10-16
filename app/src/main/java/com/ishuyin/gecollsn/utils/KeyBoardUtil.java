package com.ishuyin.gecollsn.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.text.InputType;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by gecollsn on 2016/3/24 11:05.
 * <p>
 * Company: zjp-company
 * Email: cs@zhijiepay.com
 * TEL: 0592-5101911
 * <p>
 * Declare: 键盘管理类, 打开或关闭键盘
 */
public class KeyBoardUtil {

    private static long lastClickTime; //上次点击时间

    /** 过滤是否快速点击事件， 过滤连续点击1000毫秒 */
    public static boolean isFastDoubleClick() {
        long time = System.currentTimeMillis();
        long timeD = time - lastClickTime;
        if (0 < timeD && timeD < 1000) return true;
        lastClickTime = time;
        return false;
    }

    /**
     * 打开软键盘
     *
     * @param mEditText 输入框
     * @param mContext  上下文
     */
    public static void openKeybord(EditText mEditText, Context mContext) {
        InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(mEditText, InputMethodManager.RESULT_SHOWN);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
    }

    /**
     * 关闭软键盘
     *
     * @param view 输入框
     * @param mContext  上下文
     */
    public static void closeKeybord(View view, Context mContext) {
        InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public static void closeKeybord(Activity activity) {
        View view = activity.getWindow().peekDecorView();
        closeKeybord(view, activity);
    }



    /**
     * 隐藏系统键盘
     *
     * @param activity
     * @param ed
     */
    public static void hideSoftInputMethod(Activity activity, EditText ed) {
        activity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        int currentVersion = android.os.Build.VERSION.SDK_INT;
        String methodName = null;
        if (currentVersion >= 16) {
            // 4.2
            methodName = "setShowSoftInputOnFocus";
        } else if (currentVersion >= 14) {
            // 4.0
            methodName = "setSoftInputShownOnFocus";
        }
        if (methodName == null) {
            ed.setInputType(InputType.TYPE_NULL);
        } else {
            Class<EditText> cls = EditText.class;
            Method setShowSoftInputOnFocus;
            try {
                setShowSoftInputOnFocus = cls.getMethod(methodName, boolean.class);
                setShowSoftInputOnFocus.setAccessible(true);
                setShowSoftInputOnFocus.invoke(ed, false);
            } catch (NoSuchMethodException e) {
                ed.setInputType(InputType.TYPE_NULL);
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 监听键盘高度变化
     *
     * @param activity
     * @param listener
     */
    public static void setKeyboardChangedListener(Activity activity, final OnSoftKeyboardChangeListener listener) {
        final View decorView = activity.getWindow().getDecorView();
        decorView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                Rect rect = new Rect();
                decorView.getWindowVisibleDisplayFrame(rect);
                int displayHeight = rect.bottom - rect.top;
                int height = decorView.getHeight();
                boolean hide = (double) displayHeight / height > 0.8;

                listener.onSoftKeyBoardChange(height - displayHeight, !hide);

            }
        });
    }

    /**
     * Note: if you change layout in this method, maybe this method will repeat because the
     * ViewTreeObserver.OnGlobalLayoutListener will repeat So maybe you need do some handle(ex: add some flag to avoid
     * repeat) in this callback
     * <p>
     * Example:
     * <p>
     * private int previousHeight = -1; private void setupKeyboardLayoutWhenEdit() {
     * SoftKeyboardUtil.observeSoftKeyBoard(this, new OnSoftKeyboardChangeListener() {
     */
    public interface OnSoftKeyboardChangeListener {
        void onSoftKeyBoardChange(int softKeybardHeight, boolean visible);
    }
}
