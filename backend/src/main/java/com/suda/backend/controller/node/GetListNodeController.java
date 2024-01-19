package com.suda.backend.controller.node;


import com.suda.backend.pojo.Node;

import com.suda.backend.service.node.GetListNodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class GetListNodeController {
    @Autowired
    private GetListNodeService getListNodeService;

    @GetMapping("/node/getlist/")
    public List<Node> getList() {
        System.out.println(getListNodeService.getList());
        return getListNodeService.getList();
    }
}
