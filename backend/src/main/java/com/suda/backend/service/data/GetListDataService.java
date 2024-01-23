package com.suda.backend.service.data;



import com.baomidou.mybatisplus.core.metadata.IPage;
import com.suda.backend.pojo.Data;

import java.util.List;

public interface GetListDataService {
    IPage<Data> getList(int pageNum, int pageSize);
}
