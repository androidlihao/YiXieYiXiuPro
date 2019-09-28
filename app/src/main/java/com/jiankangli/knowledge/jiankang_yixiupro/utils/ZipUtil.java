package com.jiankangli.knowledge.jiankang_yixiupro.utils;//package com.geostar.support.utils;
//
//
//import net.lingala.zip4j.core.ZipFile;
//import net.lingala.zip4j.exception.ZipException;
//import net.lingala.zip4j.model.ZipParameters;
//import net.lingala.zip4j.util.Zip4jConstants;
//
//import java.io.File;
//import java.util.List;
//
///**
// * @author zuoguozhen
// * @date 2018/4/2
// */
//public class ZipUtil {
////    /*单个压缩*/
////    public static File zipForUpload(String taskRootPath, String fileName) throws Exception {
////        /*生成临时文件夹*/
////        File tmpDir = getTempZipFolder(taskRootPath);
////        /* 拷贝照片到目录*/
////        List<String> paths = MEICApplication.getApplicationComponent().getDataManager().getPathList(taskRootPath);
////        copyFiles(tmpDir.getAbsolutePath(), paths);
////        String path = taskRootPath + "/temp_export" + fileName;
////        ZipUtil.compressZip4j(tmpDir.getPath(), path);
////        clearFolder(tmpDir);
////        return new File(path);
////    }
////
//    /**
//     * 传media.db
//     */
//    public static File zipForUploadEncrypt(String taskRootPath, String fileName) throws Exception {
//        /*生成临时文件夹*/
//        File tmpDir = getTempZipFolder(taskRootPath);
//        /* 拷贝照片到目录*/
//        List<String> paths = MEICApplication.getApplicationComponent().getDataManager().getPathList(taskRootPath);
//        //读取加密的数据库
////        List<String> paths = MEICApplication.getApplicationComponent().getDataManager().getEncryptPathList(taskRootPath);
//
//        copyFiles(tmpDir.getAbsolutePath(), paths);
//        String path = taskRootPath + "/temp_export" + fileName;
//        new File(path).getParentFile().mkdir();
//        ZipUtil.compressZip4j(tmpDir.getPath(), path);
//        clearFolder(tmpDir);
//        return new File(path);
//    }
//
//    /*拷贝文件至打包目录*/
//    private static void copyFiles(String folder, List<String> filePaths) throws Exception {
//        for (String path : filePaths) {
//            File file = new File(path);
//            if (file.exists()) {
//                String str;
//                if (path.contains("record")) {
//                    str = "record";
//                } else if (path.contains("photo")) {
//                    str = "photo";
//                } else {
//                    str = "videos";
//                }
//                String destFilePath = folder + "/media/" + str + "/" + file.getName();
////                //如果是图片，则压缩
////                if(path.contains("photo")){
////                    ImageCompressUtil.compressImage(MEICApplication.getInstance().getApplicationContext(),destFilePath,80);
////                }
//                FileUtil.copyFile(path, destFilePath);
//            }
//        }
//    }
//
//    //生成临时文件夹
//    private static File getTempZipFolder(String taskRootPath) {
//        String path = taskRootPath +
//                File.separator + "temp" +
//                File.separator;
//
//        File tmpDir = new File(path);
//        if (tmpDir.exists()) { // 清空目录内容;
////            clearFolder(tmpDir);
//        } else {
//            tmpDir.mkdirs();
//        }
//        return tmpDir;
//    }
//
//    /**
//     * 清空文件夹内容
//     *
//     * @param file
//     */
//    public static void clearFolder(File file) {
//        File[] subFiles = file.listFiles();
//        if (subFiles != null && subFiles.length > 0) {
//            for (File sub : subFiles) {
//                if (sub.isDirectory()) {
//                    clearFolder(sub);
//                }
//                sub.delete();
//            }
//        }
//    }
//
//    /**
//     * zip4j压缩
//     */
//    public static void compressZip4j(String filePath, String zipFilePath) {
//        try {
//            ZipFile zipFile = new ZipFile(zipFilePath);
//            ZipParameters parameters = new ZipParameters();
//            parameters.setCompressionMethod(Zip4jConstants.COMP_STORE);
//            parameters.setCompressionLevel(Zip4jConstants.DEFLATE_LEVEL_NORMAL);
//            //设置密码
//            parameters.setEncryptFiles(true);
//            parameters.setEncryptionMethod(Zip4jConstants.ENC_METHOD_STANDARD);
//            parameters.setPassword("geoyh");
//            File[] files = new File(filePath).listFiles();
//            for (File file : files) {
//                if (file.isDirectory()) {
//                    zipFile.addFolder(file, parameters);
//                } else {
//                    zipFile.addFile(file, parameters);
//                }
//            }
//
//        } catch (ZipException e) {
//            e.printStackTrace();
//        }
//    }
//}
