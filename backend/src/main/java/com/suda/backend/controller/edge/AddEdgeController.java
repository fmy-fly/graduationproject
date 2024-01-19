package com.suda.backend.controller.edge;

import com.suda.backend.service.edge.AddEdgeService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
@RestController
public class AddEdgeController {
    @Autowired
    private AddEdgeService addEdgeService ;

    @PostMapping("/edge/add/")
    public Map<String, String> add(@RequestParam Map<String, String> data) {
        return addEdgeService.add(data);
    }
}
