package com.qtn.restful;

import java.util.Map;
import com.alibaba.fastjson.JSONObject;
import com.qtn.modules.utils.MyHandler;
import com.qtn.modules.utils.RestfulRetUtils;
import com.qtn.modules.utils.ThreadUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
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
                String organizerId = (String)param.get("organizerId");
                if(path == null || organizerId == null){
                    json = RestfulRetUtils.getErrorMsg("51001","发送信息失败");
                    return json;
                }
                ThreadUtils thread = new ThreadUtils();
                thread.setOrganizedId(organizerId);
                thread.setPath(path);
                thread.start();
                thread.sleep(4000);
                json = RestfulRetUtils.getRetSuccess();
            }catch(Exception e){
                logger.error(e.getMessage(),e);
                json = RestfulRetUtils.getErrorMsg("51001","发送信息失败");
            }
            return  json;
        }

    }
}
