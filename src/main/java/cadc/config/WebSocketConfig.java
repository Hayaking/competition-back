package cadc.config;

import com.corundumstudio.socketio.SocketConfig;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.annotation.SpringAnnotationScanner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;

import java.io.Serializable;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author haya
 */
@Configuration
public class WebSocketConfig {
    /**
     * 开启SocketIOServer注解支持
     * @param socketServer
     * @return
     */
    /**
     * tomcat启动时，扫描socket服务器并注册
     *
     * ?????????@Bean和@AutoWired同时存在的原因
     * @param socketServer
     * @return
     */
    @Bean
    @Autowired
    public SpringAnnotationScanner springAnnotationScanner(SocketIOServer socketServer) {
        return new SpringAnnotationScanner(socketServer);
    }

    @Bean(initMethod = "start")
    public SocketIOServer socketIOServer() {
        //开启Socket端口复用
        SocketConfig socketConfig = new SocketConfig();
        socketConfig.setTcpNoDelay(true);
        //当网卡收到关闭连接请求后，无论数据是否发送完毕，立即发送RST包关闭连接
        socketConfig.setSoLinger(0);

        com.corundumstudio.socketio.Configuration config = new com.corundumstudio.socketio.Configuration();
        config.setSocketConfig(socketConfig);
        config.setHostname("localhost");
        config.setPort(8888);
        config.setBossThreads(1);
        //连接数大小
        config.setWorkerThreads(20);
        //允许客户请求
        config.setAllowCustomRequests(true);
        //协议升级超时时间（单位毫秒），默认10秒，HTTP握手升级为ws协议超时时间
        config.setUpgradeTimeout(1000000);
        //Ping消息超时时间（单位毫秒），默认60秒，这个时间间隔内没有收到心跳消息就会发送超时事件
        config.setPingTimeout(6000000);
        //Ping消息间隔时间（单位毫秒），默认25秒，客户端向服务器发送一条消息间隔
        config.setPingInterval(25000);
        return new SocketIOServer(config);
    }

    @Bean
    public Map<UUID, Serializable> uuidPoll() {
        return new ConcurrentHashMap<>();
    }

    @Bean
    public Map<Serializable,UUID> idPoll() {
        return new ConcurrentHashMap<>();
    }
}
