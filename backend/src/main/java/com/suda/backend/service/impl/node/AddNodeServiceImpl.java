package com.suda.backend.service.impl.node;

import com.suda.backend.mapper.NodeMapper;
import com.suda.backend.pojo.Node;
import com.suda.backend.service.node.AddNodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class AddNodeServiceImpl implements AddNodeService {

    @Autowired
    private NodeMapper nodeMapper;

    @Override
    public Map<String, String> add(Map<String, String> data) {
        Map<String, String> map = new HashMap<>();
        if(data.get("size") == null || data.get("state") == null){
            map.put("error_message", "图片不能为空");
            return map;
        }
        String label = data.get("label");
        String shape = data.get("shape");
        String image = data.get("image");
        int size = Integer.parseInt(data.get("size"));
        int state = Integer.parseInt(data.get("state"));


        if (image == null || image.length() == 0) {
            map.put("error_message", "图片不能为空");
            return map;
        }

        if (image.length() > 100) {
            map.put("error_message", "图片长度不能大于100");
            return map;
        }

        if (label == null || label.length() == 0) {
            label = "这个用户很懒，什么也没留下~";
        }



        if (shape== null || shape.length() == 0) {
            map.put("error_message", "类型不能为空");
            return map;
        }




        Node node = new Node(null,size,label,shape,image,state);
        nodeMapper.insert(node);
        map.put("error_message", "success");

        return map;
    }
}
