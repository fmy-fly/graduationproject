package com.suda.backend.controller.data;



import com.baomidou.mybatisplus.core.metadata.IPage;
import com.suda.backend.pojo.Data;

import com.suda.backend.service.data.GetListDataService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class GetListDataController {
    @Autowired
    private GetListDataService getListdataService;

    @GetMapping("/record/data/getlist/")
    public IPage<Data> getList(
            @RequestParam(name = "pageNum", defaultValue = "1") int pageNum,
            @RequestParam(name = "pageSize", defaultValue = "10") int pageSize) {

        return getListdataService.getList(pageNum, pageSize);
    }

}
