package com.qtn.restful;

import java.util.Date;
import java.util.Map;
import com.alibaba.fastjson.JSONObject;
import com.qtn.modules.utils.MyHandler;
import com.qtn.modules.utils.RedisUtils;
import com.qtn.modules.utils.RestfulRetUtils;
import com.qtn.modules.utils.ThreadUtils;
import com.sun.org.apache.xpath.internal.operations.Bool;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.socket.TextMessage;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(value="/webSocket/qtn/connectWebSocket")
public class QtnWebSocketRestful {
    private static final Logger logger = LogManager.getLogger(QtnWebSocketRestful.class);

    @RequestMapping(value="/sendMsgToMessagePage", method= RequestMethod.POST)
    public JSONObject sendMsgToMessagePage(HttpServletRequest request, @RequestBody Map<String,Object> param){
        synchronized (this){
            JSONObject json = new JSONObject();
            try{
                String path = (String)param.get("path");
                String organizerId = String.valueOf(param.get("organizerId"));
                if(path == null || organizerId == null){
                    json = RestfulRetUtils.getErrorMsg("51001","发送信息失败");
                    return json;
                }
                ThreadUtils thread = new ThreadUtils();
                thread.setOrganizerId(organizerId);
                thread.setPath(path);
                thread.start();
                thread.sleep(8000);
           /*       Date date= new Date();
                for(;;){
                    if((new Date().getTime()-date.getTime())/(1000)>3){
                        break;
                    }
                }
              if(MyHandler.isSuccess){//发送成功证明连接还存在，将标识改为默认
                    MyHandler.isSuccess = false;
                }else{//连接默认状态标识发送没成功
                    RedisUtils redis =new RedisUtils();
                    String pathName = path.substring(0,path.indexOf("."));
                    String value = pathName.replace(organizerId,"");
                    if(value.indexOf("V") == -1){

                    }else{

                    }
                }
                if(MyHandler.)*/
                json = RestfulRetUtils.getRetSuccess();
            }catch(Exception e){
                logger.error(e.getMessage(),e);
                json = RestfulRetUtils.getErrorMsg("51001","发送信息失败");
            }
            return  json;
        }

    }

    @RequestMapping(value="/isConnetion", method= RequestMethod.GET)
    public JSONObject isConnetion(HttpServletRequest request, @RequestParam Map<String,Object> param) {
        synchronized (this) {
            JSONObject json = new JSONObject();
            try {
                String organizerId = String.valueOf(param.get("organizerId"));
//                MyHandler handler = new MyHandler();
//                handler.sendMessageToUser(organizerId, new TextMessage("验证连接"));
//                Date date = new Date();
//                for (; ; ) {
//                    if ((new Date().getTime() - date.getTime()) / 1000 > 2)  {
//                        break;
//                    }
//                }
                Boolean isConnec =  false;
                //每秒接受前端的信息 超过一秒表示连接中断
                if((new Date().getTime()-MyHandler.lastDateList.get(organizerId))/1000 <1){
                    isConnec = true;
                }
                //页面没连接过，直接返回false；
//                if (MyHandler.isConnection.get(organizerId) != null) {
//                    //页面连接过，但中间可能出现断连的情况
//                    if (MyHandler.isConnection.get(organizerId)) {
//                        isConnec = MyHandler.isConnection.get(organizerId);
//                        MyHandler.isConnection.put(organizerId, false);
//                    }
//                }
                json = RestfulRetUtils.getRetSuccess(isConnec);
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
                json = RestfulRetUtils.getErrorMsg("51002", "查询失败");
            }
            return json;
        }
    }
}
