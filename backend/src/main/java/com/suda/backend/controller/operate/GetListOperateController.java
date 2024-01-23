package com.suda.backend.controller.operate;



import com.baomidou.mybatisplus.core.metadata.IPage;
import com.suda.backend.pojo.Operate;

import com.suda.backend.service.operate.GetListOperateService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class GetListOperateController {
    @Autowired
    private GetListOperateService getListoperateService;

    @GetMapping("/record/operate/getlist/")
    public IPage<Operate> getList(
            @RequestParam(name = "pageNum", defaultValue = "1") int pageNum,
            @RequestParam(name = "pageSize", defaultValue = "10") int pageSize) {

        return getListoperateService.getList(pageNum, pageSize);
    }

}
