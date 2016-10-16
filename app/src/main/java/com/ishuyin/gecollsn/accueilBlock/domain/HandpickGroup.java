package com.ishuyin.gecollsn.accueilBlock.domain;

import com.ishuyin.gecollsn.R;

/**
 * @author gecollsn
 * @create 6/7/2016
 * @company www.ishuyin.com
 */
public class HandpickGroup {
    private String title = "";
    private String logoUrl = "";
    private int logoId = R.drawable.main_handpick_suggest;

    public HandpickGroup() { }

    public HandpickGroup(String title, String logoUrl, int logoId) {
        this.title = title;
        this.logoUrl = logoUrl;
        this.logoId = logoId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }

    public int getLogoId() {
        return logoId;
    }

    public void setLogoId(int logoId) {
        this.logoId = logoId;
    }
}
