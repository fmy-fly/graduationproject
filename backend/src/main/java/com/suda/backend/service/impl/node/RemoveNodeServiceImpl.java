package com.suda.backend.service.impl.node;



import com.suda.backend.mapper.NodeMapper;
import com.suda.backend.pojo.Node;
import com.suda.backend.service.node.RemoveNodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class RemoveNodeServiceImpl implements RemoveNodeService {
    @Autowired
    private NodeMapper nodeMapper;

    @Override
    public Map<String, String> remove(Map<String, String> data) {
        Map<String, String> map = new HashMap<>();
        if (data.get("id") == null) {
            map.put("error_message", "策略不存在或已被删除");
            return map;
        }

        Integer id = Integer.parseInt(data.get("id"));
        Node node = nodeMapper.selectById(id);


        if (node == null) {
            map.put("error_message", "策略不存在或已被删除");
            return map;
        }



        nodeMapper.deleteById(id);

        map.put("error_message", "success");
        return map;
    }
}
