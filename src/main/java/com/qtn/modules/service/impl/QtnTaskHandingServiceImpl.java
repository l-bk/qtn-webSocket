package com.qtn.modules.service.impl;

import java.util.Map;
import java.util.List;
import com.alibaba.fastjson.JSONObject;
import com.qtn.modules.repository.QtnTaskHandingRepository;
import com.qtn.modules.service.QtnTaskHandingService;
import com.qtn.modules.utils.RestfulRetUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
@Service
public class QtnTaskHandingServiceImpl implements QtnTaskHandingService {

    @Resource
    private QtnTaskHandingRepository qtnTaskHandingRepository;

    @Override
    public JSONObject getList() {
        List<Map<String,Object>> list  = qtnTaskHandingRepository.getList();
        return RestfulRetUtils.getRetSuccessWithPage(list,list.size());
    }

}
