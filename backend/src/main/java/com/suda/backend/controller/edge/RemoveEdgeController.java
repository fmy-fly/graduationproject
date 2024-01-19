package com.suda.backend.controller.edge;


import com.suda.backend.service.edge.RemoveEdgeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class RemoveEdgeController {
    @Autowired
    private RemoveEdgeService removeEdgeService;

    @PostMapping("/edge/remove/")
    public Map<String, String> remove(@RequestParam Map<String, String> data) {
        return removeEdgeService.remove(data);
    }
}
