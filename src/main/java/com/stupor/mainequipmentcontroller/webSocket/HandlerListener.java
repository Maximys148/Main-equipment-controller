package com.stupor.mainequipmentcontroller.webSocket;

import com.stupor.mainequipmentcontroller.service.EquipmentService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

@Component
public class HandlerListener implements Runnable {
    private final int port;
    @Autowired
    private EquipmentService equipmentService;
    private final Logger log = LogManager.getLogger(HandlerListener.class);

    @Autowired
    public HandlerListener(@Value("${app.websocket.port}") int port) {
        this.port = port;
    }

    @Override
    public void run() {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            log.info("Начинаю слушать сообщения на порту " + port);
            while (true) {
                Socket socket = serverSocket.accept();
                new Thread(() -> handleClient(socket)).start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void handleClient(Socket socket) {
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(socket.getInputStream()))) {
            equipmentService.sendMsg(reader.readLine());
        } catch (Exception e) {
            log.error("Ошибка", e);
        } finally {
            try { socket.close(); } catch (IOException ignored) {}
        }
    }
}