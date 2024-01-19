package com.suda.backend.controller.diydefence;

import com.suda.backend.service.divdefence.RemoveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class RemoveDiyController {
    @Autowired
    private RemoveService removeService;

    @PostMapping("/defence/diydefence/remove/")
    public Map<String, String> remove(@RequestParam Map<String, String> data) {
        return removeService.remove(data);
    }
}
