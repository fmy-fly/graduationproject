package com.suda.backend.controller.data;
import com.suda.backend.service.data.AddDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class AddDataController {
    @Autowired
    private AddDataService addDataService;

    @PostMapping("/record/data/add/")
    public Map<String, String> add(@RequestParam int [] data) {
        return addDataService.add(data);
    }
}
