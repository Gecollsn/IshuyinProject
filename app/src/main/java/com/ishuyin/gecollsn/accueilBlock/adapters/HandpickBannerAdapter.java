package com.ishuyin.gecollsn.accueilBlock.adapters;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.ishuyin.gecollsn.accueilBlock.AC;
import com.ishuyin.gecollsn.accueilBlock.domain.BannerItem;
import com.ishuyin.gecollsn.accueilBlock.fragments.BannerFragment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author gecollsn
 * @create 6/6/2016
 * @company www.ishuyin.com
 */
public class HandpickBannerAdapter extends FragmentPagerAdapter {
    private final List<BannerItem> banners = new ArrayList<>();

    public HandpickBannerAdapter(FragmentManager fm) {
        super(fm);
    }

    public List<BannerItem> getBanners() {
        return Collections.unmodifiableList(banners);
    }
    public void setBanners(List<BannerItem> banners) {
        this.banners.clear();
        this.banners.addAll(banners);
        notifyDataSetChanged();
    }

    @Override
    public Fragment getItem(int position) {
        BannerFragment bannerFragment = new BannerFragment();
        Bundle args = new Bundle();
        args.putSerializable(AC.io.BANNER_ITEM, banners.get(position));
        bannerFragment.setArguments(args);
        return bannerFragment;
    }

    @Override
    public int getCount() {
        return banners.size();
    }
}
