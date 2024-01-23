package com.suda.backend.controller.setting;


import com.suda.backend.pojo.Setting;

import com.suda.backend.service.setting.GetListSettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class GetListSettingController {
    @Autowired
    private GetListSettingService getListSettingService;

    @GetMapping("/setting/getlist/")
    public List<Setting> getList() {
        System.out.println(getListSettingService.getList());
        return getListSettingService.getList();
    }
}
