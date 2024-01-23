package com.suda.backend.service.impl.setting;



import com.suda.backend.mapper.SettingMapper;
import com.suda.backend.pojo.Setting;
import com.suda.backend.pojo.User;
import com.suda.backend.service.impl.utils.UserDetailsImpl;

import com.suda.backend.service.setting.UpdateSettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;


import java.util.HashMap;
import java.util.Map;

@Service
public class UpdateSettingServiceImpl implements UpdateSettingService {
    @Autowired
    private SettingMapper settingMapper;

    @Override
    public Map<String, String> update(Map<String, String> data) {
        System.out.println(data.get("ml"));
        System.out.println(data.get("threshold"));
        System.out.println(data.get("notice"));

        int ml = Integer.parseInt(data.get("ml"));
        int  threshold = Integer.parseInt(data.get("threshold"));
        int  notice = Integer.parseInt(data.get("notice"));
        Setting setting = new Setting(
               1, ml,threshold,notice



        );
        Map<String, String> map = new HashMap<>();



        settingMapper.updateById(setting);









        map.put("error_message", "success");

        return map;
    }
}
