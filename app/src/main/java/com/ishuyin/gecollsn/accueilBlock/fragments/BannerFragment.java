package com.ishuyin.gecollsn.accueilBlock.fragments;

import android.os.Bundle;

import com.ishuyin.gecollsn.R;
import com.ishuyin.gecollsn.accueilBlock.AC;
import com.ishuyin.gecollsn.accueilBlock.domain.BannerItem;
import com.ishuyin.gecollsn.base.BaseFragment;

/**
 * @author gecollsn
 * @create 6/6/2016
 * @company www.ishuyin.com
 */
public class BannerFragment extends BaseFragment {
    private BannerItem bannerItem;

    @Override
    protected int definedLayoutId() {
        return R.layout.fragment_banner;
    }

    @Override
    protected void doInitEverything() {

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

    @Override
    public void setArguments(Bundle args) {
        super.setArguments(args);
        bannerItem = (BannerItem) args.get(AC.io.BANNER_ITEM);
    }

}
