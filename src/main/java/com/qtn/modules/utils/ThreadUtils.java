package com.qtn.modules.utils;

import org.springframework.web.socket.TextMessage;

public class ThreadUtils extends Thread {

    private static String path =null;
    private static String organizerId =null;

    public static String getOrganizerId() {
        return organizerId;
    }

    public static void setOrganizerId(String organizerId) {
        ThreadUtils.organizerId = organizerId;
    }

    public static String getPath() {
        return path;
    }

    public static void setPath(String path) {
        ThreadUtils.path = path;
    }


    @Override
    public void run() {
        MyHandler handler =new MyHandler();
        handler.sendMessageToUser(organizerId,new TextMessage(path));
    }

}
