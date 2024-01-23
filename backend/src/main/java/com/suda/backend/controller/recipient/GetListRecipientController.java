package com.suda.backend.controller.recipient;


import com.suda.backend.pojo.Recipient;

import com.suda.backend.service.recipient.GetListRecipientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class GetListRecipientController {
    @Autowired
    private GetListRecipientService getListRecipientService;

    @GetMapping("/recipient/getlist/")
    public List<Recipient> getList() {
        System.out.println(getListRecipientService.getList());
        return getListRecipientService.getList();
    }
}
