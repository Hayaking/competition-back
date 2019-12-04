package cadc.config;

import com.corundumstudio.socketio.SocketIOServer;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * @author haya
 */
@Log4j2
@Component
@Order(1)
public class Initializer implements CommandLineRunner {
    @Autowired
    private SocketIOServer server;

    @Override
    public void run(String... args) {
        server.start();
        log.info( "socket.io启动成功！" );
    }
}
