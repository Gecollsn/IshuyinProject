package com.ishuyin.gecollsn.base;

import android.app.Activity;
import android.app.Application;
import android.database.sqlite.SQLiteDatabase;

import com.ishuyin.gecollsn.green.DaoMaster;
import com.ishuyin.gecollsn.green.DaoSession;
import com.ishuyin.gecollsn.utils.DensityUtil;
import com.ishuyin.gecollsn.utils.ThreadUtil;
import com.ishuyin.gecollsn.utils.ToastUtil;
import com.ishuyin.gecollsn.utils.spUtil.SPUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @author gecollsn
 * @create 5/12/2016
 * @company www.ishuyin.com
 */
public class BaseApplication extends Application {

    private List<Activity> activities = new ArrayList<>();
    private static BaseApplication mInstance;
    private DaoMaster.DevOpenHelper mHelper;
    private SQLiteDatabase db;
    private DaoMaster mDaoMaster;
    private DaoSession mDaoSession;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        registerUtils();
        initDatabase();
    }

    private void initDatabase() {
        mHelper = new DaoMaster.DevOpenHelper(this, "UniteMainSql.db");
        db = mHelper.getWritableDatabase();
        mDaoMaster = new DaoMaster(db);
        mDaoSession = mDaoMaster.newSession();
    }

    public DaoSession getDaoSession() {
        return mDaoSession;
    }

    public SQLiteDatabase getDb() {
        return db;
    }

    private void registerUtils() {
        DensityUtil.initContext(getApplicationContext());
        ToastUtil.initContext(getApplicationContext());
        ThreadUtil.initThreadPool();
        SPUtil.initContext(getApplicationContext());
    }

    public static BaseApplication getInstance() {
        return mInstance;
    }

    public void loadActivity(Activity activity) {
        if (!activities.contains(activity)) activities.add(activity);
    }

    public void unloadActivity(Activity activity) {
        activities.remove(activity);
    }

    public void clearAboveActivity() {
        for (int i = 0; i < activities.size() - 1; i++) {
            activities.remove(i);
            activities.get(i).finish();
        }
    }

    public void clearAllActivity() {
        for (int i = 0; i < activities.size(); i++) {
            activities.remove(i);
            activities.get(i).finish();
        }
    }
}
