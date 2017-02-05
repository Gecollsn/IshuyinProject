package com.ishuyin.gecollsn.accueilBlock.activitys;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ishuyin.gecollsn.R;
import com.ishuyin.gecollsn.base.BaseActivity;
import com.ishuyin.gecollsn.base.UserInfo;
import com.ishuyin.gecollsn.userBlock.activitys.UserLoginActivity;
import com.ishuyin.gecollsn.utils.KeyBoardUtil;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.OnClick;

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
    TextView tv_user_onlinePeriod;
    @BindView(R.id.tv_user_des)
    TextView tv_user_des;
    @BindView(R.id.ly_user_function)
    ViewGroup ly_user_function;
    @BindView(R.id.ly_user_unLogin)
    View ly_user_unLogin;
    @BindView(R.id.ly_user_login)
    View ly_user_login;
    @BindView(R.id.ly_user)
    ViewGroup ly_user;
    private View ly_vip;
    private View ly_words;
    private View ly_consume;
    private View ly_play;
    private View ly_settings;
    private TextView tv_coin_count;
    private TextView tv_coin_title;
    private TextView tv_point_count;
    private TextView tv_point_title;
    private TextView tv_balance_count;
    private TextView tv_balance_title;
    private TextView tv_user_vip;
    private TextView tv_user_words;
    private TextView tv_user_consume;
    private TextView tv_user_play;
    private TextView tv_user_settings;

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

        tv_user_vip = (TextView) ly_vip.findViewById(R.id.tv_user_function);
        tv_user_words = (TextView) ly_words.findViewById(R.id.tv_user_function);
        tv_user_consume = (TextView) ly_consume.findViewById(R.id.tv_user_function);
        tv_user_play = (TextView) ly_play.findViewById(R.id.tv_user_function);
        tv_user_settings = (TextView) ly_settings.findViewById(R.id.tv_user_function);

        tv_coin_count = (TextView) findViewById(R.id.inc_tab_coin).findViewById(R.id.tv_tag_count);
        tv_coin_title = (TextView) findViewById(R.id.inc_tab_coin).findViewById(R.id.tv_tag_title);
        tv_point_count = (TextView) findViewById(R.id.inc_tab_point).findViewById(R.id.tv_tag_count);
        tv_point_title = (TextView) findViewById(R.id.inc_tab_point).findViewById(R.id.tv_tag_title);
        tv_balance_count = (TextView) findViewById(R.id.inc_tab_balance).findViewById(R.id.tv_tag_count);
        tv_balance_title = (TextView) findViewById(R.id.inc_tab_balance).findViewById(R.id.tv_tag_title);
    }

    @Override
    public void doInitDefaultEvent() {
        tv_user_des.setText("我的");
        tv_coin_title.setText("听币");
        tv_point_title.setText("积分");
        tv_balance_title.setText("余额");
        ((View) tv_coin_title.getParent()).setBackgroundColor(Color.WHITE);
        ((View) tv_point_title.getParent()).setBackgroundColor(Color.WHITE);
        ((View) tv_balance_title.getParent()).setBackgroundColor(Color.WHITE);

        tv_user_vip.setText("VIP特权");
        tv_user_words.setText("我的留言");
        tv_user_consume.setText("消费记录");
        tv_user_play.setText("点播记录");
        tv_user_settings.setText("设置");

        setFunctionLeftDrawable(tv_user_vip, R.drawable.mine_vip);
        setFunctionLeftDrawable(tv_user_words, R.drawable.mine_leave_msg);
        setFunctionLeftDrawable(tv_user_consume, R.drawable.mine_consume_record);
        setFunctionLeftDrawable(tv_user_play, R.drawable.mine_play_record);
        setFunctionLeftDrawable(tv_user_settings, R.drawable.mine_settings);
        setFunctionLeftDrawable(tv_user_des, R.drawable.mine_tag);

        if (UserInfo.getUser().isLogin()) {
            tv_user_name.setText(UserInfo.getUser().getUserName());
            Picasso.with(this).load(UserInfo.getUser().getLogo()).into(iv_user_logo);
            ly_user_login.setVisibility(View.VISIBLE);
            ly_user_unLogin.setVisibility(View.GONE);
        } else {
            ly_user_unLogin.setVisibility(View.VISIBLE);
            ly_user_login.setVisibility(View.GONE);
        }
    }

    @OnClick(R.id.ly_user_unLogin)
    void requestUser2Login() {
        if (KeyBoardUtil.isFastDoubleClick()) return;
        startActivity(new Intent(this, UserLoginActivity.class));
    }

    private void setFunctionLeftDrawable(TextView v, int resId) {
        Drawable drawable = getResources().getDrawable(resId);
        v.setCompoundDrawablesWithIntrinsicBounds(drawable, null, null, null);
    }

    @Override
    protected void dealStatusSpace(ViewGroup container) {
        ViewGroup.LayoutParams lp = ly_user.getLayoutParams();
        lp.height = lp.height + getSystemBarTint().getConfig().getStatusBarHeight();
        ly_user.setLayoutParams(lp);
        ly_user.getChildAt(0).setPadding(0, getSystemBarTint().getConfig().getStatusBarHeight(), 0, 0);
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

    @Override
    protected int getStatusBarColorFromResId() {
        return R.color.black_33;
    }
}
