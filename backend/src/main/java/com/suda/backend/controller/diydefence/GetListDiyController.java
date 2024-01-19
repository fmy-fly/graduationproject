package com.suda.backend.controller.diydefence;

import com.suda.backend.pojo.Diydefence;
import com.suda.backend.service.divdefence.GetListDiyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class GetListDiyController {
    @Autowired
    private GetListDiyService getListDiyService;

    @GetMapping("/defence/diydefence/getlist/")
    public List<Diydefence> getList() {
        return getListDiyService.getList();
    }
}
