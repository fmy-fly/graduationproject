package com.suda.backend.service.impl.edge;

import com.suda.backend.mapper.EdgeMapper;
import com.suda.backend.pojo.Edge;
import com.suda.backend.service.edge.AddEdgeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class AddEdgeServiceImpl implements AddEdgeService {

    @Autowired
    private EdgeMapper edgeMapper;

    @Override
    public Map<String, String> add(Map<String, String> data) {
        Integer fromn  = Integer.valueOf(data.get("from"));
        Integer ton = Integer.valueOf(data.get("to"));
        Integer length = Integer.valueOf(data.get("length"));
        String label = data.get("label");
        String color = data.get("color");
        String highlight = data.get("highlight");
        String hover = data.get("hover");
        Map<String, String> map = new HashMap<>();


        if (color == null || color.length() == 0) {
            map.put("error_message", "颜色不能为空");
            return map;
        }

        if (color.length() > 100) {
            map.put("error_message", "颜色长度不能大于100");
            return map;
        }

        if (label == null || label.length() == 0) {
            label= "这个用户很懒，什么也没留下~";
        }


        if (highlight == null || highlight.length() == 0) {
            map.put("error_message", "代码不能为空");
            return map;
        }
        if (hover == null || hover.length() == 0) {
            map.put("error_message", "代码不能为空");
            return map;
        }




        Edge edge = new Edge(null,fromn,ton,label,color,highlight,hover,length);
        edgeMapper.insert(edge);
        map.put("error_message", "success");

        return map;
    }
}
