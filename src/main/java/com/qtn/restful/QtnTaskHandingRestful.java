package com.qtn.restful;

import com.alibaba.fastjson.JSONObject;
import com.qtn.modules.service.QtnTaskHandingService;
import com.qtn.modules.utils.RestfulRetUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping(value="/qtn/task")
public class QtnTaskHandingRestful {

    protected static final Logger logger = LogManager.getLogger(QtnTaskHandingRestful.class);

    @Resource
    private QtnTaskHandingService qtnTaskHandingService;


    @RequestMapping(value="/getList",method = RequestMethod.POST)
    public JSONObject getList(){
        JSONObject json = new JSONObject();
        try{
            json = qtnTaskHandingService.getList();
        }catch(Exception e){
            logger.error(e.getMessage(),e);
            json = RestfulRetUtils.getErrorMsg("51001","获取列表失败");
        }
        return json;
    }
}
