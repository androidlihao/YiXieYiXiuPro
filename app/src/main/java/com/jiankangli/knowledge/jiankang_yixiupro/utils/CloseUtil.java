package com.jiankangli.knowledge.jiankang_yixiupro.utils;

import java.io.Closeable;
import java.io.IOException;

/**
 * @author : lihao
 * @date: 2019-01-17 16:30
 * @description : 关闭工具类（eg: 关闭IO流）
 */
public class CloseUtil {

    private CloseUtil() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * 关闭实现Closeables 接口的对象（eg: IO流对象）
     *
     * @param closeables 实现了Closeable接口的的对象
     */
    public static void close(Closeable... closeables) {
        if (closeables == null) {
            return;
        }
        for (Closeable closeable : closeables) {
            if (closeable != null) {
                try {
                    closeable.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
