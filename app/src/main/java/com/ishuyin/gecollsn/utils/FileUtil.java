package com.ishuyin.gecollsn.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;

import com.ishuyin.gecollsn.GBC;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.nio.charset.Charset;

import info.monitorenter.cpdetector.io.ASCIIDetector;
import info.monitorenter.cpdetector.io.CodepageDetectorProxy;
import info.monitorenter.cpdetector.io.JChardetFacade;
import info.monitorenter.cpdetector.io.ParsingDetector;
import info.monitorenter.cpdetector.io.UnicodeDetector;


/**
 * Created by gecollsn on 2016/3/24 10:59.
 * <p/>
 * Company: zjp-company
 * Email: cs@zhijiepay.com
 * TEL: 0592-5101911
 * <p/>
 * Declare: 文件管理类, 用于执行IO操作
 */
public final class FileUtil {

    private static final String FILE_NAME_SUFFIX = ".log";

    public static void writeFile(String filePath, String content) {
        writeFile(filePath, content, false);
    }

    public static void writeFile(String filePath, String content, boolean append) {
        writeFile(filePath, content, append, false);
    }

    /**
     * 将字符串内容写入到抽象路径名所表示的文件中
     *
     * @param filePath
     * @param content
     * @param append   是否追加
     * @param segment  是否在追加后有分割线
     */
    public static void writeFile(String filePath, String content, boolean append, boolean segment) {
        FileOutputStream fos = null;
        try {
            File file = new File(filePath);
            if (!file.exists()) {
                if (file.isDirectory()) throw new RuntimeException("File must not be a directory");
                createFile(file);
            }

            fos = new FileOutputStream(file, append);
        } catch (IOException e) {
            e.printStackTrace();
        }
        writeStr2Stream(content, fos, segment);
    }

    /**
     * 将字节数组保存到文件中
     *
     * @param buffer
     * @param offset
     * @param length
     * @param filePath
     */
    public static void writeFile(String filePath, byte[] buffer, int offset, int length) {
        if (null == filePath) return;
        if (null == buffer) return;
        if (offset < 0 || length <= 0) return;

        try {
            File file = new File(filePath);
            createFile(file);
            RandomAccessFile raf = new RandomAccessFile(file, "rw");
            raf.seek(file.length());
            raf.write(buffer, offset, length);
            raf.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 写文件的快捷方式
     *
     * @param fileName
     * @param content
     * @param append
     *
     * @throws IOException
     */
    public static void writeFileToCommonPath(String fileName, String content, boolean append) {
        String filePath = GBC.file.SDCARD_COMMON_PATH + fileName;
        writeFile(filePath, content, append);
    }

    /**
     * 复制文件
     *
     * @param orgPath 源路径
     * @param dstPath 目标路径
     */
    public static void copyFile(String orgPath, String dstPath) throws IOException {
        File org = new File(orgPath);
        File dst = new File(dstPath);
        createFile(dst);
        FileInputStream orgFis = new FileInputStream(org);
        FileOutputStream dstFos = new FileOutputStream(dst);
        writeStream2Stream(orgFis, dstFos);
    }

    /**
     * 通过传入的路径创建File文件
     *
     * @param path
     */
    public static File createFile(String path) {
        File file = new File(path);
        try {
            createFile(file);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        return file;
    }

    /**
     * 读取文件内容，以输入流的形式返回
     *
     * @param filePath
     */
    public static InputStream readFile2Stream(String filePath) {
        File file = new File(filePath);
        if (!file.exists()) return null;

        try {
            return new FileInputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 读取文件内容，以字符串的形式返回
     *
     * @param filePath
     */
    public static String readFile2Str(String filePath) {
        File file = new File(filePath);
        if (!file.exists()) return null;

        try {
            FileInputStream fis = new FileInputStream(file);
            byte[] bytes = readStream2Bytes(fis);
            return new String(bytes, "UTF-8");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "";
    }

    /**
     * 将输入流转化为byte[]
     *
     * @param inStream
     *
     * @throws Exception
     */
    public static byte[] readStream2Bytes(InputStream inStream) throws IOException {
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len;
        while ((len = inStream.read(buffer)) != -1) {
            outStream.write(buffer, 0, len);
        }
        byte[] data = outStream.toByteArray();
        outStream.flush();
        outStream.close();
        inStream.close();
        return data;
    }

    /**
     * 获取文件的长度
     *
     * @param filePath
     */
    public static long getFileSize(String filePath) {
        File file = new File(filePath);
        return file.exists() ? file.length() : -1;
    }

    /**
     * 获取输入流的长度
     *
     * @param is
     */
    public static long getStreamSize(InputStream is) {
        FileInputStream fis;
        try {
            if (is instanceof FileInputStream) fis = (FileInputStream) is;
            else return is.available();
            return fis.getChannel().size();
        } catch (IOException e) {
            e.printStackTrace();
            return -1;
        }
    }

    /**
     * 获取图片储存的路径
     */
    public static String getBitmapStorePath() {
        File file = new File(GBC.file.DIRECT_BITMAP_STORE);
        if (!file.exists()) file.mkdirs();
        return file.getAbsolutePath();
    }

    /**
     * 获取文件的编码格式
     *
     * @param file
     */
    public static String getFileEncoding(File file) {

        CodepageDetectorProxy detector = CodepageDetectorProxy.getInstance();

        detector.add(new ParsingDetector(false));
        detector.add(JChardetFacade.getInstance());
        detector.add(ASCIIDetector.getInstance());
        detector.add(UnicodeDetector.getInstance());
        Charset charset = null;
        try {
            charset = detector.detectCodepage(file.toURI().toURL());
        } catch (Exception e) {
            e.printStackTrace();
        }

        String charsetName = "GBK";
        if (charset != null) {
            if (charset.name().equals("US-ASCII")) {
                charsetName = "ISO_8859_1";
            } else if (charset.name().startsWith("UTF")) {
                charsetName = charset.name();// 例如:UTF-8,UTF-16BE.
            }
        }
        if ("GBK".equals(charsetName)) {
            charsetName = ensureEncode(file);
        }
        return charsetName;
    }

    /**
     * 从Asset文件中获取输入流
     *
     * @param context
     * @param fileName
     */
    public static InputStream getStreamFromAsset(Context context, String fileName) throws IOException {
        return context.getAssets().open(fileName);
    }

    /**
     * 从raw文件中获取输入流
     *
     * @param context
     * @param resId
     */
    public static InputStream getStreamFromRaw(Context context, int resId) {
        return context.getResources().openRawResource(resId);
    }

    /**
     * 删除此路径名下的所表示的文件或目录
     * 谨慎操作！！
     *
     * @param filePath
     */
    public static void deleteFile(String filePath) {
        File file = new File(filePath);
        if (!file.exists()) return;
        deleteFileRecusive(file);
    }

    /**
     * 清除本应用内部缓存(/data/data/com.xxx.xxx/cache)
     *
     * @param context
     */
    public static void deleteInternalCache(Context context) {
        deleteFile(context.getCacheDir().getAbsolutePath());
    }

    /**
     * 清除本应用外部缓存
     *
     * @param context
     */
    public static void deleteAllCache(Context context) {
        deleteFile(context.getCacheDir().getAbsolutePath());
        if (SDCardUtil.isSDCardEnable()) {
            deleteFile(context.getExternalCacheDir().getAbsolutePath());
        }
    }

    /**
     * 清除本应用所有数据库(/data/data/com.xxx.xxx/databases)
     *
     * @param context
     */
    public static void deleteDatabases(Context context) {
        deleteFile("/data/data/" + context.getPackageName() + "/databases");
    }

    /**
     * 清除本应用SharedPreference(/data/data/com.xxx.xxx/shared_prefs)
     *
     * @param context
     */
    public static void deleteSharedPreference(Context context) {
        deleteFile("/data/data/" + context.getPackageName() + "/shared_prefs");
    }

    /**
     * 通过数据库名称清除本应用数据库
     *
     * @param context
     * @param dbName
     */
    public static void deleteDatabaseByName(Context context, String dbName) {
        context.deleteDatabase(dbName);
    }

    /**
     * 清除/data/data/com.xxx.xxx/files
     *
     * @param context
     */
    public static void deleteInternalFiles(Context context) {
        deleteFile(context.getFilesDir().getAbsolutePath());
    }

    /**
     * 清除外部cache下的内容(/mnt/sdcard/android/data/com.xxx.xxx/cache)
     *
     * @param context
     */
    public static void deleteExternalCache(Context context) {
        if (SDCardUtil.isSDCardEnable()) {
            deleteFile(context.getExternalCacheDir().getAbsolutePath());
        }
    }

    /**
     * 清除本应用所有的数据
     *
     * @param context
     * @param filepath
     */
    public static void deleteApplicationData(Context context, String... filepath) {
        deleteInternalCache(context);
        deleteExternalCache(context);
        deleteDatabases(context);
        deleteSharedPreference(context);
        deleteInternalFiles(context);
        for (String filePath : filepath) {
            deleteFile(filePath);
        }
    }

    /**
     * 保存崩溃日记
     *
     * @param context
     * @param e
     *
     * @throws IOException
     */
    public static void saveCrashLogFile(Context context, Throwable e) throws IOException {
        File file = new File(GBC.file.DIRECT_CRASH_LOG + DateUtil.date(DateUtil.DATE_ULINE) + FILE_NAME_SUFFIX);
        PrintWriter pw = null;
        if (!file.exists()) {
            createFile(file);
            pw = new PrintWriter(new FileOutputStream(file, true));
            pw.println(DateUtil.date());
            dumpPhoneInfo(context, pw);
        }

        if (null == pw) pw = new PrintWriter(new FileOutputStream(file, true));
        pw.print("\r\n\r\n------------------------------------------------");
        pw.println(DateUtil.date());
        e.printStackTrace(pw);
        pw.close();
    }

    /**
     * 保存普通日记
     *
     * @param content
     *
     * @throws IOException
     */
    public static void saveCommonLogFile(String content) throws IOException {
        writeFile(GBC.file.DIRECT_COMMON_LOG + DateUtil.date(DateUtil.DATE_ULINE) + FILE_NAME_SUFFIX, content, true);
    }

    /**
     * 关闭流的简易做法
     *
     * @param is
     */
    public static void closeInputStream(InputStream is) {
        if (is == null) return;
        try {
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 关闭流的简易做法
     *
     * @param os
     */
    public static void closeOutputStream(OutputStream os) {
        if (os == null) return;
        try {
            os.flush();
            os.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }    


/*-----------------------以下为工具类的私有方法，仅供类中的方法使用-------------------------------------- */

    /**
     * 拿到文本文件的编码格式，UTF-8，x-UTF-16LE-BOM，Unicode big
     * endian,GBK，但本方法无法判断出UTF-8无BOM
     *
     * @param file
     */
    private static String ensureEncode(File file) {
        String charSet = "";
        try {
            FileInputStream fis = new FileInputStream(file);
            byte[] bf = new byte[3];
            fis.read(bf);
            fis.close();
            if (bf[0] == -17 && bf[1] == -69 && bf[2] == -65) {
                charSet = "UTF-8";// "文件编码 UTF-8"
            } else if ((bf[0] == -1 && bf[1] == -2)) {
                charSet = "x-UTF-16LE-BOM";// "文件编码 Unicode"
            } else if ((bf[0] == -2 && bf[1] == -1)) {
                charSet = "Unicode";// "文件编码 Unicode big endian"
            } else {
                charSet = "GBK";// "文件编码 ANSI"
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return charSet;
    }

    private static void writeStr2Stream(String content, OutputStream os, boolean nextLine) {
        if (nextLine) content = "\r\n\r\n---------------------------------\r\n\r\n" + content;
        try {
            byte[] bytes = content.getBytes("utf-8");
            os.write(bytes);
            os.flush();
            os.close();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void writeStream2Stream(InputStream is, OutputStream os) {
        byte[] buffer = new byte[1024];
        int data;
        try {
            while (-1 != (data = is.read(buffer))) {
                os.write(buffer, 0, data);
            }
            is.close();
            os.flush();
            os.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void createFile(File file) throws IOException {
        if (file.exists()) return;
        if (file.isDirectory()) {
            file.mkdirs();
            return;
        }
        File parent = file.getParentFile();
        if (!parent.exists()) parent.mkdirs();
        file.createNewFile();
    }

    private static void deleteFileRecusive(File file) {
        if (file.isDirectory()) {
            File[] childFiles = file.listFiles();
            for (File child : childFiles) deleteFileRecusive(child);
            file.delete();
        } else file.delete();
    }

    private static long getFileLengthRecusive(File file, long length) {
        if (!file.isDirectory()) return file.length();
        File[] files = file.listFiles();
        for (File temp : files) {
            if (temp.isDirectory()) length = getFileLengthRecusive(temp, length);
            else length += temp.length();
        }

        return length;
    }

    private static String formatLengthUnit(double size) {
        double kiloByte = size / 1024;
        if (kiloByte < 1) {
            return "0K";
        }

        double megaByte = kiloByte / 1024;
        if (megaByte < 1) {
            BigDecimal result1 = new BigDecimal(Double.toString(kiloByte));
            return result1.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "KB";
        }

        double gigaByte = megaByte / 1024;
        if (gigaByte < 1) {
            BigDecimal result2 = new BigDecimal(Double.toString(megaByte));
            return result2.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "MB";
        }

        double teraBytes = gigaByte / 1024;
        if (teraBytes < 1) {
            BigDecimal result3 = new BigDecimal(Double.toString(gigaByte));
            return result3.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "GB";
        }
        BigDecimal result4 = new BigDecimal(teraBytes);
        return result4.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "TB";
    }

    private static void dumpPhoneInfo(Context context, PrintWriter pw) {
        try {
            PackageManager pm = context.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(context.getPackageName(), PackageManager.GET_ACTIVITIES);
            //application version info
            pw.print("App Version: ");
            pw.print(pi.versionName);
            pw.print("_");
            pw.println(pi.versionCode);
            //android edition
            pw.print("OS Version: ");
            pw.print(Build.VERSION.RELEASE);
            pw.print("_");
            pw.println(Build.VERSION.SDK_INT);
            //machine factory
            pw.print("Vendor: ");
            pw.println(Build.MANUFACTURER);
            //machine model
            pw.print("Model: ");
            pw.println(Build.MODEL);
            //cpu abi
            pw.print("CPU ABI: ");
            pw.println(Build.CPU_ABI);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }
}
