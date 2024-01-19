package com.suda.backend.service.impl.edge;



import com.suda.backend.mapper.EdgeMapper;
import com.suda.backend.pojo.Edge;
import com.suda.backend.pojo.User;
import com.suda.backend.service.impl.utils.UserDetailsImpl;

import com.suda.backend.service.edge.UpdateEdgeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;


import java.util.HashMap;
import java.util.Map;

@Service
public class UpdateEdgeServiceImpl implements UpdateEdgeService {
    @Autowired
    private EdgeMapper edgeMapper;

    @Override
    public Map<String, String> update(Map<String, String> data) {
        Map<String, String> map = new HashMap<>();
        if (data.get("id")==null || data.get("from") == null  ||data.get("to") == null){
            map.put("error_message", "类型不能为空");
            return map;
        }
        int id = Integer.parseInt(data.get("id"));
        int fromn = Integer.parseInt(data.get("from"));
        int ton = Integer.parseInt(data.get("to"));
        String label = data.get("label");
        String color = data.get("color");
        String highlight = data.get("highlight");

        String hover = data.get("hover");
        int length = Integer.parseInt(data.get("length"));



        if (color== null || color.length() == 0) {
            map.put("error_message", "类型不能为空");
            return map;
        }



        if (label == null || label.length() == 0) {
            label = "这个用户很懒，什么也没留下~";
        }



        if (highlight == null || highlight.length() == 0) {
            map.put("error_message", "图片不能为空");
            return map;
        }


        Edge edge= edgeMapper.selectById(id);

        if (edge== null) {
            map.put("error_message", "策略不存在或已被删除");
            return map;
        }
        Edge edge1 = new Edge(
                edge.getId(),
                fromn,
                ton,
                label,
                color,
                highlight,
                hover,
                length
        );

        edgeMapper.updateById(edge1);

        map.put("error_message", "success");

        return map;
    }
}
