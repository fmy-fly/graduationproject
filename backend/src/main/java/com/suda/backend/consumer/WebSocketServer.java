package com.suda.backend.consumer;

import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import javax.websocket.Session;
import java.util.concurrent.CopyOnWriteArrayList;

@Component
@ServerEndpoint("/websocket/{token}")
public class WebSocketServer {

    private static final CopyOnWriteArrayList<Session> sessions = new CopyOnWriteArrayList<>();

    @OnOpen
    public void onOpen(Session session, @PathParam("token") String token) {
        // 建立连接
        sessions.add(session);
        System.out.println("connected");
    }

    @OnClose
    public void onClose(Session session) {
        // 关闭连接
        sessions.remove(session);
        System.out.println("disconnected");
    }

    @OnMessage
    public void onMessage(String message, Session session) {
        // 从Client接收消息
    }

    @OnError
    public void onError(Session session, Throwable error) {
        error.printStackTrace();
    }

    // 向所有连接的客户端广播消息
    public static void broadcastMessage(int message) {
        for (Session session : sessions) {
            if (session.isOpen()) {
                try {
                    session.getBasicRemote().sendText(String.valueOf(message));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

