package com.suda.backend.service.impl.recipient;
import com.suda.backend.mapper.RecipientMapper;
import com.suda.backend.pojo.Recipient;
import com.suda.backend.service.recipient.AddRecipientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class AddRecipientServiceImpl implements AddRecipientService {

    @Autowired
    private RecipientMapper recipientMapper;

    @Override
    public Map<String, String> add(Map<String, String> data) {
        Map<String, String> map = new HashMap<>();
        if(data.get("email") == null){
            map.put("error_message", "不能为空");
            return map;
        }
        String email= data.get("email");



        Recipient recipient = new Recipient(null,email);
        recipientMapper.insert(recipient);
        map.put("error_message", "success");

        return map;
    }
}
