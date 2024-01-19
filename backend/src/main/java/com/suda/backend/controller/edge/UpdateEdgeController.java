package com.suda.backend.controller.edge;


import com.suda.backend.service.edge.UpdateEdgeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;



import java.util.Map;

@RestController
public class UpdateEdgeController {
    @Autowired
    private UpdateEdgeService updateedgeService;

    @PostMapping("/edge/update/")
    public Map<String, String> update(@RequestParam Map<String, String> data) {
        return updateedgeService.update(data);
    }
}
