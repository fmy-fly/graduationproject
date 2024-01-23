package com.suda.backend.service.impl.setting;

import com.suda.backend.mapper.SettingMapper;
import com.suda.backend.pojo.Setting;
import com.suda.backend.service.setting.GetListSettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetListSettingServiceImpl implements GetListSettingService {
    @Autowired
    private SettingMapper settingMapper;

    @Override
    public List<Setting> getList() {

        return settingMapper.selectList(null);
    }
}
