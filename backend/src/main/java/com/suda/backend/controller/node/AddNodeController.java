package com.suda.backend.controller.node;


import com.suda.backend.service.node.AddNodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class AddNodeController {
    @Autowired
    private AddNodeService addNodeService ;

    @PostMapping("/node/add/")
    public Map<String, String> add(@RequestParam Map<String, String> data) {
        return addNodeService.add(data);
    }
}
