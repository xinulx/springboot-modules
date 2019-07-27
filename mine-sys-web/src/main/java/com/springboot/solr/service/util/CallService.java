package com.springboot.solr.service.util;

import com.alibaba.fastjson.JSON;
import com.springboot.common.util.PropertiesUtil;
import com.springboot.entity.vo.ResponseData;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.*;

@Controller
@RequestMapping("/callService")
@Slf4j
public class CallService {

    @RequestMapping("/tomcat-solr")
    @ResponseBody
    public Object start(Integer type) {
        String command;
        if (type == 0) {
            command = "D:\\Server\\apache-tomcat-solr\\bin\\shutdown.bat";//关闭tomcat命令
        } else if (type == 1) {
            command = "D:\\Server\\apache-tomcat-solr\\bin\\tomcat7w.exe";//启动Tomcat命令，仅限windows版本，无弹框
        } else if (type == 2) {
            command = "D:\\Server\\apache-tomcat-solr\\bin\\startup.bat";//启动tomcat命令
        } else {
            return ResponseData.fail("参数错误");
        }
        try {
            callCommand(command);
        } catch (IOException e) {
            log.error("执行命令时出错：{}", e.getMessage());
        }
        return new String(Base64.encodeBase64(JSON.toJSONBytes(ResponseData.success())));
    }

    /**
     * 执行命令
     *
     * @throws IOException
     */
    private void callCommand(String command) throws IOException {
        Runtime runtime = Runtime.getRuntime();//返回与当前的Java应用相关的运行时对象
        //指示Java虚拟机创建一个子进程执行指定的可执行程序，并返回与该子进程对应的Process对象实例
        Process process = runtime.exec(command,null,new File("D:\\Server\\apache-tomcat-solr\\bin"));
        runtime.gc();//运行垃圾回收器
        String line;
        BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream()));
        String logPath = System.getProperty("user.dir") + "/" + PropertiesUtil.getProperty("logging.file");
        RandomAccessFile randomFile = null;
        try {
            // 打开一个随机访问文件流，按读写方式
            randomFile = new RandomAccessFile(logPath, "rw");
            // 文件长度，字节数
            long fileLength = randomFile.length();
            // 将写文件指针移到文件尾。
            randomFile.seek(fileLength);
            while ((line = br.readLine()) != null) {
                randomFile.writeBytes(line + "\r\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally{
            if(randomFile != null){
                try {
                    randomFile.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}