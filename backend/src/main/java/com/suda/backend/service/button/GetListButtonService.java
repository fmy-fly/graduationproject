package com.suda.backend.service.button;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.suda.backend.pojo.Button;
import com.suda.backend.pojo.Data;

import java.util.List;

public interface GetListButtonService {
    List<Button> getList();
}