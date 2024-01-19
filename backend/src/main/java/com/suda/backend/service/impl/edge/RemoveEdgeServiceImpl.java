package com.suda.backend.service.impl.edge;
import com.suda.backend.mapper.EdgeMapper;
import com.suda.backend.pojo.Edge;
import com.suda.backend.service.edge.RemoveEdgeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.Map;

@Service
public class RemoveEdgeServiceImpl implements RemoveEdgeService {
    @Autowired
    private EdgeMapper edgeMapper;

    @Override
    public Map<String, String> remove(Map<String, String> data) {
        Map<String, String> map = new HashMap<>();
        if (data.get("id") == null) {
            map.put("error_message", "策略不存在或已被删除");
            return map;
        }

        Integer id = Integer.parseInt(data.get("id"));
        Edge edge = edgeMapper.selectById(id);


        if (edge == null) {
            map.put("error_message", "边不存在或已被删除");
            return map;
        }



        edgeMapper.deleteById(id);
        map.put("error_message", "success");
        return map;
    }
}
