package com.ishuyin.gecollsn.accueilBlock.domain;

import java.io.Serializable;

/**
 * @author gecollsn
 * @create 6/6/2016
 * @company www.ishuyin.com
 */
public class BannerItem implements Serializable{
    private String bannerUrl = "";

    public String getBannerUrl() {
        return bannerUrl;
    }

    public void setBannerUrl(String bannerUrl) {
        this.bannerUrl = bannerUrl;
    }
}
