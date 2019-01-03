package com.qtn.modules.utils;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Service;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;

import com.alibaba.fastjson.JSONObject;

@Service
public class MyHandler implements WebSocketHandler{

	//在线用户列表
    public static  Map<String, WebSocketSession> users;

//    public static Map<String,Boolean> isConnection;

    public static Map<String,Long> lastDateList ;


    //判断语音发送到前端是否成功；
//    public static Boolean isConnection = false;

    static {
        users = new HashMap<>();
//        isConnection =new HashMap<>();?
        lastDateList =new HashMap<>();
    }
    //新增socket
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
    	 System.out.println("成功建立连接");
        String organizerId = session.getUri().toString().split("organizerId=")[1];
        System.out.println("新加入的机构Id："+organizerId);
         if (organizerId != null) {
             users.put(organizerId, session);
//             isConnection.put(organizerId,false);
             lastDateList.put(organizerId,new Date().getTime());
//             session.sendMessage(new TextMessage("成功建立socket连接"));
         }
         System.out.println("当前总连接数："+users.size());
    }

    //接收socket信息
    @Override
	public void handleMessage(WebSocketSession webSocketSession, WebSocketMessage<?> webSocketMessage) throws Exception {
    	try{
    	    String msg = webSocketMessage.getPayload().toString();
            if(msg.indexOf("isConnection_") != -1){
                String organizerId= msg.replace("isConnection_","");
//                isConnection.put(organizerId,true);
                lastDateList.put(organizerId,new Date().getTime());
//                System.out.println("true："+organizerId);
            }
    	 }catch(Exception e){
      	   e.printStackTrace();
         }

	}

    /**
     * 发送信息给指定用户
     * @param clientId
     * @param message
     * @return
     */
    public boolean sendMessageToUser(String clientId, TextMessage message) {
        if (users.get(clientId) == null) return false;
        WebSocketSession session = users.get(clientId);
        System.out.println("sendMessage:" + session);

        if (!session.isOpen()) {
            System.out.println("连接已断");
            return false;
        }
        try {
//            System.out.println("给js发送信息"+session.isOpen());
            session.sendMessage(message);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 广播信息
     * @param message
     * @return
     */
    public boolean sendMessageToAllUsers(TextMessage message) {
        boolean allSendSuccess = true;
        Set<String> clientIds = users.keySet();
        WebSocketSession session = null;
        for (String clientId : clientIds) {
            try {
                session = users.get(clientId);
                if (session.isOpen()) {
                    session.sendMessage(message);
                }
            } catch (IOException e) {
                e.printStackTrace();
                allSendSuccess = false;
            }
        }

        return  allSendSuccess;
    }


    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        if (session.isOpen()) {
            session.close();
        }
        System.out.println("连接出错");
//        users.remove(getClientId(session));
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        System.out.println("连接已关闭：" + status);
//        users.remove(getClientId(session));
    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }

    /**
     * 获取用户标识
     * @param session
     * @return
     */
    private Integer getClientId(WebSocketSession session) {
        try {
            Integer clientId = (Integer) session.getAttributes().get("WEBSOCKET_USERID");
            return clientId;
        } catch (Exception e) {
            return null;
        }
    }


}
