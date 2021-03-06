package com.springboot.common.util;

import com.mongodb.gridfs.GridFSDBFile;
import com.springboot.entity.vo.MongoFileVO;
import com.springboot.service.mongodb.base.IMongoDbFileServer;
import com.springboot.service.mongodb.base.impl.MongoDbFileServerImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Base64;

public class DocFileManage {

    private static Logger logger = LoggerFactory.getLogger(DocFileManage.class);
    private static IMongoDbFileServer fileServer = new MongoDbFileServerImpl();

    /**
     * 读取文件
     *
     * @param path
     * @return
     */
    public static String readFile(String path) {

        String content = null;
        try {
            GridFSDBFile gf = fileServer.getGridFSDBFile(path, "template");

            InputStream is = gf.getInputStream();

            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            StringBuilder builder = new StringBuilder();

            String line;
            try {
                while ((line = reader.readLine()) != null) {
                    builder.append(line);
                    builder.append("\n");
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            content = new String(builder.toString().getBytes(), "UTF-8");
        } catch (Exception e) {
            logger.error(e.getMessage());
            e.printStackTrace();
        }

        return content;
    }

    /**
     * 保存文件
     *
     * @param fileName
     * @param content
     * @param id
     * @return
     */
    public static String writeFile(String fileName, String content, Long id) {

        MongoFileVO fileVO = null;
        try {
            fileVO = fileServer.uploadFile(content.getBytes("UTF-8"), fileName, id, "template");
        } catch (Exception e) {
            logger.error(e.getMessage());
            e.printStackTrace();
        }

        return fileVO.getMongoId();
    }

    /**
     * 删除文件
     *
     * @param path
     * @return
     */
    public static boolean delFile(String path) {
        if (AppUtil.isEmpty(path))
            return false;
        boolean rst = false;
        try {
            rst = fileServer.delete(path, "template");
        } catch (Exception e) {
            logger.error(e.getMessage());
            e.printStackTrace();
        }
        return rst;
    }

    /**
     * @param contents 二进制数据
     * @param filePath 文件存放目录，包括文件名及其后缀，如D:\file\bike.jpg
     * @Title: byteToFile
     * @Description: 把二进制数据转成指定后缀名的文件，例如PDF，PNG等
     */
    public static void byteToFile(byte[] contents, String filePath) {
        BufferedInputStream bis = null;
        FileOutputStream fos = null;
        BufferedOutputStream output = null;
        try {
            ByteArrayInputStream byteInputStream = new ByteArrayInputStream(contents);
            bis = new BufferedInputStream(byteInputStream);
            File file = new File(filePath);
            // 获取文件的父路径字符串
            File path = file.getParentFile();
            if (!path.exists()) {
                logger.info("文件夹不存在，创建。path={}", path);
                boolean isCreated = path.mkdirs();
                if (!isCreated) {
                    logger.error("创建文件夹失败，path={}", path);
                }
            }
            fos = new FileOutputStream(file);
            // 实例化OutputString 对象
            output = new BufferedOutputStream(fos);
            byte[] buffer = new byte[1024];
            int length = bis.read(buffer);
            while (length != -1) {
                output.write(buffer, 0, length);
                length = bis.read(buffer);
            }
            output.flush();
        } catch (Exception e) {
            logger.error("输出文件流时抛异常，filePath={}", filePath, e);
        } finally {
            try {
                bis.close();
                fos.close();
                output.close();
            } catch (IOException e0) {
                logger.error("文件处理失败，filePath={}", filePath, e0);
            }
        }
    }

    public static void main(String[] args) {
        byte[] data = Base64.getDecoder().decode("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");
        byteToFile(data, "D:\\test\\基层政务试点专题_25.gif");
    }
}
