package com.i2f.paycore.common.socket;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**

 */
@Component
@Slf4j
public class ServerInit {

        /**
         * 方法一
         * 构造函数中调用，目的就是在类的初始化的时候进行调用。
         *
         * @param starter
         */

    public ServerInit(ServerStarter starter) {
        starter.startServer();
    }



//    @PostConstruct
//    public void init(){
//        ServerStarter starter = new ServerStarter();
//        starter.startServer();
//    }

        /**
         * 方法2
         *
         * jar包情况下，将Socket服务交由IOC容器进行管理，然后在IOC容器创建完成后，获取到已经初始化的Socket服务实例，然后开始Socket服务。在这样的情况下，可以在Socket服务类上进行依赖注入，也就是说可以使用@Autowired
         *
         *SocketServer socketServer =applicationContext.getBean(SocketServer.class);
         *         socketServer.start();
         * @param starter
         */
        /**
         * 方法3
         * 静态代码块可以试一试！
         *
         static {
         protocolUtils = ProtocolUtils.getInstance();
         }
         * @param starter
         */
//    public Server(ServerStarter starter) {
//        starter.startServer();
//    }

}