package com.suda.backend.service.impl.recipient;


import com.suda.backend.mapper.RecipientMapper;
import com.suda.backend.pojo.Recipient;
import com.suda.backend.service.recipient.RemoveRecipientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class RemoveRecipientServiceImpl implements RemoveRecipientService {
    @Autowired
    private RecipientMapper recipientMapper;

    @Override
    public Map<String, String> remove(Map<String, String> data) {
        Map<String, String> map = new HashMap<>();
        if (data.get("id") == null) {
            map.put("error_message", "策略不存在或已被删除");
            return map;
        }

        Integer id = Integer.parseInt(data.get("id"));
        Recipient recipient = recipientMapper.selectById(id);


        if (recipient == null) {
            map.put("error_message", "策略不存在或已被删除");
            return map;
        }



        recipientMapper.deleteById(id);

        map.put("error_message", "success");
        return map;
    }
}
