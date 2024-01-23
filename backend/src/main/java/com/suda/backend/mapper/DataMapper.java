package com.suda.backend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.suda.backend.pojo.Data;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface DataMapper extends BaseMapper<Data> {
}
