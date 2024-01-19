package com.suda.backend.controller.edge;

import com.suda.backend.pojo.Edge;
import com.suda.backend.pojo.Node;
import com.suda.backend.service.edge.GetListEdgeService;
import com.suda.backend.service.node.GetListNodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@RestController
public class GetListEdgeController {
    @Autowired
    private GetListEdgeService getListEdgeService;

    @GetMapping("/edge/getlist/")
    public List<Edge> getList() {
        System.out.println(getListEdgeService.getList());
        return getListEdgeService.getList();
    }
}
