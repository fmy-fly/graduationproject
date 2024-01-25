package com.suda.backend.service.impl.email;

import com.suda.backend.pojo.Data;
import com.suda.backend.pojo.Recipient;
import com.suda.backend.service.email.SendMailService;
import com.suda.backend.service.email.SendMailsService;
import com.suda.backend.service.operate.AddOperateService;
import com.suda.backend.service.recipient.GetListRecipientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class SendMailsServiceImpl implements SendMailsService {
    @Autowired
    private SendMailService sendMailService;
    @Autowired
    private GetListRecipientService getListRecipientService;
    @Autowired
    private AddOperateService addOperateService;
    public void sendMails() {
       List<Recipient> address = getListRecipientService.getList();
       for (Recipient recipient:address){
           System.out.println(recipient.getEmail());
           sendMailService.sendMail(recipient.getEmail());
       }
        HashMap<String,String> map = new HashMap<>();
       map.put("type","邮件");
       map.put("description","邮件通知");
       addOperateService.add(map);
    }

}
