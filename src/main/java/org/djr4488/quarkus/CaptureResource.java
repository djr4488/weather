package org.djr4488.quarkus;

import io.quarkus.websockets.next.OnOpen;
import io.quarkus.websockets.next.OnPongMessage;
import io.quarkus.websockets.next.OnTextMessage;
import io.quarkus.websockets.next.WebSocket;
import io.quarkus.websockets.next.WebSocketConnection;
import org.slf4j.Logger;

import jakarta.inject.Inject;

import javax.websocket.Session;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

//@WebSocket(path = "/capture/{apikey}")
public class CaptureResource {
    // Inject
   // WebSocketConnection session;
    @Inject
    Logger log;
    private final Set<WebSocketConnection> sessions = new CopyOnWriteArraySet<>();
    private int clients = 0;

    //@OnOpen
    public void onOpen(){
        clients++;
    }

    //@OnTextMessage
    public void onMessage(final String message) {
        //publish to jms
    }

    //@OnPongMessage
    public void onPong() {

    }

}
