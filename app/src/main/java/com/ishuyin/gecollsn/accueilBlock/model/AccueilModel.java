package com.ishuyin.gecollsn.accueilBlock.model;

import com.ishuyin.gecollsn.accueilBlock.AC;
import com.ishuyin.gecollsn.accueilBlock.domain.BookInfo;
import com.ishuyin.gecollsn.base.BaseApplication;
import com.ishuyin.gecollsn.gen.BookInfoDao;

import java.util.List;

/**
 * Created by Gecollsn on 2016/10/5.
 */
public class AccueilModel {
    /** 最近下载的书 */
    public static List<BookInfo> getDownloadBooks() {
        BookInfoDao bookInfoDao = getBookDao();
        return bookInfoDao.queryBuilder().where(BookInfoDao.Properties.Type.eq(0)).orderDesc(BookInfoDao.Properties.Id)
                .build().list();
    }

    /**
     * 添加我的下载
     *
     * @param info
     */
    public static void addDownloadBook(BookInfo info) {
        info.setType(AC.type.BOOK_DOWNLOAD);
        setBookId(info);
        BookInfoDao bookDao = getBookDao();
        removeDuplicateData(info, bookDao);
        bookDao.insert(info);
    }


    /** 收藏的书 */
    public static List<BookInfo> getFavoriteBooks() {
        BookInfoDao bookInfoDao = getBookDao();
        return bookInfoDao.queryBuilder().where(BookInfoDao.Properties.Type.eq(1)).orderDesc(BookInfoDao.Properties
                .Id).build().list();
    }

    /**
     * 添加我的收藏
     *
     * @param info
     */
    public static void addFavoriteBook(BookInfo info) {
        info.setType(AC.type.BOOK_FAVORITE);
        setBookId(info);
        BookInfoDao bookDao = getBookDao();
        removeDuplicateData(info, bookDao);
        bookDao.insert(info);
    }

    /** 最近收听的书 */
    public static List<BookInfo> getRecentBooks() {
        BookInfoDao bookInfoDao = getBookDao();
        return bookInfoDao.queryBuilder().where(BookInfoDao.Properties.Type.eq(2)).orderDesc(BookInfoDao.Properties
                .Id).build().list();
    }

    /**
     * 添加我的下载
     *
     * @param info
     */
    public static void addRecentBook(BookInfo info) {
        info.setType(AC.type.BOOK_RECENT);
        setBookId(info);
        BookInfoDao bookDao = getBookDao();
        removeDuplicateData(info, bookDao);
        bookDao.insert(info);
    }

    /*----------------------------- 私有方法区域 -------------------------------------*/
    private static BookInfoDao getBookDao() {
        return BaseApplication.getInstance().getDaoSession().getBookInfoDao();
    }


    private static void removeDuplicateData(BookInfo info, BookInfoDao bookDao) {
//        bookDao.delete(info);
    }

    private static void setBookId(BookInfo info) {
        info.setId((int) (Math.random() * 1000));
    }
}
