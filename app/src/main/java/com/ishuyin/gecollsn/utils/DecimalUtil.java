package com.ishuyin.gecollsn.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;

/**
 * Created by gecollsn on 2016/3/24.
 * <p/>
 * Company: zjp-company
 * Email: cs@zhijiepay.com
 * TEL: 0592-5101911
 * <p/>
 * Declare: 数字处理相关的辅助类, 例如处理小数点后的位数...
 */
public final class DecimalUtil {
    public static final String INTEGER0 = "#0";
    public static final String BIT_2_START_0 = "0.00";
    public static final String BIT_2_START_DOT = "#.00";
    public static final String BIT_2_START_DOT_PERCENT = "#.00%";

    private final static DecimalFormat df = new DecimalFormat(BIT_2_START_0);

    /**
     * 提供精确加法计算的add方法
     *
     * @param value1
     * @param value2
     */
    public static double add(double value1, double value2) {
        BigDecimal b1 = new BigDecimal(Double.valueOf(value1));
        BigDecimal b2 = new BigDecimal(Double.valueOf(value2));
        return b1.add(b2).doubleValue();
    }

    /**
     * 两个参数的差  
     *
     * @param value1
     * @param value2
     */
    public static double sub(double value1, double value2) {
        BigDecimal b1 = new BigDecimal(Double.valueOf(value1));
        BigDecimal b2 = new BigDecimal(Double.valueOf(value2));
        return b1.subtract(b2).doubleValue();
    }

    /**
     * 两个参数的积  
     *
     * @param value1
     * @param value2
     */
    public static double mul(double value1, double value2) {
        BigDecimal b1 = new BigDecimal(Double.valueOf(value1));
        BigDecimal b2 = new BigDecimal(Double.valueOf(value2));
        return b1.multiply(b2).doubleValue();
    }

    /**
     * 两个参数的商
     *
     * @param value1
     * @param value2
     * @param scale  精确范围
     *
     * @throws IllegalAccessException
     */
    public static double div(double value1, double value2, int scale) throws IllegalAccessException {
        // 如果精确范围小于0，抛出异常信息
        if (scale < 0) {
            throw new IllegalAccessException("精确度不能小于0");
        }
        BigDecimal b1 = new BigDecimal(Double.valueOf(value1));
        BigDecimal b2 = new BigDecimal(Double.valueOf(value2));
        return b1.divide(b2, scale).doubleValue();
    }
//-------------------------------------------------------------------------------------------------------------------

    /**
     * 固定格式转化值
     *
     * @param f
     * @param format
     */
    public static double parse(double f, String format) {
        df.applyPattern(format);
        String value = df.format(f);
        return Double.valueOf(value);
    }

    /**
     * 保留两小数,直接去尾
     *
     * @param f
     *
     * @return double
     */
    public static double roundDown2Bit(double f) {
        df.applyPattern(BIT_2_START_DOT);
        df.setRoundingMode(RoundingMode.DOWN);
        return Double.parseDouble(df.format(f));
    }

    /**
     * 保留两小数,直接去尾
     * 整数补0
     *
     * @param f
     */
    public static String roundDown2Bit0(double f) {
        df.applyPattern(BIT_2_START_DOT);
        df.setRoundingMode(RoundingMode.DOWN);
        String str = df.format(f);
        str = append0AtFrontIfStartDot(str);
        return str;
    }
    public static String roundDown2Bit0(String s) {
        try {
            double f = Double.parseDouble(s);
            return roundDown2Bit0(f);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return s;
        }
    }

    /**
     * 保留两小数,直接取进位
     *
     * @param f
     *
     * @return String
     */
    public static String roundUp2Bit(double f) {
        df.applyPattern(BIT_2_START_DOT);
        df.setRoundingMode(RoundingMode.UP);
        return df.format(f);
    }

    /**
     * 四舍五入保留两小数,
     *
     * @param f
     *
     * @return String
     */
    public static String halfUp2Bit(double f) {
        df.applyPattern(BIT_2_START_DOT);
        df.setRoundingMode(RoundingMode.HALF_UP);
        return df.format(f);
    }

    /**
     * 四舍五入保留两小数,
     * 整数补0
     *
     * @param f
     *
     * @return String
     */
    public static String halfUp2Bit0(double f) {
        df.applyPattern(BIT_2_START_DOT);
        df.setRoundingMode(RoundingMode.HALF_UP);
        String str = df.format(f);
        str = append0AtFrontIfStartDot(str);
        return str;
    }

    /**
     * 四舍五入保留两小数,百分数形式，
     *
     * @param f
     *
     * @return 返回百分数形式的小数，保留两位
     */
    public static String halfUp2BitPercent(float f) {
        df.applyPattern(BIT_2_START_DOT_PERCENT);
        df.setRoundingMode(RoundingMode.HALF_UP);
        String str = df.format(f);

        str = append0AtFrontIfStartDot(str);
        return str;
    }


/*********************************************************************************************************************/
/********************************************************************************************************************/
/** 以下为工具类私有方法，提供给本类使用*/
    /**
     * 如果字符串以.开始
     * 在字符串前添0
     *
     * @param str
     */
    private static String append0AtFrontIfStartDot(String str) {
        if (str.startsWith(".")) {
            str = "0" + str;
        }

        return str;
    }
}
