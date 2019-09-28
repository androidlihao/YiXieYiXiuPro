package com.jiankangli.knowledge.jiankang_yixiupro.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;

/**
 * @author lihao
 * @date 2019-01-26 15:44
 * @description :
 */
public class NumberUtil {
    public static boolean isInteger(String value) {
        if (value == null) {
            return false;
        }
        try {
            Integer.parseInt(value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * 判断字符串是否是浮点数
     */
    public static boolean isDouble(String value) {
        if (value == null) {
            return false;
        }
        try {
            Double.parseDouble(value);
            if (value.contains(".")) {
                return true;
            }
            return false;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * 判断字符串是否是数字
     */
    public static boolean isNumber(String value) {
        return isInteger(value) || isDouble(value);
    }

    public static double parseDouble(String value, double mDefualt) {
        if (value == null) {
            return mDefualt;
        }
        try {
            return Double.parseDouble(value);
        } catch (Exception e) {
            return mDefualt;
        }
    }

    public static float parseFloat(String value, float mDefualt) {
        if (value == null) {
            return mDefualt;
        }
        try {
            return Float.parseFloat(value);
        } catch (Exception e) {
            return mDefualt;
        }
    }

    public static int parseInt(String value, int defualt) {
        if (value == null) {
            return defualt;
        }
        try {
            return Integer.parseInt(value);
        } catch (Exception e) {
            return defualt;
        }
    }

    /**
     * 数据保留小数位数格式转换
     *
     * @param num                   需要转换的String数据(num需为double数字)
     * @param maximumFractionDigits 最多保留的小数位数
     * @return
     */
    public static String numberFormat(String num, int maximumFractionDigits) {
        try {
            if (isDouble(num)) {
                double number = Double.parseDouble(num);
                return numberFormat(number, maximumFractionDigits);
            } else {
                return num;
            }
        } catch (Exception e) {
            return num;
        }
    }

    /**
     * 数据保留小数位数格式转换
     *
     * @param num                   需要转换的double数据
     * @param maximumFractionDigits 最多保留的小数位数
     * @return
     */
    public static String numberFormat(double num, int maximumFractionDigits) {
        if (Double.isNaN(num)) {
            num = 0.0;
        }
        DecimalFormat g = new DecimalFormat();
        g.setMaximumFractionDigits(maximumFractionDigits);
        g.setGroupingUsed(false);
        return g.format(num);
    }


    public static String percentToString(double numerator, double denominator) {
        double percent = numerator / denominator;
        DecimalFormat nf = (DecimalFormat) NumberFormat.getPercentInstance();
        return nf.format(percent);
    }


    /**
     * 四舍五入
     *
     * @param d
     * @param scale
     * @return
     */
    public static double formatDouble(String d, int scale) {
        try {
            // 新方法，如果不需要四舍五入，可以使用RoundingMode.DOWN
            BigDecimal bg = new BigDecimal(d).setScale(scale, RoundingMode.HALF_UP);
            return bg.doubleValue();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 四舍五入
     *
     * @param d
     * @param scale
     * @return
     */
    public static double formatDouble(double d, int scale) {
        try {
            // 新方法，如果不需要四舍五入，可以使用RoundingMode.DOWN
            BigDecimal bg = new BigDecimal(d).setScale(scale, RoundingMode.HALF_UP);
            return bg.doubleValue();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

}
