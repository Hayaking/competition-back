package cadc.websocket;

import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.annotation.OnConnect;
import com.corundumstudio.socketio.annotation.OnDisconnect;
import com.corundumstudio.socketio.annotation.OnEvent;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Map;
import java.util.UUID;

/**
 * @author haya
 */
@Log4j2
@Component
public class WebSocketHandler {
    @Autowired
    private SocketIOServer socketIOServer;
    @Autowired
    private Map<UUID, Serializable> uuidPoll;

    @OnConnect
    public void onConnect(SocketIOClient client) {
        log.info( "客户端:" + client.getSessionId() + "已连接" );
    }

    @OnDisconnect
    public void onDisconnect(SocketIOClient client) {
        log.info( "客户端:" + client.getSessionId() + "断开连接" );
    }

    /**
     * http登陆成功后 前端发送cookie过来 这里将websocket的sessionId与cookie做映射
     *
     * @param client  客户端
     * @param request
     * @param message 包含cookie的消息体
     */
    @OnEvent(value = "logined")
    public void logined(SocketIOClient client, AckRequest request, Message message) {
        uuidPoll.put( client.getSessionId(), message.getBody() );
    }

    @OnEvent(value = "logouted")
    public void logouted(SocketIOClient client, AckRequest request, Message message) {
        uuidPoll.remove( client.getSessionId() );
    }
}
