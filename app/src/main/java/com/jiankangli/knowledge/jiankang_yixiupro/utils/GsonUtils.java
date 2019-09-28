package com.jiankangli.knowledge.jiankang_yixiupro.utils;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

/**
 * @author : lihao
 * @date: 2019-01-16 10:00
 * @description : Gson转换工具类
 */
public class GsonUtils {

    private static Gson mGson = new GsonBuilder().serializeNulls().create();

    /**
     * json转换成实体bean
     *
     * @param json  需要转换的json串
     * @param clazz 需要转换的bean对象
     * @param <T>   需要返回的泛型实体类
     * @return {@code 转换成功}: T <br>{@code 转换失败}: null
     */
    public static <T> T jsonTobean(String json, Class<T> clazz) {
        try {
            T t = mGson.fromJson(json, clazz);
            return t;
        } catch (JsonSyntaxException e) {
            return null;
        }
    }

    /**
     * json转换成实体bean
     *
     * @param object 需要转换的map
     * @param clazz  需要转换的bean对象
     * @param <T>    需要返回的泛型实体类
     * @return {@code 转换成功}: T <br>{@code 转换失败}: null
     */
    public static <T> T jsonTobean(Object object, Class<T> clazz) {
        try {
            String json = mGson.toJson(object);
            T t = mGson.fromJson(json, clazz);
            return t;
        } catch (JsonSyntaxException e) {
            return null;
        }
    }

    /**
     * 实体bean转换成json字符串
     *
     * @param bean 需要转换的bean
     * @return {@code 转换成功}: jsonString <br>{@code 转换失败}: null
     */
    public static String beanTojson(Object bean) {
        try {
            return mGson.toJson(bean);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * json字符串转换对List<对象>
     *
     * @param json  字符串
     * @param clazz 对应实体类
     * @param <T>   泛型实体类
     * @return null 转换失败 List<实体类>转换成功</>
     */
    public static <T> List<T> json2List(String json, Class<T> clazz) {
        try {
            Type type = new ParameterizedTypeImpl(clazz);
            List<T> list = mGson.fromJson(json, type);
            return list;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 集合转json字符串
     *
     * @param list
     * @param <T>  泛型对象
     * @return null 为失败  转换成字符串成功
     */
    public static <T> String list2Json(List<T> list) {
        try {
            return mGson.toJson(list);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    /**
     * json字符串转List<对象>实体类 type帮助类</>
     */
    private static class ParameterizedTypeImpl implements ParameterizedType {
        Class clazz;

        public ParameterizedTypeImpl(Class clz) {
            clazz = clz;
        }

        @Override
        public Type[] getActualTypeArguments() {
            return new Type[]{clazz};
        }

        @Override
        public Type getRawType() {
            return List.class;
        }

        @Override
        public Type getOwnerType() {
            return null;
        }
    }
}
