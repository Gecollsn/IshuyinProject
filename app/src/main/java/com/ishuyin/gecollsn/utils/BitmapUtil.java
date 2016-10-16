package com.ishuyin.gecollsn.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Base64;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by gecollsn on 2016/3/24.
 * <p/>
 * Company: zjp-company
 * Email: cs@zhijiepay.com
 * TEL: 0592-5101911
 * <p/>
 * Declare: 处理图片相关的辅助类
 */
public final class BitmapUtil {

    /** 设置一个统一的上下文环境 */
    private static Context context;

    public static void initContext(Context context) { BitmapUtil.context = context; }

    /**
     * 保存Bitmap到sdcard
     *
     * @param b
     */
    public static boolean saveBitmap(Bitmap b, String fileName) {
        String path = FileUtil.getBitmapStorePath();
        String jpegName = path + fileName;
        File file = new File(jpegName);
        try {
            FileOutputStream fout = new FileOutputStream(file);
            BufferedOutputStream bos = new BufferedOutputStream(fout);
            b.compress(Bitmap.CompressFormat.JPEG, 100, bos);
            bos.flush();
            bos.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 压缩并保存图片
     *
     * @param bitmap
     * @param quality
     * @param fileName
     */
    public static void saveBitmapWithCompress(Bitmap bitmap, int quality, String fileName) {
        bitmap = compressBitmap(bitmap, quality);
        saveBitmap(bitmap, fileName);
    }

    /**
     * 将图片保存为A4格式
     *
     * @param b
     * @param fileName
     */
    public static void saveBitmapToA4(Bitmap b, String fileName) {
        Bitmap bitmap = Bitmap.createBitmap(2480, 3508, b.getConfig());
        bitmap.eraseColor(0xFFFFFFFF);
        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint();
        Rect rect = new Rect(0, 0, 2480, 3508);
        // canvas.drawRect(rect, paint);
        canvas.drawBitmap(b, 1240 - b.getWidth() / 2, 877 - b.getHeight() / 2, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.MULTIPLY));
        canvas.drawBitmap(b, 1240 - b.getWidth() / 2, 1754 + 877 - b.getHeight() / 2, paint);
        saveBitmap(bitmap, fileName);
    }

    /**
     * 从sdcard中获取bitmap
     *
     * @param fileName 文件名
     */
    public static Bitmap getBitmapFromFile(String fileName) {
        Drawable drawable = getDrawableFromFile(fileName);
        Bitmap bitmap = null;
        if (drawable != null) {
            bitmap = ((BitmapDrawable) drawable).getBitmap();
        }
        return bitmap;
    }

    /**
     * 从sdcard获取bitmapDrawable
     *
     * @param fileName 文件名
     */
    public static Drawable getDrawableFromFile(String fileName) {
        String path = FileUtil.getBitmapStorePath();
        String jpegName = path + fileName;
        File file = new File(jpegName);
        if (!file.exists()) {
            return null;
        }

        BitmapDrawable bd = null;
        InputStream fis = null;
        try {
            fis = new FileInputStream(file);
            bd = new BitmapDrawable(fis);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                fis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return bd;
    }

    /**
     * 图片压缩 以最省内存的方式读取本地资源的图片
     *
     * @param resId 图片id
     */
    public static Bitmap getBitmapFromResource(int resId) {
        BitmapFactory.Options opt = new BitmapFactory.Options();
        opt.inPreferredConfig = Bitmap.Config.ARGB_8888;
        opt.inPurgeable = true;
        opt.inInputShareable = true;
        // 获取资源图片
        InputStream is = context.getResources().openRawResource(resId);
        return BitmapFactory.decodeStream(is, null, opt);
    }

    /**
     * 获取图片的字节数组
     *
     * @param bitmap
     */
    public static byte[] getBitmapBytes(Bitmap bitmap) {
        int nWidth = bitmap.getWidth();
        int nMode = 0;

        int width = ((nWidth + 7) / 8) * 8;
        int height = bitmap.getHeight() * width / bitmap.getWidth();
        height = ((height + 7) / 8) * 8;
        Bitmap rszBitmap = bitmap;
        if (bitmap.getWidth() != width) rszBitmap = resizeBitmap(bitmap, width, height);
        Bitmap grayBitmap = convertBitmapToGrayscale(rszBitmap);
        byte[] dithered = thresholdToBWPic(grayBitmap);
        overWriteBitmap(grayBitmap, dithered);
        return eachLinePixToCmd(dithered, width, nMode);
    }

    /**
     * 质量压缩方法
     *
     * @param bitmap
     * @param quality
     */
    public static Bitmap compressBitmap(Bitmap bitmap, int quality) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, quality, baos);// 质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        int options = quality;
        while (baos.toByteArray().length / 1024 > 100) { // 循环判断如果压缩后图片是否大于100kb,大于继续压缩
            baos.reset();// 重置baos即清空baos
            bitmap.compress(Bitmap.CompressFormat.JPEG, options, baos);// 这里压缩options%，把压缩后的数据存放到baos中
            options -= 10;// 每次都减少10
        }
        ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());// 把压缩后的数据baos存放到ByteArrayInputStream中
        return BitmapFactory.decodeStream(bais, null, null);// 把ByteArrayInputStream数据生成图片
    }

    /**
     * 图片按比例大小压缩（根据Bitmap图片压缩）
     * 根据长和宽中较长的比例缩放
     *
     * @param bitmap
     * @param quality
     */
    public static Bitmap compressBitmap(Bitmap bitmap, int quality, float wPx, float hPx) {
        if (bitmap == null) return null;

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        if (baos.toByteArray().length / 1024 > 1024) {// 判断如果图片大于1M,进行压缩避免在生成图片（BitmapFactory.decodeStream）时溢出
            baos.reset();// 重置baos即清空baos
            bitmap.compress(Bitmap.CompressFormat.JPEG, 50, baos);// 这里压缩50%，把压缩后的数据存放到baos中
        }
        ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        // 开始读入图片，此时把options.inJustDecodeBounds 设回true了
        newOpts.inJustDecodeBounds = true;
        BitmapFactory.decodeStream(bais, null, newOpts);
        // 重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
        newOpts.inJustDecodeBounds = false;
        setBtimapOption(wPx, hPx, newOpts);
        bais = new ByteArrayInputStream(baos.toByteArray());
        Bitmap temp = BitmapFactory.decodeStream(bais, null, newOpts);
        return compressBitmap(temp, quality);// 压缩好比例大小后再进行质量压缩
    }

    /**
     * 图片按给定长宽与实际长宽的比例进行压缩
     *
     * @param bitmapPath 全路径
     * @param quality
     * @param wPx
     * @param hPx
     */
    public static Bitmap compressBitmapFromPath(String bitmapPath, int quality, float wPx, float hPx) {
        File file = new File(bitmapPath);
        if (!file.exists()) return null;

        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        // 开始读入图片，此时把options.inJustDecodeBounds 设回true了
        newOpts.inJustDecodeBounds = true;
        Bitmap bitmap = BitmapFactory.decodeFile(bitmapPath, newOpts);// 此时返回bm为空
        // 重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
        newOpts.inJustDecodeBounds = false;
        setBtimapOption(wPx, hPx, newOpts);
        // 重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
        bitmap = BitmapFactory.decodeFile(bitmapPath, newOpts);
        return compressBitmap(bitmap, quality);// 压缩好比例大小后再进行质量压缩
    }

    /**
     * base64字符串转bitmap
     *
     * @param string
     */
    public static Bitmap base64ToBitmap(String string) {
        // 将字符串转换成Bitmap类型
        Bitmap bitmap = null;
        try {
            byte[] bitmapArray;
            bitmapArray = Base64.decode(string, Base64.DEFAULT);
            bitmap = BitmapFactory.decodeByteArray(bitmapArray, 0, bitmapArray.length);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return bitmap;
    }

    /**
     * bitmap转base64字符串
     *
     * @param bitmap
     */
    public static String bitmapToBase64(Bitmap bitmap) {
        // 将Bitmap转换成字符串
        String string = null;
        ByteArrayOutputStream bStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bStream);
        byte[] bytes = bStream.toByteArray();
        string = Base64.encodeToString(bytes, Base64.DEFAULT);
        return string;
    }

    /**
     * bitmap转drawable
     *
     * @param bmp
     */
    public static Drawable bitmap2Drawable(Bitmap bmp) {
        BitmapDrawable bd = new BitmapDrawable(bmp);
        return bd;
    }

    /**
     * drawable转bitmap
     *
     * @param d
     */
    public static Bitmap drawable2Bitmap(Drawable d) {
        BitmapDrawable bd = (BitmapDrawable) d;
        Bitmap bm = bd.getBitmap();
        return bm;
    }

    /**
     * 矩形圆角图片
     *
     * @param bitmap
     * @param pixels
     */
    public static Bitmap roundRectBitmap(Bitmap bitmap, int pixels) {

        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        final RectF rectF = new RectF(rect);
        final float roundPx = pixels;

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);

        return output;
    }

    /**
     * 绘制圆形图片
     *
     * @param bitmap
     */
    public static Bitmap roundCircleBitmap(Bitmap bitmap) {
        int w = bitmap.getWidth();
        int h = bitmap.getHeight();
        int dia = Math.min(w, h);

        Bitmap output = Bitmap.createBitmap(dia, dia, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);
        final Paint paint = new Paint();
        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        canvas.drawCircle(dia / 2, dia / 2, dia / 2, paint);

        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, 0, 0, paint);

        return output;
    }

    /**
     * 给指定图片添加白背景
     *
     * @param bitmap
     */
    public static Bitmap addWhiteBackground(Bitmap bitmap) {

        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_4444);
        Canvas canvas = new Canvas(output);

        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());

        paint.setAntiAlias(true);
        canvas.drawARGB(255, 255, 255, 255);
        canvas.drawBitmap(bitmap, rect, rect, paint);

        return output;
    }

    /**
     * 以给定的宽高缩放图片
     *
     * @param bitmap
     * @param w
     * @param h
     */
    public static Bitmap resizeBitmap(Bitmap bitmap, int w, int h) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        float scaleWidth = ((float) w) / width;
        float scaleHeight = ((float) h) / height;
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        return Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true);
    }

    /**
     * 转成灰度图
     *
     * @param bmpOriginal
     */
    public static Bitmap convertBitmapToGrayscale(Bitmap bmpOriginal) {
        int width, height;
        height = bmpOriginal.getHeight();
        width = bmpOriginal.getWidth();
        Bitmap bmpGrayscale = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(bmpGrayscale);
        Paint paint = new Paint();
        ColorMatrix cm = new ColorMatrix();
        cm.setSaturation(0);
        ColorMatrixColorFilter f = new ColorMatrixColorFilter(cm);
        paint.setColorFilter(f);
        c.drawBitmap(bmpOriginal, 0, 0, paint);
        return bmpGrayscale;
    }


/*********************************************************************************************************************/
/********************************************************************************************************************/
    /** 以下为类私有方法，提供给本类使用 */

    private static void setBtimapOption(float wPx, float hPx, BitmapFactory.Options newOpts) {
        int w = newOpts.outWidth;
        int h = newOpts.outHeight;
        // 现在主流手机比较多是800*480分辨率，所以高和宽我们设置为
        float hh = hPx;// 这里设置高度为800f
        float ww = wPx;// 这里设置宽度为480f
        // 缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
        int be = 1;// be=1表示不缩放
        if (w > h && w > ww) {// 如果宽度大的话根据宽度固定大小缩放
            be = (int) (newOpts.outWidth / ww);
        } else if (w < h && h > hh) {// 如果高度高的话根据宽度固定大小缩放
            be = (int) (newOpts.outHeight / hh);
        }
        if (be <= 0) be = 1;
        newOpts.inSampleSize = be;// 设置缩放比例
    }

    private static byte[] eachLinePixToCmd(byte[] src, int nWidth, int nMode) {
        int nHeight = src.length / nWidth;
        int nBytesPerLine = nWidth / 8;
        byte[] data = new byte[nHeight * (8 + nBytesPerLine)];
        int offset;
        int k = 0;
        for (int i = 0; i < nHeight; i++) {
            offset = i * (8 + nBytesPerLine);
            data[offset] = 0x1d;
            data[offset + 1] = 0x76;
            data[offset + 2] = 0x30;
            data[offset + 3] = (byte) (nMode & 0x01);
            data[offset + 4] = (byte) (nBytesPerLine % 0x100);
            data[offset + 5] = (byte) (nBytesPerLine / 0x100);
            data[offset + 6] = 0x01;
            data[offset + 7] = 0x00;
            for (int j = 0; j < nBytesPerLine; j++) {
                data[offset + 8 + j] = (byte) (p0[src[k]] + p1[src[k + 1]] + p2[src[k + 2]] + p3[src[k + 3]] +
                        p4[src[k + 4]] + p5[src[k + 5]] + p6[src[k + 6]] + src[k + 7]);
                k = k + 8;
            }
        }

        return data;
    }

    private static byte[] thresholdToBWPic(Bitmap mBitmap) {
        int[] pixels = new int[mBitmap.getWidth() * mBitmap.getHeight()];
        byte[] data = new byte[mBitmap.getWidth() * mBitmap.getHeight()];

        mBitmap.getPixels(pixels, 0, mBitmap.getWidth(), 0, 0, mBitmap.getWidth(), mBitmap.getHeight());

        // for the toGrayscale, we need to select a red or green or blue color
        format_K_threshold(pixels, mBitmap.getWidth(), mBitmap.getHeight(), data);

        return data;
    }

    private static void format_K_threshold(int[] orgpixels, int xsize, int ysize, byte[] despixels) {
        int graytotal = 0, grayave, gray;
        int i, j, k = 0;
        for (i = 0; i < ysize; i++) {
            for (j = 0; j < xsize; j++) {
                gray = orgpixels[k] & 0xff;
                graytotal += gray;
                k++;
            }
        }
        grayave = graytotal / ysize / xsize;

        // 二值化
        k = 0;
        for (i = 0; i < ysize; i++) {
            for (j = 0; j < xsize; j++) {
                gray = orgpixels[k] & 0xff;
                if (gray > grayave) despixels[k] = 0;// white
                else despixels[k] = 1;
                k++;
            }
        }
    }

    private static void overWriteBitmap(Bitmap mBitmap, byte[] dithered) {
        int h = mBitmap.getHeight();
        int w = mBitmap.getWidth();

        int k = 0;
        for (int i = 0; i < h; i++) {
            for (int j = 0; j < w; j++) {
                if (dithered[k] == 0) mBitmap.setPixel(j, i, Color.WHITE);
                else mBitmap.setPixel(j, i, Color.BLACK);
                k++;
            }
        }
    }

    // nWidth必须为8的倍数,这个只需在上层控制即可
    // 之所以弄成一维数组，是因为一维数组速度会快一点
    private static int[] p0 = {0, 0x80};
    private static int[] p1 = {0, 0x40};
    private static int[] p2 = {0, 0x20};
    private static int[] p3 = {0, 0x10};
    private static int[] p4 = {0, 0x08};
    private static int[] p5 = {0, 0x04};
    private static int[] p6 = {0, 0x02};
}
