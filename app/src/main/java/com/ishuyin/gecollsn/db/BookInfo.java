package com.ishuyin.gecollsn.db;

import com.ishuyin.gecollsn.utils.DateUtil;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;

/**
 * @author gecollsn
 * @create 5/22/2016
 * @company www.ishuyin.com
 */
@Entity(nameInDb = "bookInfoTable", generateConstructors = false)
public class BookInfo {
    @Id(autoincrement = true)
    @Property(nameInDb = "id")
    private Long id;
    private String order;
    private String title = "";
    private String thumb = "";
    private String bookPlayer = "";
    private String latestUpdate = "";
    private String bookDownloadUrl = "";
    private String createTime = DateUtil.timestamp(true) + "";
    private int download;   // 0：否，1：是
    private int favorite;   // 0：否，1：是
    private int recent;   // 0：否，1：是

    public BookInfo() {
    }

    public String getOrder() {
        return this.order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getThumb() {
        return this.thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

    public String getBookPlayer() {
        return this.bookPlayer;
    }

    public void setBookPlayer(String bookPlayer) {
        this.bookPlayer = bookPlayer;
    }

    public String getLatestUpdate() {
        return this.latestUpdate;
    }

    public void setLatestUpdate(String latestUpdate) {
        this.latestUpdate = latestUpdate;
    }

    public String getBookDownloadUrl() {
        return this.bookDownloadUrl;
    }

    public void setBookDownloadUrl(String bookDownloadUrl) {
        this.bookDownloadUrl = bookDownloadUrl;
    }

    public String getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public int getDownload() {
        return this.download;
    }

    public void setDownload(int download) {
        this.download = download;
    }

    public int getFavorite() {
        return this.favorite;
    }

    public void setFavorite(int favorite) {
        this.favorite = favorite;
    }

    public int getRecent() {
        return this.recent;
    }

    public void setRecent(int recent) {
        this.recent = recent;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}
