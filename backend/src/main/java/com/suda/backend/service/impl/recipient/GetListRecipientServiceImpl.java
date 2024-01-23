package com.suda.backend.service.impl.recipient;


import com.suda.backend.mapper.RecipientMapper;
import com.suda.backend.pojo.Recipient;
import com.suda.backend.service.recipient.GetListRecipientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetListRecipientServiceImpl implements GetListRecipientService {
    @Autowired
    private RecipientMapper recipientMapper;

    @Override
    public List<Recipient> getList() {



        return recipientMapper.selectList(null);
    }
}
