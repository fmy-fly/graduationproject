package com.suda.backend.controller.node;

import com.suda.backend.service.divdefence.RemoveService;
import com.suda.backend.service.node.RemoveNodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class RemoveNodeController {
    @Autowired
    private RemoveNodeService removeNodeService;

    @PostMapping("/node/remove/")
    public Map<String, String> remove(@RequestParam Map<String, String> data) {
        return removeNodeService.remove(data);
    }
}
