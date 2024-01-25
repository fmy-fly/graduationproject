package com.suda.backend.controller.button;


import com.suda.backend.service.button.UpdateButtonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;



import java.util.Map;

@RestController
public class UpdateButtonController {
    @Autowired
    private UpdateButtonService updatebuttonService;

    @PostMapping("/button/update/")
    public Map<String, String> update(@RequestParam Map<String, String> data) {
        return updatebuttonService.update(data);
    }
}
