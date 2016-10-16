package com.ishuyin.gecollsn.base;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.ishuyin.gecollsn.utils.KeyBoardUtil;

import butterknife.ButterKnife;

/**
 * @author gecollsn
 * @create 5/12/2016
 * @company www.ishuyin.com
 */
public abstract class BaseActivity extends AppCompatActivity implements IDefault {

    protected Activity mActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(definedLayoutId());
        ButterKnife.bind(this);
        doInitEverything();
    }

    protected void setStatusBar(){
        makeStatusTranslucent(translucentStatus());
        SystemBarTintManager sbtMgr = new SystemBarTintManager(this);
        sbtMgr.setStatusBarTintEnabled(true);
        sbtMgr.setStatusBarTintColor(getStatusBarColor());
    }

    protected boolean translucentStatus(){
        return true;
    }

    private void makeStatusTranslucent(boolean on){
        Window win = getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }

    @Override
    protected void onResume() {
        super.onResume();
        setBackGround();
    }

    @Override
    public void onContentChanged() {
        super.onContentChanged();
        mActivity = this;
        setStatusBar();
    }

    protected void setBackGround() {
        ViewGroup container = (ViewGroup) getWindow().getDecorView().findViewById(android.R.id.content);
        container.setBackgroundDrawable(BackGroud.getSystemBackgroud());
    }

    @Override
    protected void onPause() {
        super.onPause();
        KeyBoardUtil.closeKeybord(this);
    }

    protected abstract int definedLayoutId();

    protected abstract void doInitEverything();

    /**
     * 重写该方法直接解析颜色值
     *
     * @return
     */
    protected int getStatusBarColor(){
        if (getStatusBarColorFromResId() == -1) return Color.parseColor("#8DC44C");
        else return getStatusBarColorFromResId();
    }

    /**
     * 重写该方法解析资源给定的颜色值
     *
     * @return
     */
    protected int getStatusBarColorFromResId(){
       return -1;
    }
}
