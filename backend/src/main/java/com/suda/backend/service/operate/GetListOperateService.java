package com.suda.backend.service.operate;



import com.baomidou.mybatisplus.core.metadata.IPage;
import com.suda.backend.pojo.Operate;

import java.util.List;

public interface GetListOperateService {


    IPage<Operate> getList(int pageNum, int pageSize);
}
