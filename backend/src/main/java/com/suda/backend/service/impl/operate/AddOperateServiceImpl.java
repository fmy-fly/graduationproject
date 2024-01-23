package com.suda.backend.service.impl.operate;

import com.suda.backend.mapper.OperateMapper;
import com.suda.backend.pojo.Operate;
import com.suda.backend.service.operate.AddOperateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class AddOperateServiceImpl implements AddOperateService {

    @Autowired
    private OperateMapper operateMapper;

    @Override
    public Map<String, String> add(Map<String, String> data) {
        Map<String, String> map = new HashMap<>();
        Date now = new Date();
        String decription = "测试";
        String type = "测试";
        Operate operate = new Operate(null,now,type,decription);
        operateMapper.insert(operate);





        map.put("error_message", "success");

        return map;
    }
}
