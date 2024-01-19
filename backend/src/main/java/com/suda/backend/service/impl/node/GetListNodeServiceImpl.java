package com.suda.backend.service.impl.node;

import com.suda.backend.mapper.NodeMapper;
import com.suda.backend.pojo.Node;
import com.suda.backend.service.node.GetListNodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetListNodeServiceImpl implements GetListNodeService {
    @Autowired
    private NodeMapper nodeMapper;

    @Override
    public List<Node> getList() {



        return nodeMapper.selectList(null);
    }
}
