package com.suda.backend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import com.suda.backend.pojo.Setting;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SettingMapper extends BaseMapper<Setting> {
}