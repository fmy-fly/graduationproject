package com.suda.backend.controller.operate;
import com.suda.backend.service.operate.AddOperateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class AddOperateController {
    @Autowired
    private AddOperateService addOperateService;

    @PostMapping("/record/operate/add/")
    public Map<String, String> add(@RequestParam Map<String, String> data) {
        return addOperateService.add(data);
    }
}
