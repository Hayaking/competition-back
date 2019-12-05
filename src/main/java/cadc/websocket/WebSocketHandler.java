package cadc.websocket;

import cadc.entity.Message;
import cadc.service.MessageService;
import com.alibaba.fastjson.JSONObject;
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
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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
    @Autowired
    private Map<Serializable, UUID> idPoll;
    @Autowired
    private MessageService messageService;
    private ExecutorService threadPool = Executors.newCachedThreadPool();

    @OnConnect
    public void onConnect(SocketIOClient client) {
        log.info( "客户端:" + client.getSessionId() + "已连接" );
    }

    @OnDisconnect
    public void onDisconnect(SocketIOClient client) {
        log.info( "客户端:" + client.getSessionId() + "断开连接" );
    }

    public boolean isOnLine(Serializable id) {
        return idPoll.containsKey( String.valueOf( id ) );
    }


    public void send(String event, Message message, Serializable id) {
        UUID uuid = idPoll.get( String.valueOf( id ) );
        SocketIOClient client = socketIOServer.getClient( uuid );
        client.sendEvent( event, message );
    }

    public void send(String event, Message message) {
        UUID uuid = idPoll.get( message.getTo() );
        SocketIOClient client = socketIOServer.getClient( uuid );
        client.sendEvent( event, message );
        boolean insert = message.insert();
    }

    /**
     * @param client  客户端
     * @param request
     * @param message 包含uid的消息体
     */
    @OnEvent(value = "logined")
    public void logined(SocketIOClient client, AckRequest request, Message message) {
        threadPool.execute( () ->{
            // 1.uuid与session映射
            String userId = message.getBody();
            uuidPoll.put( client.getSessionId(), userId );
            idPoll.put( userId, client.getSessionId() );
            // 2.查询系统消息消息
            List<Message> systemMessageList = messageService.getSystemMessageList( userId );
            String json = JSONObject.toJSONString( systemMessageList );
            client.sendEvent( "systemMessage", new Message(json) );
            // 3.邀请消息
            List<Message> inviteMessageList = messageService.getInviteMessageList( userId );
            json = JSONObject.toJSONString( inviteMessageList );
            client.sendEvent( "inviteMessage", new Message( json ) );
        } );
    }

    @OnEvent(value = "logouted")
    public void logouted(SocketIOClient client, AckRequest request, Message message) {
        idPoll.remove( uuidPoll.get( client.getSessionId() ) );
        uuidPoll.remove( client.getSessionId() );
    }
}
