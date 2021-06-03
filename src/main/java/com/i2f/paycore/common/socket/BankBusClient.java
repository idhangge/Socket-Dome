package com.i2f.paycore.common.socket;


import com.i2f.paycore.model.data.Test2;
import com.i2f.paycore.model.xmlP.XmlPackage;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

//socket发送接收XML报文
//TODO 封装为函数
public class BankBusClient {
    private static final Logger LOGGER = LoggerFactory.getLogger(BankBusClient.class);

    //TODO 改成配置方式
    //核心系统服务器地址
    private static final String MIN_SHENG_BANK_SOCKET_SERVER_IP = "127.0.0.1";
    //核心系统服务器地址的端口号
    private static final int MIN_SHENG_BANK__SERVER_PORT = 10110;
//有时new XStream会导致传送对象转换失败，需要在构造函数中添加参数new DomDriver
    private static final XStream XSTREAM = new XStream(new DomDriver());

    /**
     * 获取核心系统socket服务
     *
     * @return socket对象
     * @throws IOException
     */
    private static Socket getAccountServerSocket() {
        try {
            return new Socket(MIN_SHENG_BANK_SOCKET_SERVER_IP,MIN_SHENG_BANK__SERVER_PORT);
        } catch (Exception e) {
            LOGGER.error("创建民生系统socket连接失败", e);
            return null;
        }
    }

    /**
     * socket发送报文
     *
     * @param socket socket对象
     * @throws IOException
     */
    public static void sendAcctPackage(Socket socket, XmlPackage xmlPackage) throws IOException {
        String packageStr;
        // xStream处理成xml字符串
        //类加载器
        XSTREAM.setClassLoader(new BankBusClient().getClass().getClassLoader());
        //开启类名添加注解别名解析  每一个解析的类都需要加上别名注解  @XStreamAlias("XmlPackage")
        XSTREAM.autodetectAnnotations(true);
        XSTREAM.processAnnotations(new Class[]{XmlPackage.class, Test2.class});
        //将数据转换成XML格式
        packageStr = XSTREAM.toXML(xmlPackage);
        //报文数据写入
        IOUtils.write(packageStr, socket.getOutputStream(), String.valueOf(StandardCharsets.UTF_8));
        //关闭输出流
        socket.shutdownOutput();
        LOGGER.info("socket[{}:{}]写入信息:[{}]", socket.getInetAddress(), socket.getPort(), packageStr);
    }

    /**
     * socket接收返回信息
     *
     * @param socket socket对象
     * @return XmlPackage对象
     * @throws IOException
     */
    //TODO 应该返回Object或者泛型
    public static XmlPackage receiveResponse(Socket socket) throws IOException {
        //读取返回结果
        byte[] bytes = IOUtils.toByteArray(socket.getInputStream());
        String xmlStr = new String(bytes, StandardCharsets.UTF_8);
        LOGGER.info("读取到响应报文[{}]", xmlStr);
        //解析返回结果
        XSTREAM.setClassLoader(new BankBusClient().getClass().getClassLoader());
        XSTREAM.alias("XmlPackage", XmlPackage.class);
        XSTREAM.autodetectAnnotations(true);
        XSTREAM.processAnnotations(new Class[]{XmlPackage.class, Test2.class});
        return (XmlPackage) XSTREAM.fromXML(xmlStr);
    }


    /**
     * 释放Socket资源
     *
     * @param socket
     * @throws IOException
     */
    public static void releaseSocket(Socket socket) {
        try {
            socket.close();
        } catch (IOException e) {
            LOGGER.error("关闭socket失败", e);
        }
    }


}
