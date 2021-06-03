package com.i2f.paycore.common.socket;

import com.i2f.paycore.model.xmlP.XmlPackage;
import com.i2f.paycore.service.Test2Service;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.*;
import java.net.Socket;

@Component
@Slf4j
public class DealThead extends Thread{
// @Description: 这里为接收民生传过来的标识符调用相关方法
    private Socket socket;
    @Autowired
    Test2Service test2Service;

    public DealThead() {
    }

    public DealThead(Socket socket) {
        this.socket=socket;
        this.test2Service = new Test2Service() {
            @Override
            public void test2(XmlPackage xmlPackage) {

            }
        };
    }

    @SneakyThrows
    @Override
    public void run() {
        //接收客户端的socket 并解析XML
        XmlPackage xmlPackage = BankBusClient.receiveResponse(socket);
        log.info("springboot核心系统-------接收数据完毕");
        String type = xmlPackage.getHeadType();
        Object data=xmlPackage.getBodyData();
        log.info(data.toString());
        switch (type) {
            //通过case的标识数字调用相关方法，调用完之后break返回
            case "9527":
                test2Service.test2(xmlPackage);
                break;
            default:
                break;
        }

        BankBusClient.sendAcctPackage(socket, xmlPackage);
        log.info("springboot系统-------返回数据完毕");
    }
    /**
     * 处理数据并发送
     */
    public void handle(Socket socket) throws IOException {

    }

}
