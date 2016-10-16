package com.ishuyin.gecollsn.utils;

import java.util.Locale;
import java.util.Random;

/**
 * Created by gecollsn on 2016/3/24.
 *
 * Company: zjp-company
 * Email: cs@zhijiepay.com
 * TEL: 0592-5101911
 *
 * Declare: 数据处理工具, byte, char等
 */
public class DataUtil {

    private static final byte hexByte[] = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 0, 0, 0, 0, 0, 0, 0, 0xA, 0xB, 0xC, 0xD,
            0xE, 0xF};
    private static final char hexChar[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D',
            'E', 'F'};

    /**
     * 判断两个字节数组是否相同
     *
     * @param d1
     * @param d2
     *
     * @return
     */
    public static boolean bytesEquals(byte[] d1, byte[] d2) {
        if (d1 == null && d2 == null) return true;
        else if (d1 == null || d2 == null) return false;

        if (d1.length != d2.length) return false;

        for (int i = 0; i < d1.length; i++)
            if (d1[i] != d2[i]) return false;

        return true;
    }

    /**
     * 通过给定起始位置和长度判断两个字节数组是否相同
     *
     * @param d1
     * @param offset1
     * @param d2
     * @param offset2
     * @param length
     *
     * @return
     */
    public static boolean bytesEquals(byte[] d1, int offset1, byte[] d2, int offset2, int length) {
        if (d1 == null || d2 == null) return false;

        if ((offset1 + length > d1.length) || (offset2 + length > d2.length)) return false;

        for (int i = 0; i < length; i++)
            if (d1[i + offset1] != d2[i + offset2]) return false;

        return true;
    }

    /**
     * 获取一个指定长度的随机字节数组.
     * 随机数为[0~spec[0])或[0~256), 数组长度为nlength
     *
     * @param nlength
     * @param spec
     *
     * @return
     */
    public static byte[] getRandomBytes(int nlength, int... spec) {
        int n = 256;
        if (spec.length > 0) n = spec[0];
        byte[] data = new byte[nlength];
        Random rmByte = new Random(System.currentTimeMillis());
        for (int i = 0; i < nlength; i++) {
            // 该方法的作用是生成一个随机的int值，该值介于[0,n)的区间，也就是0到n之间的随机int值，包含0而不包含n
            data[i] = (byte) rmByte.nextInt(n);
        }
        return data;
    }

    /**
     * 对字节数组进行取反操作
     *
     * @param data
     */
    public static void oppositeBytes(byte data[]) {
        for (int i = 0; i < data.length; i++) {
            data[i] = (byte) ~(data[i] & 0xff);
        }
    }

    /**
     * 通过给的起始位置和长度, 截取字节数组
     *
     * @param org
     * @param start
     * @param length
     *
     * @return
     */
    public static byte[] subBytes(byte[] org, int start, int length) {
        byte[] ret = new byte[length];
        for (int i = 0; i < length; i++) {
            ret[i] = org[i + start];
        }
        return ret;
    }

    /**
     * 克隆字节数组
     *
     * @param data
     *
     * @return
     */
    public static byte[] cloneBytes(byte[] data) {
        byte[] ret = new byte[data.length];
        for (int i = 0; i < data.length; i++)
            ret[i] = data[i];
        return ret;
    }

    /**
     * 复制数组中的元素到另一个数组
     *
     * @param orgdata
     * @param orgstart
     * @param desdata
     * @param desstart
     * @param copylen
     */
    public static void copyBytes(byte[] orgdata, int orgstart, byte[] desdata, int desstart, int copylen) {
        for (int i = 0; i < copylen; i++) {
            desdata[desstart + i] = orgdata[orgstart + i];
        }
    }

    /**
     * 异或数组中的元素
     * 使用场景：数组中查找唯一的不同元素
     *
     * @param data
     * @param offset
     * @param length
     *
     * @return
     */
    public static byte bytes2xor(byte[] data, int offset, int length) {
        if (length == 0) return 0;
        else if (length == 1) return data[offset];
        else {
            int result = data[offset] ^ data[offset + 1];
            for (int i = offset + 2; i < offset + length; i++)
                result ^= data[i];
            return (byte) result;
        }
    }

    /**
     * 将二维字节数组按顺序合并为一维数组
     *
     * @param data
     *
     * @return
     */
    public static byte[] bytes2byte(byte[][] data) {
        int length = 0;
        for (int i = 0; i < data.length; i++)
            length += data[i].length;
        byte[] send = new byte[length];
        int k = 0;
        for (int i = 0; i < data.length; i++)
            for (int j = 0; j < data[i].length; j++)
                send[k++] = data[i][j];
        return send;
    }

    /**
     * 将字节数组转换成字符数组
     *
     * @param data
     *
     * @return
     */
    public static char[] bytes2chars(byte[] data) {
        char[] cdata = new char[data.length];
        for (int i = 0; i < cdata.length; i++)
            cdata[i] = (char) (data[i] & 0xff);
        return cdata;
    }

    /**
     * 将字节数组中的每一个元素转换成十六进制, 添加到字符串中
     * 字符串中元素间以空格分隔开, 每15个元素为一行
     * 有格式化
     *
     * @param rcs
     *
     * @return
     */
    public static String bytes2HexFormatStr(byte[] rcs) {
        StringBuilder stringBuilder = new StringBuilder();
        String tmp;
        for (int i = 0; i < rcs.length; i++) {
            tmp = Integer.toHexString(rcs[i] & 0xff);
            tmp = tmp.toUpperCase(Locale.getDefault());
            if (tmp.length() == 1) stringBuilder.append("0x0" + tmp);
            else stringBuilder.append("0x" + tmp);

            if (i == rcs.length - 1) continue;
            if ((i % 14) == 0 && i != 0) stringBuilder.append("\n");
            else stringBuilder.append(" ");
        }
        return stringBuilder.toString();
    }

    /**
     * 重载函数, 指定数组起始位置和长度
     * 有格式化
     *
     * @param rcs
     * @param offset
     * @param count
     *
     * @return
     */
    public static String bytes2HexFormatStr(byte[] rcs, int offset, int count) {
        StringBuilder stringBuilder = new StringBuilder();
        String tmp;
        for (int i = 0; i < count; i++) {
            tmp = Integer.toHexString(rcs[i + offset] & 0xff);
            tmp = tmp.toUpperCase(Locale.getDefault());
            if (tmp.length() == 1) stringBuilder.append("0x0" + tmp);
            else stringBuilder.append("0x" + tmp);

            if (i == rcs.length - 1) continue;
            if ((i % 14) == 0 && i != 0) stringBuilder.append("\n");
            else stringBuilder.append(" ");
        }
        return stringBuilder.toString();
    }

    /**
     * 重载函数, 指定数组起始位置和长度
     * 无格式化
     *
     * @param data
     * @param offset
     * @param count
     *
     * @return
     */
    public static StringBuilder bytes2HexStr(byte[] data, int offset, int count) {
        StringBuilder str = new StringBuilder();
        for (int i = offset; i < offset + count; i++) {
            str.append(byte2HexChars(data[i]));
        }
        return str;
    }

    /**
     * 字节转16进制字符数组
     *
     * @param b
     *
     * @return
     */
    public static char[] byte2HexChars(byte b) {
        char chs[] = {'0', '0'};
        chs[0] = hexChar[(b >> 4) & 0xF];
        chs[1] = hexChar[(b) & 0xF];
        return chs;
    }

    /**
     * 字节转16进制字符串
     *
     * @param b
     *
     * @return
     */
    public static String byte2Hex(byte b) {
        String hex;
        String tmp = Integer.toHexString(b & 0xff);
        tmp = tmp.toUpperCase(Locale.getDefault());

        if (tmp.length() == 1) {
            hex = "0x0" + tmp;
        } else {
            hex = "0x" + tmp;
        }

        return hex;
    }

    /**
     * 将16进制字符串转成byte
     *
     * @param hexStr
     *
     * @return
     */
    public static byte hex2Byte(String hexStr) {
        hexStr = hexStr.toUpperCase();
        if (hexStr.startsWith("0X")) hexStr = hexStr.replace("0X", "");
        return (byte) Integer.parseInt(hexStr, 16);
    }

    /**
     * 重载函数
     * 将16进制字符数组转成byte
     *
     * @param ch
     * @param cl
     *
     * @return
     */
    public static byte hex2Byte(char ch, char cl) {
        byte b = (byte) (((hexByte[ch - '0'] << 4) & 0xF0) | ((hexByte[cl - '0']) & 0xF));
        return b;
    }

    /**
     * 16进制字符串转字节数组
     *
     * @param str
     *
     * @return
     */
    public static byte[] hex2Bytes(String str) {
        int count = str.length();
        byte[] data = null;
        if (count % 2 == 0) {
            data = new byte[count / 2];
            for (int i = 0; i < count; i += 2) {
                char ch = str.charAt(i);
                char cl = str.charAt(i + 1);
                if (isHexChar(ch) && isHexChar(cl)) {
                    if (ch >= 'a') ch -= 0x20;
                    if (cl >= 'a') cl -= 0x20;
                    data[i / 2] = hex2Byte(ch, cl);
                } else {
                    data = null;
                    break;
                }
            }
        }
        return data;
    }

    /**
     * 判断是否为16进制字符
     *
     * @param c
     *
     * @return
     */
    public static boolean isHexChar(char c) {
        if ((c >= '0' && c <= '9') || (c >= 'a' && c <= 'f') || (c >= 'A' && c <= 'F')) return true;
        else return false;
    }
}
