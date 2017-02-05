package com.ishuyin.gecollsn.base;

import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.ishuyin.gecollsn.R;
import com.ishuyin.gecollsn.utils.KeyBoardUtil;

import butterknife.ButterKnife;

/**
 * @author gecollsn
 * @create 5/12/2016
 * @company www.ishuyin.com
 */
public abstract class BaseActivity extends AppCompatActivity implements IDefault {

    protected Activity mActivity;
    private SystemBarTintManager sbtMgr;
    private boolean isDisplayActionBar;
    private TextView actionBarTitle;

    public boolean isDisplayActionBar() {
        return isDisplayActionBar;
    }

    public void setDisplayActionBar(boolean displayActionBar) {
        isDisplayActionBar = displayActionBar;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(definedLayoutId());
        ButterKnife.bind(this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            dealStatusSpace((ViewGroup) getWindow().getDecorView().findViewById(android.R.id.content));
        }
        doInitEverything();
    }

    protected void dealStatusSpace(ViewGroup container) {
        final ViewGroup viewParent = (ViewGroup) container.getChildAt(0);
        int actionH = 0;
        int statusH = getSystemBarTint().getConfig().getStatusBarHeight();
        if (isDisplayActionBar) actionH = getSystemBarTint().getConfig().getActionBarHeight();
        if (isDisplayActionBar) {
            View actionBar = getLayoutInflater().inflate(R.layout.action_bar, null);
            actionBar.setPadding(0, statusH, 0, 0);
            container.addView(actionBar, new FrameLayout.LayoutParams(-1, actionH + statusH));
            actionBar.findViewById(R.id.action_back).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onActionBackPressed();
                }
            });
            actionBarTitle = (TextView) actionBar.findViewById(R.id.action_title);
        }

        viewParent.setPadding(viewParent.getPaddingLeft(), viewParent.getPaddingTop() + statusH + actionH, viewParent.getPaddingRight(),
                viewParent.getPaddingBottom());
    }

    public void setActionBarTitle(String title) {
        if (actionBarTitle == null) return;
        actionBarTitle.setText(title);
    }

    protected void onActionBackPressed() {

    }

    protected void setStatusBar() {
        makeStatusTranslucent(translucentStatus());
        sbtMgr = new SystemBarTintManager(this);
        sbtMgr.setStatusBarTintEnabled(true);
        sbtMgr.setStatusBarTintColor(getStatusBarColor());
    }

    protected SystemBarTintManager getSystemBarTint() {
        return sbtMgr;
    }

    protected boolean translucentStatus() {
        return true;
    }

    private void makeStatusTranslucent(boolean on) {
        Window win = getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (on) winParams.flags |= bits;
        else winParams.flags &= ~bits;
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
        container.setBackgroundDrawable(BackGround.getSystemBackground());
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
    protected int getStatusBarColor() {
        if (getStatusBarColorFromResId() == -1) return Color.parseColor("#8DC44C");
        else return getResources().getColor(getStatusBarColorFromResId());
    }

    /**
     * 重写该方法解析资源给定的颜色值
     *
     * @return
     */
    protected int getStatusBarColorFromResId() {
        return -1;
    }
}
