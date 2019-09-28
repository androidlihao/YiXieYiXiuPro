package com.jiankangli.knowledge.jiankang_yixiupro.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author linchao
 * @date 2017/11/21
 */

public class FileEncAndDec {

    private static int dataOfFile = 0;

    /**
     * 加密 或 解密 数据库文件
     *
     * @param srcFile
     * @param encFile
     * @param codes
     * @throws Exception
     */
    public static void encFile(File srcFile, File encFile, byte[] codes) throws Exception {
        if (!srcFile.exists()) {
            System.out.println("source file not exixt");
            return;
        }

        if (!encFile.exists()) {
            System.out.println("encrypt file created");
            encFile.createNewFile();
        }
        FileInputStream fis = new FileInputStream(srcFile);
        List<Byte> bytes = new ArrayList<>();
        int i = 0;
        while ((dataOfFile = fis.read()) != -1) {
            if (i % 6 == 0) {
                bytes.add((byte) dataOfFile);
                i++;
                continue;
            }
            for (int j = 0; j < codes.length; j++) {
                dataOfFile ^= codes[j];
            }
            i++;
            bytes.add((byte) dataOfFile);
        }
        OutputStream fos = new FileOutputStream(encFile);
        for (i = 0; i < bytes.size(); i++) {
            fos.write(bytes.get(i));
        }
        fis.close();
        fos.flush();
        fos.close();

    }


    public static void decodeFile(File srcFile, File encFile, byte[] codes) throws Exception {
        if (!srcFile.exists()) {
            System.out.println("source file not exixt");
            return;
        }

        if (!encFile.exists()) {
            System.out.println("encrypt file created");
            encFile.createNewFile();
        }
        FileInputStream fis = new FileInputStream(srcFile);
        int size = (int) fis.getChannel().size();
        byte[] bytes = new byte[size];
        for (int i = 0; i < bytes.length; i++) {
            if (i % 6 == 0) {
                continue;
            }
            for (int j = codes.length; j > 0; j--) {
                bytes[i] ^= codes[j];
            }
        }
        OutputStream fos = new FileOutputStream(encFile);
        fos.write(bytes, 0, size);
        fis.close();
        fos.flush();
        fos.close();
    }


}
