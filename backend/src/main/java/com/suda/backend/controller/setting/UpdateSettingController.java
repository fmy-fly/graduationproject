package com.suda.backend.controller.setting;


import com.suda.backend.service.setting.UpdateSettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;



import java.util.Map;

@RestController
public class UpdateSettingController {
    @Autowired
    private UpdateSettingService updateSettingService;

    @PostMapping("/setting/update/")
    public Map<String, String> update(@RequestParam Map<String, String> data) {
        return updateSettingService.update(data);
    }
}
