package com.ishuyin.gecollsn;

import android.content.Intent;
import android.os.SystemClock;

import com.ishuyin.gecollsn.accueilBlock.activitys.CentralActivity;
import com.ishuyin.gecollsn.base.BaseActivity;
import com.ishuyin.gecollsn.utils.ThreadUtil;

public class SplashActivity extends BaseActivity {

    @Override
    protected int definedLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    protected void doInitEverything() {
        ThreadUtil.execute(new Runnable() {
            public void run() {
                SystemClock.sleep(1000);
                startActivity(new Intent(mActivity, CentralActivity.class));
                finish();
            }
        });
    }

    @Override
    public void doInitViewsFromLayout() {

    }

    @Override
    public void doInitDefaultEvent() {

    }

    @Override
    public void doInitData() {

    }

    @Override
    public void doInitListener() {

    }
}
