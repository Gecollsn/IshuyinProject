package com.ishuyin.gecollsn.accueilBlock.activitys;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.widget.TextView;

import com.ishuyin.gecollsn.R;
import com.ishuyin.gecollsn.accueilBlock.fragments.HandpickFragment;
import com.ishuyin.gecollsn.accueilBlock.fragments.SubscribeFragment;
import com.ishuyin.gecollsn.base.BaseActivity;
import com.ishuyin.gecollsn.utils.ToastUtil;

import butterknife.BindView;
import butterknife.OnClick;

public class CentralActivity extends BaseActivity {
    @BindView(R.id.main_tag_subscribe)
    TextView tv_subscribe;
    @BindView(R.id.main_tag_handpick)
    TextView tv_handpick;
    private SubscribeFragment subscribeFragment;
    private HandpickFragment handpickfragment;
    private final int TAG_SUBSCRIBE = 0;
    private final int TAG_HANDPICK = 1;
    private long nvBack = System.currentTimeMillis();

    @Override
    protected int definedLayoutId() {
        return R.layout.activity_central;
    }

    @Override
    protected void doInitEverything() {
        doInitDefaultEvent();
    }

    @Override
    public void doInitViewsFromLayout() {

    }

    @Override
    public void doInitData() {

    }

    @OnClick(R.id.main_tag_subscribe)
    void replaceSubscribeFragment() {
        replaceFragment(subscribeFragment, TAG_SUBSCRIBE);
    }

    @OnClick(R.id.main_tag_handpick)
    void replaceHandpicFragment() {
        replaceFragment(handpickfragment, TAG_HANDPICK);
    }

    @OnClick(R.id.main_tag_mine)
    void moveToMinePage() {
        Intent intent = new Intent(this, MinePageActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.main_tag_sort)
    void moveToSortPage() {
        Intent intent = new Intent(this, SortPageActivity.class);
        startActivity(intent);
    }

    public void doInitDefaultEvent() {
        subscribeFragment = new SubscribeFragment();
        handpickfragment = new HandpickFragment();
        replaceFragment(subscribeFragment, TAG_SUBSCRIBE);
    }

    private void replaceFragment(Fragment fragment, int tag) {
        FragmentManager fManager = getSupportFragmentManager();
        FragmentTransaction ft = fManager.beginTransaction();
        ft.replace(R.id.main_container, fragment);
        ft.commit();
        if (tag == TAG_HANDPICK) {
            tv_subscribe.setSelected(false);
            tv_handpick.setSelected(true);
        } else if (tag == TAG_SUBSCRIBE) {
            tv_subscribe.setSelected(true);
            tv_handpick.setSelected(false);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (System.currentTimeMillis() - nvBack >= 1000) {
                ToastUtil.show("再按一次，退出应用");
                nvBack = System.currentTimeMillis();
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void doInitListener() {

    }
}
