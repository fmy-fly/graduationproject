package com.suda.backend.controller.node;


import com.suda.backend.service.node.UpdateNodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;



import java.util.Map;

@RestController
public class UpdateNodeController {
    @Autowired
    private UpdateNodeService updatenodeService;

    @PostMapping("/node/update/")
    public Map<String, String> update(@RequestParam Map<String, String> data) {
        return updatenodeService.update(data);
    }
}
