package org.djr4488.quarkus;

import io.quarkus.websockets.next.WebSocket;
import io.quarkus.websockets.next.WebSocketConnection;
import org.slf4j.Logger;

import jakarta.inject.Inject;

import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

@WebSocket(path = "/capture/{apikey}")
public class CaptureResource {
    @Inject
    WebSocketConnection connection;
    @Inject
    Logger log;
    private final Set<Session> sessions = new CopyOnWriteArraySet<Session>();
    private int clients = 0;
    @OnOpen
    public void onOpen(final Session session){
        log.trace("onOpen() session:{}, clients:{}", session, clients);
        sessions.add(session);
        clients++;
    }

    @OnMessage
    public void onMessage(final String message, final Session session) {
        log.trace("onMessage() message:{}, session:{}", message, session);
        //publish to jms
    }
}
