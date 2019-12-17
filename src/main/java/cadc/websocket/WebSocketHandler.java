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
 * @author syh
 */
@Log4j2
@Component
public class WebSocketHandler {
    @Autowired
    private SocketIOServer socketIOServer;
    @Autowired
    private Map<UUID, Serializable> uuidPool;
    @Autowired
    private Map<Serializable, UUID> idPool;
    @Autowired
    private MessageService messageService;
    private ExecutorService threadPool = Executors.newCachedThreadPool();

    /**
     * 客户端发起连接时调用
     */
    @OnConnect
    public void onConnect(SocketIOClient client) {
        if (client != null) {
            String userId    = client.getHandshakeData().getSingleUrlParam("userId");
            UUID   sessionId = client.getSessionId();
            threadPool.execute(() -> {
                uuidPool.put(sessionId, userId);
                idPool.put(userId, sessionId);
//                client.joinRoom(String.valueOf(sessionId));
            });
//        List<Message> systemMessageList = messageService.getSystemMessageList(userId);
//        String        json              = JSONObject.toJSONString(systemMessageList);
//        client.sendEvent("systemMessage", new Message(json));
//        // 3.邀请消息
//        List<Message> inviteMessageList = messageService.getInviteMessageList(userId);
//        json = JSONObject.toJSONString(inviteMessageList);
//        client.sendEvent("inviteMessage", new Message(json));
            log.info("客户端:" + client.getSessionId() + "已连接");
        } else {
            log.info("客户端为空");
        }
    }

    /**
     * 客户端断开连接时调用，刷新客户端信息
     */
    @OnDisconnect
    public void onDisconnect(SocketIOClient client) {
        String userId    = client.getHandshakeData().getSingleUrlParam("userId");
        UUID   sessionId = client.getSessionId();
        threadPool.execute(() -> {
            if (uuidPool.containsKey(sessionId)) uuidPool.remove(sessionId);
            if (idPool.containsKey(userId)) idPool.remove(userId);
        });
        log.info("客户端:" + client.getSessionId() + "断开连接");
    }

    public boolean isOnLine(Serializable id) {
        return idPool.containsKey(String.valueOf(id));
    }

    /**
     * 定向给某人发送信息
     *
     * @param event   事件名称
     * @param message 事件内容
     * @param id      目标的userid
     */
    public void send(String event, Message message, Serializable id) {
        UUID           uuid   = idPool.get(String.valueOf(id));
        SocketIOClient client = socketIOServer.getClient(uuid);
        client.sendEvent(event, message);
    }

    /**
     * 广播
     *
     * @param event   事件名称
     * @param message 事件内容
     */
    public void send(String event, Message message) {
        UUID           uuid   = idPool.get(message.getTo());
        SocketIOClient client = socketIOServer.getClient(uuid);
        client.sendEvent(event, message);
        boolean insert = message.insert();
    }

//    @OnEvent(value = "broadcast")
//    public void broadcast(SocketIOServer server, AckRequest request, Message message) {
//        server.getBroadcastOperations().sendEvent("broadmessage",message);
//    }

    /**
     * @param client  客户端
     * @param request 请求信息
     * @param message 包含uid的消息体
     */
    @OnEvent(value = "logined")
    public void logined(SocketIOClient client, AckRequest request, Message message) {
        threadPool.execute(() -> {
            // 1.uuid与session映射
            String userId = message.getBody();
            uuidPool.put(client.getSessionId(), userId);
            idPool.put(userId, client.getSessionId());
            // 2.查询系统消息消息
            List<Message> systemMessageList = messageService.getSystemMessageList(userId);
            String        json              = JSONObject.toJSONString(systemMessageList);
            client.sendEvent("systemMessage", new Message(json));
            // 3.邀请消息
            List<Message> inviteMessageList = messageService.getInviteMessageList(userId);
            json = JSONObject.toJSONString(inviteMessageList);
            client.sendEvent("inviteMessage", new Message(json));
        });
    }

    @OnEvent(value = "logouted")
    public void logouted(SocketIOClient client, AckRequest request, Message message) {
        idPool.remove(uuidPool.get(client.getSessionId()));
        uuidPool.remove(client.getSessionId());
    }
}
