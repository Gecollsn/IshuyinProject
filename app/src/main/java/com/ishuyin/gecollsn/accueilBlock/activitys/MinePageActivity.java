package com.ishuyin.gecollsn.accueilBlock.activitys;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ishuyin.gecollsn.R;
import com.ishuyin.gecollsn.base.BaseActivity;

import butterknife.BindView;

/**
 * @author gecollsn
 * @create 5/23/2016
 * @company www.ishuyin.com
 */
public class MinePageActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.iv_user_logo)
    ImageView iv_user_logo;
    @BindView(R.id.tv_user_name)
    TextView tv_user_name;
    @BindView(R.id.tv_user_locate)
    TextView tv_user_locate;
    @BindView(R.id.ly_user_function)
    ViewGroup ly_user_function;
    private View ly_vip;
    private View ly_words;
    private View ly_consume;
    private View ly_play;
    private View ly_settings;

    @Override
    protected int definedLayoutId() {
        return R.layout.activity_mine_page;
    }

    @Override
    protected void doInitEverything() {
        doInitData();
        doInitViewsFromLayout();
        doInitDefaultEvent();
        doInitListener();
    }

    @Override
    public void doInitViewsFromLayout() {
        ly_vip = ly_user_function.getChildAt(0);
        ly_words = ly_user_function.getChildAt(1);
        ly_consume = ly_user_function.getChildAt(2);
        ly_play = ly_user_function.getChildAt(3);
        ly_settings = ly_user_function.getChildAt(4);
//        ly_vip.findViewById(R.id)
    }

    @Override
    public void doInitDefaultEvent() {

    }

    @Override
    public void doInitData() {

    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void doInitListener() {
        ly_vip.setOnClickListener(this);
        ly_words.setOnClickListener(this);
        ly_consume.setOnClickListener(this);
        ly_play.setOnClickListener(this);
        ly_settings.setOnClickListener(this);

    }
}
