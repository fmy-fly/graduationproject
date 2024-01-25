package com.suda.backend.controller.node;

import com.suda.backend.service.node.CloseNodeService;
import com.suda.backend.service.node.UpdateNodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class CloseNodeController {
    @Autowired
    private CloseNodeService closenodeService;

    @PostMapping("/node/close/")
    public Map<String, String> close(@RequestParam Map<String, String> data) {
        return closenodeService.close(data);
    }
}
