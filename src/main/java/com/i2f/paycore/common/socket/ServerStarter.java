package com.i2f.paycore.common.socket;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Socket服务端
 */
@Slf4j
@Component
//@PropertySource("classpath:socket.properties")
public class ServerStarter {


//    @Resource
//    DealThead dealThead;
    /**
     * 端口号
     */
    private int port = 10110;

    @Async("taskExecutor")
    public void startServer() {
        ServerSocket serverSocket = null;
        Socket socket = null;

        try {
            System.out.println("boot核心系统-----开始初始化Socket服务端");
            serverSocket = new ServerSocket(port);
            while (true) {
                System.out.println("boot核心系统-----Socket服务端准备接受请求并处理");
                socket = serverSocket.accept();
                new DealThead(socket).start();
//                dealThead.handle(accept);
            }
        } catch (IOException e) {
            try {
                socket.close();
            } catch (IOException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
        } finally {
            try {
                serverSocket.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

}
