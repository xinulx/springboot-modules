package com.springboot;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

//java jdbc使用SSH隧道连接mysql数据库demo
public class SSHConnectUtil {
    public static void go() {
        try {
            JSch jsch = new JSch();
            Session session = jsch.getSession("root", "218.95.95.30", 60001);
            session.setPassword("gcloud123!@#");
            session.setConfig("StrictHostKeyChecking", "no");
            session.connect();
            System.out.println(session.getServerVersion());//这里打印SSH服务器版本信息
            // 本地端口随便写 别超出范围或未被占用即可
            int assinged_port = session.setPortForwardingL( 123, "192.168.20.169", 8082);//端口映射 转发
            System.out.println("localhost:" + assinged_port);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args){
        go();
    }
}
