package com.ishuyin.gecollsn.accueilBlock.fragments;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ishuyin.gecollsn.R;
import com.ishuyin.gecollsn.accueilBlock.adapters.HandpickBannerAdapter;
import com.ishuyin.gecollsn.accueilBlock.adapters.HandpickExpandAdapter;
import com.ishuyin.gecollsn.accueilBlock.domain.BannerItem;
import com.ishuyin.gecollsn.accueilBlock.domain.HandpickChild;
import com.ishuyin.gecollsn.accueilBlock.domain.HandpickGroup;
import com.ishuyin.gecollsn.base.BaseFragment;
import com.ishuyin.gecollsn.utils.DensityUtil;
import com.ishuyin.gecollsn.utils.view.indicator.CirclePageIndicator;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author gecollsn
 * @create 5/26/2016
 * @company www.ishuyin.com
 */
public class HandpickFragment extends BaseFragment {
    private final int RANK = 0;
    private final int OVER = 1;
    private final int LOAD = 2;
    @BindView(R.id.tab_selection1)
    TextView tab_rank;
    @BindView(R.id.tab_selection2)
    TextView tab_over;
    @BindView(R.id.tab_selection3)
    TextView tab_load;
    @BindView(R.id.action_back)
    View action_back;
    @BindView(R.id.action_title)
    TextView action_title;
    @BindView(R.id.ly_searchBar)
    View action_searchBar;
    @BindView(R.id.action_search_et)
    EditText action_searchBar_edit;
    @BindView(R.id.elv_main_handpick)
    ExpandableListView elv_main_handpick;
    private View bannerView;
    private ViewPager vp_banner;
    private CirclePageIndicator indicator_banner;
    private HandpickBannerAdapter adapter_banner;
    private HandpickExpandAdapter adapter_expand;

    @Override
    protected int definedLayoutId() {
        return R.layout.fragment_main_handpick;
    }

    @Override
    protected void doInitEverything() {
        doInitData();
        doInitViewsFromLayout();
        doInitDefaultEvent();
    }

    @Override
    public void doInitData() {

    }

    @Override
    public void doInitListener() {

    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    public void doInitViewsFromLayout() {
        bannerView = layoutInflater().inflate(R.layout.header_banner_handpick, null);
        adapter_banner = new HandpickBannerAdapter(getActivity().getSupportFragmentManager());
        vp_banner = (ViewPager) bannerView.findViewById(R.id.vp_banner_handpick);
        indicator_banner = (CirclePageIndicator) bannerView.findViewById(R.id.indicator_banner_handpick);
        adapter_expand = new HandpickExpandAdapter(elv_main_handpick);
    }

    public void doInitDefaultEvent() {
        tab_rank.setText("榜单");
        tab_over.setText("完结");
        tab_load.setText("连载");

        action_back.setVisibility(View.GONE);
        action_title.setText("LOGO");
        LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) action_title.getLayoutParams();
        lp.leftMargin = DensityUtil.dp2px(5);
        action_title.setLayoutParams(lp);
        action_searchBar.setVisibility(View.VISIBLE);
        action_searchBar_edit.setHint("作者/播音/关键字");
        action_searchBar_edit.setFocusable(false);
        toggleTag(RANK);

        List<BannerItem> bannerItems = new ArrayList<>();
        List<HandpickGroup> groupItems = new ArrayList<>();
        List<HandpickChild> childItems = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            bannerItems.add(new BannerItem());
        }
        childItems.add(new HandpickChild());
        groupItems.add(new HandpickGroup("推荐", "", R.drawable.main_handpick_suggest));
        groupItems.add(new HandpickGroup("热播", "", R.drawable.main_handpick_hot));
        groupItems.add(new HandpickGroup("最新", "", R.drawable.main_handpick_new));
        vp_banner.setAdapter(adapter_banner);
        indicator_banner.setViewPager(vp_banner);
        adapter_banner.setBanners(bannerItems);
        elv_main_handpick.addHeaderView(bannerView);
        elv_main_handpick.setAdapter(adapter_expand);
        adapter_expand.setGroupData(groupItems);
        adapter_expand.setChildData(childItems);
    }

    @OnClick(R.id.tab_selection1)
    void onRankClick() {
        toggleTag(RANK);
    }

    @OnClick(R.id.tab_selection2)
    void onOverClick() {
        toggleTag(OVER);
    }

    @OnClick(R.id.tab_selection3)
    void onLoadClick() {
        toggleTag(LOAD);
    }

    private void toggleTag(int tag) {
        tab_rank.setSelected(false);
        tab_over.setSelected(false);
        tab_load.setSelected(false);
        if (tag == RANK) tab_rank.setSelected(true);
        if (tag == OVER) tab_over.setSelected(true);
        if (tag == LOAD) tab_load.setSelected(true);
    }
}
