package com.ishuyin.gecollsn.utils;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by gecollsn on 2016/4/11 17:23.
 * <p>
 * Company: zjp-company
 * Email: cs@zhijiepay.com
 * TEL: 0592-5101911
 * <p>
 * Declare: TPoolUtil 统一的线程池管理类
 */
public class ThreadUtil {
    private static Executor mainExe;
    private static Executor singleExe;

    public static void initThreadPool() {
        if (mainExe != null) return;
        mainExe = new ThreadPoolExecutor(10, 20, 3 * 60, TimeUnit.MILLISECONDS, new ArrayBlockingQueue<Runnable>(10,
                true));
        singleExe = Executors.newSingleThreadExecutor();
    }

    public static void execute(Runnable command) {
        mainExe.execute(command);
    }

    public static void executeSingle(Runnable command) {
        singleExe.execute(command);
    }
}
