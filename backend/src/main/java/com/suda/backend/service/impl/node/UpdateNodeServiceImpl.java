package com.suda.backend.service.impl.node;



import com.suda.backend.mapper.NodeMapper;
import com.suda.backend.pojo.Node;
import com.suda.backend.pojo.User;
import com.suda.backend.service.impl.utils.UserDetailsImpl;

import com.suda.backend.service.node.UpdateNodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;


import java.util.HashMap;
import java.util.Map;

@Service
public class UpdateNodeServiceImpl implements UpdateNodeService {
    @Autowired
    private NodeMapper nodeMapper;

    @Override
    public Map<String, String> update(Map<String, String> data) {


        int id = Integer.parseInt(data.get("id"));

        String label = data.get("label");
        String shape = data.get("shape");
        String image = data.get("image");
        int size = Integer.parseInt(data.get("size"));
        int state = Integer.parseInt(data.get("state"));
        Map<String, String> map = new HashMap<>();

        if (shape== null || shape.length() == 0) {
            map.put("error_message", "类型不能为空");
            return map;
        }



        if (label == null || label.length() == 0) {
            label = "这个用户很懒，什么也没留下~";
        }



        if (image == null || image.length() == 0) {
            map.put("error_message", "图片不能为空");
            return map;
        }


        Node node= nodeMapper.selectById(id);

        if (node== null) {
            map.put("error_message", "策略不存在或已被删除");
            return map;
        }



        Node node1 = new Node(
                node.getId(),
                size,
                label,
                shape,
                image,
                state
        );

        nodeMapper.updateById(node1);

        map.put("error_message", "success");

        return map;
    }
}
