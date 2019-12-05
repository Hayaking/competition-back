package cadc.config;

import com.corundumstudio.socketio.SocketConfig;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.annotation.SpringAnnotationScanner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.Serializable;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author haya
 */
@Configuration
public class WebSocketConfig {
    @Bean
    @Autowired
    public SpringAnnotationScanner springAnnotationScanner(SocketIOServer socketServer) {
        return new SpringAnnotationScanner(socketServer);
    }

    @Bean(initMethod = "start")
    public SocketIOServer socketIOServer() {
        SocketConfig socketConfig = new SocketConfig();
        socketConfig.setTcpNoDelay(true);
        socketConfig.setSoLinger(0);
        com.corundumstudio.socketio.Configuration config = new com.corundumstudio.socketio.Configuration();
        config.setSocketConfig(socketConfig);
        config.setHostname("localhost");
        config.setPort(8888);
        config.setBossThreads(1);
        config.setWorkerThreads(20);
        config.setAllowCustomRequests(true);
        config.setUpgradeTimeout(1000000);
        config.setPingTimeout(6000000);
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
