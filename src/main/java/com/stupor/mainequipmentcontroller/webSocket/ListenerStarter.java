package com.stupor.mainequipmentcontroller.webSocket;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ListenerStarter {
    @Value("${app.websocket.port}")
    private int webSocketPort;

    @PostConstruct
    public void init() {
        Thread tcpThread = new Thread(new HandlerListener(webSocketPort));
        tcpThread.setDaemon(true);
        tcpThread.start();
    }

}