package com.suda.backend.controller.diydefence;

import com.suda.backend.service.divdefence.AddDiyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class AddDiyController {
    @Autowired
    private AddDiyService addDiyService;

    @PostMapping("/defence/diydefence/add/")
    public Map<String, String> add(@RequestParam Map<String, String> data) {
        return addDiyService.add(data);
    }
}
