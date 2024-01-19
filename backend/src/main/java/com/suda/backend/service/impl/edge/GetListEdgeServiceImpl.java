package com.suda.backend.service.impl.edge;

import com.suda.backend.mapper.EdgeMapper;
import com.suda.backend.pojo.Edge;
import com.suda.backend.service.edge.GetListEdgeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class GetListEdgeServiceImpl  implements GetListEdgeService {
    @Autowired
    private EdgeMapper edgeMapper;

    @Override
    public List<Edge> getList() {



        return edgeMapper.selectList(null);
    }
}
