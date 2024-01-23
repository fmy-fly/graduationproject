package com.suda.backend.service.impl.operate;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.suda.backend.mapper.OperateMapper;
import com.suda.backend.pojo.Operate;
import com.suda.backend.service.operate.GetListOperateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GetListOperateServiceImpl implements GetListOperateService {
    @Autowired
    private OperateMapper operateMapper;

    @Override
    public IPage<Operate> getList(int pageNum, int pageSize) {
        Page<Operate> page = new Page<>(pageNum,pageSize);
        page.addOrder(OrderItem.desc("id"));
        IPage<Operate> resultPage = operateMapper.selectPage(page, null);
        return resultPage;
    }
}