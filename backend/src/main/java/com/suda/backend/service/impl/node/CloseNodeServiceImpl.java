package com.suda.backend.service.impl.node;





import com.suda.backend.mapper.NodeMapper;
import com.suda.backend.pojo.Node;
import com.suda.backend.pojo.User;
import com.suda.backend.service.impl.utils.UserDetailsImpl;

import com.suda.backend.service.node.CloseNodeService;
import com.suda.backend.service.node.UpdateNodeService;
import com.suda.backend.service.operate.AddOperateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;


import java.util.HashMap;
import java.util.Map;

@Service
public class CloseNodeServiceImpl implements CloseNodeService {
    @Autowired
    private NodeMapper nodeMapper;
    @Autowired
    private  AddOperateService addOperateService;
    @Override
    public Map<String, String> close(Map<String, String> data) {

        HashMap <String ,String> map = new HashMap<>();
        int id = Integer.parseInt(data.get("id"));
        Node nodeupdate = nodeMapper.selectById(id);
        nodeupdate.setState(0);
        nodeMapper.updateById(nodeupdate);
        map.put("type","节点操作");
        map.put("description","关闭节点"+id);
        addOperateService.add(map);


        return map;
    }
}
