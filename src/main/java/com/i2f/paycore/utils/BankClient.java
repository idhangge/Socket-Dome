package com.i2f.paycore.utils;

import com.i2f.paycore.model.xmlP.XmlPackage;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

/**
 * socket客户端  发
 */
public class BankClient {
    private static final Logger LOGGER = LoggerFactory.getLogger(BankClient.class);

    //民生系统服务器地址
    private static final String MIN_SHENG_BANK_SOCKET_SERVER_IP="127.0.0.1";
    //民生系统服务器地址的端口号
    private static final int MIN_SHENG_BANK__SERVER_PORT = 10110;

    private static final XStream XSTREAM = new XStream(new DomDriver());

    /**
     * 获取民生系统socket服务
     * @return socket对象
     */
    private static Socket getBankServerSocket(){
        try {
            return new Socket(MIN_SHENG_BANK_SOCKET_SERVER_IP,MIN_SHENG_BANK__SERVER_PORT);
        }catch (Exception e){
            LOGGER.error("创建民生系统socket连接失败",e);
            return null;
        }
    }

    /**
     * socket发送报文
     *
     * @param socket socket对象
     * @param data   请求数据
     * @throws IOException
     */
    public static void sendBankPackage(Socket socket,String type, Object data, String serialNum) throws IOException{
        String packageStr;
        XmlPackage aPackage = new XmlPackage<>();

        //组装报文头
        aPackage.setHeadSerialNum(serialNum);
        aPackage.setHeadType(type);
        //组装报文体
        if (data != null){
            aPackage.setBodyData(data);
        }else {
            //避免无数据时影响xml可读性
            aPackage.setBodyData("");
        }
        //XStream处理成xml字符串
        //类加载器
        XSTREAM.setClassLoader(new BankClient().getClass().getClassLoader());
        //开启类名添加注解别名解析  每一个解析的类都需要加上别名注解 @XStreamAlias("XmlPackage")
        XSTREAM.autodetectAnnotations(true);
        //你想传/接收什么参数，封装加别名之后把他放到下面这里，
        XSTREAM.processAnnotations(new Class[]{XmlPackage.class});
        //将数据转换成XML格式
        packageStr = XSTREAM.toXML(aPackage);
        //报文数据写入
        IOUtils.write(packageStr,socket.getOutputStream(),String.valueOf(StandardCharsets.UTF_8));
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
    public static XmlPackage receiveResponse(Socket socket) throws IOException{
        //读取返回结果
        byte[] bytes = IOUtils.toByteArray(socket.getInputStream());
        String xmlStr = new String(bytes, StandardCharsets.UTF_8);
        LOGGER.info("读取到响应报文[{}]", xmlStr);
        //解析返回结果
        XSTREAM.setClassLoader(new BankClient().getClass().getClassLoader());
        XSTREAM.alias("XmlPackage", XmlPackage.class);
        XSTREAM.autodetectAnnotations(true);
        XSTREAM.processAnnotations(new Class[]{XmlPackage.class});
        return (XmlPackage) XSTREAM.fromXML(xmlStr);
    }

    /**
     * 释放Socket资源
     */
    public static void releaseSocket(Socket socket){
        try {
            socket.close();
        } catch (IOException e) {
            LOGGER.error("关闭socket失败", e);
        }
    }

    /**
     * 携带数据请求一次民生系统
     * * @param type 请求类型
     * * @param data 请求数据
     ** @return XmlPackage对象
     * * @throws IOException
     */
    public static XmlPackage executeTran(String type, Object data, String serialNum) throws IOException {
        //获取socket
        Socket socket = getBankServerSocket();
        if (socket != null) {
            //发送报文
            sendBankPackage(socket, type, data, serialNum);
            //获取结果
            XmlPackage result = receiveResponse(socket);
            //释放资源
            releaseSocket(socket);
            return result;
        }
        return null;
    }

    /**
     * 不携带数据请求一次民生系统
     *
     * @param type 业务类型
     * @return XmlPackage对象
     * @throws IOException
     */
    public static XmlPackage executeTran(String type,String serialNum) throws IOException {
        //获取socket
        Socket socket = getBankServerSocket();
        if (socket != null) {
            //发送报文
            sendBankPackage(socket, type, null,serialNum);
            //获取结果
            XmlPackage result = receiveResponse(socket);
            //释放资源
            releaseSocket(socket);
            return result;
        }
        return null;
    }

}
