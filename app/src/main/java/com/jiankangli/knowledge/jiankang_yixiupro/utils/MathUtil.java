package com.jiankangli.knowledge.jiankang_yixiupro.utils;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author : lihao
 * @date: 2019-01-17 16:57
 * @description : 数学计算相关工具类
 */
public class MathUtil {


    /**
     * 获取集合中的最大值
     *
     * @param list
     * @return
     */
    public static Double getMax(List<Double> list) {
        Double max = 0.0;
        if (list == null || list.size() == 0) {
            return max;
        }
        for (int i = 0; i < list.size(); i++) {
            if (max < list.get(i)) {
                max = list.get(i);
            }
        }
        return max;
    }

    /**
     * 获取集合中的最小值
     *
     * @param list
     * @return
     */
    public static Double getMin(List<Double> list) {
        Double min = 0.0;
        if (list == null || list.size() == 0) {
            return min;
        }
        min = list.get(0);
        for (int i = 0; i < list.size(); i++) {
            if (min > list.get(i)) {
                min = list.get(i);
            }
        }
        return min;
    }

    /**
     * 提供精确的加法运算。
     *
     * @param v1 被加数
     * @param v2 加数
     * @return 两个参数的和
     */
    public static double add(double v1, double v2) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.add(b2).doubleValue();
    }

    /**
     * 提供（相对）精确的除法运算，当发生除不尽的情况时，精确到 小数点以后10位，以后的数字四舍五入。
     *
     * @param v1 被除数
     * @param v2 除数
     * @return 两个参数的商
     */
    public static double div(double v1, double v2) {
        return div(v1, v2, 10);
    }

    /**
     * 提供（相对）精确的除法运算。当发生除不尽的情况时，由scale参数指 定精度，以后的数字四舍五入。
     *
     * @param v1    被除数
     * @param v2    除数
     * @param scale 表示表示需要精确到小数点以后几位。
     * @return 两个参数的商
     */
    public static double div(double v1, double v2, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException(
                    "The scale must be a positive integer or zero");
        }
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    /**
     * 提供精确的乘法运算。
     *
     * @param v1 被乘数
     * @param v2 乘数
     * @return 两个参数的积
     */
    public static double mul(double v1, double v2) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.multiply(b2).doubleValue();
    }

    /**
     * 提供精确的小数位四舍五入处理。
     *
     * @param v     需要四舍五入的数字
     * @param scale 小数点后保留几位
     * @return 四舍五入后的结果
     */
    public static double round(double v, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException(
                    "The scale must be a positive integer or zero");
        }
        BigDecimal b = new BigDecimal(Double.toString(v));
        BigDecimal one = new BigDecimal("1");
        return b.divide(one, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    /**
     * 提供精确的减法运算。
     *
     * @param v1 被减数
     * @param v2 减数
     * @return 两个参数的差
     */
    public static double sub(double v1, double v2) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.subtract(b2).doubleValue();
    }

    /**
     * 得到近似值
     *
     * @param aValue double
     * @return double
     */
    public static double getApproximation(double aValue) {
        if (java.lang.Math.abs(aValue) <= 0.01) {
            aValue = 0;
        }
        return aValue;
    }

    /**
     * 设置数字精度 需要格式化的数据
     *
     * @param value     double 精度描述（0.00表示精确到小数点后两位）
     * @param precision String
     * @return double
     */
    public static double setPrecision(double value, String precision) {
        // 小数点后的位数
        int Length = precision.length() - precision.indexOf(".") - 1;
        if (Length < 0) {
            throw new IllegalArgumentException(
                    "The precision must be a positive integer or zero");
        }
        BigDecimal b = new BigDecimal(Double.toString(value));
        BigDecimal one = new BigDecimal("1");
        return b.divide(one, Length, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    /**
     * 计算目标位置和正北方向的夹角(y轴)
     *
     * @param x
     * @param y
     * @return
     */
    public static double getAngle(double x, double y) {
        double result = 0.0;
        //计算斜边长
        double c = Math.sqrt((Math.pow(x, 2.0) + Math.pow(y, 2.0)));
        //计算正弦值
        double sin = div(Math.abs(x), c, 10);

        double asin = Math.asin(sin);
        //换成角度
        double angel = Math.toDegrees(asin);

        //第一象限
        if (x > 0 && y > 0) {
            result = angel;
            //第二象限
        } else if (x > 0 && y < 0) {
            result = 180 - angel;
            //第四象限
        } else if (x < 0 && y > 0) {
            result = 360 - angel;
            //第三象限
        } else if (x < 0 && y < 0) {
            result = 180 + angel;
        }
        return result;
    }


    /**
     * 第三个参数是保留小数点位数
     *
     * @param v1
     * @param v2
     * @param scale
     * @return
     */
    public static Double div(Double v1, Double v2, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException(
                    "The scale must be a positive integer or zero");
        }
        BigDecimal b1 = new BigDecimal(v1.toString());
        BigDecimal b2 = new BigDecimal(v2.toString());
        return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

}
