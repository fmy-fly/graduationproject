package com.suda.backend.service.impl.node;

import com.suda.backend.mapper.NodeMapper;
import com.suda.backend.pojo.Node;
import com.suda.backend.service.node.AddHoneyPotService;
import com.suda.backend.service.node.AddNodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class AddHoneyPotServiceImpl implements AddHoneyPotService {

    @Autowired
    private NodeMapper nodeMapper;

    @Override
    public Map<String, String> addPot() {
        Map<String, String> map = new HashMap<>();




        Node node = new Node(null,1,30,"蜜罐","image","https://img1.baidu.com/it/u=578663439,1103502860&fm=253&fmt=auto&app=138&f=JPEG?w=400&h=400",1);
        nodeMapper.insert(node);
        map.put("error_message", "success");

        return map;
    }
}
