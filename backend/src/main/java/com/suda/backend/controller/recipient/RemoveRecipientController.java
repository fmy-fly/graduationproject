package com.suda.backend.controller.recipient;

import com.suda.backend.service.divdefence.RemoveService;
import com.suda.backend.service.recipient.RemoveRecipientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class RemoveRecipientController {
    @Autowired
    private RemoveRecipientService removeRecipientService;

    @PostMapping("/recipient/remove/")
    public Map<String, String> remove(@RequestParam Map<String, String> data) {
        return removeRecipientService.remove(data);
    }
}
