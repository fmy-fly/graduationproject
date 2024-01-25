package com.suda.backend.controller.button;


import com.suda.backend.pojo.Button;

import com.suda.backend.service.button.GetListButtonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class GetListButtonController {
    @Autowired
    private GetListButtonService getListButtonService;

    @GetMapping("/button/getlist/")
    public List<Button> getList() {
        System.out.println(getListButtonService.getList());
        return getListButtonService.getList();
    }
}
