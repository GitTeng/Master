/**
 * Copyright (c) 2005-2012 https://github.com/zhangkaitao
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package tv.huan.master.common.util.upload;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.web.multipart.MultipartFile;
import tv.huan.master.common.util.IpUtils;
import tv.huan.master.common.util.upload.excepiton.InvalidExtensionException;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Date;

import static org.apache.commons.fileupload.FileUploadBase.FileSizeLimitExceededException;

public class FileUploadUtils {

    //默认大小 50M
    public static long DEFAULT_MAX_SIZE = 52428800;
    //默认上传的地址
    private static String defaultBaseDir = "upload";
    public static String[] DEFAULT_ALLOWED_EXTENSION = {
            //图片
            "bmp", "gif", "jpg", "jpeg", "png",
//            //word excel powerpoint
//            "doc", "docx", "xls", "xlsx", "ppt", "pptx",
            "html", "htm", "txt",
//            //压缩文件
            "rar", "zip",
//            "gz", "bz2",
//            //pdf
//            "pdf"
    };


    public static void setDefaultBaseDir(String defaultBaseDir) {
        FileUploadUtils.defaultBaseDir = defaultBaseDir;
    }

    public static String getDefaultBaseDir() {
        return defaultBaseDir;
    }

    /**
     * 以默认配置进行文件上传
     *
     * @param request 当前请求
     * @param file    上传的文件
     * @return
     */
    public static String upload(HttpServletRequest request, MultipartFile file)
            throws IOException, InvalidExtensionException, FileSizeLimitExceededException {
        return upload(request, file, DEFAULT_ALLOWED_EXTENSION, DEFAULT_MAX_SIZE, null);
    }

    /**
     * 以固定文件名进行文件上传
     *
     * @param request 当前请求
     * @param file    上传的文件
     * @return
     */
    public static String upload(HttpServletRequest request, MultipartFile file, String fileName)
            throws InvalidExtensionException, IOException, FileSizeLimitExceededException {
        return upload(request, file, DEFAULT_ALLOWED_EXTENSION, DEFAULT_MAX_SIZE, fileName);
    }


    /**
     * 文件上传
     *
     * @param request          当前请求 从请求中提取 应用上下文根
     * @param file             上传的文件
     * @param allowedExtension 允许的文件类型 null 表示允许所有
     * @param maxSize          最大上传的大小 -1 表示不限制
     * @return 返回上传成功的文件名
     * @throws InvalidExtensionException      如果MIME类型不允许
     * @throws FileSizeLimitExceededException 如果超出最大大小
     *                                        文件名太长
     * @throws java.io.IOException            比如读写文件出错时
     */
    public static String upload(HttpServletRequest request, MultipartFile file, String[] allowedExtension, long maxSize, String fileName)
            throws IOException, InvalidExtensionException, FileSizeLimitExceededException {
        assertAllowed(file, allowedExtension, maxSize);
        String ctx = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + "/";
        if (fileName == null) {
            String rename = new IpTimeStamp(IpUtils.getIpAddr(request)).getIpTimeRand();
//            String rename = new IpTimeStamp("127.0.0.1").getIpTimeRand();
            fileName = extractFilename(file, getDefaultBaseDir(), rename);
        } else {
            fileName = extractFilename(file, getDefaultBaseDir(), fileName);
        }
        File desc = getAbsoluteFile(extractUploadDir(request), fileName);

        file.transferTo(desc);
        return ctx + fileName;
    }

    private static File getAbsoluteFile(String uploadDir, String filename) throws IOException {

        uploadDir = FilenameUtils.normalizeNoEndSeparator(uploadDir);
        File desc = new File(uploadDir + File.separator + filename);
        if (!desc.getParentFile().exists()) {
            desc.getParentFile().mkdirs();
        }
        if (!desc.exists()) {
            desc.createNewFile();
        }
        return desc;
    }


    public static String extractFilename(MultipartFile file, String baseDir, String rename)
            throws UnsupportedEncodingException {
        String extension = FilenameUtils.getExtension(file.getOriginalFilename());
        return baseDir + File.separator + datePath() + File.separator + rename + "." + extension;
    }

    /**
     * 日期路径 即年/月/日  如2013/01/03
     *
     * @return
     */
    private static String datePath() {
        Date now = new Date();
        return DateFormatUtils.format(now, "yyyy/MM/dd");
    }


    /**
     * 是否允许文件上传
     *
     * @param file             上传的文件
     * @param allowedExtension 文件类型  null 表示允许所有
     * @param maxSize          最大大小 字节为单位 -1表示不限制
     * @return
     * @throws InvalidExtensionException      如果MIME类型不允许
     * @throws FileSizeLimitExceededException 如果超出最大大小
     */
    public static void assertAllowed(MultipartFile file, String[] allowedExtension, long maxSize)
            throws InvalidExtensionException, FileSizeLimitExceededException {

        String extension = FilenameUtils.getExtension(file.getOriginalFilename());

        if (allowedExtension != null && !isAllowedExtension(extension, allowedExtension)) {
            throw new InvalidExtensionException();
        }
        long size = file.getSize();
        if (maxSize != -1 && size > maxSize) {
            throw new FileSizeLimitExceededException("not allowed upload upload", size, maxSize);
        }
    }

    /**
     * 判断MIME类型是否是允许的MIME类型
     *
     * @param extension
     * @param allowedExtension
     * @return
     */
    public static boolean isAllowedExtension(String extension, String[] allowedExtension) {
        for (String str : allowedExtension) {
            if (str.equalsIgnoreCase(extension)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 提取上传的根目录 上传服务器tomcat根目录下
     *
     * @param request
     * @return
     */
    public static String extractUploadDir(HttpServletRequest request) {
        String rootPath = request.getServletContext().getRealPath("");
        rootPath = rootPath.substring(0, rootPath.lastIndexOf(System.getProperty("file.separator")));
        return rootPath;
//        return request.getServletContext().getRealPath("/");
    }


    public static void delete(HttpServletRequest request, String filename) throws IOException {
        if (StringUtils.isEmpty(filename)) {
            return;
        }
        File desc = getAbsoluteFile(extractUploadDir(request), filename);
        if (desc.exists()) {
            desc.delete();
        }
    }
}
