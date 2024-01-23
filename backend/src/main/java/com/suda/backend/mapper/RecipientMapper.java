package com.suda.backend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.suda.backend.pojo.Operate;
import com.suda.backend.pojo.Recipient;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface RecipientMapper extends BaseMapper<Recipient> {
}