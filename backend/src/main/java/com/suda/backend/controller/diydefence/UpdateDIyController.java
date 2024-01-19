package com.suda.backend.controller.diydefence;

import com.suda.backend.service.divdefence.UpdateDiyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class UpdateDIyController {
    @Autowired
    private UpdateDiyService updateService;

    @PostMapping("/defence/diydefence/update/")
    public Map<String, String> update(@RequestParam Map<String, String> data) {
        return updateService.update(data);
    }
}
