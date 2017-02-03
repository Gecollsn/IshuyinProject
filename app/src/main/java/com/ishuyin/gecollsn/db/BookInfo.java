package com.ishuyin.gecollsn.db;

import com.ishuyin.gecollsn.utils.DateUtil;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

/**
 * @author gecollsn
 * @create 5/22/2016
 * @company www.ishuyin.com
 */
@Entity
public class BookInfo {
    @Id(autoincrement = true)
    private long id;
    private String title = "";
    private String thumb = "";
    private String bookPlayer = "";
    private String latestUpdate = "";
    private String bookDownloadUrl = "";
    private String createTime = DateUtil.timestamp(true) + "";
    private int type;   // 0：我的下载，1：我的收藏，2：最近收听



    @Generated(hash = 1724191567)
    public BookInfo(long id, String title, String thumb, String bookPlayer,
            String latestUpdate, String bookDownloadUrl, String createTime,
            int type) {
        this.id = id;
        this.title = title;
        this.thumb = thumb;
        this.bookPlayer = bookPlayer;
        this.latestUpdate = latestUpdate;
        this.bookDownloadUrl = bookDownloadUrl;
        this.createTime = createTime;
        this.type = type;
    }


    @Generated(hash = 1952025412)
    public BookInfo() {
    }


    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
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

    public int getType() {
        return this.type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
