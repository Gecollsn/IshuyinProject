package com.ishuyin.gecollsn.accueilBlock.model;

import com.ishuyin.gecollsn.accueilBlock.AC;
import com.ishuyin.gecollsn.base.BaseApplication;
import com.ishuyin.gecollsn.db.BookInfo;
import com.ishuyin.gecollsn.green.BookInfoDao;

import java.util.List;


public class AccueilModel {
    /**
     * 最近下载的书
     */
    public static List<BookInfo> getDownloadBooks() {
        BookInfoDao bookInfoDao = getBookDao();
        return bookInfoDao.queryBuilder().where(BookInfoDao.Properties.Download.eq(1)).orderDesc(BookInfoDao
                .Properties.Id)
                .build().list();
    }

    /**
     * 添加我的下载
     *
     * @param info
     */
    public static void addDownloadBook(BookInfo info) {
        info.setDownload(AC.type.BOOK_DOWNLOAD);
        removeDuplicateData(info);
        insertInfo(info);
    }


    /**
     * 收藏的书
     */
    public static List<BookInfo> getFavoriteBooks() {
        BookInfoDao bookInfoDao = getBookDao();
        return bookInfoDao.queryBuilder().where(BookInfoDao.Properties.Favorite.eq(1)).orderDesc(BookInfoDao.Properties
                .Id).build().list();
    }

    /**
     * 添加我的收藏
     *
     * @param info
     */
    public static void addFavoriteBook(BookInfo info) {
        info.setFavorite(AC.type.BOOK_FAVORITE);
        removeDuplicateData(info);
        insertInfo(info);
    }

    /**
     * 最近收听的书
     */
    public static List<BookInfo> getRecentBooks() {
        BookInfoDao bookInfoDao = getBookDao();
        return bookInfoDao.queryBuilder().where(BookInfoDao.Properties.Recent.eq(1)).orderDesc(BookInfoDao.Properties
                .Id).build().list();
    }

    /**
     * 添加我的下载
     *
     * @param info
     */
    public static void addRecentBook(BookInfo info) {
        info.setRecent(AC.type.BOOK_RECENT);
        removeDuplicateData(info);
        insertInfo(info);
    }

    /**
     * 清除某条记录
     *
     * @param info
     */
    public static void removeBook(BookInfo info) {
        if (!checkInfoExist(info)) return;
        if (info.getDownload() == AC.type.BOOK_DEFAULT && info.getFavorite() == AC.type.BOOK_DEFAULT && info
                .getRecent() == AC.type.BOOK_DEFAULT)
            getBookDao().delete(info);
        else getBookDao().update(info);
    }

    /*----------------------------- 私有方法区域 -------------------------------------*/
    private static BookInfoDao getBookDao() {
        return BaseApplication.getInstance().getDaoSession().getBookInfoDao();
    }

    private static boolean checkInfoExist(BookInfo info) {
        return info.getId() != null && getBookDao().queryBuilder().where(BookInfoDao.Properties.Id.eq(info.getId()))
                .list().size() > 0;
    }

    private static void insertInfo(BookInfo info) {
        if (checkInfoExist(info)) getBookDao().update(info);
        else info.setId(getBookDao().insert(info));
    }

    private static void removeDuplicateData(BookInfo info) {
//        bookDao.delete(info);
    }
}
