package com.qtn.modules.utils;

import org.springframework.web.socket.TextMessage;

public class ThreadUtils extends Thread {

    private static String path =null;
    private static String organizedId =null;

    public static String getOrganizedId() {
        return organizedId;
    }

    public static void setOrganizedId(String organizedId) {
        ThreadUtils.organizedId = organizedId;
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
        handler.sendMessageToUser(organizedId,new TextMessage(path));
    }

}
