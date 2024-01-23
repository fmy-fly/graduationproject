package com.suda.backend.controller.recipient;

import com.suda.backend.service.recipient.AddRecipientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class AddRecipientController {
    @Autowired
    private AddRecipientService addRecipientService ;

    @PostMapping("/recipient/add/")
    public Map<String, String> add(@RequestParam Map<String, String> data) {
        return addRecipientService.add(data);
    }
}
