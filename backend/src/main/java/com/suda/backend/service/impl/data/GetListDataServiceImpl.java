package com.suda.backend.service.impl.data;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.suda.backend.mapper.DataMapper;
import com.suda.backend.pojo.Data;
import com.suda.backend.service.data.GetListDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GetListDataServiceImpl implements GetListDataService {
    @Autowired
    private DataMapper dataMapper;

    @Override
    public IPage<Data> getList(int pageNum, int pageSize) {
        Page<Data> page = new Page<>(pageNum,pageSize);
        page.addOrder(OrderItem.desc("id"));
        IPage<Data> resultPage = dataMapper.selectPage(page, null);
        return resultPage;
    }
}
