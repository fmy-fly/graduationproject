package com.suda.backend.controller.defence;

import com.suda.backend.pojo.Defence;
import com.suda.backend.service.defence.GetListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class GetListController {
    @Autowired
    private GetListService getListService;

    @GetMapping("/defence/defence/getlist/")
    public List<Defence> getList() {
        return getListService.getList();
    }
}